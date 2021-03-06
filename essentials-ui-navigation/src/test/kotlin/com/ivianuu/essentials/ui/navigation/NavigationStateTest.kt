/*
 * Copyright 2020 Manuel Wrage
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

package com.ivianuu.essentials.ui.navigation

import com.ivianuu.essentials.logging.*
import com.ivianuu.essentials.test.*
import io.kotest.matchers.*
import io.kotest.matchers.collections.*
import kotlinx.coroutines.*
import org.junit.*

class NavigationStateTest {
  object KeyA : Key<Nothing>
  object KeyB : Key<Nothing>
  object KeyC : Key<Nothing>

  @Test fun testNavigationState() = runCancellingBlockingTest {
    val navigator = NavigatorImpl(
      intentKeyHandler = { _, _ -> false },
      logger = NoopLogger,
      scope = this
    )

    val collector = navigator.state.testCollect(this)

    launch { navigator.push(KeyA) }
    navigator.pop(KeyA)
    launch { navigator.push(KeyB) }
    launch { navigator.replaceTop(KeyC) }
    navigator.popTop()

    collector.values.shouldContainExactly(
      NavigationState(listOf()),
      NavigationState(listOf(KeyA)),
      NavigationState(listOf()),
      NavigationState(listOf(KeyB)),
      NavigationState(listOf(KeyC)),
      NavigationState(listOf())
    )
  }

  object KeyWithResult : Key<String>

  @Test fun testReturnsResultOnPop() = runCancellingBlockingTest {
    val navigator = NavigatorImpl(
      intentKeyHandler = { _, _ -> false },
      logger = NoopLogger,
      scope = this
    )
    val result = async { navigator.push(KeyWithResult) }
    navigator.pop(KeyWithResult, "b")
    result.await() shouldBe "b"
  }

  @Test fun testReturnsNullResultIfNothingSent() = runCancellingBlockingTest {
    val navigator = NavigatorImpl(
      intentKeyHandler = { _, _ -> false },
      logger = NoopLogger,
      scope = this
    )
    val result = async { navigator.push(KeyWithResult) }
    navigator.popTop()
    result.await() shouldBe null
  }
}
