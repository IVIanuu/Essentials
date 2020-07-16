package com.ivianuu.essentials.util

import com.ivianuu.injekt.Given
import com.ivianuu.injekt.Reader
import com.ivianuu.injekt.given
import java.util.regex.Pattern

interface Logger {

    fun v(message: String? = null, throwable: Throwable? = null, tag: String? = null)

    fun d(message: String? = null, throwable: Throwable? = null, tag: String? = null)

    fun i(message: String? = null, throwable: Throwable? = null, tag: String? = null)

    fun w(message: String? = null, throwable: Throwable? = null, tag: String? = null)

    fun e(message: String? = null, throwable: Throwable? = null, tag: String? = null)

    fun wtf(message: String? = null, throwable: Throwable? = null, tag: String? = null)

}

// todo swap throwable and tag once we can be sure that no every usage has been migrated

@Reader
inline fun v(throwable: Throwable? = null, tag: String? = null, message: () -> String? = { null }) {
    given<Logger?>()?.v(message(), throwable, tag)
}

@Reader
inline fun d(throwable: Throwable? = null, tag: String? = null, message: () -> String? = { null }) {
    given<Logger?>()?.d(message(), throwable, tag)
}

@Reader
inline fun i(throwable: Throwable? = null, tag: String? = null, message: () -> String? = { null }) {
    given<Logger?>()?.i(message(), throwable, tag)
}

@Reader
inline fun w(throwable: Throwable? = null, tag: String? = null, message: () -> String? = { null }) {
    given<Logger?>()?.w(message(), throwable, tag)
}

@Reader
inline fun e(throwable: Throwable? = null, tag: String? = null, message: () -> String? = { null }) {
    given<Logger?>()?.e(message(), throwable, tag)
}

@Reader
inline fun wtf(
    throwable: Throwable? = null,
    tag: String? = null,
    message: () -> String? = { null }
) {
    given<Logger?>()?.wtf(message(), throwable, tag)
}

@Given
class DefaultLogger : Logger {

    override fun v(message: String?, throwable: Throwable?, tag: String?) {
        println("[VERBOSE] ${tag ?: stackTraceTag} ${render(message, throwable)}")
    }

    override fun d(message: String?, throwable: Throwable?, tag: String?) {
        println("[DEBUG] ${tag ?: stackTraceTag} ${render(message, throwable)}")
    }

    override fun i(message: String?, throwable: Throwable?, tag: String?) {
        println("[INFO] ${tag ?: stackTraceTag} ${render(message, throwable)}")
    }

    override fun w(message: String?, throwable: Throwable?, tag: String?) {
        println("[WARN] ${tag ?: stackTraceTag} ${render(message, throwable)}")
    }

    override fun e(message: String?, throwable: Throwable?, tag: String?) {
        println("[ERROR] ${tag ?: stackTraceTag} ${render(message, throwable)}")
    }

    override fun wtf(message: String?, throwable: Throwable?, tag: String?) {
        println("[WTF] ${tag ?: stackTraceTag} ${render(message, throwable)}")
    }

    private fun render(message: String?, throwable: Throwable?) {
        buildString {
            append(message.orEmpty())
            if (throwable != null) {
                append(" ")
            }
            append(throwable?.toString().orEmpty())
        }
    }

}

val Logger.stackTraceTag: String
    get() = Throwable().stackTrace
        .first {
            it.className != javaClass.canonicalName &&
                    it.className != "com.ivianuu.essentials.util.LoggerKt" &&
                    it.className != "com.ivianuu.essentials.util.Logger\$DefaultImpls"
        }
        .let { createStackElementTag(it) }

private fun createStackElementTag(element: StackTraceElement): String {
    var tag = element.className.substringAfterLast('.')
    val m = ANONYMOUS_CLASS.matcher(tag)
    if (m.find()) {
        tag = m.replaceAll("")
    }
    return tag
}

private val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")
