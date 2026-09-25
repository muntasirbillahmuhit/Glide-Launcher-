package com.example.ui.theme

import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val CyberShapes = Shapes(
    extraSmall = CutCornerShape(topStart = 4.dp, bottomEnd = 4.dp),
    small = CutCornerShape(topStart = 6.dp, bottomEnd = 6.dp),
    medium = CutCornerShape(topStart = 10.dp, bottomEnd = 10.dp),
    large = CutCornerShape(topStart = 16.dp, bottomEnd = 16.dp),
    extraLarge = RoundedCornerShape(20.dp)
)
