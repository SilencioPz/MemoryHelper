package com.example.memoryhelper.audio

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.memoryhelper.R

class AudioManager(private val context: Context) {
    private var mediaPlayer: MediaPlayer? = null
    private var soundPlayer: MediaPlayer? = null
    private var currentMusic: Int? = null

    object Sounds {
        val CLICK = R.raw.click
        val CARD_FLIP = R.raw.card_flip
        val FINISH = R.raw.finish
    }

    object Music {
        val MENU = R.raw.beethoven_adieu_au_piano
        val CHILD = R.raw.chopin_waltz_in_a_minor
        val ADULT = R.raw.beethoven_symphony_number_nine_in_d_minor
        val ELDERLY = R.raw.tchaikovsky_the_nutcracker_suite_act_one
    }

    fun playSound(soundResId: Int) {
        try {
            soundPlayer?.release()

            soundPlayer = MediaPlayer.create(context, soundResId)
            soundPlayer?.apply {
                setOnCompletionListener {
                    it.release()
                }
                start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun playMusic(musicResId: Int, loop: Boolean = true) {
        if (currentMusic == musicResId && mediaPlayer?.isPlaying == true) {
            return
        }

        stopMusic()

        try {
            mediaPlayer = MediaPlayer.create(context, musicResId)
            mediaPlayer?.apply {
                isLooping = loop
                setVolume(0.7f, 0.7f)
                setOnCompletionListener {
                    if (loop) {
                        it.seekTo(0)
                        it.start()
                    }
                }
                start()
            }
            currentMusic = musicResId
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun stopMusic() {
        mediaPlayer?.apply {
            if (isPlaying) {
                stop()
            }
            release()
        }
        mediaPlayer = null
        currentMusic = null
    }

    fun pauseMusic() {
        mediaPlayer?.pause()
    }

    fun resumeMusic() {
        mediaPlayer?.start()
    }

    fun isMusicPlaying(): Boolean {
        return mediaPlayer?.isPlaying ?: false
    }

    fun release() {
        stopMusic()
        soundPlayer?.release()
        soundPlayer = null
    }
}

@Composable
fun rememberAudioManager(): AudioManager {
    val context = LocalContext.current
    return remember { AudioManager(context) }
}