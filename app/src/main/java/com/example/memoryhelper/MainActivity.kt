package com.example.memoryhelper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.memoryhelper.audio.AudioManager
import com.example.memoryhelper.audio.rememberAudioManager
import com.example.memoryhelper.ui.MemoryApp
import com.example.memoryhelper.ui.theme.MemoryHelperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val audioManager = rememberAudioManager()

            MemoryHelperTheme(
                darkTheme = true
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) {
                    ProvideAudioManager(audioManager = audioManager) {

                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = Color.Transparent
                        ) {
                            MemoryApp()
                        }
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        // Pausa a música quando o app perde foco
    }

    override fun onResume() {
        super.onResume()
        // Retoma a música se estava tocando
    }

    override fun onDestroy() {
        super.onDestroy()
        // Libera recursos de áudio
    }
}

val LocalAudioManager = staticCompositionLocalOf<AudioManager> {
    error("AudioManager não foi fornecido!")
}

@Composable
fun ProvideAudioManager(
    audioManager: AudioManager,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalAudioManager provides audioManager
    ) {
        content()
    }

    DisposableEffect(Unit) {
        onDispose {
            audioManager.release()
        }
    }
}

@Composable
fun getAudioManager(): AudioManager {
    return LocalAudioManager.current
}