package com.example.appproject.Settings

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.appproject.MainActivity
import com.example.appproject.R
import com.example.appproject.databinding.FragmentSettingsBinding
import com.example.appproject.models.Settings
import com.google.android.material.bottomnavigation.BottomNavigationView

class SettingsFragment: Fragment(R.layout.fragment_settings) {
    private lateinit var binding: FragmentSettingsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSettingsBinding.bind(view)

        activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.visibility = View.VISIBLE

        with(binding){
            var colorFreeItems = ""
            var colorTakenItems = ""
            spinnerFreeItems.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    colorFreeItems = parent?.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

            spinnerTakenItems.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    colorTakenItems = parent?.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

            btnSave.setOnClickListener {
                Settings.colorForTakenItems = colorFreeItems
                Settings.colorForTakenItems = colorTakenItems
                activity?.let {
                    (it as MainActivity).saveData()
                }

                view.findNavController().navigate(R.id.action_profileSettingsFragment_to_profileFragment)
            }
        }
    }
}