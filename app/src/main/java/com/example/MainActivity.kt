package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.ui.LauncherScreen
import com.example.ui.LauncherViewModel
import com.example.ui.components.DockBar
import com.example.ui.components.XrayBackground
import com.example.ui.screens.AppDrawerScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.theme.CyberDarkBg
import com.example.ui.theme.XRayLauncherTheme

class MainActivity : ComponentActivity() {

    private val viewModel: LauncherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val currentScreen by viewModel.currentScreen.collectAsState()
            val filteredApps by viewModel.filteredApps.collectAsState()
            val pinnedApps by viewModel.pinnedApps.collectAsState()
            val dockApps by viewModel.dockApps.collectAsState()
            val searchQuery by viewModel.searchQuery.collectAsState()

            XRayLauncherTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = CyberDarkBg
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        XrayBackground(
                            showGrid = true,
                            showParticles = false
                        )

                        AnimatedContent(
                            targetState = currentScreen,
                            transitionSpec = {
                                fadeIn(animationSpec = tween(200)) togetherWith
                                fadeOut(animationSpec = tween(150))
                            },
                            label = "screen_transition",
                            modifier = Modifier.fillMaxSize()
                        ) { screen ->
                            when (screen) {
                                LauncherScreen.HOME -> {
                                    HomeScreen(
                                        pinnedApps = pinnedApps,
                                        onNavigate = viewModel::navigateTo,
                                        onLaunchApp = viewModel::launchApp,
                                        onTogglePin = viewModel::togglePin,
                                        onOpenDetails = viewModel::openAppDetails
                                    )
                                }
                                LauncherScreen.DRAWER -> {
                                    AppDrawerScreen(
                                        apps = filteredApps,
                                        searchQuery = searchQuery,
                                        onSearchChange = viewModel::setSearchQuery,
                                        onNavigate = viewModel::navigateTo,
                                        onLaunchApp = viewModel::launchApp,
                                        onTogglePin = viewModel::togglePin,
                                        onOpenDetails = viewModel::openAppDetails
                                    )
                                }
                            }
                        }

                        // Bottom Minimal Dock
                        DockBar(
                            dockApps = dockApps,
                            currentScreen = currentScreen,
                            onNavigate = viewModel::navigateTo,
                            onLaunchApp = viewModel::launchApp,
                            onTogglePin = viewModel::togglePin,
                            onOpenDetails = viewModel::openAppDetails,
                            modifier = Modifier.align(Alignment.BottomCenter)
                        )
                    }
                }
            }
        }
    }
}
