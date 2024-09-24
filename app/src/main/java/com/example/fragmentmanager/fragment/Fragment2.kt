package com.example.fragmentmanager.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.fragmentmanager.R
import com.example.fragmentmanager.navigation.NavigationManager
import com.example.fragmentmanager.navigation.getNavigationManager
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Fragment2 : Fragment(R.layout.fragment_2) {

    private lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigationManager = getNavigationManager(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressBar = requireActivity().findViewById<LinearProgressIndicator>(R.id.progressBar2)

        lifecycleScope.launch {
            repeat(5) {
                delay(1000)
                progressBar.progress = it + 1
            }
            delay(250)

            navigationManager.backMain()
        }
    }

    companion object {
        fun newInstance(): Fragment2 = Fragment2()
    }
}