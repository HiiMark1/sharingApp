package com.example.appproject.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.example.appproject.R
import com.example.appproject.databinding.FragmentProfileBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.visibility = View.VISIBLE

        binding = FragmentProfileBinding.bind(view)

        val currentUser = auth.currentUser

        if (currentUser != null) {
            with(binding) {
                ivAvatar.load(currentUser.photoUrl) {
                    transformations(CircleCropTransformation())
                }

                ivSettings.setOnClickListener {
                    //TODO view.findNavController().navigate(R.id.action_profileFragment_to_profileSettingsFragment2)
                }
                btnExit.setOnClickListener {
                    signOut()
                    view.findNavController().navigate(R.id.loginFragment)
                }
            }
        }
    }

    private fun signOut() {
        Firebase.auth.signOut()
        view?.findNavController()?.navigate(R.id.action_profileFragment_to_loginFragment)
    }
}