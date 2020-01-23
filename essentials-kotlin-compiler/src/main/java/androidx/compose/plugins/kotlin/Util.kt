/*
 * Copyright 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.compose.plugins.kotlin

import org.jetbrains.kotlin.descriptors.ModuleDescriptor
import org.jetbrains.kotlin.descriptors.SourceElement
import org.jetbrains.kotlin.descriptors.annotations.Annotated
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor
import org.jetbrains.kotlin.descriptors.annotations.Annotations
import org.jetbrains.kotlin.descriptors.findClassAcrossModuleDependencies
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.resolve.constants.ConstantValue
import org.jetbrains.kotlin.resolve.descriptorUtil.annotationClass
import org.jetbrains.kotlin.types.KotlinType
import org.jetbrains.kotlin.types.TypeUtils.NO_EXPECTED_TYPE
import org.jetbrains.kotlin.types.TypeUtils.UNIT_EXPECTED_TYPE
import org.jetbrains.kotlin.types.typeUtil.replaceAnnotations

val ComposableAnnotation = FqName("androidx.compose.Composable")
val PivotalAnnotation = FqName("androidx.compose.Pivotal")
val StableMarkerAnnotation = FqName("androidx.compose.StableMarker")

fun makeComposableAnnotation(module: ModuleDescriptor): AnnotationDescriptor =
    object : AnnotationDescriptor {
        override val type: KotlinType
            get() = module.findClassAcrossModuleDependencies(
                ClassId.topLevel(ComposableAnnotation)
            )!!.defaultType
        override val allValueArguments: Map<Name, ConstantValue<*>> get() = emptyMap()
        override val source: SourceElement get() = SourceElement.NO_SOURCE
        override fun toString() = "[@Composable]"
    }

fun KotlinType.makeComposable(module: ModuleDescriptor): KotlinType {
    if (hasComposableAnnotation()) return this
    val annotation = makeComposableAnnotation(module)
    return replaceAnnotations(Annotations.create(annotations + annotation))
}

fun KotlinType.hasComposableAnnotation(): Boolean =
    !isSpecialType && annotations.findAnnotation(ComposableAnnotation) != null

fun KotlinType.isMarkedStable(): Boolean =
    !isSpecialType && (
                    annotations.hasStableMarker() ||
                    (constructor.declarationDescriptor?.annotations?.hasStableMarker() ?: false))

fun Annotated.hasComposableAnnotation(): Boolean =
    annotations.findAnnotation(ComposableAnnotation) != null
fun Annotated.hasPivotalAnnotation(): Boolean =
    annotations.findAnnotation(PivotalAnnotation) != null

internal val KotlinType.isSpecialType: Boolean get() =
    this === NO_EXPECTED_TYPE || this === UNIT_EXPECTED_TYPE

val AnnotationDescriptor.isComposableAnnotation: Boolean get() = fqName == ComposableAnnotation

fun Annotations.hasStableMarker(): Boolean = any(AnnotationDescriptor::isStableMarker)

fun AnnotationDescriptor.isStableMarker(): Boolean {
    val classDescriptor = annotationClass ?: return false
    return classDescriptor.annotations.hasAnnotation(StableMarkerAnnotation)
}