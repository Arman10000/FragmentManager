package com.example.fragmentmanager.navigation

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.savedstate.SavedStateRegistryOwner
import com.example.fragmentmanager.R
import com.example.fragmentmanager.activity.MainActivity
import com.example.fragmentmanager.fragment.Home
import com.example.fragmentmanager.fragment.Profile
import com.google.android.material.bottomnavigation.BottomNavigationView

class NavigationManager(
    private val savedStateRegistryOwner: SavedStateRegistryOwner,
    private val supportFragmentManager: FragmentManager
) : DefaultLifecycleObserver {

    private var currentTabId: Int = R.id.home

    private val tabsStarted: MutableMap<String, Boolean> = mutableMapOf(
        HOME_STACK to false, PROFILE_STACK to false
    )

    init {
        if (savedStateRegistryOwner.lifecycle.currentState != Lifecycle.State.INITIALIZED) {
            error("can't build ${this::class.java.simpleName} after onCreate")
        }

        registerObserver()

        restoreInstanceState {
            currentTabId = it.getInt(CURRENT_TAB_ID)
            tabsStarted[HOME_STACK] = it.getBoolean(HOME_STACK)
            tabsStarted[PROFILE_STACK] = it.getBoolean(PROFILE_STACK)
        }

        registerSavedInstanceStateListener {
            bundleOf(
                CURRENT_TAB_ID to currentTabId,
                HOME_STACK to tabsStarted[HOME_STACK],
                PROFILE_STACK to tabsStarted[PROFILE_STACK]
            )
        }
    }

    override fun onStart(owner: LifecycleOwner) {
        restoreBackStack(currentTabId)
    }

    override fun onStop(owner: LifecycleOwner) {
        saveBackStack()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        unregisterObserver()
        unregisterSaveInstanceStateListener()
    }

    fun onBackPressed(finish: () -> Unit) {
        supportFragmentManager.apply {
            if (backStackEntryCount == 1) finish()
            else popBackStack()
        }
    }

    fun back() {
        supportFragmentManager.popBackStack()
    }

    fun backMain() {
        val backStackName = getBackStackName(currentTabId)
        supportFragmentManager.popBackStack(backStackName, 0)
    }

    fun goToTab(tabId: Int) {
        if (currentTabId == tabId) return
        saveBackStack()
        restoreBackStack(tabId)
    }

    fun launchFragment(fragment: Fragment, backStackName: String? = null) {
        supportFragmentManager.beginTransaction().addToBackStack(backStackName)
            .replace(R.id.fragmentContainer, fragment).setReorderingAllowed(true).commit()
    }

    private fun saveBackStack() {
        val currentBackStackName = getBackStackName(currentTabId)
        supportFragmentManager.saveBackStack(currentBackStackName)
    }

    private fun restoreBackStack(tabId: Int) {
        val backStackName = getBackStackName(tabId)
        val isTabStarted = tabsStarted[backStackName] ?: false

        if (isTabStarted) {
            supportFragmentManager.restoreBackStack(backStackName)
        } else {
            val fragment = getNewFragment(tabId)
            launchFragment(fragment, backStackName)
            tabsStarted[backStackName] = true
        }
        currentTabId = tabId
    }

    private fun getBackStackName(tabId: Int): String {
        return when (tabId) {
            R.id.home -> HOME_STACK
            R.id.profile -> PROFILE_STACK
            else -> error("unknown tabId $tabId")
        }
    }

    private fun getNewFragment(tabId: Int): Fragment = when (tabId) {
        R.id.home -> Home.newInstance()
        R.id.profile -> Profile.newInstance()
        else -> error("unknown tabId $tabId")
    }

    private fun registerSavedInstanceStateListener(saveState: () -> Bundle) {
        savedStateRegistryOwner.savedStateRegistry.registerSavedStateProvider(
            this::class.java.simpleName
        ) {
            saveState()
        }
    }

    private fun unregisterSaveInstanceStateListener() {
        savedStateRegistryOwner.savedStateRegistry.unregisterSavedStateProvider(
            this::class.java.simpleName
        )
    }

    private fun restoreInstanceState(restoreState: (Bundle) -> Unit) {
        val currentState = savedStateRegistryOwner.savedStateRegistry.consumeRestoredStateForKey(
            this::class.java.simpleName
        ) ?: return

        restoreState(currentState)
    }

    private fun registerObserver() {
        savedStateRegistryOwner.lifecycle.addObserver(this)
    }

    private fun unregisterObserver() {
        savedStateRegistryOwner.lifecycle.removeObserver(this)
    }

    private companion object {
        private const val HOME_STACK = "home_stack"
        private const val PROFILE_STACK = "profile_stack"
        private const val CURRENT_TAB_ID = "current_tab_id"
    }
}

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

fun Fragment.getNavigationManager(currentFullScreen: Boolean): NavigationManager {
    if (lifecycle.currentState != Lifecycle.State.INITIALIZED) error("can't call getNavigationManager after or before onCreate")
    val mainActivity = (requireActivity() as MainActivity)
    BottomNavigationManager(this, mainActivity.bottomNavigation, currentFullScreen)
    return mainActivity.navigationManager
}