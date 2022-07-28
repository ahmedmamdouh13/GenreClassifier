package com.am.genreclassifier.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsControllerCompat

private val DarkColorScheme = darkColors(
    primary = PrimaryColorDark,
    surface = TrackItemBackgroundColorDark,
    primaryVariant = BckgrndGradientColorStartDark,
    secondaryVariant = BckgrndGradientColorEndDark,
    onPrimary = TextColorDark,
    onSurface = GenreTextColorDark,
    onBackground = DateTextColorDark
)

private val LightColorScheme = lightColors(
    primary = PrimaryColorLight,
    surface = TrackItemBackgroundColorLight,
    primaryVariant = BckgrndGradientColorStartLight,
    secondaryVariant = BckgrndGradientColorEndLight,
    onPrimary = TextColorLight,
    onSurface = GenreTextColorLight,
    onBackground = DateTextColorLight
)

@Composable
fun GenreClassifierTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) DarkColorScheme else LightColorScheme
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowInsetsControllerCompat(window, view).isAppearanceLightStatusBars = !darkTheme
        }

    }

    MaterialTheme(
        colors = colorScheme,
        typography = Typography,
        content = content,
    )
}