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

class Fragment4 : Fragment(R.layout.fragment_4) {

    private lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigationManager = getNavigationManager(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressBar = requireActivity().findViewById<LinearProgressIndicator>(R.id.progressBar4)

        lifecycleScope.launch {
            repeat(5) {
                delay(1000)
                progressBar.progress = it + 1
            }
            delay(250)

            navigationManager.back()
        }
    }

    companion object {
        fun newInstance(): Fragment4 = Fragment4()
    }
}