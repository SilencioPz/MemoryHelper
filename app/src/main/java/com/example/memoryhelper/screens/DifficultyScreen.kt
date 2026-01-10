package com.example.memoryhelper.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memoryhelper.model.Difficulty
import com.example.memoryhelper.model.IconCategory
import androidx.compose.runtime.*
import com.example.memoryhelper.audio.AudioManager
import com.example.memoryhelper.getAudioManager


@Composable
fun DifficultyScreen(
    selectedCategory: IconCategory,
    onDifficultySelected: (Difficulty) -> Unit,
    onBack: () -> Unit
) {
    val audioManager = getAudioManager()

    LaunchedEffect(selectedCategory) {
        when (selectedCategory) {
            IconCategory.CHILD -> audioManager.playMusic(AudioManager.Music.CHILD)
            IconCategory.ADULT -> audioManager.playMusic(AudioManager.Music.ADULT)
            IconCategory.ELDERLY -> audioManager.playMusic(AudioManager.Music.ELDERLY)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = when (selectedCategory) {
                IconCategory.CHILD -> "👶 Crianças"
                IconCategory.ADULT -> "👨‍💼 Adultos"
                IconCategory.ELDERLY -> "👵 Idosos"
            },
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        Text(
            text = "Escolha a Dificuldade:",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 30.dp)
        )

        Difficulty.values().forEach { difficulty ->
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = when (difficulty) {
                            Difficulty.EASY -> "Fácil"
                            Difficulty.MEDIUM -> "Médio"
                            Difficulty.HARD -> "Difícil"
                        },
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = "${difficulty.cardCount} cartas",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                    )

                    Button(
                        onClick = {
                            audioManager.playSound(AudioManager.Sounds.CLICK)
                            onDifficultySelected(difficulty)
                        },
                        modifier = Modifier.fillMaxWidth(0.8f)
                    ) {
                        Text(text = "Jogar")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedButton(
            onClick = {
                audioManager.playSound(AudioManager.Sounds.CLICK)
                onBack()
            },
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            Text(text = "Voltar")
        }
    }
}