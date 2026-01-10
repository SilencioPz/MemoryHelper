package com.example.memoryhelper.model

enum class IconCategory {
    CHILD, ADULT, ELDERLY
}

enum class Difficulty(val cardCount: Int) {
    EASY(6), MEDIUM(14), HARD(20)
}