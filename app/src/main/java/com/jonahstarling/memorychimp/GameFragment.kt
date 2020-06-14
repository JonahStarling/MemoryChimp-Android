package com.jonahstarling.memorychimp

import android.animation.ValueAnimator
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_game.*
import kotlin.random.Random

class GameFragment(private val difficulty: Difficulty): Fragment() {

    private lateinit var gameAdapter: GameAdapter
    private lateinit var gameLayoutManager: GridLayoutManager

    private val grid = Array<Int?>(gridSize){null}
    private val currentNumber = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_game, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridSize = 36
        val currentBoxLevel = difficulty.maxBoxes

        createLevel(currentBoxLevel)

        gameAdapter = GameAdapter(requireContext(), grid, difficulty.time)
        gameLayoutManager = GridLayoutManager(activity, 6, GridLayoutManager.VERTICAL, false)
        gameGrid.adapter = gameAdapter
        gameGrid.layoutManager = gameLayoutManager

        startCountdown()

        view.viewTreeObserver.addOnGlobalLayoutListener(object:
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                animateGameScreenIn(view)
            }
        })
    }

    private fun animateGameScreenIn(view: View) {
        view.alpha = 0f
        val gameBackgroundAnimator = ValueAnimator.ofFloat(0.0f, 1.0f)
        gameBackgroundAnimator.duration = 200L
        gameBackgroundAnimator.interpolator = AccelerateInterpolator()
        gameBackgroundAnimator.addUpdateListener {
            view.alpha = gameBackgroundAnimator.animatedValue as Float
        }
        gameBackgroundAnimator.start()
    }

    private fun startCountdown() {
        countdown.text = (difficulty.time / 1000).toString()
        val startTimer = object : CountDownTimer(difficulty.time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countdown.text = ((millisUntilFinished / 1000) + 1).toString()
            }

            override fun onFinish() {
                countdown.visibility = View.GONE
            }
        }
        startTimer.start()
    }

    private fun createLevel(boxesToTap: Int) {
        for (box in 1..boxesToTap) {
            var placed = false
            while (!placed) {
                val possiblePosition = Random.nextInt(0, gridSize)
                if (grid[possiblePosition] == null) {
                    placed = true
                    grid[possiblePosition] = box
                }
            }
        }
    }

    companion object {
        val TAG = GameFragment::class.java.simpleName
        const val gridSize = 36
    }
}