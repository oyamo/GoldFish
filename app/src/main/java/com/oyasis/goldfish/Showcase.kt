package com.oyasis.goldfish

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.coroutines.GlobalScope

class Showcase : AppCompatActivity() {
    lateinit var scoreView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showcase)

        var score = 20 + ( Math.random() * 30 )
        scoreView = findViewById(R.id.score)

        scoreView.text = "${score.toInt()}"
    }

    fun replay(view: View) {
        startActivity(Intent(this, GameActivity::class.java))
        finish()
    }
}