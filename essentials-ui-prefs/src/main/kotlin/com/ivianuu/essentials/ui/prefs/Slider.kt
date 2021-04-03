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

package com.ivianuu.essentials.ui.prefs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ivianuu.essentials.coroutines.EventFlow
import com.ivianuu.essentials.coroutines.raceOf
import com.ivianuu.essentials.ui.material.ListItem
import com.ivianuu.essentials.ui.material.NoStepsStepPolicy
import com.ivianuu.essentials.ui.material.Slider
import com.ivianuu.essentials.ui.material.StepPolicy
import com.ivianuu.essentials.util.toDouble
import com.ivianuu.essentials.util.toDuration
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlin.time.Duration

@Composable
fun DoubleSliderListItem(
    value: Double,
    onValueChange: (Double) -> Unit,
    title: @Composable (() -> Unit)? = null,
    subtitle: @Composable (() -> Unit)? = null,
    leading: @Composable (() -> Unit)? = null,
    valueText: @Composable ((Double) -> Unit)? = null,
    valueRange: ClosedRange<Double> = 0.0..1.0,
    stepPolicy: StepPolicy<Double> = NoStepsStepPolicy,
    modifier: Modifier = Modifier,
) {
    BaseSliderListItem(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        toFloat = { it.toFloat() },
        fromFloat = { it.toDouble() },
        title = title,
        subtitle = subtitle,
        leading = leading,
        valueRange = valueRange,
        stepPolicy = stepPolicy,
        valueText = valueText
    )
}

@Composable
fun FloatSliderListItem(
    value: Float,
    onValueChange: (Float) -> Unit,
    title: @Composable (() -> Unit)? = null,
    subtitle: @Composable (() -> Unit)? = null,
    leading: @Composable (() -> Unit)? = null,
    valueText: @Composable ((Float) -> Unit)? = null,
    valueRange: ClosedRange<Float> = 0f..1f,
    stepPolicy: StepPolicy<Float> = NoStepsStepPolicy,
    modifier: Modifier = Modifier,
) {
    BaseSliderListItem(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        toFloat = { it },
        fromFloat = { it },
        title = title,
        subtitle = subtitle,
        leading = leading,
        valueRange = valueRange,
        stepPolicy = stepPolicy,
        valueText = valueText
    )
}

@Composable
fun IntSliderListItem(
    value: Int,
    onValueChange: (Int) -> Unit,
    title: @Composable (() -> Unit)? = null,
    subtitle: @Composable (() -> Unit)? = null,
    leading: @Composable (() -> Unit)? = null,
    valueText: @Composable ((Int) -> Unit)? = null,
    valueRange: IntRange = 0..100,
    stepPolicy: StepPolicy<Int> = NoStepsStepPolicy,
    modifier: Modifier = Modifier,
) {
    BaseSliderListItem(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        toFloat = { it.toFloat() },
        fromFloat = { it.toInt() },
        title = title,
        subtitle = subtitle,
        leading = leading,
        valueText = valueText,
        valueRange = valueRange,
        stepPolicy = stepPolicy
    )
}

@Composable
fun LongSliderListItem(
    value: Long,
    onValueChange: (Long) -> Unit,
    title: @Composable (() -> Unit)? = null,
    subtitle: @Composable (() -> Unit)? = null,
    leading: @Composable (() -> Unit)? = null,
    valueText: @Composable ((Long) -> Unit)? = null,
    valueRange: LongRange = 0L..100L,
    stepPolicy: StepPolicy<Long> = NoStepsStepPolicy,
    modifier: Modifier = Modifier,
) {
    BaseSliderListItem(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        toFloat = { it.toFloat() },
        fromFloat = { it.toLong() },
        title = title,
        subtitle = subtitle,
        leading = leading,
        valueText = valueText,
        valueRange = valueRange,
        stepPolicy = stepPolicy
    )
}

@Composable
fun DpSliderListItem(
    value: Dp,
    onValueChange: (Dp) -> Unit,
    title: @Composable (() -> Unit)? = null,
    subtitle: @Composable (() -> Unit)? = null,
    leading: @Composable (() -> Unit)? = null,
    valueText: @Composable ((Dp) -> Unit)? = null,
    valueRange: ClosedRange<Dp> = 0.dp..1.dp,
    stepPolicy: StepPolicy<Dp> = NoStepsStepPolicy,
    modifier: Modifier = Modifier,
) {
    BaseSliderListItem(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        toFloat = { it.value },
        fromFloat = { it.dp },
        title = title,
        subtitle = subtitle,
        leading = leading,
        valueText = valueText,
        valueRange = valueRange,
        stepPolicy = stepPolicy
    )
}

@Composable
fun DurationSliderListItem(
    value: Duration,
    onValueChange: (Duration) -> Unit,
    title: @Composable (() -> Unit)? = null,
    subtitle: @Composable (() -> Unit)? = null,
    leading: @Composable (() -> Unit)? = null,
    valueText: @Composable ((Duration) -> Unit)? = null,
    valueRange: ClosedRange<Duration>,
    stepPolicy: StepPolicy<Duration> = NoStepsStepPolicy,
    modifier: Modifier = Modifier,
) {
    BaseSliderListItem(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        toFloat = { it.toDouble().toFloat() },
        fromFloat = { it.toDouble().toDuration() },
        title = title,
        subtitle = subtitle,
        leading = leading,
        valueRange = valueRange,
        stepPolicy = stepPolicy,
        valueText = valueText
    )
}

@Composable
fun <T : Comparable<T>> BaseSliderListItem(
    value: T,
    onValueChange: (T) -> Unit,
    toFloat: (T) -> Float,
    fromFloat: (Float) -> T,
    title: @Composable (() -> Unit)? = null,
    subtitle: @Composable (() -> Unit)? = null,
    leading: @Composable (() -> Unit)? = null,
    valueRange: ClosedRange<T>,
    stepPolicy: StepPolicy<T> = NoStepsStepPolicy,
    valueText: @Composable ((T) -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        ListItem(
            modifier = Modifier.align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            title = title,
            subtitle = subtitle,
            leading = leading,
            onClick = {}
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(
                    start = 12.dp, // make the slider pretty
                    end = 16.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            var internalValue by remember(value) { mutableStateOf(toFloat(value)) }

            val floatRange = remember(toFloat, valueRange) {
                toFloat(valueRange.start)..toFloat(valueRange.endInclusive)
            }

            // workaround to reset the internal value if the value doesn't change
            val internalValueResetNotifier = remember { EventFlow<Unit>() }
            val valueChanges = remember { EventFlow<Unit>() }
            LaunchedEffect(internalValueResetNotifier) {
                internalValueResetNotifier
                    .collectLatest {
                        raceOf(
                            {
                                delay(1000)
                                internalValue = toFloat(value)
                            },
                            {
                                valueChanges.first()
                            }
                        )
                    }
            }

            Slider(
                value = internalValue,
                onValueChange = {
                    internalValue = it
                    valueChanges.tryEmit(Unit)
                },
                onValueChangeEnd = {
                    onValueChange(fromFloat(internalValue))
                    internalValueResetNotifier.tryEmit(Unit)
                },
                valueRange = floatRange,
                stepPolicy = remember(stepPolicy) {
                    { valueRange ->
                        stepPolicy(fromFloat(valueRange.start)..fromFloat(valueRange.endInclusive))
                    }
                },
                modifier = Modifier.weight(1f)
            )

            if (valueText != null) {
                Box(
                    modifier = Modifier.widthIn(min = 72.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    CompositionLocalProvider(
                        LocalTextStyle provides MaterialTheme.typography.body2
                    ) {
                        valueText(fromFloat(internalValue))
                    }
                }
            }
        }
    }
}

@Composable
fun <T> SimpleValueTextProvider(toString: (T) -> String = { it.toString() }): @Composable (T) -> Unit {
    return { Text(toString(it)) }
}

@Composable
fun <T> UnitValueTextProvider(
    unit: UnitValueTextProvider.Unit,
    toString: (T) -> String = { it.toString() }
): @Composable (T) -> Unit {
    val textProvider = UnitValueTextProvider(LocalContext.current, unit)
    return { Text(textProvider(toString(it))) }
}
