package com.oyasis.goldfish

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import kotlinx.coroutines.delay

class STrackService : Service() {

    private lateinit var mp: MediaPlayer
    override fun onBind(intent: Intent): IBinder? {
        return null;
    }

    override fun onCreate() {
        mp = MediaPlayer.create(this, R.raw.game_track_1)
        mp.isLooping = true
    }

    override fun onDestroy() {
        mp.stop()
        mp.release()
        stopSelf()
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        mp.setVolume(70f, 70f)
        mp.start()
        return START_NOT_STICKY
    }
}