/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.analysis.checkers.extended

import org.jetbrains.kotlin.fir.FirFakeSourceElement
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirBasicExpresionChecker
import org.jetbrains.kotlin.fir.analysis.diagnostics.DiagnosticReporter
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors
import org.jetbrains.kotlin.fir.expressions.FirExpression
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall
import org.jetbrains.kotlin.fir.psi

object EmptyRangeChecker : FirBasicExpresionChecker() {
    override fun check(functionCall: FirExpression, context: CheckerContext, reporter: DiagnosticReporter) {
        if (functionCall.source is FirFakeSourceElement<*>) return
        if (functionCall !is FirFunctionCall) return
        val range = functionCall.psi ?: return
        val left = range.children[0].text.toLongOrNull() ?: return
        val right = range.children[2].text.toLongOrNull() ?: return

        val needReport = when (functionCall.calleeReference.name.asString()) {
            "rangeTo" -> {
                left > right
            }
            "downTo" -> {
                right > left
            }
            "until" -> {
                left >= right
            }
            else -> false
        }

        if (needReport) {
            reporter.report(functionCall.source, FirErrors.EMPTY_RANGE)

        }
    }

}