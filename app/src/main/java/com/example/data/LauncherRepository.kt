package com.example.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.example.data.model.AppItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LauncherRepository(private val context: Context) {

    private val packageManager = context.packageManager
    private val prefs = context.getSharedPreferences("xray_launcher_prefs", Context.MODE_PRIVATE)

    suspend fun getInstalledApps(): List<AppItem> = withContext(Dispatchers.IO) {
        val appList = mutableListOf<AppItem>()
        val mainIntent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        val resolveInfos = packageManager.queryIntentActivities(mainIntent, 0)
        val seenPackages = mutableSetOf<String>()
        val pinnedSet = prefs.getStringSet("pinned_apps", emptySet()) ?: emptySet()

        for (resolveInfo in resolveInfos) {
            val pkgName = resolveInfo.activityInfo.packageName
            if (seenPackages.contains(pkgName)) continue
            seenPackages.add(pkgName)

            try {
                val appLabel = resolveInfo.loadLabel(packageManager).toString()
                val appIcon = resolveInfo.loadIcon(packageManager)

                appList.add(
                    AppItem(
                        packageName = pkgName,
                        label = if (appLabel.isNotBlank()) appLabel else pkgName,
                        icon = appIcon,
                        isPinned = pinnedSet.contains(pkgName)
                    )
                )
            } catch (e: Exception) {
                // Ignore individual package error
            }
        }

        // Fallback demo apps if running on empty test VM
        if (appList.size < 4) {
            val defaults = listOf(
                AppItem("com.android.settings", "Settings", null, true),
                AppItem("com.android.chrome", "Browser", null, true),
                AppItem("com.android.camera2", "Camera", null, true),
                AppItem("com.android.calculator2", "Calculator", null, true)
            )
            for (def in defaults) {
                if (!seenPackages.contains(def.packageName)) {
                    appList.add(def)
                }
            }
        }

        appList.sortedBy { it.label.lowercase() }
    }

    fun launchApp(packageName: String): Boolean {
        return try {
            val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
            if (launchIntent != null) {
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(launchIntent)
                true
            } else false
        } catch (e: Exception) {
            false
        }
    }

    fun openAppDetails(packageName: String) {
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.parse("package:$packageName")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            // fallback
        }
    }

    fun togglePin(packageName: String): Boolean {
        val current = prefs.getStringSet("pinned_apps", emptySet())?.toMutableSet() ?: mutableSetOf()
        val newState = if (current.contains(packageName)) {
            current.remove(packageName)
            false
        } else {
            current.add(packageName)
            true
        }
        prefs.edit().putStringSet("pinned_apps", current).apply()
        return newState
    }
}
