package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val LocalHudPreset = staticCompositionLocalOf { HudThemePreset.CYAN_MATRIX }

@Composable
fun XRayLauncherTheme(
    themePreset: HudThemePreset = HudThemePreset.CYAN_MATRIX,
    content: @Composable () -> Unit
) {
    val colorScheme = darkColorScheme(
        primary = themePreset.primary,
        onPrimary = Color.Black,
        primaryContainer = themePreset.primaryDim,
        onPrimaryContainer = themePreset.primary,
        secondary = themePreset.secondary,
        onSecondary = Color.Black,
        background = CyberDarkBg,
        onBackground = TextPrimary,
        surface = CyberSurface,
        onSurface = TextPrimary,
        surfaceVariant = CyberSurfaceVariant,
        onSurfaceVariant = TextSecondary,
        outline = themePreset.primary.copy(alpha = 0.5f)
    )

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

    CompositionLocalProvider(LocalHudPreset provides themePreset) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            shapes = CyberShapes,
            content = content
        )
    }
}
