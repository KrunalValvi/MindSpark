package com.example.mindspark.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6200EE),
    secondary = Color(0xFF03DAC5),
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFFFFFFF),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFBB86FC),
    secondary = Color(0xFF03DAC5),
    background = Color(0xFF121212),
    surface = Color(0xFF121212),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
)

// Add these lines to create a CompositionLocal for custom typography
private val LocalCustomTypography = staticCompositionLocalOf(
    { CustomThemeTypography }
)

// Add this extension property to access custom typography through MaterialTheme
val MaterialTheme.customTypography: CustomTypography
    @Composable
    @ReadOnlyComposable
    get() = LocalCustomTypography.current


//old Code
//@Composable
//fun MindSparkTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
//    dynamicColor: Boolean = true,
//    content: @Composable () -> Unit
//) {
//    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//        darkTheme -> DarkColorScheme
//        else -> LightColorScheme
//    }
//
//    CompositionLocalProvider(
//        LocalCustomTypography provides CustomThemeTypography
//    ) {
//        MaterialTheme(
//            colorScheme = colorScheme,
//            typography = AppTypography, // Use AppTypography instead of Typography
//            content = content
//        )
//    }
//}

//@Composable
//fun MindSparkTheme(
//    darkTheme: Boolean = false,
//    content: @Composable () -> Unit
//) {
//    val colors = if (darkTheme) {
//        DarkColorScheme
//    } else {
//        LightColorScheme
//    }
//
//    MaterialTheme(
//        colorScheme = colors,
//        typography = AppTypography,
//        content = content
//    )
//}