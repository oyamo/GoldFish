package com.oyasis.goldfish

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.checkbox.MaterialCheckBox

public class FirstTime : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_time)
    }

    fun play(view: View) {
        val cb = findViewById<MaterialCheckBox>(R.id.playSound)
        if(!cb.isChecked){
            stopService(Intent(this, STrackService::class.java))
        }

        startActivity(Intent(this, GameActivity::class.java))
    }
}