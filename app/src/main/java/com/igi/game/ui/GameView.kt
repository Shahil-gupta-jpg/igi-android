package com.igi.game.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import com.igi.game.engine.GameEngine
import com.igi.game.models.Player
import com.igi.game.models.Enemy

class GameView(context: Context) : View(context) {
    private val gameEngine = GameEngine()
    private val paint = Paint().apply {
        isAntiAlias = true
        textSize = 40f
    }
    
    private val joystickRadius = 80f
    private val joystickCenterX = 150f
    private val joystickCenterY = height - 150f
    
    init {
        setBackgroundColor(Color.BLACK)
        // Start the game loop
        startGameLoop()
    }
    
    private fun startGameLoop() {
        val gameLoopThread = Thread {
            while (true) {
                gameEngine.update()
                postInvalidate()
                Thread.sleep(16) // ~60 FPS
            }
        }
        gameLoopThread.start()
    }
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        
        // Draw game background
        canvas.drawColor(Color.BLACK)
        
        // Draw game world
        gameEngine.player?.let { player ->
            drawPlayer(canvas, player)
        }
        
        // Draw enemies
        gameEngine.enemies.forEach { enemy ->
            drawEnemy(canvas, enemy)
        }
        
        // Draw UI elements
        drawUI(canvas)
        drawJoystick(canvas)
    }
    
    private fun drawPlayer(canvas: Canvas, player: Player) {
        paint.color = Color.GREEN
        canvas.drawCircle(player.x, player.y, 15f, paint)
        
        // Draw vision cone
        paint.color = Color.argb(30, 0, 255, 0)
        canvas.drawCircle(player.x, player.y, player.visionRange, paint)
    }
    
    private fun drawEnemy(canvas: Canvas, enemy: Enemy) {
        paint.color = when {
            enemy.isAlerted -> Color.RED
            enemy.isPatrolling -> Color.YELLOW
            else -> Color.GRAY
        }
        canvas.drawCircle(enemy.x, enemy.y, 12f, paint)
        
        // Draw vision range if alerted
        if (enemy.isAlerted) {
            paint.color = Color.argb(20, 255, 0, 0)
            canvas.drawCircle(enemy.x, enemy.y, enemy.visionRange, paint)
        }
    }
    
    private fun drawUI(canvas: Canvas) {
        paint.color = Color.WHITE
        paint.textSize = 35f
        canvas.drawText("Health: ${gameEngine.player?.health}", 20f, 50f, paint)
        canvas.drawText("Score: ${gameEngine.score}", 20f, 100f, paint)
        canvas.drawText("Mission: ${gameEngine.currentMission?.name}", 20f, 150f, paint)
        
        // Draw stealth indicator
        val stealthText = if (gameEngine.player?.isStealth == true) "STEALTH" else "DETECTED"
        paint.color = if (gameEngine.player?.isStealth == true) Color.GREEN else Color.RED
        canvas.drawText(stealthText, width - 250f, 50f, paint)
    }
    
    private fun drawJoystick(canvas: Canvas) {
        val centerX = joystickCenterX
        val centerY = height - 150f
        
        // Draw joystick background circle
        paint.color = Color.argb(100, 255, 255, 255)
        canvas.drawCircle(centerX, centerY, joystickRadius, paint)
        
        // Draw joystick thumb
        paint.color = Color.argb(200, 100, 200, 255)
        canvas.drawCircle(
            centerX + (gameEngine.joystickX * joystickRadius),
            centerY + (gameEngine.joystickY * joystickRadius),
            40f,
            paint
        )
        
        // Draw action buttons
        drawActionButton(canvas, width - 150f, height - 150f, "FIRE")
        drawActionButton(canvas, width - 150f, height - 50f, "AIM")
    }
    
    private fun drawActionButton(canvas: Canvas, x: Float, y: Float, label: String) {
        paint.color = Color.argb(150, 200, 100, 100)
        canvas.drawCircle(x, y, 40f, paint)
        paint.color = Color.WHITE
        paint.textSize = 20f
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText(label, x, y + 7f, paint)
    }
    
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                // Check joystick input
                val dx = event.x - joystickCenterX
                val dy = event.y - (height - 150f)
                val distance = kotlin.math.sqrt((dx * dx + dy * dy).toDouble()).toFloat()
                
                if (distance < joystickRadius) {
                    gameEngine.joystickX = dx / joystickRadius
                    gameEngine.joystickY = dy / joystickRadius
                }
                
                // Check action buttons
                if (event.x > width - 200 && event.x < width - 100) {
                    if (event.y > height - 200 && event.y < height - 100) {
                        gameEngine.fireWeapon()
                    }
                    if (event.y > height - 100) {
                        gameEngine.toggleAim()
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                gameEngine.joystickX = 0f
                gameEngine.joystickY = 0f
            }
        }
        return true
    }
}