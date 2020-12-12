package com.oyasis.goldfish

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class HighScores : AppCompatActivity() {
    lateinit var fewestTurns: TextView;
    lateinit var quickestTurns :TextView
    lateinit var longestSequence: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_scores)

        var gameProgress = GameProgress(this@HighScores)
        fewestTurns = findViewById(R.id.fewestTurns)
        quickestTurns = findViewById(R.id.quickestTime)
        longestSequence = findViewById(R.id.longestSequence)

        fewestTurns.text = "${gameProgress.fewestTurns}"
        quickestTurns.text = "${gameProgress.shortestTime}"
        longestSequence.text = "${gameProgress.longestProgress}"
    }
}