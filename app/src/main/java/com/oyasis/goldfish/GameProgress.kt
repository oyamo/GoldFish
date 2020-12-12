package com.oyasis.goldfish

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GameProgress(val context: Context) {
    private val fileName = "game_progress"
    private val progressLabel = "progress"
    private val gamesPlayedLabel = "gamesPlayed"
    private val fewestTurnsLabel = "fewestTurns"
    private val shortestTimeTakenLabel = "shortestTime"
    private val longestProgressLabel = "longestTime"
    private val firstTimeLuckyLabel = "1sttime"
    private val threeInarowLabel ="3inarow"
    private val fiveInARowLabel = "fiveInARow"

    private val sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)

    var firstTimeLucky: Boolean
        get() {
            return sharedPreferences.getBoolean(firstTimeLuckyLabel, false);
        }
        set(value) {
            GlobalScope.launch(Dispatchers.IO) {
                val editor = sharedPreferences.edit()
                editor.putBoolean(firstTimeLuckyLabel, value)
                editor.apply()
            }
        }

    var threeInArow: Boolean
        get() {
            return sharedPreferences.getBoolean(threeInarowLabel, false);
        }
        set(value) {
            GlobalScope.launch(Dispatchers.IO) {
                val editor = sharedPreferences.edit()
                editor.putBoolean(shortestTimeTakenLabel, value)
                editor.apply()
            }
        }

    var fiveInArow: Boolean
        get() {
            return sharedPreferences.getBoolean(fiveInARowLabel, false);
        }
        set(value) {
            GlobalScope.launch(Dispatchers.IO) {
                val editor = sharedPreferences.edit()
                editor.putBoolean(fiveInARowLabel, value)
                editor.apply()
            }
        }

    var shortestTime: Int

        get() {
            return sharedPreferences.getInt(shortestTimeTakenLabel, 0);
        }
        set(value) {
            GlobalScope.launch(Dispatchers.IO) {
                val editor = sharedPreferences.edit()
                editor.putInt(shortestTimeTakenLabel, value)
                editor.apply()
            }
        }

    var longestProgress: Int
        get() {
            return sharedPreferences.getInt(longestProgressLabel, 0);
        }
        set(value) {
            GlobalScope.launch(Dispatchers.IO) {
                val editor = sharedPreferences.edit()
                editor.putInt(longestProgressLabel, value)
                editor.apply()
            }
        }

    var fewestTurns: Int
        get() {
            return sharedPreferences.getInt(fewestTurnsLabel, 0);
        }
        set(value) {
            GlobalScope.launch(Dispatchers.IO) {
                val editor = sharedPreferences.edit()
                editor.putInt(fewestTurnsLabel, value)
                editor.apply()
            }
        }

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