package com.igi.game.models

data class Player(
    var x: Float,
    var y: Float,
    var health: Int = 100,
    var ammo: Int = 30,
    var visionRange: Float = 200f,
    var isStealth: Boolean = true,
    var isAiming: Boolean = false
) {
    fun takeDamage(damage: Int) {
        health -= damage
        if (health < 0) health = 0
    }
    
    fun reload() {
        ammo = 30
    }
}