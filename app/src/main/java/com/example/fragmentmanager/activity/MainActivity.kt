package com.example.fragmentmanager.activity

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.fragmentmanager.R
import com.example.fragmentmanager.navigation.NavigationManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var navigationManager: NavigationManager

    lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationManager = NavigationManager(
            this,
            supportFragmentManager
        )
        bottomNavigation = findViewById(R.id.bottomNavigation)

        bottomNavigation.setOnItemSelectedListener {
            navigationManager.goToTab(it.itemId)
            true
        }

        onBackPressedDispatcher.addCallback(this) {
            navigationManager.onBackPressed(::finish)
        }
    }
}