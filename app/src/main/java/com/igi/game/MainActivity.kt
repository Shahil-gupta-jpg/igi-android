package com.igi.game

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.igi.game.ui.GameView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Create and set the custom GameView
        val gameView = GameView(this)
        setContentView(gameView)
        
        // Hide the action bar for fullscreen gaming experience
        supportActionBar?.hide()
    }
}