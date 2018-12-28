package com.ivianuu.injekt

import com.ivianuu.injekt.Declaration.Kind
import kotlin.reflect.KClass

/**
 * A module provides the actual dependencies
 */
class Module internal constructor(
    val createOnStart: Boolean,
    val override: Boolean
) {
    internal val declarations = mutableListOf<Declaration<*>>()
    internal val declarationsByName = mutableMapOf<String, Declaration<*>>()
    internal val declarationsByType = mutableMapOf<KClass<*>, Declaration<*>>()

    /**
     * Adds the [declaration]
     */
    fun <T : Any> declare(
        declaration: Declaration<T>
    ): Declaration<T> {
        val createOnStart = if (createOnStart) createOnStart else declaration.options.createOnStart
        val override = if (override) override else declaration.options.override

        declaration.options.createOnStart = createOnStart
        declaration.options.override = override

        declarations.add(declaration)

        if (declaration.name != null) {
            declarationsByName[declaration.name]
        } else {
            declarationsByType[declaration.primaryType]
        }

        return declaration
    }

}

/**
 * Defines a [Module]
 */
fun module(
    createOnStart: Boolean = false,
    override: Boolean = false,
    body: Module.() -> Unit
) = Module(createOnStart, override).apply(body)

/**
 * Provides a dependency
 */
inline fun <reified T : Any> Module.factory(
    name: String? = null,
    override: Boolean = false,
    noinline definition: Definition<T>
) = factory(type = T::class, name = name, override = override, definition = definition)

/**
 * Provides a dependency
 */
fun <T : Any> Module.factory(
    type: KClass<T>,
    name: String? = null,
    override: Boolean = false,
    definition: Definition<T>
) = declare(
    type = type,
    kind = Kind.FACTORY,
    name = name,
    createOnStart = false,
    override = override,
    definition = definition
)

/**
 * Provides a singleton dependency
 */
inline fun <reified T : Any> Module.single(
    name: String? = null,
    override: Boolean = false,
    createOnStart: Boolean = false,
    noinline definition: Definition<T>
) = single(
    type = T::class,
    name = name,
    override = override,
    createOnStart = createOnStart,
    definition = definition
)

/**
 * Provides a singleton dependency
 */
fun <T : Any> Module.single(
    type: KClass<T>,
    name: String? = null,
    override: Boolean = false,
    createOnStart: Boolean = false,
    definition: Definition<T>
) = declare(
    type = type,
    kind = Kind.SINGLE,
    name = name,
    override = override,
    createOnStart = createOnStart,
    definition = definition
)

/**
 * Adds a [Declaration] for the provided params
 */
inline fun <reified T : Any> Module.declare(
    kind: Kind,
    name: String? = null,
    override: Boolean = false,
    createOnStart: Boolean = false,
    noinline definition: Definition<T>
) = declare(
    type = T::class,
    kind = kind,
    name = name,
    override = override,
    createOnStart = createOnStart,
    definition = definition
)

/**
 * Adds a [Declaration] for the provided params
 */
fun <T : Any> Module.declare(
    type: KClass<T>,
    kind: Kind,
    name: String? = null,
    override: Boolean = false,
    createOnStart: Boolean = false,
    definition: Definition<T>
) = declare(
    Declaration.create(type, name, kind, definition).also {
        it.options.createOnStart = createOnStart
        it.options.override = override
    }
)

/**
 * Adds a binding for [type] and [name] to [to] to a previously added [Declaration]
 */
inline fun <reified T : S, reified S : Any> Module.bind(name: String? = null) =
    bind(T::class, S::class, name)

/**
 * Adds a binding for [type] and [name] to [to] to a previously added [Declaration]
 */
fun <T : S, S : Any> Module.bind(
    type: KClass<T>,
    to: KClass<S>,
    name: String? = null
) {
    val declaration = declarations.firstOrNull { it.classes.contains(type) && name == name }
        ?: throw IllegalArgumentException("no declaration found for $type")

    declaration.bind(to)
}

/**
 * Adds a binding for [type] and [name] to [to] to a previously added [Declaration]
 */
inline fun <reified T : Any, reified S : Any> Module.bindIntoSet(
    declarationName: String? = null,
    setBinding: SetBinding<S>
) = bindIntoSet(T::class, declarationName, setBinding)

/**
 * Adds a binding for [declarationType] and [declarationName] to [to] to a previously added [Declaration]
 */
fun <T : Any> Module.bindIntoSet(
    declarationType: KClass<*>,
    declarationName: String? = null,
    setBinding: SetBinding<T>
) {
    getDeclaration(declarationType, declarationName).intoSet(setBinding)
}

inline fun <reified T : Any, reified S : Any> Module.bindIntoSet(
    declarationName: String? = null,
    setName: String? = null
) = getDeclaration(S::class, declarationName).intoSet(setBinding(T::class, setName))

@PublishedApi
internal fun Module.getDeclaration(type: KClass<*>, name: String?): Declaration<*> {
    return if (name != null) {
        declarationsByName[name]
    } else {
        declarationsByType[type]
    } ?: throw IllegalArgumentException("no declaration found for type: $type name: $name")
}

class DeclarationBuilder(val component: Component)

/** Calls trough [Component.get] */
inline fun <reified T : Any> DeclarationBuilder.get(
    name: String? = null,
    noinline params: ParamsDefinition? = null
) = get(T::class, name, params)

/** Calls trough [Component.get] */
fun <T : Any> DeclarationBuilder.get(
    type: KClass<T>,
    name: String? = null,
    params: ParamsDefinition? = null
) = component.get(type, name, params)