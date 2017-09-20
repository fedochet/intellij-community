/*
 * Copyright 2000-2017 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.profile.codeInspection

import com.intellij.codeInspection.ex.InspectionProfileImpl
import com.intellij.codeInspection.ex.InspectionToolRegistrar
import com.intellij.openapi.externalSystem.model.project.ConfigurationData
import com.intellij.openapi.externalSystem.service.project.IdeModifiableModelsProvider
import com.intellij.openapi.externalSystem.service.project.manage.ConfigurationHandler
import com.intellij.openapi.project.Project

/**
 * Created by Nikita.Skvortsov
 * date: 19.09.2017.
 */
class InspectionsProfileConfigurationHandler: ConfigurationHandler {
  override fun apply(project: Project, modelsProvider: IdeModifiableModelsProvider, configuration: ConfigurationData) {
    val inspectionsSettings: Map<String, *> = configuration.find("inspections") as? Map<String,*> ?: return

    val gradleProfileName = "Gradle Imported"
    val profileManager = ProjectInspectionProfileManager.getInstance(project)
    val importedProfile = InspectionProfileImpl(gradleProfileName, InspectionToolRegistrar.getInstance(), profileManager)

    importedProfile.copyFrom(profileManager.getProfile(com.intellij.codeInspection.ex.DEFAULT_PROFILE_NAME))
    importedProfile.initInspectionTools(project)
    val modifiableModel = importedProfile.modifiableModel
    modifiableModel.name = gradleProfileName

    modifiableModel.commit()
    profileManager.addProfile(importedProfile)
    profileManager.setRootProfile(gradleProfileName)
  }
}