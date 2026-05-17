package com.moreai.hebrew.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val DarkBackground = Color(0xFF232323)
val SurfaceLight = Color(0xFFF8F8FF)
val BlueAccent = Color(0xFF2499FF)
val GhostWhite = Color(0xFFF8F8FF)
val Black = Color(0xFF232323)

private val DarkColorScheme = darkColorScheme(
    primary = BlueAccent,
    background = DarkBackground,
    surface = DarkBackground,
    onBackground = GhostWhite,
    onSurface = GhostWhite,
    onPrimary = GhostWhite,
)

@Composable
fun MoreAITheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content
    )
}
