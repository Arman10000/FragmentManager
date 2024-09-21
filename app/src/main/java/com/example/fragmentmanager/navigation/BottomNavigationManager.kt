package com.example.fragmentmanager.navigation

import android.view.View
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationManager(
    private val lifecycleOwner: LifecycleOwner,
    private val bottomNavigation: BottomNavigationView,
    private var fullScreen: Boolean
) : DefaultLifecycleObserver {

    init {
        registerObserver()
    }

    override fun onStart(owner: LifecycleOwner) {
        if (fullScreen) bottomNavigation.visibility = View.GONE
        else bottomNavigation.visibility = View.VISIBLE
    }

    override fun onDestroy(owner: LifecycleOwner) {
        unregisterObserver()
    }

    private fun registerObserver() {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    private fun unregisterObserver() {
        lifecycleOwner.lifecycle.removeObserver(this)
    }
}