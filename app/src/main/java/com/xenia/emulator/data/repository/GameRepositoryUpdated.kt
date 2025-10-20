package com.xenia.emulator.data.repository

import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.OpenableColumns
import com.xenia.emulator.data.model.Game
import com.xenia.emulator.data.model.GameFileType
import com.xenia.emulator.native.XeniaNative
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID

class GameRepository(private val context: Context) {
    
    private val supportedExtensions = listOf(".iso", ".xex", ".xbla", ".god")
    private val cacheDir = File(context.cacheDir, "game_icons").apply { mkdirs() }
    
    suspend fun scanDirectory(directoryUri: Uri): List<Game> = withContext(Dispatchers.IO) {
        val games = mutableListOf<Game>()
        
        try {
            val childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(
                directoryUri,
                DocumentsContract.getTreeDocumentId(directoryUri)
            )
            
            val cursor = context.contentResolver.query(
                childrenUri,
                arrayOf(
                    DocumentsContract.Document.COLUMN_DOCUMENT_ID,
                    DocumentsContract.Document.COLUMN_DISPLAY_NAME,
                    DocumentsContract.Document.COLUMN_SIZE,
                    DocumentsContract.Document.COLUMN_MIME_TYPE
                ),
                null,
                null,
                null
            )
            
            cursor?.use { c ->
                while (c.moveToNext()) {
                    val documentId = c.getString(0)
                    val displayName = c.getString(1)
                    val size = c.getLong(2)
                    val mimeType = c.getString(3)
                    
                    val extension = displayName.substringAfterLast(".", "")
                    if (supportedExtensions.any { it.endsWith(extension, ignoreCase = true) }) {
                        val documentUri = DocumentsContract.buildDocumentUriUsingTree(
                            directoryUri,
                            documentId
                        )
                        
                        val game = Game(
                            id = UUID.randomUUID().toString(),
                            title = displayName.substringBeforeLast("."),
                            path = documentUri.toString(),
                            fileSize = size,
                            fileType = GameFileType.fromExtension(".$extension")
                        )
                        
                        games.add(game)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        
        games
    }
    
    suspend fun scanPath(path: String): List<Game> = withContext(Dispatchers.IO) {
        val games = mutableListOf<Game>()
        val directory = File(path)
        
        if (!directory.exists() || !directory.isDirectory) {
            return@withContext games
        }
        
        directory.listFiles()?.forEach { file ->
            if (file.isFile) {
                val extension = ".${file.extension}"
                if (supportedExtensions.contains(extension.lowercase())) {
                    val title = extractGameTitle(file.absolutePath) ?: file.nameWithoutExtension
                    val coverPath = extractGameCover(file.absolutePath)
                    
                    val game = Game(
                        id = UUID.randomUUID().toString(),
                        title = title,
                        path = file.absolutePath,
                        fileSize = file.length(),
                        coverImageUri = coverPath,
                        fileType = GameFileType.fromExtension(extension)
                    )
                    games.add(game)
                }
            } else if (file.isDirectory) {
                games.addAll(scanPath(file.absolutePath))
            }
        }
        
        games
    }
    
    private fun extractGameCover(gamePath: String): String? {
        try {
            val gameFile = File(gamePath)
            val iconFile = File(cacheDir, "${gameFile.nameWithoutExtension}_icon.png")
            
            if (iconFile.exists()) {
                return iconFile.absolutePath
            }
            
            val extension = gameFile.extension.lowercase()
            if (extension == "xex" || extension == "iso") {
                val success = XeniaNative.extractGameIcon(gamePath, iconFile.absolutePath)
                if (success && iconFile.exists()) {
                    return iconFile.absolutePath
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        
        return null
    }
    
    private fun extractGameTitle(gamePath: String): String? {
        try {
            val extension = File(gamePath).extension.lowercase()
            if (extension == "xex") {
                return XeniaNative.getGameTitle(gamePath)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        
        return null
    }
    
    fun getDefaultGameDirectories(): List<String> {
        return listOf(
            "/storage/emulated/0/Download/Xbox360",
            "/storage/emulated/0/Games/Xbox360",
            "/sdcard/Download/Xbox360",
            "/sdcard/Games/Xbox360"
        ).filter { File(it).exists() }
    }
}
