package com.example.memoryhelper.model

import androidx.compose.ui.graphics.painter.Painter
import com.example.memoryhelper.R

data class MemoryCard(
    val id: Int,
    val iconResId: Int, // R.drawable.dead_space
    var isFaceUp: Boolean = false,
    var isMatched: Boolean = false
)

val childIcons = listOf(
    R.drawable.boy,
    R.drawable.cradle,
    R.drawable.diaper,
    R.drawable.dog_food,
    R.drawable.fatherandbaby,
    R.drawable.feeding_bottle,
    R.drawable.girl,
    R.drawable.mother,
    R.drawable.pacifier,
    R.drawable.parque_infantil,
    R.drawable.parque_tematico,
    R.drawable.playtime,
    R.drawable.plush_toy,
    R.drawable.rocking_horse,
    R.drawable.rubber_duck,
    R.drawable.shampoo,
    R.drawable.soccer_ball_variant,
    R.drawable.storage_box,
    R.drawable.swing,
    R.drawable.toy
)

val adultIcons = listOf(
    R.drawable.assassins_creed,
    R.drawable.counterstrike,
    R.drawable.dead_space,
    R.drawable.doom,
    R.drawable.dragonball,
    R.drawable.gta,
    R.drawable.halflife,
    R.drawable.mario,
    R.drawable.metroid,
    R.drawable.minecraft,
    R.drawable.modern_warfare,
    R.drawable.pac_man,
    R.drawable.pokemon,
    R.drawable.portal,
    R.drawable.red_dead,
    R.drawable.sims,
    R.drawable.snake,
    R.drawable.sonic,
    R.drawable.witcher,
    R.drawable.zelda
)

val elderlyIcons = listOf(
    R.drawable.aid,
    R.drawable.beret,
    R.drawable.bingo,
    R.drawable.chess,
    R.drawable.children,
    R.drawable.crossword,
    R.drawable.domino,
    R.drawable.glasses,
    R.drawable.grandfather,
    R.drawable.knitting,
    R.drawable.grandmother,
    R.drawable.mustache,
    R.drawable.old_man_walking,
    R.drawable.pamela_hat,
    R.drawable.rocking_chair,
    R.drawable.santa_claus,
    R.drawable.smoking_pipe,
    R.drawable.suspenders,
    R.drawable.outfit,
    R.drawable.food
)