package com.igi.game.models

data class Enemy(
    var x: Float,
    var y: Float,
    var health: Int = 50,
    var visionRange: Float = 250f,
    var isAlerted: Boolean = false,
    var isPatrolling: Boolean = true,
    val patrolPath: List<Pair<Float, Float>> = emptyList(),
    private var currentPathIndex: Int = 0
) {
    fun patrol() {
        if (patrolPath.isEmpty()) return
        
        val target = patrolPath[currentPathIndex]
        val dx = target.first - x
        val dy = target.second - y
        val distance = kotlin.math.sqrt((dx * dx + dy * dy).toDouble()).toFloat()
        
        if (distance < 10f) {
            currentPathIndex = (currentPathIndex + 1) % patrolPath.size
        } else {
            x += (dx / distance) * 1f
            y += (dy / distance) * 1f
        }
    }
    
    fun takeDamage(damage: Int) {
        health -= damage
    }
}