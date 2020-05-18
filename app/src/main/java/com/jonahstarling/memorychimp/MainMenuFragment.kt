package com.jonahstarling.memorychimp

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_launch.*
import kotlinx.android.synthetic.main.fragment_launch.titleLayout
import kotlinx.android.synthetic.main.fragment_main_menu.*
import kotlinx.android.synthetic.main.title_layout.*

class MainMenuFragment: Fragment() {

    private lateinit var titleAdapter: TitleAdapter
    private lateinit var titleLayoutManager: GridLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_main_menu, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleAdapter = TitleAdapter(this.requireContext(), listOf("M","E","M","O","R","Y","C","H","I","M","P"), true)
        titleLayoutManager = GridLayoutManager(activity, 6, GridLayoutManager.VERTICAL, false)
        titleGrid.adapter = titleAdapter
        titleGrid.layoutManager = titleLayoutManager

        view.viewTreeObserver.addOnGlobalLayoutListener(object:
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                animateMainMenuScreenIn(view)
            }
        })
    }

    private fun animateMainMenuScreenIn(view: View) {
        val titleStartX = view.width.toFloat()
        titleLayout.x = titleStartX
        val menuStartX = view.width.toFloat()
        menuLayout.x = menuStartX
        val menuFooterStartX = view.width.toFloat()
        menuFooterLayout.x = menuFooterStartX

        val titleAnimator = ValueAnimator.ofFloat(0.0f, 1.0f)
        titleAnimator.duration = 400L
        titleAnimator.interpolator = DecelerateInterpolator()
        titleAnimator.addUpdateListener {
            titleLayout.x = titleStartX - (titleStartX * it.animatedValue as Float)
        }
        titleAnimator.start()
        val menuAnimator = ValueAnimator.ofFloat(0.0f, 1.0f)
        menuAnimator.duration = 400L
        menuAnimator.startDelay = 100L
        menuAnimator.interpolator = DecelerateInterpolator()
        menuAnimator.addUpdateListener {
            menuLayout.x = menuStartX - (menuStartX * it.animatedValue as Float)
        }
        menuAnimator.start()
        val menuFooterAnimator = ValueAnimator.ofFloat(0.0f, 1.0f)
        menuFooterAnimator.duration = 400L
        menuFooterAnimator.startDelay = 100L
        menuFooterAnimator.interpolator = DecelerateInterpolator()
        menuFooterAnimator.addUpdateListener {
            menuFooterLayout.x = menuFooterStartX - (menuFooterStartX * it.animatedValue as Float)
        }
        menuFooterAnimator.start()
    }

    private fun animateMainMenuScreenOut() {
        // TODO
    }

    fun navigateToGameFragment() {
        (activity as MainActivity).replaceFragment(GameFragment.newInstance(), GameFragment.TAG)
    }

    fun navigateToAboutFragment() {
        (activity as MainActivity).replaceFragment(AboutFragment.newInstance(), AboutFragment.TAG)
    }

    companion object {
        val TAG = MainMenuFragment::class.java.simpleName

        fun newInstance() = MainMenuFragment()
    }
}