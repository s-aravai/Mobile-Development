package com.example.myfitness30app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = DarkFitnessGreen,
    secondary = FitnessGreenLight,
    background = DarkFitnessBackground,
    surface = DarkFitnessBackground,
    surfaceVariant = DarkFitnessCard,
    onPrimary = FitnessDark,
    onSecondary = FitnessDark,
    onBackground = androidx.compose.ui.graphics.Color.White,
    onSurface = androidx.compose.ui.graphics.Color.White,
    onSurfaceVariant = androidx.compose.ui.graphics.Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = FitnessGreen,
    secondary = FitnessGreenLight,
    background = FitnessBackground,
    surface = FitnessBackground,
    surfaceVariant = FitnessCard,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    onSecondary = FitnessDark,
    onBackground = FitnessDark,
    onSurface = FitnessDark,
    onSurfaceVariant = FitnessGray
)

@Composable
fun MyFitness30AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}