/*
 * Copyright 2019 Manuel Wrage
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

package com.ivianuu.essentials.ui.dialog

import androidx.ui.graphics.Color

enum class ColorPickerPalette(
    val front: Color,
    val colors: List<Color>
) {
    Red(
        front = Color(0xFFF44336),
        colors = listOf(
            Color(0xFFFFEBEE),
            Color(0xFFFFCDD2),
            Color(0xFFEF9A9A),
            Color(0xFFE57373),
            Color(0xFFEF5350),
            Color(0xFFF44336),
            Color(0xFFE53935),
            Color(0xFFD32F2F),
            Color(0xFFC62828),
            Color(0xFFB71C1C),
            Color(0xFFFF8A80),
            Color(0xFFFF5252),
            Color(0xFFFF1744),
            Color(0xFFD50000)
        )
    ),

    Pink(
        front = Color(0xFFE91E63),
        colors = listOf(
            Color(0xFFE91E63),
            Color(0xFFF8BBD0),
            Color(0xFFF48FB1),
            Color(0xFFF06292),
            Color(0xFFEC407A),
            Color(0xFFE91E63),
            Color(0xFFD81B60),
            Color(0xFFC2185B),
            Color(0xFFAD1457),
            Color(0xFF880E4F),
            Color(0xFFFF80AB),
            Color(0xFFFF4081),
            Color(0xFFF50057),
            Color(0xFFC51162)
        )
    ),

    Purple(
        front = Color(0xFF9C27B0),
        colors = listOf(
            Color(0xFFF3E5F5),
            Color(0xFFE1BEE7),
            Color(0xFFCE93D8),
            Color(0xFFBA68C8),
            Color(0xFFAB47BC),
            Color(0xFF9C27B0),
            Color(0xFF8E24AA),
            Color(0xFF7B1FA2),
            Color(0xFF6A1B9A),
            Color(0xFF4A148C),
            Color(0xFFEA80FC),
            Color(0xFFE040FB),
            Color(0xFFD500F9),
            Color(0xFFAA00FF)
        )
    ),

    DeepPurple(
        front = Color(0xFF673AB7),
        colors = listOf(
            Color(0xFFEDE7F6),
            Color(0xFFD1C4E9),
            Color(0xFFB39DDB),
            Color(0xFF9575CD),
            Color(0xFF7E57C2),
            Color(0xFF673AB7),
            Color(0xFF5E35B1),
            Color(0xFF512DA8),
            Color(0xFF4527A0),
            Color(0xFF311B92),
            Color(0xFFB388FF),
            Color(0xFF7C4DFF),
            Color(0xFF651FFF),
            Color(0xFF6200EA)
        )
    ),

    Indigo(
        front = Color(0xFF3F51B5),
        colors = listOf(
            Color(0xFFE8EAF6),
            Color(0xFFC5CAE9),
            Color(0xFF9FA8DA),
            Color(0xFF7986CB),
            Color(0xFF5C6BC0),
            Color(0xFF3F51B5),
            Color(0xFF3949AB),
            Color(0xFF303F9F),
            Color(0xFF283593),
            Color(0xFF1A237E),
            Color(0xFF8C9EFF),
            Color(0xFF536DFE),
            Color(0xFF3D5AFE),
            Color(0xFF304FFE)
        )
    ),

    Blue(
        front = Color(0xFF2196F3),
        colors = listOf(
            Color(0xFFE3F2FD),
            Color(0xFFBBDEFB),
            Color(0xFF90CAF9),
            Color(0xFF64B5F6),
            Color(0xFF42A5F5),
            Color(0xFF2196F3),
            Color(0xFF1E88E5),
            Color(0xFF1976D2),
            Color(0xFF1565C0),
            Color(0xFF0D47A1),
            Color(0xFF82B1FF),
            Color(0xFF448AFF),
            Color(0xFF2979FF),
            Color(0xFF2962FF)
        )
    ),

    LightBlue(
        front = Color(0xFF03A9F4),
        colors = listOf(
            Color(0xFFE1F5FE),
            Color(0xFFB3E5FC),
            Color(0xFF81D4FA),
            Color(0xFF4FC3F7),
            Color(0xFF29B6F6),
            Color(0xFF03A9F4),
            Color(0xFF039BE5),
            Color(0xFF0288D1),
            Color(0xFF0277BD),
            Color(0xFF01579B),
            Color(0xFF80D8FF),
            Color(0xFF40C4FF),
            Color(0xFF00B0FF),
            Color(
                0xFF0091EA
            )
        )
    ),

    Cyan(
        front = Color(0xFF00BCD4),
        colors = listOf(
            Color(0xFFE0F7FA),
            Color(0xFFB2EBF2),
            Color(0xFF80DEEA),
            Color(0xFF4DD0E1),
            Color(0xFF26C6DA),
            Color(0xFF00BCD4),
            Color(0xFF00ACC1),
            Color(0xFF0097A7),
            Color(0xFF00838F),
            Color(0xFF006064),
            Color(0xFF84FFFF),
            Color(0xFF18FFFF),
            Color(0xFF00E5FF),
            Color(0xFF00B8D4)
        )
    ),

    Teal(
        front = Color(0xFF009688),
        colors = listOf(
            Color(0xFFE0F2F1),
            Color(0xFFB2DFDB),
            Color(0xFF80CBC4),
            Color(0xFF4DB6AC),
            Color(0xFF26A69A),
            Color(0xFF009688),
            Color(0xFF00897B),
            Color(0xFF00796B),
            Color(0xFF00695C),
            Color(0xFF004D40),
            Color(0xFFA7FFEB),
            Color(0xFF64FFDA),
            Color(0xFF1DE9B6),
            Color(0xFF00BFA5)
        )
    ),

    Green(
        front = Color(0xFF4CAF50),
        colors = listOf(
            Color(0xFFE8F5E9),
            Color(0xFFC8E6C9),
            Color(0xFFA5D6A7),
            Color(0xFF81C784),
            Color(0xFF66BB6A),
            Color(0xFF4CAF50),
            Color(0xFF43A047),
            Color(0xFF388E3C),
            Color(0xFF2E7D32),
            Color(0xFF1B5E20),
            Color(0xFFB9F6CA),
            Color(0xFF69F0AE),
            Color(0xFF00E676),
            Color(0xFF00C853)
        )
    ),

    LightGreen(
        front = Color(0xFF8BC34A),
        colors = listOf(
            Color(0xFFF1F8E9),
            Color(0xFFDCEDC8),
            Color(0xFFC5E1A5),
            Color(0xFFAED581),
            Color(0xFF9CCC65),
            Color(0xFF8BC34A),
            Color(0xFF7CB342),
            Color(0xFF689F38),
            Color(0xFF558B2F),
            Color(0xFF33691E),
            Color(0xFFCCFF90),
            Color(0xFFB2FF59),
            Color(0xFF76FF03),
            Color(0xFF64DD17)
        )
    ),

    Lime(
        front = Color(0xFFCDDC39),
        colors = listOf(
            Color(0xFFF9FBE7),
            Color(0xFFF0F4C3),
            Color(0xFFE6EE9C),
            Color(0xFFDCE775),
            Color(0xFFD4E157),
            Color(0xFFCDDC39),
            Color(0xFFC0CA33),
            Color(0xFFAFB42B),
            Color(0xFF9E9D24),
            Color(0xFF827717),
            Color(0xFFF4FF81),
            Color(0xFFEEFF41),
            Color(0xFFC6FF00),
            Color(0xFFAEEA00)
        )
    ),

    Yellow(
        front = Color(0xFFFFEB3B),
        colors = listOf(
            Color(0xFFFFFDE7),
            Color(0xFFFFF9C4),
            Color(0xFFFFF59D),
            Color(0xFFFFF176),
            Color(0xFFFFEE58),
            Color(0xFFFFEB3B),
            Color(0xFFFDD835),
            Color(0xFFFBC02D),
            Color(0xFFF9A825),
            Color(0xFFF57F17),
            Color(0xFFFFFF8D),
            Color(0xFFFFFF00),
            Color(0xFFFFEA00),
            Color(0xFFFFD600)
        )
    ),

    Amber(
        front = Color(0xFFFFC107),
        colors = listOf(
            Color(0xFFFFF8E1),
            Color(0xFFFFECB3),
            Color(0xFFFFE082),
            Color(0xFFFFD54F),
            Color(0xFFFFCA28),
            Color(0xFFFFC107),
            Color(0xFFFFB300),
            Color(0xFFFFA000),
            Color(0xFFFF8F00),
            Color(0xFFFF6F00),
            Color(0xFFFFE57F),
            Color(0xFFFFD740),
            Color(0xFFFFC400),
            Color(0xFFFFAB00)
        )
    ),

    Orange(
        front = Color(0xFFFF9800),
        colors = listOf(
            Color(0xFFFFF3E0),
            Color(0xFFFFE0B2),
            Color(0xFFFFCC80),
            Color(0xFFFFB74D),
            Color(0xFFFFA726),
            Color(0xFFFF9800),
            Color(0xFFFB8C00),
            Color(0xFFF57C00),
            Color(0xFFEF6C00),
            Color(0xFFE65100),
            Color(0xFFFFD180),
            Color(0xFFFFAB40),
            Color(0xFFFF9100),
            Color(0xFFFF6D00)
        )
    ),

    DeepOrange(
        front = Color(0xFFFF5722),
        colors = listOf(
            Color(0xFFFBE9E7),
            Color(0xFFFFCCBC),
            Color(0xFFFFAB91),
            Color(0xFFFF8A65),
            Color(0xFFFF7043),
            Color(0xFFFF5722),
            Color(0xFFF4511E),
            Color(0xFFE64A19),
            Color(0xFFD84315),
            Color(0xFFBF360C),
            Color(0xFFFF6E40),
            Color(0xFFFFAB40),
            Color(0xFFFF3D00),
            Color(0xFFDD2C00)
        )
    ),

    Brown(
        front = Color(0xFF795548),
        colors = listOf(
            Color(0xFFEFEBE9),
            Color(0xFFD7CCC8),
            Color(0xFFBCAAA4),
            Color(0xFFA1887F),
            Color(0xFF8D6E63),
            Color(0xFF795548),
            Color(0xFF6D4C41),
            Color(0xFF5D4037),
            Color(0xFF4E342E),
            Color(0xFF3E2723)
        )
    ),

    Grey(
        front = Color(0xFF9E9E9E),
        colors = listOf(
            Color(0xFFFAFAFA),
            Color(0xFFF5F5F5),
            Color(0xFFEEEEEE),
            Color(0xFFE0E0E0),
            Color(0xFFBDBDBD),
            Color(0xFF9E9E9E),
            Color(0xFF757575),
            Color(0xFF616161),
            Color(0xFF424242),
            Color(0xFF212121)
        )
    ),

    BlueGrey(
        front = Color(0xFF607D8B),
        colors = listOf(
            Color(0xFFECEFF1),
            Color(0xFFCFD8DC),
            Color(0xFFB0BEC5),
            Color(0xFF90A4AE),
            Color(0xFF78909C),
            Color(0xFF607D8B),
            Color(0xFF546E7A),
            Color(0xFF455A64),
            Color(0xFF37474F),
            Color(0xFF263238)
        )
    ),

    Black(
        front = Color(0xFF000000),
        colors = listOf(Color(0xFF000000))
    ),

    White(
        front = Color(0xFFFFFFFF),
        colors = listOf(Color(0xFFFFFFFF))
    );
}