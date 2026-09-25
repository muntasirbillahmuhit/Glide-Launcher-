package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppItem
import com.example.ui.LauncherScreen
import com.example.ui.components.AppIconItem
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.LocalHudPreset
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(
    pinnedApps: List<AppItem>,
    onNavigate: (LauncherScreen) -> Unit,
    onLaunchApp: (AppItem) -> Unit,
    onTogglePin: (AppItem) -> Unit,
    onOpenDetails: (AppItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val theme = LocalHudPreset.current
    var currentTime by remember { mutableStateOf("") }
    var currentDate by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val dateFormat = SimpleDateFormat("EEEE, MMMM d", Locale.getDefault())
        while (true) {
            val now = Date()
            currentTime = timeFormat.format(now)
            currentDate = dateFormat.format(now)
            delay(1000L)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(28.dp))

        // Minimalist Clock & Date Header
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = if (currentTime.isNotBlank()) currentTime else "12:00",
                style = MaterialTheme.typography.displayLarge.copy(
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 54.sp,
                    lineHeight = 58.sp
                )
            )
            Text(
                text = if (currentDate.isNotBlank()) currentDate else "Monday, September 25",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = theme.primary,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Clean Search Bar Pill (Opens App Drawer)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(CyberSurface.copy(alpha = 0.85f))
                .border(1.dp, theme.primary.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
                .clickable { onNavigate(LauncherScreen.DRAWER) }
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .testTag("home_search_bar_trigger"),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = theme.primary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "Search apps...",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TextSecondary,
                    fontSize = 13.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Pinned / Favorite Apps Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            contentPadding = PaddingValues(bottom = 90.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth().weight(1f)
        ) {
            items(pinnedApps, key = { it.packageName }) { app ->
                AppIconItem(
                    app = app,
                    onLaunch = onLaunchApp,
                    onTogglePin = onTogglePin,
                    onOpenDetails = onOpenDetails
                )
            }
        }
    }
}
