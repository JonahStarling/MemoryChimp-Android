package com.jonahstarling.memorychimp

import android.content.Context
import androidx.preference.PreferenceManager

class ScoreManager(private val context: Context) {

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun reset() {
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }

    fun updateScore(score: Int, difficulty: Difficulty) {
        val editor = preferences.edit()
        editor.putInt(difficulty.name, score)
        editor.apply()
    }

    fun getScore(difficulty: Difficulty): Int {
        return preferences.getInt(difficulty.name, 0)
    }
}