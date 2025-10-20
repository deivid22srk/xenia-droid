package com.xenia.emulator.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xenia.emulator.data.model.Game
import com.xenia.emulator.data.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GamesViewModel(private val context: Context) : ViewModel() {
    
    private val repository = GameRepository(context)
    
    private val _games = MutableStateFlow<List<Game>>(emptyList())
    val games: StateFlow<List<Game>> = _games.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _selectedGame = MutableStateFlow<Game?>(null)
    val selectedGame: StateFlow<Game?> = _selectedGame.asStateFlow()
    
    init {
        loadDefaultGames()
    }
    
    private fun loadDefaultGames() {
        viewModelScope.launch {
            _isLoading.value = true
            val defaultDirs = repository.getDefaultGameDirectories()
            val allGames = mutableListOf<Game>()
            
            defaultDirs.forEach { dir ->
                allGames.addAll(repository.scanPath(dir))
            }
            
            _games.value = allGames
            _isLoading.value = false
        }
    }
    
    fun scanDirectory(uri: Uri) {
        viewModelScope.launch {
            _isLoading.value = true
            val newGames = repository.scanDirectory(uri)
            
            val existingPaths = _games.value.map { it.path }.toSet()
            val uniqueGames = newGames.filter { it.path !in existingPaths }
            
            _games.value = _games.value + uniqueGames
            _isLoading.value = false
        }
    }
    
    fun scanPath(path: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val newGames = repository.scanPath(path)
            
            val existingPaths = _games.value.map { it.path }.toSet()
            val uniqueGames = newGames.filter { it.path !in existingPaths }
            
            _games.value = _games.value + uniqueGames
            _isLoading.value = false
        }
    }
    
    fun refreshGames() {
        loadDefaultGames()
    }
    
    fun selectGame(game: Game) {
        _selectedGame.value = game
    }
    
    fun clearSelection() {
        _selectedGame.value = null
    }
    
    fun removeGame(game: Game) {
        _games.value = _games.value.filter { it.id != game.id }
    }
}
