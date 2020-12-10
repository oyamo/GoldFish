package com.oyasis.goldfish

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GameProgress(val context: Context) {
    private val fileName = "game_progress"
    private val progressLabel = "progress"
    private val gamesPlayedLabel = "gamesPlayed"
    private val sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)

    var progress: Int
        // Set the getter for this field
        get() {
            return sharedPreferences.getInt(progressLabel, 0)
        }
        // Set the setter for the progress
        set(value) {
           GlobalScope.launch(Dispatchers.IO) {
               val editor = sharedPreferences.edit()
               editor.putInt(progressLabel, value)
               editor.apply()
           }
        }

    var gamesPlayed: Int
        // Set the getter for this field
        get() {
            return sharedPreferences.getInt(gamesPlayedLabel, 0)
        }
        // Set the setter for the progress
        set(value) {
            GlobalScope.launch(Dispatchers.IO) {
                val editor = sharedPreferences.edit()
                editor.putInt(gamesPlayedLabel, value)
                editor.apply()
            }
        }
}