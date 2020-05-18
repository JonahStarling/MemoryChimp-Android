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

class TitleAdapter(private val context: Context, private val title: List<String>, private val loopAnimation: Boolean = false): RecyclerView.Adapter<TitleAdapter.TitleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        return TitleViewHolder(LayoutInflater.from(context).inflate(R.layout.cell_value, parent, false), loopAnimation)
    }

    override fun getItemCount(): Int = title.size

    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
        holder.letter.text = title[position]
    }

    class TitleViewHolder(private val view: View, private val loopAnimation: Boolean) : RecyclerView.ViewHolder(view) {
        val letter: TextView = view.cellText

        init {
            animateToBlock()
        }

        private fun animateToBlock() {
            view.setBackgroundColor(Color.parseColor("#F0F0F0"))
            view.background.alpha = 0
            val letterAnimator = ValueAnimator.ofInt(0, 255)
            letterAnimator.duration = 100L
            letterAnimator.startDelay = 1000L + (Math.random() * 100).toLong()
            letterAnimator.interpolator = AccelerateInterpolator()
            letterAnimator.addUpdateListener {
                view.background.alpha = letterAnimator.animatedValue as Int
            }
            letterAnimator.addListener(object: AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    if (loopAnimation) {
                        animateToValue()
                    }
                }
            })
            letterAnimator.start()
        }

        private fun animateToValue() {
            view.setBackgroundColor(Color.parseColor("#F0F0F0"))
            val letterAnimator = ValueAnimator.ofInt(255, 0)
            letterAnimator.duration = 100L
            letterAnimator.startDelay = 500L + (Math.random() * 100).toLong()
            letterAnimator.interpolator = AccelerateInterpolator()
            letterAnimator.addUpdateListener {
                view.background.alpha = letterAnimator.animatedValue as Int
            }
            letterAnimator.addListener(object: AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    if (loopAnimation) {
                        animateToBlock()
                    }
                }
            })
            letterAnimator.start()
        }
    }
}