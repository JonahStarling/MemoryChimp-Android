package com.jonahstarling.memorychimp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment: DialogFragment() {

    private lateinit var scoreManager: ScoreManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        scoreManager = ScoreManager(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_settings, container, false)

    override fun onStart() {
        super.onStart()
        dialog?.window?.setWindowAnimations(R.style.FullScreenDialog)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        easyScore.text = scoreManager.getScore(Difficulties.easy).toString()
        mediumScore.text = scoreManager.getScore(Difficulties.medium).toString()
        hardScore.text = scoreManager.getScore(Difficulties.hard).toString()
        impossibleScore.text = scoreManager.getScore(Difficulties.impossible).toString()

        resetScoresButton.setOnClickListener { resetScores() }
        closeButton.setOnClickListener { dismiss() }
    }

    private fun resetScores() {
        scoreManager.reset()
    }

    companion object {
        val TAG = SettingsFragment::class.java.simpleName

        fun newInstance() = SettingsFragment()
    }
}