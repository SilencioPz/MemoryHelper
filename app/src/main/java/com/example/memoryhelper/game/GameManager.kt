package com.example.memoryhelper.game

import com.example.memoryhelper.model.Difficulty
import com.example.memoryhelper.model.IconCategory
import com.example.memoryhelper.model.MemoryCard
import com.example.memoryhelper.model.adultIcons
import com.example.memoryhelper.model.childIcons
import com.example.memoryhelper.model.elderlyIcons
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameManager(
    private val difficulty: Difficulty,
    private val category: IconCategory,
    private val coroutineScope: CoroutineScope,
    private val onGameStateChanged: (List<MemoryCard>, Int, Int) -> Unit,
    private val onGameCompleted: (Int, Int) -> Unit
) {
    private var cards = mutableListOf<MemoryCard>()
    private var flippedCards = mutableListOf<MemoryCard>()
    private var moves = 0
    private var matches = 0
    private var totalPairs = 0
    private var canFlip = true

    init {
        initializeGame()
    }

    private fun initializeGame() {
        cards.clear()
        flippedCards.clear()
        moves = 0
        matches = 0
        canFlip = true

        totalPairs = when (difficulty) {
            Difficulty.EASY -> 3   // 6 cartas
            Difficulty.MEDIUM -> 7  // 14 cartas
            Difficulty.HARD -> 10   // 20 cartas
        }

        val selectedIcons = when (category) {
            IconCategory.CHILD -> childIcons.shuffled().take(totalPairs)
            IconCategory.ADULT -> adultIcons.shuffled().take(totalPairs)
            IconCategory.ELDERLY -> elderlyIcons.shuffled().take(totalPairs)
        }

        val cardPairs = selectedIcons.flatMap { icon ->
            listOf(
                MemoryCard(id = Random.nextInt(), iconResId = icon),
                MemoryCard(id = Random.nextInt(), iconResId = icon)
            )
        }.shuffled()

        cards.addAll(cardPairs)
        notifyStateChanged()
    }

    fun onCardClicked(card: MemoryCard) {
        if (!canFlip || card.isFaceUp || card.isMatched || flippedCards.size >= 2) {
            return
        }

        val cardIndex = cards.indexOfFirst { it.id == card.id }
        if (cardIndex == -1) return

        cards[cardIndex] = cards[cardIndex].copy(isFaceUp = true)
        flippedCards.add(cards[cardIndex])

        notifyStateChanged()

        if (flippedCards.size == 2) {
            moves++
            canFlip = false

            if (flippedCards[0].iconResId == flippedCards[1].iconResId) {
                val card0Index = cards.indexOfFirst { it.id == flippedCards[0].id }
                val card1Index = cards.indexOfFirst { it.id == flippedCards[1].id }

                cards[card0Index] = cards[card0Index].copy(isMatched = true)
                cards[card1Index] = cards[card1Index].copy(isMatched = true)

                flippedCards.clear()
                matches++
                canFlip = true

                if (matches == totalPairs) {
                    onGameCompleted(moves, calculateScore())
                }
            } else {
                coroutineScope.launch {
                    delay(1000)

                    val card0Index = cards.indexOfFirst { it.id == flippedCards[0].id }
                    val card1Index = cards.indexOfFirst { it.id == flippedCards[1].id }

                    cards[card0Index] = cards[card0Index].copy(isFaceUp = false)
                    cards[card1Index] = cards[card1Index].copy(isFaceUp = false)

                    flippedCards.clear()
                    canFlip = true
                    notifyStateChanged()
                }
            }

            notifyStateChanged()
        }
    }

    fun restartGame() {
        initializeGame()
    }

    private fun calculateScore(): Int {
        val baseScore = 1000
        val movePenalty = moves * 10
        val difficultyMultiplier = when (difficulty) {
            Difficulty.EASY -> 1
            Difficulty.MEDIUM -> 2
            Difficulty.HARD -> 3
        }

        return maxOf(0, (baseScore - movePenalty) * difficultyMultiplier)
    }

    fun getCards(): List<MemoryCard> = cards.toList()
    fun getTotalPairs(): Int = totalPairs

    private fun notifyStateChanged() {
        onGameStateChanged(cards.toList(), moves, matches) // .toList() cria uma cópia
    }
}