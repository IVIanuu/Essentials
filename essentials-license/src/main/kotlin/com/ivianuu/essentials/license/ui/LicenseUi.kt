package com.ivianuu.essentials.license.ui

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.*
import com.ivianuu.essentials.license.R
import com.ivianuu.essentials.license.data.*
import com.ivianuu.essentials.license.domain.*
import com.ivianuu.essentials.optics.*
import com.ivianuu.essentials.resource.*
import com.ivianuu.essentials.store.*
import com.ivianuu.essentials.ui.material.*
import com.ivianuu.essentials.ui.material.Scaffold
import com.ivianuu.essentials.ui.material.TopAppBar
import com.ivianuu.essentials.ui.navigation.*
import com.ivianuu.essentials.ui.resource.*
import com.ivianuu.injekt.*
import com.ivianuu.injekt.coroutines.*
import com.ivianuu.injekt.scope.*
import kotlinx.coroutines.flow.*

object LicenseKey : Key<Nothing>

@Provide val licenseUi: ModelKeyUi<LicenseKey, LicenseModel> = {
  Scaffold(topBar = { TopAppBar(title = { Text(stringResource(R.string.es_licenses_title)) }) }) {
    ResourceLazyColumnFor(model.projects) { project ->
      Project(
        project = project,
        onClick = { model.openProject(project) }
      )
    }
  }
}

@Composable private fun Project(onClick: () -> Unit, project: Project) {
  ListItem(
    title = { Text(project.project) },
    onClick = onClick
  )
}

@Optics data class LicenseModel(
  val projects: Resource<List<Project>> = Idle,
  val openProject: (Project) -> Unit = {}
)

@Provide fun licenseModel(
  getProjects: GetLicenseProjectsUseCase,
  navigator: Navigator,
  scope: InjektCoroutineScope<KeyUiScope>
): @Scoped<KeyUiScope> StateFlow<LicenseModel> = scope.state(LicenseModel()) {
  flow { emit(getProjects()) }
    .flowResultAsResource()
    .update { copy(projects = it) }

  action(LicenseModel.openProject()) { project ->
    if (project.url != null)
      navigator.push(UrlKey(project.url))
  }
}
