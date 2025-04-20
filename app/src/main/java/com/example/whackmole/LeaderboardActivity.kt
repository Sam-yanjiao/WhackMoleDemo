package com.example.whackmole

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LeaderboardActivity : AppCompatActivity() {
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

        databaseHelper = DatabaseHelper(this)
        recyclerView = findViewById(R.id.leaderboardRecyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        displayLeaderboard()

        findViewById<Button>(R.id.clearLeaderboardButton).setOnClickListener {
            databaseHelper.clearLeaderboard()
            displayLeaderboard()
            Toast.makeText(this, "Leaderboard cleared", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayLeaderboard() {
        val leaderboard = databaseHelper.getLeaderboard()
        if (leaderboard.isEmpty()) {
            Toast.makeText(this, "No leaderboard data yet", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = LeaderboardAdapter(leaderboard)
    }
}