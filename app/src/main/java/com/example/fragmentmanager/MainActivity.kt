package com.example.fragmentmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var currentItemId = R.id.home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            startToFragment(getNewFragment(currentItemId), getBackStackName(currentItemId))
        }

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        bottomNavigation.setOnItemSelectedListener {
            if (currentItemId != it.itemId) {
                moveToFragment(it.itemId)
                currentItemId = it.itemId
            }
            true
        }
    }

    private fun moveToFragment(itemId: Int) {
        val fragmentManager = supportFragmentManager

        fragmentManager.saveBackStack(getBackStackName(currentItemId))

        val backStackName = getBackStackName(itemId)
        val fragment = fragmentManager.findFragmentByTag(backStackName)

        if (fragment == null) {
            startToFragment(getNewFragment(itemId), backStackName)
        } else {
            fragmentManager.restoreBackStack(backStackName)
        }
    }

    private fun startToFragment(fragment: Fragment, backStackName: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment, backStackName)
            .commit()
    }

    private fun getBackStackName(itemId: Int): String {
        return when (itemId) {
            R.id.home -> HOME_STACK
            R.id.profile -> PROFILE_STACK
            else -> throw IllegalStateException("unknown itemId $itemId")
        }
    }

    private fun getNewFragment(itemId: Int): Fragment =
        when (itemId) {
            R.id.home -> Home.newInstance()
            R.id.profile -> Profile.newInstance()
            else -> throw IllegalStateException("unknown itemId $itemId")
        }

    companion object {
        private const val HOME_STACK = "home_stack"
        private const val PROFILE_STACK = "profile_stack"
    }
}