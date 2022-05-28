package com.example.appproject.Settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.appproject.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class SettingsFragment: Fragment(R.layout.fragment_settings) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.visibility = View.VISIBLE
    }
}