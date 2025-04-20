package com.example.whackmole

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var playButton: Button
    private lateinit var leaderboardButton: Button
    private lateinit var highScoreTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playButton = findViewById(R.id.playButton)
        leaderboardButton = findViewById(R.id.leaderboardButton)
        highScoreTextView = findViewById(R.id.highScoreTextView)

        val prefs: SharedPreferences = getSharedPreferences("GamePrefs", MODE_PRIVATE)
        val highScore = prefs.getInt("highScore", 0)
        highScoreTextView.text = "High Score: $highScore"

        playButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

        leaderboardButton.setOnClickListener {
            val intent = Intent(this, LeaderboardActivity::class.java)
            startActivity(intent)
        }
    }
}