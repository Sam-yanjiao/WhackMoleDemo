package com.example.whackmole

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ScoreActivity : AppCompatActivity() {
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        databaseHelper = DatabaseHelper(this)

        val score = intent.getIntExtra("score", 0)
        val finalScoreTextView = findViewById<TextView>(R.id.finalScoreTextView)
        finalScoreTextView.text = "Score: $score"

        val playerNameEditText = findViewById<EditText>(R.id.playerNameEditText)
        val saveScoreButton = findViewById<Button>(R.id.saveScoreButton)
        val leaderboardButton = findViewById<Button>(R.id.leaderboardButton)
        val playAgainButton = findViewById<Button>(R.id.playAgainButton)
        val mainMenuButton = findViewById<Button>(R.id.mainMenuButton)

        saveScoreButton.setOnClickListener {
            val playerName = playerNameEditText.text.toString().ifEmpty { "Player" }
            GlobalScope.launch(Dispatchers.IO) {
                val success = databaseHelper.insertScore(playerName, score)
                runOnUiThread {
                    if (success) {
                        Toast.makeText(this@ScoreActivity, "Score saved!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@ScoreActivity, "Failed to save score", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        leaderboardButton.setOnClickListener {
            startActivity(Intent(this, LeaderboardActivity::class.java))
        }

        playAgainButton.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
            finish()
        }

        mainMenuButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}