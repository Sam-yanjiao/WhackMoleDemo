package com.example.whackmole

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.Fragment

class GameControlsFragment : Fragment() {
    private var pauseButton: Button? = null
    private var listener: GameControlsListener? = null

    interface GameControlsListener {
        fun onPauseClicked()
        fun onHomeClicked()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        pauseButton = view.findViewById(R.id.pauseButton)
        val homeButton = view.findViewById<Button>(R.id.homeButton)

        pauseButton?.setOnClickListener {
            listener?.onPauseClicked()
            updatePauseButtonText()
        }

        homeButton.setOnClickListener {
            listener?.onHomeClicked()
        }

    }

    fun updatePauseButtonText() {
        pauseButton?.text = if ((activity as? GameActivity)?.isPaused == true) {
            "Resume"
        } else {
            "Pause"
        }
    }

    fun setListener(listener: GameControlsListener) {
        this.listener = listener
    }
}