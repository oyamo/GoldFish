package com.oyasis.goldfish

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class Achievements : AppCompatActivity() {
    private lateinit var ft: ImageView
    private lateinit var threeInArow: ImageView
    private lateinit var fiveInArow: ImageView
    private lateinit var gameProgress: GameProgress
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievements)
        gameProgress = GameProgress(applicationContext)

        ft = findViewById(R.id.firstTimeLucky)
        threeInArow = findViewById(R.id.threeInArow)
        fiveInArow = findViewById(R.id.fiveInArow)

        if (gameProgress.firstTimeLucky) {
            ft.setBackgroundColor(Color.CYAN);
        }
        if (gameProgress.threeInArow) {
            threeInArow.setBackgroundColor(Color.CYAN);
        }
        if (gameProgress.fiveInArow) {
            fiveInArow.setBackgroundColor(Color.CYAN);
        }

    }
}