package com.jonahstarling.memorychimp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_value.view.*

class GameAdapter(private val context: Context,
                  private val gridSize: Int,
                  private val startDelay: Long,
                  private val boxesToTap: Int): RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        return GameViewHolder(LayoutInflater.from(context).inflate(R.layout.cell_value, parent, false), startDelay)
    }

    override fun getItemCount(): Int = gridSize

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        // TODO - still haven't decided how I want to handle creating a level
    }

    class GameViewHolder(private val view: View, private val startDelay: Long) : RecyclerView.ViewHolder(view) {
        val boxText: TextView = view.cellText

        init {
            boxText.isEnabled = false
            animateToBlock()
        }

        private fun animateToBlock() {
            view.setBackgroundColor(Color.parseColor("#F0F0F0"))
            view.background.alpha = 0
            val letterAnimator = ValueAnimator.ofInt(0, 255)
            letterAnimator.duration = 100L
            letterAnimator.startDelay = startDelay
            letterAnimator.interpolator = AccelerateInterpolator()
            letterAnimator.addUpdateListener {
                view.background.alpha = letterAnimator.animatedValue as Int
            }
            letterAnimator.addListener(object: AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    boxText.isEnabled = true
                }
            })
            letterAnimator.start()
        }

        private fun animateToValue() {
            view.setBackgroundColor(Color.parseColor("#F0F0F0"))
            val letterAnimator = ValueAnimator.ofInt(255, 0)
            letterAnimator.duration = 100L
            letterAnimator.interpolator = AccelerateInterpolator()
            letterAnimator.addUpdateListener {
                view.background.alpha = letterAnimator.animatedValue as Int
            }
            letterAnimator.start()
        }
    }
}