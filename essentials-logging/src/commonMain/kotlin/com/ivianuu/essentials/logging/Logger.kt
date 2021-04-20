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

package com.ivianuu.essentials.logging

import com.ivianuu.essentials.*
import com.ivianuu.essentials.logging.Logger.Kind.*
import com.ivianuu.injekt.*

interface Logger {
    val isEnabled: Boolean

    fun log(
        kind: Kind,
        message: String? = null,
        throwable: Throwable? = null,
        tag: String? = null,
    )

    enum class Kind {
        VERBOSE, DEBUG, INFO, WARN, ERROR, WTF
    }
}

inline fun Logger.v(
    tag: String? = null,
    throwable: Throwable? = null,
    message: () -> String? = { null },
) {
    log(VERBOSE, throwable, tag, message)
}

inline fun Logger.d(
    throwable: Throwable? = null,
    tag: String? = null,
    message: () -> String? = { null },
) {
    log(DEBUG, throwable, tag, message)
}

inline fun Logger.i(
    throwable: Throwable? = null,
    tag: String? = null,
    message: () -> String? = { null },
) {
    log(INFO, throwable, tag, message)
}

inline fun Logger.w(
    throwable: Throwable? = null,
    tag: String? = null,
    message: () -> String? = { null },
) {
    log(WARN, throwable, tag, message)
}

inline fun Logger.e(
    throwable: Throwable? = null,
    tag: String? = null,
    message: () -> String? = { null },
) {
    log(ERROR, throwable, tag, message)
}

inline fun Logger.wtf(
    throwable: Throwable? = null,
    tag: String? = null,
    message: () -> String? = { null },
) {
    log(WTF, throwable, tag, message)
}

inline fun Logger.log(
    kind: Logger.Kind,
    throwable: Throwable? = null,
    tag: String? = null,
    message: () -> String? = { null },
) {
    if (isEnabled) log(kind, message(), throwable, tag)
}

inline fun Logger.warn(
    tag: String? = null,
    throwable: Throwable? = null,
    message: () -> String? = { null },
) {
    log(WARN, throwable, tag, message)
}

@Given
@Factory
object NoopLogger : Logger {
    override val isEnabled: Boolean
        get() = false

    override fun log(kind: Logger.Kind, message: String?, throwable: Throwable?, tag: String?) {
    }
}

@Given
@Factory
class PrintingLogger(@Given override val isEnabled: LoggingEnabled) : Logger {
    override fun log(kind: Logger.Kind, message: String?, throwable: Throwable?, tag: String?) {
        println("[${kind.name}] ${tag ?: stackTraceTag} ${render(message, throwable)}")
    }

    private fun render(message: String?, throwable: Throwable?) = buildString {
        append(message.orEmpty())
        if (throwable != null) {
            append(" ")
        }
        append(throwable?.toString().orEmpty())
    }
}

expect val Logger.stackTraceTag: String

typealias LoggingEnabled = Boolean