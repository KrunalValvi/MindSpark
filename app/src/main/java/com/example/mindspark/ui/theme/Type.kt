package com.example.mindspark.ui.theme

import androidx.compose.material3.Typography // Make sure to import material3.Typography
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mindspark.R

// Custom font classes for Jost and Mulish
@Stable
class JostTypography internal constructor(
    val regular: TextStyle,
    val semiBold: TextStyle,
    val bold: TextStyle,
    val extraBold: TextStyle
)

@Stable
class MulishTypography internal constructor(
    val regular: TextStyle,
    val semiBold: TextStyle,
    val bold: TextStyle,
    val extraBold: TextStyle
)

@Stable
class CustomTypography internal constructor(
    val jost: JostTypography,
    val mulish: MulishTypography
)

// Define font families
private val JostFontFamily = FontFamily(
    Font(R.font.jost_regular, FontWeight.Normal),
    Font(R.font.jost_semibold, FontWeight.SemiBold),
    Font(R.font.jost_bold, FontWeight.Bold),
    Font(R.font.jost_extrabold, FontWeight.ExtraBold)
)

private val MulishFontFamily = FontFamily(
    Font(R.font.mulish_regular, FontWeight.Normal),
    Font(R.font.mulish_semibold, FontWeight.SemiBold),
    Font(R.font.mulish_bold, FontWeight.Bold),
    Font(R.font.mulish_extrabold, FontWeight.ExtraBold)
)

// Create the custom typography instance
val CustomThemeTypography = CustomTypography(
    jost = JostTypography(
        regular = TextStyle(
            fontFamily = JostFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        semiBold = TextStyle(
            fontFamily = JostFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        bold = TextStyle(
            fontFamily = JostFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        extraBold = TextStyle(
            fontFamily = JostFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
            lineHeight = 24.sp
        )
    ),
    mulish = MulishTypography(
        regular = TextStyle(
            fontFamily = MulishFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        semiBold = TextStyle(
            fontFamily = MulishFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        bold = TextStyle(
            fontFamily = MulishFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        extraBold = TextStyle(
            fontFamily = MulishFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
            lineHeight = 24.sp
        )
    )
)

// Material3 Typography
val AppTypography = Typography(
    // Define default material3 typography using Mulish as default font
    bodyLarge = TextStyle(
        fontFamily = MulishFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = JostFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = MulishFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    // Add other text styles as needed
)