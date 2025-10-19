package com.xenia.emulator.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.xenia.emulator.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    var vulkanEnabled by remember { mutableStateOf(true) }
    var vsyncEnabled by remember { mutableStateOf(true) }
    var audioEnabled by remember { mutableStateOf(true) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.graphics_settings),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                ListItem(
                    headlineContent = { Text("Vulkan Renderer") },
                    supportingContent = { Text("Use Vulkan for better performance") },
                    leadingContent = {
                        Icon(Icons.Filled.Palette, contentDescription = null)
                    },
                    trailingContent = {
                        Switch(
                            checked = vulkanEnabled,
                            onCheckedChange = { vulkanEnabled = it }
                        )
                    }
                )
                
                ListItem(
                    headlineContent = { Text("VSync") },
                    supportingContent = { Text("Synchronize with display refresh rate") },
                    leadingContent = {
                        Icon(Icons.Filled.Settings, contentDescription = null)
                    },
                    trailingContent = {
                        Switch(
                            checked = vsyncEnabled,
                            onCheckedChange = { vsyncEnabled = it }
                        )
                    }
                )
            }
            
            Text(
                text = stringResource(R.string.audio_settings),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                ListItem(
                    headlineContent = { Text("Enable Audio") },
                    supportingContent = { Text("Enable audio emulation") },
                    leadingContent = {
                        Icon(Icons.Filled.Audiotrack, contentDescription = null)
                    },
                    trailingContent = {
                        Switch(
                            checked = audioEnabled,
                            onCheckedChange = { audioEnabled = it }
                        )
                    }
                )
            }
            
            Text(
                text = stringResource(R.string.input_settings),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                ListItem(
                    headlineContent = { Text("Configure Controller") },
                    supportingContent = { Text("Map controller buttons") },
                    leadingContent = {
                        Icon(Icons.Filled.Gamepad, contentDescription = null)
                    }
                )
            }
        }
    }
}
