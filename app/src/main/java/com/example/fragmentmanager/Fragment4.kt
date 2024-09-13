package com.example.fragmentmanager

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

class Fragment4 : Fragment(R.layout.fragment_4) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        fun newInstance(): Fragment4 = Fragment4()
    }
}