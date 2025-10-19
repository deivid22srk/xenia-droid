package com.xenia.emulator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.xenia.emulator.native.XeniaNative
import com.xenia.emulator.ui.XeniaApp
import com.xenia.emulator.ui.theme.XeniaTheme

class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        
        XeniaNative.initialize()
        
        setContent {
            XeniaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    XeniaApp()
                }
            }
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        XeniaNative.shutdown()
    }
}
