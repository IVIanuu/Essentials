package com.ivianuu.injekt

/**
 * The [Instance] of an [Declaration]
 */
abstract class Instance<T : Any>(val declaration: Declaration<T>) {

    lateinit var component: Component

    abstract val isCreated: Boolean

    fun get(params: ParamsDefinition?): T {
        if (isCreated) {
            info { "${component.nameString()}Returning existing instance for $declaration" }
        } else {
            info { "${component.nameString()}Created instance for $declaration" }
        }

        return getInternal(params)
    }

    fun create(params: ParamsDefinition?): T {
        return try {
            get(params)
        } catch (e: InjektException) {
            throw e
        } catch (e: Exception) {
            throw InstanceCreationException(
                "${component.nameString()}Could not instantiate $declaration",
                e
            )
        }
    }

    protected abstract fun getInternal(params: ParamsDefinition?): T

}

internal class FactoryInstance<T : Any>(
    declaration: Declaration<T>
) : Instance<T>(declaration) {

    override val isCreated: Boolean
        get() = false

    override fun getInternal(params: ParamsDefinition?) =
        declaration.definition.invoke(params?.invoke() ?: emptyParameters())

}

private object UNINITIALIZED_VALUE

internal class SingleInstance<T : Any>(
    declaration: Declaration<T>
) : Instance<T>(declaration) {

    private var _value: Any? = UNINITIALIZED_VALUE

    private val lock = this

    override val isCreated: Boolean
        get() = _value !== UNINITIALIZED_VALUE

    override fun getInternal(params: ParamsDefinition?): T {
        val _v1 = _value
        if (_v1 !== UNINITIALIZED_VALUE) {
            @Suppress("UNCHECKED_CAST")
            return _v1 as T
        }

        return synchronized(lock) {
            val _v2 = _value
            if (_v2 !== UNINITIALIZED_VALUE) {
                @Suppress("UNCHECKED_CAST") (_v2 as T)
            } else {
                val typedValue = declaration.definition
                    .invoke(params?.invoke() ?: emptyParameters())
                _value = typedValue
                typedValue
            }
        }
    }

}