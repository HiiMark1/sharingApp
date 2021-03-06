package com.example.appproject

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.appproject.databinding.ActivityMainBinding
import com.example.appproject.Settings.Settings

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var pref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        readData()

        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        val controller = (supportFragmentManager.findFragmentById(R.id.container)
                as NavHostFragment).navController
        val navView = binding.navView
        navView.setupWithNavController(controller)
    }

    fun saveData(){
        val editor = pref?.edit()
        editor?.putString("ColorFreeItems", Settings.colorForFreeItems)
        editor?.putString("ColorTakenItems", Settings.colorForTakenItems)
    }

    fun readData(){
        Settings.colorForFreeItems = pref?.getString("ColorFreeItems", "gray")
        Settings.colorForTakenItems = pref?.getString("ColorTakenItems", "red")
    }
}