
package com.mystiq.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val AppFont = FontFamily.SansSerif

private val Base = Typography()

val Typography = Typography(

    displayLarge = Base.displayLarge.copy(
        fontFamily = AppFont,
        fontWeight = FontWeight.Light
    ),

    displayMedium = Base.displayMedium.copy(
        fontFamily = AppFont,
        fontWeight = FontWeight.Light,
        fontSize = 44.sp,
        lineHeight = 52.sp
    ),

    displaySmall = Base.displaySmall.copy(
        fontFamily = AppFont,
        fontWeight = FontWeight.Normal
    ),

    headlineLarge = Base.headlineLarge.copy(
        fontFamily = AppFont,
        fontWeight = FontWeight.Light
    ),

    headlineMedium = Base.headlineMedium.copy(
        fontFamily = AppFont,
        fontWeight = FontWeight.Light,
        fontSize = 28.sp
    ),

    headlineSmall = Base.headlineSmall.copy(
        fontFamily = AppFont,
        fontWeight = FontWeight.Normal
    ),

    titleLarge = Base.titleLarge.copy(
        fontFamily = AppFont,
        fontWeight = FontWeight.Light,
        fontSize = 22.sp
    ),

    titleMedium = Base.titleMedium.copy(
        fontFamily = AppFont,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),

    titleSmall = Base.titleSmall.copy(
        fontFamily = AppFont,
        fontWeight = FontWeight.Normal
    ),

    bodyLarge = Base.bodyLarge.copy(
        fontFamily = AppFont,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp
    ),

    bodyMedium = Base.bodyMedium.copy(
        fontFamily = AppFont,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp
    ),

    bodySmall = Base.bodySmall.copy(
        fontFamily = AppFont,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp
    ),

    labelLarge = Base.labelLarge.copy(
        fontFamily = AppFont,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp
    ),

    labelMedium = Base.labelMedium.copy(
        fontFamily = AppFont,
        fontWeight = FontWeight.Normal
    ),

    labelSmall = Base.labelSmall.copy(
        fontFamily = AppFont,
        fontWeight = FontWeight.Light
    )
)



