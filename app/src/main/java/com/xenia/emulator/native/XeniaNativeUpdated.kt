package com.xenia.emulator.native

object XeniaNative {
    
    init {
        System.loadLibrary("xenia-android")
    }
    
    external fun initialize(): Boolean
    
    external fun shutdown()
    
    external fun getVersion(): String
    
    external fun loadGame(path: String): Boolean
    
    external fun startEmulation(): Boolean
    
    external fun pauseEmulation()
    
    external fun resumeEmulation()
    
    external fun stopEmulation()
    
    external fun isEmulationRunning(): Boolean
    
    external fun setSurfaceSize(width: Int, height: Int)
    
    external fun setRendererType(type: Int)
    
    external fun setVulkanEnabled(enabled: Boolean)
    
    external fun setVSyncEnabled(enabled: Boolean)
    
    external fun setAudioEnabled(enabled: Boolean)
    
    external fun sendControllerInput(button: Int, pressed: Boolean)
    
    external fun extractGameIcon(gamePath: String, outputPath: String): Boolean
    
    external fun getGameTitle(gamePath: String): String?
    
    external fun getGameInfo(gamePath: String): GameInfo?
}

data class GameInfo(
    val title: String,
    val titleId: String,
    val mediaId: String,
    val version: String,
    val baseVersion: String,
    val executableOffset: Long,
    val certificateOffset: Long
)
