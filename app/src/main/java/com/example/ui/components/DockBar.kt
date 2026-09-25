package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.data.model.AppItem
import com.example.ui.LauncherScreen
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.LocalHudPreset
import com.example.ui.theme.TextPrimary

@Composable
fun DockBar(
    dockApps: List<AppItem>,
    currentScreen: LauncherScreen,
    onNavigate: (LauncherScreen) -> Unit,
    onLaunchApp: (AppItem) -> Unit,
    onTogglePin: (AppItem) -> Unit,
    onOpenDetails: (AppItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val theme = LocalHudPreset.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            CyberSurface.copy(alpha = 0.92f),
                            CyberSurface.copy(alpha = 0.98f)
                        )
                    ),
                    shape = RoundedCornerShape(24.dp)
                )
                .border(
                    width = 1.dp,
                    color = theme.primary.copy(alpha = 0.35f),
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Dock Favorite Apps (Up to 4)
            dockApps.take(4).forEach { app ->
                AppIconItem(
                    app = app,
                    iconSize = 42.dp,
                    showLabel = false,
                    onLaunch = onLaunchApp,
                    onTogglePin = onTogglePin,
                    onOpenDetails = onOpenDetails
                )
            }

            // All Apps Drawer / Home Toggle Button
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(theme.primary.copy(alpha = 0.15f), shape = CircleShape)
                    .border(1.dp, theme.primary.copy(alpha = 0.6f), shape = CircleShape)
                    .clickable {
                        if (currentScreen == LauncherScreen.DRAWER) {
                            onNavigate(LauncherScreen.HOME)
                        } else {
                            onNavigate(LauncherScreen.DRAWER)
                        }
                    }
                    .testTag("dock_toggle_drawer_button")
            ) {
                Icon(
                    imageVector = if (currentScreen == LauncherScreen.DRAWER) Icons.Default.Home else Icons.Default.Apps,
                    contentDescription = "Toggle Drawer",
                    tint = theme.primary,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}
