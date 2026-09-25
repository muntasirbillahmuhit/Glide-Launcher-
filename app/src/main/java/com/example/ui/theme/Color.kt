package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// Futuristic Cyber HUD Color Palette
val CyberDarkBg = Color(0xFF070B14)
val CyberSurface = Color(0xFF0E1726)
val CyberSurfaceVariant = Color(0xFF16243B)
val CyberSurfaceGlow = Color(0x3300F0FF)

val NeonCyan = Color(0xFF00F0FF)
val NeonCyanDim = Color(0x3300F0FF)
val NeonCyanBright = Color(0xFF80F8FF)

val NeonAmber = Color(0xFFFFB300)
val NeonAmberDim = Color(0x33FFB300)

val NeonPurple = Color(0xFFD500F9)
val NeonPurpleDim = Color(0x33D500F9)

val NeonGreen = Color(0xFF00E676)
val NeonGreenDim = Color(0x3300E676)

val NeonRed = Color(0xFFFF1744)
val NeonRedDim = Color(0x33FF1744)

val CyberGridLine = Color(0xFF1A2A44)
val CyberGridScan = Color(0x2200F0FF)

val TextPrimary = Color(0xFFE0F7FA)
val TextSecondary = Color(0xFF90A4AE)
val TextMuted = Color(0xFF546E7A)

enum class HudThemePreset(
    val title: String,
    val primary: Color,
    val primaryDim: Color,
    val secondary: Color,
    val accent: Color
) {
    CYAN_MATRIX("CYAN MATRIX", NeonCyan, NeonCyanDim, Color(0xFF00B0FF), NeonGreen),
    AMBER_HAZARD("AMBER HAZARD", NeonAmber, NeonAmberDim, Color(0xFFFF9100), NeonRed),
    PURPLE_SYNTH("SYNTH PURPLE", NeonPurple, NeonPurpleDim, Color(0xFF7C4DFF), NeonCyan),
    EMERALD_CYBER("EMERALD CYBER", NeonGreen, NeonGreenDim, Color(0xFF64DD17), NeonCyan),
    CRIMSON_THREAT("CRIMSON THREAT", NeonRed, NeonRedDim, Color(0xFFFF5252), NeonAmber)
}
