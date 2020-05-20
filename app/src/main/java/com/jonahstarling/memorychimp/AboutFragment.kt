package com.jonahstarling.memorychimp

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_about.*

class AboutFragment: DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_about, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setWindowAnimations(R.style.FullScreenDialog)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        learnMoreButton.setOnClickListener { learnMoreTapped() }
        closeButton.setOnClickListener { dismiss() }
    }

    private fun learnMoreTapped() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.cell.com/current-biology/pdf/S0960-9822(07)02088-X.pdf"))
        startActivity(browserIntent)
    }

    companion object {
        val TAG = AboutFragment::class.java.simpleName

        fun newInstance() = AboutFragment()
    }
}