package com.xenia.emulator.ui.screens

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.xenia.emulator.R
import com.xenia.emulator.data.model.Game
import com.xenia.emulator.native.XeniaNative
import com.xenia.emulator.ui.components.GameCard
import com.xenia.emulator.viewmodel.GamesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenNew() {
    val context = LocalContext.current
    val viewModel = remember { GamesViewModel(context) }
    
    val games by viewModel.games.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val selectedGame by viewModel.selectedGame.collectAsState()
    
    var showGameDialog by remember { mutableStateOf(false) }
    
    val directoryPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocumentTree()
    ) { uri: Uri? ->
        uri?.let {
            context.contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            viewModel.scanDirectory(it)
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                actions = {
                    IconButton(
                        onClick = { viewModel.refreshGames() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = "Refresh games"
                        )
                    }
                    
                    IconButton(
                        onClick = { directoryPickerLauncher.launch(null) }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.FolderOpen,
                            contentDescription = "Select folder"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { directoryPickerLauncher.launch(null) }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add games folder")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                
                games.isEmpty() -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.FolderOpen,
                            contentDescription = null,
                            modifier = Modifier.size(120.dp),
                            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                        )
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Text(
                            text = "No games found",
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "Tap the + button or folder icon to select a directory with Xbox 360 games",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Button(
                            onClick = { directoryPickerLauncher.launch(null) },
                            modifier = Modifier.fillMaxWidth(0.6f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.FolderOpen,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Select Games Folder")
                        }
                    }
                }
                
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 150.dp),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(games, key = { it.id }) { game ->
                            GameCard(
                                game = game,
                                onClick = {
                                    viewModel.selectGame(game)
                                    showGameDialog = true
                                }
                            )
                        }
                    }
                }
            }
        }
    }
    
    if (showGameDialog && selectedGame != null) {
        GameDetailsDialog(
            game = selectedGame!!,
            onDismiss = {
                showGameDialog = false
                viewModel.clearSelection()
            },
            onLaunch = { game ->
                XeniaNative.loadGame(game.path)
                XeniaNative.startEmulation()
                showGameDialog = false
            },
            onRemove = { game ->
                viewModel.removeGame(game)
                showGameDialog = false
            }
        )
    }
}

@Composable
fun GameDetailsDialog(
    game: Game,
    onDismiss: () -> Unit,
    onLaunch: (Game) -> Unit,
    onRemove: (Game) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(game.title) },
        text = {
            Column {
                Text("Type: ${game.fileType.name}")
                Text("Size: ${formatFileSize(game.fileSize)}")
                Text("Path: ${game.path}")
            }
        },
        confirmButton = {
            Button(onClick = { onLaunch(game) }) {
                Text("Launch")
            }
        },
        dismissButton = {
            Row {
                TextButton(onClick = { onRemove(game) }) {
                    Text("Remove")
                }
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        }
    )
}

private fun formatFileSize(size: Long): String {
    if (size <= 0) return "0 B"
    val units = arrayOf("B", "KB", "MB", "GB", "TB")
    val digitGroups = (Math.log10(size.toDouble()) / Math.log10(1024.0)).toInt()
    return "%.1f %s".format(
        size / Math.pow(1024.0, digitGroups.toDouble()),
        units[digitGroups]
    )
}
