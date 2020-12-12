package com.oyasis.goldfish

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GameActivity : AppCompatActivity() {


    // Declare the recyclerView
    private lateinit var gameGrid: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // Assign the recyclerview
        gameGrid = findViewById(R.id.gameGrid)

        // Initialise the grid
        gameGrid.layoutManager = GridLayoutManager(this, 5)
        gameGrid.setHasFixedSize(true)
        val fishAdapter = FishAdapter()

        gameGrid.adapter = fishAdapter


        // Get the height of the screen
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)



        // Draw the game grid away from the main Thread
        GlobalScope.launch(Dispatchers.Main) {

            var fishes = mutableListOf<Fish>()

            var fishPics = listOf(R.drawable.fish_1, R.drawable.fish_2, R.drawable.fish_3, R.drawable.fish_4, R.drawable.fish_5, R.drawable.fish_6, R.drawable.fish_7, R.drawable.fish_8, R.drawable.fish_9, R.drawable.fish_10, R.drawable.fish_11, R.drawable.fish_12, R.drawable.fish_13, R.drawable.fish_14, R.drawable.fish_15, R.drawable.fish_16, R.drawable.fish_17, R.drawable.fish_18, R.drawable.fish_19, R.drawable.fish_20)

            // Insert Fishes into the list
            for (i in 1..40) {
                val fishType = (( i + 1) / 2 ) - 1
                val fish = Fish( fishType , fishPics[fishType], false)
                fishes.add(fish)
            }

            // Randomly shuffle the fishes
            fishes.shuffle()

            fishes.forEachIndexed { index, fish ->
                Log.d("Cheat", "$index -> ${fish.type}")
            }
            fishAdapter.setDisplayHeight(displayMetrics.heightPixels)
            fishAdapter.setFishes(fishes)

            // Set onclick handler

        }
        fishAdapter.toggleRestingMode(true)
        val dialog = Dialog(this@GameActivity)
        dialog.setContentView(R.layout.home_pop_up)
        var playButton = dialog.findViewById<MaterialButton>(R.id.play)
        var highScoreBtn = dialog.findViewById<MaterialButton>(R.id.highScores)
        var achievements = dialog.findViewById<MaterialButton>(R.id.achievements)

        playButton.setOnClickListener {
            dialog.dismiss()
            fishAdapter.toggleRestingMode(false)
            fishAdapter.showRealFishes(true)
        }

        highScoreBtn.setOnClickListener {
            dialog.dismiss();
            startActivity(Intent(this@GameActivity, HighScores::class.java))
            finish()
        }

        achievements.setOnClickListener {
            dialog.dismiss();
            startActivity(Intent(this@GameActivity, Achievements::class.java))
            finish()
        }

        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.show()
    }

    override fun onStop() {
        super.onStop()
        finish();
    }

    override fun onPause() {
        super.onPause()
        finish();
    }
}