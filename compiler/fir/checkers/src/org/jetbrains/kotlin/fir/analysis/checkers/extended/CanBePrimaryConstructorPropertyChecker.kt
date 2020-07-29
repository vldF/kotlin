/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.analysis.checkers.extended

import org.jetbrains.kotlin.descriptors.ClassKind
import org.jetbrains.kotlin.descriptors.Modality
import org.jetbrains.kotlin.descriptors.Visibilities
import org.jetbrains.kotlin.fir.FirElement
import org.jetbrains.kotlin.fir.FirFakeSourceElement
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirMemberDeclarationChecker
import org.jetbrains.kotlin.fir.analysis.checkers.findClosestClassOrObject
import org.jetbrains.kotlin.fir.analysis.diagnostics.DiagnosticReporter
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors.CAN_BE_PRIMARY_CONSTRUCTOR_PROPERTY
import org.jetbrains.kotlin.fir.declarations.*
import org.jetbrains.kotlin.fir.expressions.*
import org.jetbrains.kotlin.fir.psi
import org.jetbrains.kotlin.fir.references.FirErrorNamedReference
import org.jetbrains.kotlin.fir.references.FirNamedReference
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol
import org.jetbrains.kotlin.fir.types.ConeClassErrorType
import org.jetbrains.kotlin.fir.types.coneType
import org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
import org.jetbrains.kotlin.psi.KtPrimaryConstructor

object CanBePrimaryConstructorPropertyChecker : FirMemberDeclarationChecker() {
    override fun check(property: FirMemberDeclaration, context: CheckerContext, reporter: DiagnosticReporter) {
        if (property !is FirProperty) return
        if (property.psi?.parent?.parent is KtPrimaryConstructor) return
        if (property.isLocal) return
        if (
            property.getter.isExist
            || property.setter.isExist
            || property.delegate.isExist
        ) return

        val assigned = property.initializer as? FirQualifiedAccessExpression ?: return
        if (assigned.calleeReference is FirErrorNamedReference) return
        val assignedName = (assigned.calleeReference as? FirNamedReference)?.name?.asString() ?: return

        val propertyType = property.returnTypeRef.coneType
        if (propertyType is ConeClassErrorType) return
        if (propertyType != assigned.typeRef.coneType) return

        val nameIdentifier = property.name.asString()
        if (nameIdentifier != assignedName) return

        val containingClassOrObject = context.findClosestClassOrObject()
        if (containingClassOrObject?.classKind == ClassKind.INTERFACE) return
        if ((containingClassOrObject as? FirRegularClass)?.status?.isData == true) return

        val containingConstructorParams = containingClassOrObject?.getPrimaryConstructorIfAny()?.valueParameters ?: return
        val assignedVariableSymbol =
            (assigned.calleeReference as? FirResolvedNamedReference)?.resolvedSymbol as? FirVariableSymbol ?: return
        if (!containingConstructorParams.any { it.symbol === assignedVariableSymbol }) return
        if (property.modality == Modality.OPEN
            && containingClassOrObject is FirRegularClass
            && (containingClassOrObject.modality != Modality.FINAL)
            && assignedVariableSymbol.isUsedInClassInit(containingClassOrObject)
        ) return

        reporter.report(property.source, CAN_BE_PRIMARY_CONSTRUCTOR_PROPERTY)
    }

    private val FirPropertyAccessor?.isExist
        get() = !(this == null || source is FirFakeSourceElement<*> && visibility == Visibilities.DEFAULT_VISIBILITY)

    private val FirExpression?.isExist
        get() = this != null && source !is FirFakeSourceElement<*>


    private fun FirVariableSymbol<*>.isUsedInClassInit(containingClass: FirClass<*>): Boolean {
        val init = containingClass.declarations.firstOrNull { it is FirAnonymousInitializer } as? FirAnonymousInitializer ?: return false
        val visitor = PropertyVisitor(this)
        visitor.visitAnonymousInitializer(init)
        return visitor.isUsed
    }

    private class PropertyVisitor(private val property: FirVariableSymbol<*>) : FirVisitorVoid() {
        var isUsed = false
        override fun visitElement(element: FirElement) {
            if (element is FirProperty && element.symbol == property) {
                isUsed = true
            }
        }

        override fun visitAnonymousInitializer(anonymousInitializer: FirAnonymousInitializer) {
            visitBlock(anonymousInitializer.body ?: return)
        }

        override fun visitBlock(block: FirBlock) {
            for (s in block.statements) {
                visitStatement(s)
            }
        }

        override fun visitStatement(statement: FirStatement) {
            when (statement) {
                is FirCall -> {
                    for (arg in statement.argumentList.arguments) {
                        visitStatement(arg)
                    }
                }
                is FirQualifiedAccessExpression -> visitQualifiedAccessExpression(statement)
            }
        }

        override fun visitQualifiedAccessExpression(qualifiedAccessExpression: FirQualifiedAccessExpression) {
            val symbol = (qualifiedAccessExpression.calleeReference as? FirResolvedNamedReference)?.resolvedSymbol as? FirVariableSymbol
            visitVariable(symbol?.fir as? FirVariable<*> ?: return)
            if (symbol == property) {
                isUsed = true
            }
        }
    }
}