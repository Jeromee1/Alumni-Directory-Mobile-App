package com.apa.alumnidirectory.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Text1 = Color(244, 244, 244)
val Primary = Color(51, 168, 166)

val Secondary = Color(55, 114, 161)
val SecondaryT = Color(55, 114, 161, 155)

val SecondaryG = Brush.linearGradient(
    colors = listOf(
        Secondary,
        SecondaryT
    )
)

val Danger = Color(235, 67, 67)

val Background = Brush.linearGradient(
    colors = listOf(
        Color(29, 48, 89),
        Color(18, 33, 66)
    ),
    start = Offset(0f, 0f),
    end = Offset(0f, Float.POSITIVE_INFINITY)
)

val Background1 = Color(29, 48, 89)

val Background2 = Color(18, 33, 66)

//Contact
val Phone = Color(50, 165, 200, 255)
val Email = Color(185, 80, 80)
val Github = Color(0, 0, 0)
val LinkedIn = Color(44, 116, 176)
val Website = Color(120, 120, 120)