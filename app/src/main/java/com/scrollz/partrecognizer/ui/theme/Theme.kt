package com.scrollz.partrecognizer.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val darkColorScheme = darkColorScheme(
    background = DarkGrayD,
    onBackground = White,

    surface = GrayD,
    inverseSurface = GrayD.copy(alpha = 0.9f),
    onSurface = White,

    surfaceVariant = TP60GrayD,
    onSurfaceVariant = White,

    primary = Blue,
    onPrimary = White,

    secondary = LightGrayD,
    onSecondary = BrightGrayD,

    scrim = TP70DarkGrayD,
    outline = Black,
    error = Red
)

private val lightColorScheme = lightColorScheme(
    background = BrightGrayL,
    onBackground = Black,

    surface = White,
    inverseSurface = White.copy(alpha = 0.9f),
    onSurface = Black,

    surfaceVariant = TP60WhiteL,
    onSurfaceVariant = Black,

    primary = Blue,
    onPrimary = White,

    secondary = GrayL,
    onSecondary = DarkGrayL,

    scrim = TP70BrightGrayL,
    outline = LightGrayL,
    error = Red
)

@Composable
fun PartRecognizerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun ScannerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
