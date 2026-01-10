package com.example.memoryhelper.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memoryhelper.R
import com.example.memoryhelper.model.IconCategory
import androidx.compose.runtime.*
import com.example.memoryhelper.audio.AudioManager
import com.example.memoryhelper.getAudioManager

@Composable
fun MenuScreen(
    onCategorySelected: (IconCategory) -> Unit,
    onAboutClicked: () -> Unit = {}
) {
    var showAboutDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val audioManager = getAudioManager()

    LaunchedEffect(Unit) {
        audioManager.playMusic(AudioManager.Music.MENU)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.silenciopz_logo2),
            contentDescription = "Logo SilencioPz",
            modifier = Modifier
                .size(200.dp, 80.dp)
                .padding(bottom = 30.dp)
        )

        Text(
            text = "Memory Helper",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 40.dp)
        )

        Text(
            text = "Escolha uma Categoria:",
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        IconCategory.values().forEach { category ->
            Button(
                onClick = {
                    audioManager.playSound(AudioManager.Sounds.CLICK)
                    onCategorySelected(category)
                },
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding(vertical = 6.dp)
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = when (category) {
                        IconCategory.CHILD -> Color(0xFF2D5A27)
                        IconCategory.ADULT -> Color(0xFF1A365D)
                        IconCategory.ELDERLY -> Color(0xFF4A235A)
                    }
                )
            ) {
                Text(
                    text = when (category) {
                        IconCategory.CHILD -> "👶 Crianças"
                        IconCategory.ADULT -> "👨‍💼 Adultos"
                        IconCategory.ELDERLY -> "👵 Idosos"
                    },
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedButton(
            {
                audioManager.playSound(AudioManager.Sounds.CLICK)
                showAboutDialog = true
            },
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            ),
            border = null
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Sobre",
                modifier = Modifier.padding(end = 8.dp),
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
            Text(
                text = "Sobre",
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Um jogo para exercitar a memória",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.outline,
            fontStyle = MaterialTheme.typography.bodySmall.fontStyle
        )

        Text(
            text = "by SilencioPZ",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
            modifier = Modifier.padding(top = 10.dp)
        )
    }

    if (showAboutDialog) {
        AboutDialog(
            onDismiss = {
                audioManager.playSound(AudioManager.Sounds.CLICK)
                showAboutDialog = false
            },
            context = context
        )
    }
}

@Composable
fun AboutDialog(
    onDismiss: () -> Unit,
    context: Context
) {

    val audioManager = getAudioManager()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Sobre MemoryHelper",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.silenciopz_logo2),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(bottom = 16.dp)
                )

                Text(
                    text = "Versão 1.0",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Desenvolvido por SilencioPz para exercitar a memória!",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Visite meu site:",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(top = 12.dp, bottom = 8.dp)
                )

                Text(
                    text = "https://silenciopz.neocities.org/",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .clickable {
                            audioManager.playSound(AudioManager.Sounds.CLICK)
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://silenciopz.neocities.org/")
                            )
                            context.startActivity(intent)
                        }
                        .padding(vertical = 8.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    audioManager.playSound(AudioManager.Sounds.CLICK)
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Fechar")
            }
        }
    )
}