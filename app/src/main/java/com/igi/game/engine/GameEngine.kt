package com.igi.game.engine

import com.igi.game.models.Player
import com.igi.game.models.Enemy
import com.igi.game.models.Mission
import kotlin.math.sqrt

class GameEngine {
    var player: Player? = null
    var enemies = mutableListOf<Enemy>()
    var score = 0
    var currentMission: Mission? = null
    
    // Input state
    var joystickX = 0f
    var joystickY = 0f
    private var isAiming = false
    
    init {
        initializeGame()
    }
    
    private fun initializeGame() {
        player = Player(x = 200f, y = 300f)
        currentMission = Mission("Infiltrate Base", "Reach the objective without being detected")
        
        // Spawn enemies
        enemies.add(Enemy(x = 400f, y = 200f, patrolPath = listOf(400f to 200f, 600f to 200f)))
        enemies.add(Enemy(x = 600f, y = 400f, patrolPath = listOf(600f to 400f, 800f to 400f)))
        enemies.add(Enemy(x = 500f, y = 300f, patrolPath = listOf(500f to 300f, 500f to 500f)))
    }
    
    fun update() {
        player?.let { player ->
            // Update player position based on joystick input
            val speed = 3f
            player.x += joystickX * speed
            player.y += joystickY * speed
            
            // Update stealth status
            updateStealth(player)
            
            // Update enemies
            updateEnemies(player)
            
            // Check mission completion
            checkMissionCompletion(player)
        }
    }
    
    private fun updateStealth(player: Player) {
        var detected = false
        
        enemies.forEach { enemy ->
            val distance = calculateDistance(player.x, player.y, enemy.x, enemy.y)
            
            // Check if player is in enemy vision range
            if (distance < enemy.visionRange) {
                // If player is moving, more likely to be detected
                val movementFactor = if (joystickX != 0f || joystickY != 0f) 0.7f else 1.0f
                if (distance < enemy.visionRange * movementFactor) {
                    detected = true
                    enemy.isAlerted = true
                }
            }
        }
        
        player.isStealth = !detected
    }
    
    private fun updateEnemies(player: Player) {
        enemies.forEach { enemy ->
            val distance = calculateDistance(player.x, player.y, enemy.x, enemy.y)
            
            if (enemy.isAlerted) {
                // Chase the player if alerted
                val direction = atan2(player.y - enemy.y, player.x - enemy.x)
                enemy.x += kotlin.math.cos(direction) * 2f
                enemy.y += kotlin.math.sin(direction) * 2f
                
                // Fire at player if in range
                if (distance < 150f) {
                    fireAtPlayer(enemy, player)
                }
            } else if (distance < 80f) {
                // Patrol behavior
                enemy.patrol()
            }
        }
    }
    
    private fun fireAtPlayer(enemy: Enemy, player: Player) {
        val distance = calculateDistance(player.x, player.y, enemy.x, enemy.y)
        if (distance < 300f) {
            player.takeDamage(5)
        }
    }
    
    private fun checkMissionCompletion(player: Player) {
        // Simple mission completion: reach the objective area
        if (player.x > 1000f && player.y > 500f) {
            completeMission()
        }
    }
    
    private fun completeMission() {
        score += 1000
        currentMission = Mission("Mission Complete!", "Proceed to next objective")
    }
    
    fun fireWeapon() {
        player?.let { player ->
            // Simple firing mechanism
            enemies.forEach { enemy ->
                val distance = calculateDistance(player.x, player.y, enemy.x, enemy.y)
                if (distance < 300f) {
                    enemy.health -= 25
                    score += 100
                    if (enemy.health <= 0) {
                        enemies.remove(enemy)
                    }
                }
            }
        }
    }
    
    fun toggleAim() {
        isAiming = !isAiming
    }
    
    private fun calculateDistance(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        return sqrt(((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)).toDouble()).toFloat()
    }
    
    private fun atan2(y: Float, x: Float): Float {
        return kotlin.math.atan2(y.toDouble(), x.toDouble()).toFloat()
    }
}