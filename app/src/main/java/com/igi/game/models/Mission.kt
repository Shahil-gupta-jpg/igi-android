package com.igi.game.models

data class Mission(
    val name: String,
    val description: String,
    var isCompleted: Boolean = false,
    var objectives: List<String> = emptyList()
) {
    fun complete() {
        isCompleted = true
    }
}