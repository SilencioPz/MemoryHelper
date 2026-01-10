package com.example.memoryhelper.ui

import androidx.compose.runtime.*
import com.example.memoryhelper.model.GameConfig
import com.example.memoryhelper.model.IconCategory
import com.example.memoryhelper.screens.*

@Composable
fun MemoryApp() {
    var screenState by remember { mutableStateOf<ScreenState>(ScreenState.Menu) }

    when (val state = screenState) {
        is ScreenState.Menu -> {
            MenuScreen(
                onCategorySelected = { category ->
                    screenState = ScreenState.DifficultySelection(category)
                }
            )
        }

        is ScreenState.DifficultySelection -> {
            DifficultyScreen(
                selectedCategory = state.category,
                onDifficultySelected = { difficulty ->
                    screenState = ScreenState.Game(
                        GameConfig(state.category, difficulty)
                    )
                },
                onBack = { screenState = ScreenState.Menu }
            )
        }

        is ScreenState.Game -> {
            GameScreen(
                config = state.config,
                onBackToMenu = { screenState = ScreenState.Menu }
            )
        }
    }
}

sealed class ScreenState {
    object Menu : ScreenState()
    data class DifficultySelection(val category: IconCategory) : ScreenState()
    data class Game(val config: GameConfig) : ScreenState()
}