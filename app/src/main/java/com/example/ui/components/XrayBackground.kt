package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.R
import com.example.ui.theme.CyberDarkBg

@Composable
fun XrayBackground(
    modifier: Modifier = Modifier,
    showGrid: Boolean = true,
    showParticles: Boolean = false
) {
    Box(modifier = modifier.fillMaxSize().background(CyberDarkBg)) {
        Image(
            painter = painterResource(id = R.drawable.wallpaper_main),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().alpha(0.18f)
        )
    }
}
