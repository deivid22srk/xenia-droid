package com.xenia.emulator.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.xenia.emulator.R

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector
) {
    data object Home : Screen("home", R.string.select_game, Icons.Filled.Home)
    data object Settings : Screen("settings", R.string.settings, Icons.Filled.Settings)
    data object About : Screen("about", R.string.about, Icons.Filled.Info)
    
    companion object {
        val bottomNavItems = listOf(Home, Settings, About)
    }
}
