package com.example.whackmole

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class GameActivity : AppCompatActivity(), GameControlsFragment.GameControlsListener {
    private lateinit var moleGrid: GridLayout
    private lateinit var timerTextView: TextView
    private lateinit var scoreTextView: TextView
    private lateinit var gameTimer: CountDownTimer
    private lateinit var mediaPlayer: MediaPlayer
    private val handler = Handler(Looper.getMainLooper())
    private val random = Random.Default

    private var score = 0
    private var timeLeft = 60
    var isPaused = false
    private var remainingTime = 0L

    private val moleRunnable = object : Runnable {
        override fun run() {
            spawnMole()
            val delay = (1000 - (score / 10)).coerceAtLeast(300)
            handler.postDelayed(this, delay.toLong())
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        findViewById<Button>(R.id.pauseButton).setOnClickListener {
            onPauseClicked()
        }

        findViewById<Button>(R.id.homeButton).setOnClickListener {
            onHomeClicked()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.controlsContainer, GameControlsFragment().apply {
                setListener(this@GameActivity)
            })
            .commit()

        moleGrid = findViewById(R.id.moleGrid)
        timerTextView = findViewById(R.id.timerTextView)
        scoreTextView = findViewById(R.id.scoreTextView)

        mediaPlayer = MediaPlayer.create(this, R.raw.background_music)
        mediaPlayer.isLooping = true

        setupGameGrid()
        startGame()
    }

    override fun onHomeClicked() {
        finish()
    }

    override fun onPauseClicked() {
        if (isPaused) {
            isPaused = false
            startGameTimer(remainingTime)
            handler.postDelayed(moleRunnable, 300)
            mediaPlayer.start()
        } else {
            isPaused = true
            gameTimer.cancel()
            handler.removeCallbacks(moleRunnable)
            mediaPlayer.pause()
        }
    }

    private fun setupGameGrid() {
        moleGrid.removeAllViews()

        val cellWidth = 276.dpToPx() / 4
        val cellHeight = 409.dpToPx() / 4

        for (i in 0 until 16) {
            val moleHole = ImageButton(this).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 68.dpToPx()
                    height = 68.dpToPx()
                    columnSpec = GridLayout.spec(i % 4, 1f)
                    rowSpec = GridLayout.spec(i / 4, 1f)
                }
                setBackgroundResource(R.drawable.mole_hole)
                tag = "empty"
                setOnClickListener { onMoleClicked(it) }
                adjustViewBounds = true
                scaleType = ImageView.ScaleType.FIT_CENTER
            }
            moleGrid.addView(moleHole)
        }
    }

    fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

    private fun startGameTimer(duration: Long = 60000) {
        gameTimer = object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished
                timeLeft = (millisUntilFinished / 1000).toInt()
                timerTextView.text = "Time: ${timeLeft}s"
            }

            override fun onFinish() {
                endGame()
            }
        }.start()
    }

    private fun spawnMole() {
        if (isPaused) return

        val randomIndex = random.nextInt(16)
        val moleHole = moleGrid.getChildAt(randomIndex) as? ImageButton ?: return

        if (moleHole.tag == "empty") {
            moleHole.setImageResource(R.drawable.mole)
            moleHole.tag = "mole"

            handler.postDelayed({
                if (moleHole.tag == "mole") {
                    moleHole.setImageResource(0)
                    moleHole.tag = "empty"
                }
            }, 800)
        }

        moleHole.setBackgroundResource(R.drawable.mole_hole)
        moleHole.apply {
            setImageResource(R.drawable.mole)
            scaleType = ImageView.ScaleType.FIT_CENTER
            adjustViewBounds = true
        }
    }

    private fun startGame() {
        score = 0
        timeLeft = 60
        isPaused = false
        updateScoreDisplay()
        timerTextView.text = "Time: ${timeLeft}s"
        startGameTimer()
        handler.postDelayed(moleRunnable, 1000)
        mediaPlayer.start()
    }

    private fun onMoleClicked(view: View) {
        val moleHole = view as? ImageButton ?: return
        if (moleHole.tag == "mole") {
            score++
            moleHole.setImageResource(0)
            moleHole.tag = "empty"
            updateScoreDisplay()
        }
    }

    private fun updateScoreDisplay() {
        scoreTextView.text = "Score: $score"
    }

    private fun endGame() {
        handler.removeCallbacks(moleRunnable)
        gameTimer.cancel()
        mediaPlayer.stop()
        mediaPlayer.prepare()

        val prefs = getSharedPreferences("GamePrefs", MODE_PRIVATE)
        val highScore = prefs.getInt("highScore", 0)
        if (score > highScore) {
            prefs.edit().putInt("highScore", score).apply()
        }

        Intent(this, ScoreActivity::class.java).apply {
            putExtra("score", score)
            startActivity(this)
        }
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(moleRunnable)
        gameTimer.cancel()
        mediaPlayer.release()
    }
}