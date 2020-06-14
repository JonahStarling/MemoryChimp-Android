package com.jonahstarling.memorychimp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.AccelerateInterpolator
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_value.view.*
import kotlinx.android.synthetic.main.fragment_launch.*
import kotlinx.android.synthetic.main.title_layout.*

class LaunchFragment: Fragment() {

    private lateinit var titleAdapter: TitleAdapter
    private lateinit var titleLayoutManager: GridLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_launch, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleAdapter = TitleAdapter(requireContext(), listOf("M","E","M","O","R","Y","C","H","I","M","P"))
        titleLayoutManager = GridLayoutManager(activity, 6, GridLayoutManager.VERTICAL, false)
        titleGrid.adapter = titleAdapter
        titleGrid.layoutManager = titleLayoutManager

        view.viewTreeObserver.addOnGlobalLayoutListener(object:
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                animateLaunchScreenOut()
            }
        })
    }

    fun animateLaunchScreenOut() {
        val location = IntArray(2)
        titleLayout.getLocationOnScreen(location)
        val titleOriginalX: Int = location[0]

        val titleAnimator = ValueAnimator.ofFloat(0.0f, 1.0f)
        titleAnimator.duration = 400L
        titleAnimator.startDelay = 1400L
        titleAnimator.interpolator = AccelerateInterpolator()
        titleAnimator.addUpdateListener {
            titleLayout.x = titleOriginalX - ((titleOriginalX - (0 - titleLayout.width)) * it.animatedValue as Float)
        }
        titleAnimator.addListener(object: AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                navigateToMainMenuFragment()
            }
        })
        titleAnimator.start()
    }

    fun navigateToMainMenuFragment() {
        (activity as MainActivity).replaceFragment(MainMenuFragment.newInstance(), MainMenuFragment.TAG)
    }

    companion object {
        val TAG = LaunchFragment::class.java.simpleName

        fun newInstance() = LaunchFragment()
    }
}