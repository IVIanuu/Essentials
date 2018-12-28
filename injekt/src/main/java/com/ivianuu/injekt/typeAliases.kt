package com.ivianuu.injekt

typealias Definition<T> = DeclarationBuilder.(params: Parameters) -> T

typealias ModuleDefinition = Module.() -> Unit

typealias ParamsDefinition = () -> Parameters