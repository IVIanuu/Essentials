package com.ivianuu.essentials.sample.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.*
import com.ivianuu.essentials.sample.R
import com.ivianuu.essentials.ui.*
import com.ivianuu.essentials.ui.animation.transition.*
import com.ivianuu.essentials.ui.core.*
import com.ivianuu.essentials.ui.material.Scaffold
import com.ivianuu.essentials.ui.material.TopAppBar
import com.ivianuu.essentials.ui.navigation.*
import com.ivianuu.injekt.*
import com.ivianuu.injekt.scope.*

data class ContainerTransformDetailsKey(val closedKey: Any) : Key<Nothing>

@Provide val containerTransformDetailsUi: KeyUi<ContainerTransformDetailsKey> = {
  var listInfo by scoped("list_state", LocalScope.current) {
    mutableStateOf(0 to 0)
  }
  ContainerTransformSurface(key = "opened", elevation = 8.dp, isOpened = false) {
    Scaffold(topBar = { TopAppBar(title = { Text("Details") }) }) {
      val listState = rememberLazyListState(listInfo.first, listInfo.second)
      listInfo = listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset
      LazyColumn(state = listState, contentPadding = localVerticalInsetsPadding()) {
        item {
          Box(
            modifier = Modifier.fillMaxWidth()
              .height(250.dp)
              .background(Color.Black.copy(alpha = 0.38f)),
            contentAlignment = Alignment.Center
          ) {
            Image(
              modifier = Modifier.padding(70.dp),
              painter = painterResource(R.drawable.placeholder_image),
              contentDescription = null
            )
          }
        }
        item {
          Text(
            modifier = Modifier.padding(
              start = 16.dp,
              end = 16.dp,
              top = 16.dp
            ),
            text = "Title",
            style = MaterialTheme.typography.h4
          )
        }
        item {
          CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
              modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp
              ),
              text = Strings.LoremIpsumChain,
              style = MaterialTheme.typography.body2
            )
          }
        }
      }
    }
  }
}

@Provide
val containerTransformDetailsOptionsFactory: KeyUiOptionsFactory<ContainerTransformDetailsKey> = {
  KeyUiOptions(ContainerTransformStackTransition(it.closedKey, "opened"))
}
