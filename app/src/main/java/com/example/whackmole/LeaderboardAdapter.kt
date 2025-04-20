package com.example.whackmole

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LeaderboardAdapter(private val leaderboard: List<ScoreEntry>) :
    RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rankTextView: TextView = itemView.findViewById(R.id.rankTextView)
        val playerNameTextView: TextView = itemView.findViewById(R.id.playerNameTextView)
        val scoreTextView: TextView = itemView.findViewById(R.id.scoreTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_leaderboard, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val scoreEntry = leaderboard[position]
        holder.rankTextView.text = "${position + 1}."
        holder.playerNameTextView.text = scoreEntry.playerName
        holder.scoreTextView.text = scoreEntry.score.toString()
    }

    override fun getItemCount(): Int = leaderboard.size
}