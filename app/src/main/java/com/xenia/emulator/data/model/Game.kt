package com.xenia.emulator.data.model

import android.net.Uri
import java.io.Serializable

data class Game(
    val id: String,
    val title: String,
    val path: String,
    val fileSize: Long = 0,
    val coverImageUri: String? = null,
    val lastPlayed: Long? = null,
    val playTime: Long = 0,
    val fileType: GameFileType = GameFileType.UNKNOWN
) : Serializable

enum class GameFileType(val extensions: List<String>) {
    ISO(listOf(".iso")),
    XEX(listOf(".xex")),
    XBLA(listOf(".xbla")),
    GOD(listOf(".god")),
    UNKNOWN(emptyList());
    
    companion object {
        fun fromExtension(extension: String): GameFileType {
            val ext = extension.lowercase()
            return entries.find { type ->
                type.extensions.any { it == ext }
            } ?: UNKNOWN
        }
    }
}
