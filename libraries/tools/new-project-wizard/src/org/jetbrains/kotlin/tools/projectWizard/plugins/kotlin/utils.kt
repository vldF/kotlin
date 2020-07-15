/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.tools.projectWizard.plugins.kotlin

import org.jetbrains.kotlin.tools.projectWizard.core.Reader
import org.jetbrains.kotlin.tools.projectWizard.core.andThen
import org.jetbrains.kotlin.tools.projectWizard.core.div
import org.jetbrains.kotlin.tools.projectWizard.core.service.FileSystemWizardService
import org.jetbrains.kotlin.tools.projectWizard.moduleConfigurators.ModuleConfigurator
import java.nio.file.Path

fun Reader.createKotlinAndResourceDirectories(moduleConfigurator: ModuleConfigurator, path: Path) =
    with(service<FileSystemWizardService>()) {
        createDirectory(path / moduleConfigurator.kotlinDirectoryName) andThen
                createDirectory(path / moduleConfigurator.resourcesDirectoryName)
    }