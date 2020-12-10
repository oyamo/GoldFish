package com.oyasis.goldfish

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import kotlinx.coroutines.*

class Splash : AppCompatActivity() {
    private lateinit var musicIntent: Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Make the activity full screen

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        // Set the content View
        setContentView(R.layout.activity_splash)

        // Music player for SoundTrack
        musicIntent = Intent(this, STrackService::class.java)


        GlobalScope.launch(Dispatchers.Main) {
            delay(2000)
            startActivity(Intent(this@Splash, FirstTime::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        startService(musicIntent)
    }


}