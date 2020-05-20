package com.jonahstarling.memorychimp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_main_menu.*
import kotlinx.android.synthetic.main.menu_footer_layout.*
import kotlinx.android.synthetic.main.menu_layout.*
import kotlinx.android.synthetic.main.title_layout.*

class MainMenuFragment: Fragment() {

    private var difficulty = Difficulties.easy

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

        easyButton.setOnClickListener { easyDifficultySelected() }
        mediumButton.setOnClickListener { mediumDifficultySelected() }
        hardButton.setOnClickListener { hardDifficultySelected() }
        impossibleButton.setOnClickListener { impossibleDifficultySelected() }

        playButton.setOnClickListener { animateMainMenuScreenOut() }

        aboutButton.setOnClickListener { navigateToAboutFragment() }
        settingsButton.setOnClickListener { navigateToSettingsFragment() }

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
        menuFooterAnimator.startDelay = 200L
        menuFooterAnimator.interpolator = DecelerateInterpolator()
        menuFooterAnimator.addUpdateListener {
            menuFooterLayout.x = menuFooterStartX - (menuFooterStartX * it.animatedValue as Float)
        }
        menuFooterAnimator.start()
    }

    private fun animateMainMenuScreenOut() {
        val location = IntArray(2)
        titleLayout.getLocationOnScreen(location)
        val titleStartX: Int = location[0]
        menuLayout.getLocationOnScreen(location)
        val menuStartX: Int = location[0]
        menuFooterLayout.getLocationOnScreen(location)
        val menuFooterStartX: Int = location[0]


        val titleAnimator = ValueAnimator.ofFloat(0.0f, 1.0f)
        titleAnimator.duration = 400L
        titleAnimator.interpolator = DecelerateInterpolator()
        titleAnimator.addUpdateListener {
            titleLayout.x = titleStartX - ((titleStartX - (0 - titleLayout.width)) * it.animatedValue as Float)
        }
        titleAnimator.start()
        val menuAnimator = ValueAnimator.ofFloat(0.0f, 1.0f)
        menuAnimator.duration = 400L
        menuAnimator.startDelay = 100L
        menuAnimator.interpolator = DecelerateInterpolator()
        menuAnimator.addUpdateListener {
            menuLayout.x = menuStartX - ((menuStartX - (0 - menuLayout.width)) * it.animatedValue as Float)
        }
        menuAnimator.start()
        val menuFooterAnimator = ValueAnimator.ofFloat(0.0f, 1.0f)
        menuFooterAnimator.duration = 400L
        menuFooterAnimator.startDelay = 200L
        menuFooterAnimator.interpolator = DecelerateInterpolator()
        menuFooterAnimator.addUpdateListener {
            menuFooterLayout.x = menuFooterStartX - ((menuFooterStartX - (0 - menuFooterLayout.width)) * it.animatedValue as Float)
        }
        menuFooterAnimator.addListener(object: AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                navigateToGameFragment()
            }
        })
        menuFooterAnimator.start()
    }

    private fun easyDifficultySelected() {
        difficulty = Difficulties.easy
        updateDifficultyStates(difficulty)
    }

    private fun mediumDifficultySelected() {
        difficulty = Difficulties.medium
        updateDifficultyStates(difficulty)
    }

    private fun hardDifficultySelected() {
        difficulty = Difficulties.hard
        updateDifficultyStates(difficulty)
    }

    private fun impossibleDifficultySelected() {
        difficulty = Difficulties.impossible
        updateDifficultyStates(difficulty)
    }

    private fun updateDifficultyStates(difficulty: Difficulty) {
        if (difficulty.name == Difficulties.easy.name) toggleButtonOn(easyButton)
        else toggleButtonOff(easyButton)

        if (difficulty.name == Difficulties.medium.name) toggleButtonOn(mediumButton)
        else toggleButtonOff(mediumButton)

        if (difficulty.name == Difficulties.hard.name) toggleButtonOn(hardButton)
        else toggleButtonOff(hardButton)

        if (difficulty.name == Difficulties.impossible.name) toggleButtonOn(impossibleButton)
        else toggleButtonOff(impossibleButton)
    }

    private fun toggleButtonOn(button: Button) {
        button.setBackgroundColor(resources.getColor(R.color.colorAccent, activity?.theme))
        button.setTextColor(resources.getColor(R.color.colorPrimary, activity?.theme))
    }

    private fun toggleButtonOff(button: Button) {
        button.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark, activity?.theme))
        button.setTextColor(resources.getColor(R.color.colorText, activity?.theme))
    }

    fun navigateToGameFragment() {
        (activity as MainActivity).addFragment(GameFragment.newInstance(), GameFragment.TAG)
    }

    private fun navigateToAboutFragment() {
        val aboutDialogFragment = AboutFragment.newInstance()
        fragmentManager?.let {
            aboutDialogFragment.show(it, AboutFragment.TAG)
        }
    }

    private fun navigateToSettingsFragment() {
        val settingsDialogFragment = SettingsFragment.newInstance()
        fragmentManager?.let {
            settingsDialogFragment.show(it, SettingsFragment.TAG)
        }
    }

    companion object {
        val TAG = MainMenuFragment::class.java.simpleName

        fun newInstance() = MainMenuFragment()
    }
}