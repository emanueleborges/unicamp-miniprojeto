package com.example.miniprojeto.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val SensorMonitorColorScheme = darkColorScheme(
    primary = ColorPrimary,
    onPrimary = ColorText,
    primaryContainer = ColorPrimaryDark,
    onPrimaryContainer = ColorAccent,
    secondary = ColorAccelerometer,
    onSecondary = ColorText,
    secondaryContainer = ColorAccelerometerDark,
    onSecondaryContainer = ColorAccelerometer,
    tertiary = ColorHistory,
    onTertiary = ColorText,
    tertiaryContainer = ColorHistoryDark,
    onTertiaryContainer = ColorHistory,
    error = ColorDanger,
    onError = ColorText,
    surface = ColorSurface,
    onSurface = ColorText,
    surfaceVariant = ColorCard,
    onSurfaceVariant = ColorTextSecondary,
    outline = ColorCardBorder,
    outlineVariant = ColorNavBarBorder,
    background = ColorBackground,
    onBackground = ColorText,
)

@Composable
fun MiniProjetoTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = SensorMonitorColorScheme,
        typography = Typography,
        content = content
    )
}