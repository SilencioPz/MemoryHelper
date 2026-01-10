package com.example.memoryhelper.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memoryhelper.game.GameManager
import com.example.memoryhelper.model.GameConfig
import com.example.memoryhelper.model.MemoryCard
import androidx.compose.runtime.*
import com.example.memoryhelper.audio.AudioManager
import com.example.memoryhelper.getAudioManager
import com.example.memoryhelper.model.IconCategory
import kotlinx.coroutines.delay


@Composable
fun GameScreen(
    config: GameConfig,
    onBackToMenu: () -> Unit
) {
    var cards by remember { mutableStateOf(emptyList<MemoryCard>()) }
    var moves by remember { mutableStateOf(0) }
    var matches by remember { mutableStateOf(0) }
    var showWinDialog by remember { mutableStateOf(false) }
    var finalScore by remember { mutableStateOf(0) }

    val audioManager = getAudioManager()
    val scope = rememberCoroutineScope()

    LaunchedEffect(config.category) {
        delay(100)

        when (config.category) {
            IconCategory.CHILD -> audioManager.playMusic(AudioManager.Music.CHILD)
            IconCategory.ADULT -> audioManager.playMusic(AudioManager.Music.ADULT)
            IconCategory.ELDERLY -> audioManager.playMusic(AudioManager.Music.ELDERLY)
        }
    }

    val gameManager = remember(config) {
        GameManager(
            difficulty = config.difficulty,
            category = config.category,
            coroutineScope = scope,
            onGameStateChanged = { updatedCards, updatedMoves, updatedMatches ->
                cards = updatedCards
                moves = updatedMoves
                matches = updatedMatches
            },
            onGameCompleted = { completedMoves, score ->
                finalScore = score
                audioManager.playSound(AudioManager.Sounds.FINISH)
                showWinDialog = true
            }
        )
    }

    LaunchedEffect(gameManager) {
        cards = gameManager.getCards()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GameHeader(
            moves = moves,
            matches = matches,
            totalPairs = gameManager.getTotalPairs(),
            onBack = onBackToMenu,
            onRestart = { gameManager.restartGame() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CardGrid(
            cards = cards,
            difficulty = config.difficulty,
            onCardClicked = { card ->
                gameManager.onCardClicked(card)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        GameInfo(config = config)
    }

    if (showWinDialog) {
        WinDialog(
            score = finalScore,
            moves = moves,
            onDismiss = { showWinDialog = false },
            onPlayAgain = {
                gameManager.restartGame()
                showWinDialog = false
            },
            onBackToMenu = onBackToMenu
        )
    }
}

@Composable
private fun GameHeader(
    moves: Int,
    matches: Int,
    totalPairs: Int,
    onBack: () -> Unit,
    onRestart: () -> Unit
) {
    val audioManager = getAudioManager()

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                audioManager.playSound(AudioManager.Sounds.CLICK)
                onBack()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Voltar ao Menu"
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Pares: $matches/$totalPairs",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Movimentos: $moves",
                    fontSize = 16.sp
                )
            }

            IconButton(onClick = {
                audioManager.playSound(AudioManager.Sounds.CLICK)
                onRestart()
            }) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Reiniciar Jogo"
                )
            }
        }
    }
}

@Composable
private fun CardGrid(
    cards: List<MemoryCard>,
    difficulty: com.example.memoryhelper.model.Difficulty,
    onCardClicked: (MemoryCard) -> Unit
) {
    val audioManager = getAudioManager()

    val columns = when (difficulty) {
        com.example.memoryhelper.model.Difficulty.EASY -> 3
        com.example.memoryhelper.model.Difficulty.MEDIUM -> 4
        com.example.memoryhelper.model.Difficulty.HARD -> 5
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(cards) { card ->
            MemoryCardItem(
                card = card,
                onClick = { onCardClicked(card) }
            )
        }
    }
}

@Composable
private fun MemoryCardItem(
    card: MemoryCard,
    onClick: () -> Unit
) {
    val audioManager = getAudioManager()

    val cardColor = if (card.isMatched) {
        Color.Green.copy(alpha = 0.3f)
    } else if (card.isFaceUp) {
        MaterialTheme.colorScheme.surface
    } else {
        MaterialTheme.colorScheme.primary
    }

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(cardColor)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(
                enabled = !card.isMatched,
                onClick = {
                    if (!card.isFaceUp && !card.isMatched) {
                        audioManager.playSound(AudioManager.Sounds.CARD_FLIP)
                    }
                    onClick()
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        if (card.isFaceUp || card.isMatched) {
            Image(
                painter = painterResource(id = card.iconResId),
                contentDescription = "Ícone da Carta",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentScale = ContentScale.Fit
            )
        } else {
            Text(
                text = "?",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
private fun GameInfo(config: com.example.memoryhelper.model.GameConfig) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Categoria: ${config.category.name}",
                fontSize = 16.sp
            )
            Text(
                text = "Dificuldade: ${config.difficulty.name}",
                fontSize = 16.sp
            )
            Text(
                text = "Cartas: ${config.difficulty.cardCount}",
                fontSize = 16.sp
            )
        }
    }
}

@Composable
private fun WinDialog(
    score: Int,
    moves: Int,
    onDismiss: () -> Unit,
    onPlayAgain: () -> Unit,
    onBackToMenu: () -> Unit
) {
    val audioManager = getAudioManager()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "🎉 Parabéns! 🎉",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Você completou o jogo!",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Movimentos: $moves",
                    fontSize = 16.sp
                )
                Text(
                    text = "Pontuação: $score",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    audioManager.playSound(AudioManager.Sounds.CLICK)
                    onPlayAgain()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Jogar Novamente")
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = {
                    audioManager.playSound(AudioManager.Sounds.CLICK)
                    onBackToMenu()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Voltar ao Menu")
            }
        }
    )
}