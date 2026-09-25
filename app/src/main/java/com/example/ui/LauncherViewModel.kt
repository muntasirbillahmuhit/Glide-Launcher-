package com.example.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.LauncherRepository
import com.example.data.model.AppItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class LauncherScreen {
    HOME,
    DRAWER
}

class LauncherViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = LauncherRepository(application)

    private val _currentScreen = MutableStateFlow(LauncherScreen.HOME)
    val currentScreen: StateFlow<LauncherScreen> = _currentScreen.asStateFlow()

    private val _allApps = MutableStateFlow<List<AppItem>>(emptyList())
    val allApps: StateFlow<List<AppItem>> = _allApps.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val filteredApps: StateFlow<List<AppItem>> = combine(_allApps, _searchQuery) { apps, query ->
        if (query.isBlank()) {
            apps.sortedBy { it.label.lowercase() }
        } else {
            val q = query.trim().lowercase()
            apps.filter {
                it.label.lowercase().contains(q) || it.packageName.lowercase().contains(q)
            }.sortedBy { it.label.lowercase() }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val pinnedApps: StateFlow<List<AppItem>> = _allApps.combine(_allApps) { apps, _ ->
        val pinned = apps.filter { it.isPinned }
        if (pinned.isNotEmpty()) pinned else apps.take(8)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val dockApps: StateFlow<List<AppItem>> = _allApps.combine(_allApps) { apps, _ ->
        val pinned = apps.filter { it.isPinned }
        if (pinned.isNotEmpty()) pinned.take(4) else apps.take(4)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        loadApps()
    }

    fun loadApps() {
        viewModelScope.launch {
            _allApps.value = repository.getInstalledApps()
        }
    }

    fun navigateTo(screen: LauncherScreen) {
        _currentScreen.value = screen
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun launchApp(app: AppItem) {
        repository.launchApp(app.packageName)
    }

    fun openAppDetails(app: AppItem) {
        repository.openAppDetails(app.packageName)
    }

    fun togglePin(app: AppItem) {
        val isPinned = repository.togglePin(app.packageName)
        _allApps.value = _allApps.value.map {
            if (it.packageName == app.packageName) it.copy(isPinned = isPinned) else it
        }
    }
}
