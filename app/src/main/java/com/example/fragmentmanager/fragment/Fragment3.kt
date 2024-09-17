package com.example.fragmentmanager.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.fragmentmanager.R
import com.example.fragmentmanager.navigation.NavigationManager
import com.example.fragmentmanager.navigation.getNavigationManager

class Fragment3 : Fragment(R.layout.fragment_3) {

    private lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigationManager = getNavigationManager(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<Button>(R.id.button3).setOnClickListener {
            navigationManager.launchFragment(Fragment4.newInstance())
        }
    }

    companion object {
        fun newInstance(): Fragment3 = Fragment3()
    }
}