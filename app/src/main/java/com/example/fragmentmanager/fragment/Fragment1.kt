package com.example.fragmentmanager.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.fragmentmanager.R
import com.example.fragmentmanager.navigation.NavigationManager
import com.example.fragmentmanager.navigation.getNavigationManager
import com.google.android.material.progressindicator.CircularProgressIndicator

class Fragment1 : Fragment(R.layout.fragment_1) {

    private lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigationManager = getNavigationManager(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<Button>(R.id.button1).setOnClickListener {
            navigationManager.launchFragment(Fragment2.newInstance())
        }
    }

    companion object {
        fun newInstance(): Fragment1 = Fragment1()
    }
}