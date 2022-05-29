package com.example.appproject.profile

import android.net.Uri
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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var userInfoDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        database = Firebase.database
        userInfoDbRef = database.getReference("usersInfo")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.visibility = View.VISIBLE

        binding = FragmentProfileBinding.bind(view)

        val currentUser = auth.currentUser

        if (currentUser != null) {
            with(binding) {
                var userInfo = userInfoDbRef.child(auth.currentUser!!.uid).get().addOnSuccessListener {
                    val userInfo = it.getValue(UserInfo::class.java)
                    if(userInfo!=null){
                        tvName.text = userInfo.name
                        tvSurname.text = userInfo.surname
                        tvAgeUserInfo.text = userInfo.age.toString()
                        tvAddressUserInfo.text = userInfo.address
                        tvWorkWeekdaysUserInfo.text = userInfo.hoursWeek
                        tvWorkInWeekendUserInfo.text = userInfo.hoursWeekend
                        ivAvatar.load(Uri.parse(userInfo.photoUri)) {
                            transformations(CircleCropTransformation())
                        }
                    }
                }

                ivSettings.setOnClickListener {
                    view.findNavController().navigate(R.id.action_profileFragment_to_profileSettingsFragment)
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