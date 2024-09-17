package com.example.fragmentmanager.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.fragmentmanager.R
import com.example.fragmentmanager.navigation.NavigationManager
import com.example.fragmentmanager.navigation.getNavigationManager

class Home : Fragment(R.layout.home) {

    private lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigationManager = getNavigationManager(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<Button>(R.id.home).setOnClickListener {
            navigationManager.launchFragment(Fragment1.newInstance())
        }
    }

    companion object {
        fun newInstance(): Home = Home()
    }
}