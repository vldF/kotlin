/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.tools.projectWizard.settings.buildsystem

import org.jetbrains.kotlin.tools.projectWizard.core.*
import org.jetbrains.kotlin.tools.projectWizard.ir.buildsystem.BuildSystemIR
import org.jetbrains.kotlin.tools.projectWizard.ir.buildsystem.DependencyType
import org.jetbrains.kotlin.tools.projectWizard.ir.buildsystem.GradleRootProjectDependencyIR
import org.jetbrains.kotlin.tools.projectWizard.ir.buildsystem.ModuleDependencyIR
import org.jetbrains.kotlin.tools.projectWizard.ir.buildsystem.gradle.irsList
import org.jetbrains.kotlin.tools.projectWizard.moduleConfigurators.*
import org.jetbrains.kotlin.tools.projectWizard.plugins.buildSystem.isGradle
import org.jetbrains.kotlin.tools.projectWizard.plugins.kotlin.ModulesToIrConversionData
import java.nio.file.Path
import kotlin.reflect.KClass

sealed class ModuleDependencyType(
    val from: KClass<out ModuleConfigurator>,
    val to: KClass<out ModuleConfigurator>
) {
    fun accepts(from: Module, to: Module) =
        this.from.isInstance(from.configurator)
                && this.to.isInstance(to.configurator)
                && additionalAcceptanceChecker(from, to)

    open fun additionalAcceptanceChecker(from: Module, to: Module) = true

    open fun createDependencyIrs(from: Module, to: Module, data: ModulesToIrConversionData): List<BuildSystemIR> {
        val path = to.path
        val modulePomIr = data.pomIr.copy(artifactId = to.name)
        return when {
            data.isSingleRootModuleMode
                    && to.path.parts.singleOrNull() == data.rootModules.single().name
                    && data.buildSystemType.isGradle -> GradleRootProjectDependencyIR(DependencyType.MAIN)
            else -> ModuleDependencyIR(
                path.considerSingleRootModuleMode(data.isSingleRootModuleMode),
                modulePomIr,
                DependencyType.MAIN
            )
        }.asSingletonList()
    }

    open fun SettingsWriter.runArbitraryTask(
        from: Module,
        to: Module,
        toModulePath: Path,
        data: ModulesToIrConversionData
    ): TaskResult<Unit> =
        UNIT_SUCCESS

    open fun Reader.createToIRs(from: Module, to: Module, data: ModulesToIrConversionData): TaskResult<List<BuildSystemIR>> =
        Success(emptyList())

    object JVMSinglePlatformToJVMSinglePlatform : ModuleDependencyType(
        from = JvmSinglePlatformModuleConfigurator::class,
        to = JvmSinglePlatformModuleConfigurator::class
    )

    object AndroidSinglePlatformToMPP : ModuleDependencyType(
        from = AndroidSinglePlatformModuleConfigurator::class,
        to = MppModuleConfigurator::class
    )

    object IOSToMppSinglePlatformToMPP : ModuleDependencyType(
        from = IOSSinglePlatformModuleConfigurator::class,
        to = MppModuleConfigurator::class
    ) {
        override fun createDependencyIrs(from: Module, to: Module, data: ModulesToIrConversionData): List<BuildSystemIR> =
            emptyList()

        override fun SettingsWriter.runArbitraryTask(
            from: Module,
            to: Module,
            toModulePath: Path,
            data: ModulesToIrConversionData
        ): TaskResult<Unit> = withSettingsOf(from) {
            IOSSinglePlatformModuleConfigurator.dependentModule.reference
                .setValue(IOSSinglePlatformModuleConfigurator.DependentModuleReference(to))
            locateDummyFile(to.iosTargetSafe()!!.name, to.configurator.kotlinDirectoryName, toModulePath)
        }

        override fun additionalAcceptanceChecker(from: Module, to: Module): Boolean =
            to.iosTargetSafe() != null

        private fun Module.iosTargetSafe(): Module? = subModules.firstOrNull { module ->
            module.configurator.safeAs<NativeTargetConfigurator>()?.isIosTarget == true
        }

        @OptIn(ExperimentalStdlibApi::class)
        override fun Reader.createToIRs(from: Module, to: Module, data: ModulesToIrConversionData): TaskResult<List<BuildSystemIR>> {
            val iosTargetName = to.iosTargetSafe()?.name
                ?: return Failure(InvalidModuleDependencyError(from, to, "Module ${to.name} should contain at least one iOS target"))


            return irsList {
                +packForXcode(iosTargetName)
                addRaw { +"""tasks.getByName(${"build".quotified}).dependsOn(packForXcode)""" }
                import("org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget")

            }.asSuccess()
        }
    }

    companion object {
        private val ALL = listOf(
            JVMSinglePlatformToJVMSinglePlatform,
            AndroidSinglePlatformToMPP,
            IOSToMppSinglePlatformToMPP
        )

        fun getPossibleDependencyType(from: Module, to: Module): ModuleDependencyType? =
            ALL.firstOrNull { it.accepts(from, to) }

        fun isDependencyPossible(from: Module, to: Module): Boolean =
            getPossibleDependencyType(from, to) != null
    }
}