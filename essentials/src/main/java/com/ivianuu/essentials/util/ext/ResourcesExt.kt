/*
 * Copyright 2018 Manuel Wrage
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

package com.ivianuu.essentials.util.ext

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.util.TypedValue
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.ivianuu.conductor.Controller

fun Context.getResAnim(resId: Int) : Animation = AnimationUtils.loadAnimation(this, resId)

fun Context.getResIntArray(resId: Int) : IntArray = resources.getIntArray(resId)

fun Context.getResStringArray(resId: Int) : Array<String> =
    resources.getStringArray(resId)

fun Context.getResTextArray(resId: Int) : Array<CharSequence> =
    resources.getTextArray(resId)

fun Context.getResTypedArray(resId: Int) : TypedArray = resources.obtainTypedArray(resId)

fun Context.getResBool(resId: Int) : Boolean = resources.getBoolean(resId)

fun Context.getResDimen(resId : Int) : Float = resources.getDimension(resId)

fun Context.getResDimenPx(resId : Int) : Int = resources.getDimensionPixelSize(resId)

fun Context.getResDimenPxOffset(resId : Int) : Int = resources.getDimensionPixelOffset(resId)

fun Context.getResFloat(resId: Int) : Float {
    val value = ValueHolder.VALUE
    resources.getValue(resId, value, true)
    return value.float
}

fun Context.getResInt(resId: Int) : Int = resources.getInteger(resId)

fun Context.getResBitmap(resId: Int) : Bitmap = BitmapFactory.decodeResource(resources, resId)

fun Context.getResColor(resId: Int) : Int = ContextCompat.getColor(this, resId)

fun Context.getResColorStateList(resId: Int) : ColorStateList =
    ContextCompat.getColorStateList(this, resId)!!

fun Context.getResDrawable(resId : Int) : Drawable =
    ContextCompat.getDrawable(this, resId)!!

fun Context.getResFont(resId: Int) : Typeface = ResourcesCompat.getFont(this, resId)!!

fun Controller.getResAnim(resId: Int) = requireActivity().getResAnim(resId)

fun Controller.getResIntArray(resId: Int)  = requireActivity().getResIntArray(resId)

fun Controller.getResStringArray(resId: Int) = requireActivity().getResStringArray(resId)

fun Controller.getResTextArray(resId: Int)= requireActivity().getResTextArray(resId)

fun Controller.getResTypedArray(resId: Int) = requireActivity().getResTypedArray(resId)

fun Controller.getResBool(resId: Int) = requireActivity().getResBool(resId)

fun Controller.getResDimen(resId : Int) = requireActivity().getResDimen(resId)

fun Controller.getResDimenPx(resId : Int) = requireActivity().getResDimenPx(resId)

fun Controller.getResDimenPxOffset(resId : Int) = requireActivity().getResDimenPxOffset(resId)

fun Controller.getResFloat(resId: Int) = requireActivity().getResFloat(resId)

fun Controller.getResInt(resId: Int) : Int = requireActivity().getResInt(resId)

fun Controller.getResBitmap(resId: Int) = requireActivity().getResBitmap(resId)

fun Controller.getResColor(resId: Int) = requireActivity().getResColor(resId)

fun Controller.getResColorStateList(resId: Int)= requireActivity().getResColorStateList(resId)

fun Controller.getResDrawable(resId : Int)= requireActivity().getResDrawable(resId)

fun Controller.getResFont(resId: Int) = requireActivity().getResFont(resId)

fun Fragment.getResAnim(resId: Int) = requireActivity().getResAnim(resId)

fun Fragment.getResIntArray(resId: Int)  = requireActivity().getResIntArray(resId)

fun Fragment.getResStringArray(resId: Int) = requireActivity().getResStringArray(resId)

fun Fragment.getResTextArray(resId: Int)= requireActivity().getResTextArray(resId)

fun Fragment.getResTypedArray(resId: Int) = requireActivity().getResTypedArray(resId)

fun Fragment.getResBool(resId: Int) = requireActivity().getResBool(resId)

fun Fragment.getResDimen(resId : Int) = requireActivity().getResDimen(resId)

fun Fragment.getResDimenPx(resId : Int) = requireActivity().getResDimenPx(resId)

fun Fragment.getResDimenPxOffset(resId : Int) = requireActivity().getResDimenPxOffset(resId)

fun Fragment.getResFloat(resId: Int) = requireActivity().getResFloat(resId)

fun Fragment.getResInt(resId: Int) : Int = requireActivity().getResInt(resId)

fun Fragment.getResBitmap(resId: Int) = requireActivity().getResBitmap(resId)

fun Fragment.getResColor(resId: Int) = requireActivity().getResColor(resId)

fun Fragment.getResColorStateList(resId: Int)= requireActivity().getResColorStateList(resId)

fun Fragment.getResDrawable(resId : Int)= requireActivity().getResDrawable(resId)

fun Fragment.getResFont(resId: Int) = requireActivity().getResFont(resId)

fun View.getResAnim(resId: Int) = context.getResAnim(resId)

fun View.getResIntArray(resId: Int)  = context.getResIntArray(resId)

fun View.getResStringArray(resId: Int) = context.getResStringArray(resId)

fun View.getResTextArray(resId: Int)= context.getResTextArray(resId)

fun View.getResTypedArray(resId: Int) = context.getResTypedArray(resId)

fun View.getResBool(resId: Int) = context.getResBool(resId)

fun View.getResDimen(resId : Int) = context.getResDimen(resId)

fun View.getResDimenPx(resId : Int) = context.getResDimenPx(resId)

fun View.getResDimenPxOffset(resId : Int) = context.getResDimenPxOffset(resId)

fun View.getResFloat(resId: Int) = context.getResFloat(resId)

fun View.getResInt(resId: Int) : Int = context.getResInt(resId)

fun View.getResBitmap(resId: Int) = context.getResBitmap(resId)

fun View.getResColor(resId: Int) = context.getResColor(resId)

fun View.getResColorStateList(resId: Int)= context.getResColorStateList(resId)

fun View.getResDrawable(resId : Int)= context.getResDrawable(resId)

fun View.getResFont(resId: Int) = context.getResFont(resId)

private object ValueHolder {
    val VALUE = TypedValue()
}
