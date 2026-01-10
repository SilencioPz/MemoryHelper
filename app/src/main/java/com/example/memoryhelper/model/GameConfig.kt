package com.example.memoryhelper.model

enum class Category {
        CHILDREN, ADULTS, ELDERLY
    }

    data class GameConfig(
        val category: IconCategory,
        val difficulty: Difficulty
    )
