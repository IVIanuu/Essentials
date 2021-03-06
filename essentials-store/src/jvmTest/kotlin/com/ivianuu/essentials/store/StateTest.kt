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

package com.ivianuu.essentials.store

import com.ivianuu.essentials.coroutines.*
import com.ivianuu.essentials.test.*
import io.kotest.matchers.*
import io.kotest.matchers.collections.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.*

class StateTest {
  @Test fun testUpdate() = runCancellingBlockingTest {
    val state = state(0) {
      update { inc() }
    }
    state.testCollect(this)
      .values
      .shouldContainExactly(1)
  }

  @Test fun testEmitsInitialState() = runCancellingBlockingTest {
    val state = state(0) {}
    state.testCollect(this)
      .values
      .shouldContainExactly(0)
  }

  @Test fun testFlowUpdate() = runCancellingBlockingTest {
    val actions = EventFlow<Int>()
    val state = state(0) {
      actions
        .update { it }
    }
    val collector = state.testCollect(this)

    actions.emit(1)
    actions.emit(0)

    collector
      .values
      .shouldContainExactly(0, 1, 0)
  }

  @Test fun testCancelsStateScope() = runCancellingBlockingTest {
    val actions = EventFlow<Unit>()
    val collector = TestCollector<Unit>()
    val stateScope = TestCoroutineScope(childJob())
    stateScope.state(0) {
      actions
        .onEach { collector.emit(it) }
        .launchIn(this)
    }
    stateScope.runCurrent()

    actions.emit(Unit)
    actions.emit(Unit)
    stateScope.cancel()
    actions.emit(Unit)

    collector.values.size shouldBe 2
  }
}
