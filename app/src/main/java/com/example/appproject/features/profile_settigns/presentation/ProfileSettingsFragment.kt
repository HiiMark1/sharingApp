package com.example.appproject.features.profile_settigns.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.appproject.R
import com.example.appproject.databinding.FragmentProfileSettingsBinding
import com.example.appproject.features.profile_settigns.domain.UserInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProfileSettingsFragment : Fragment(R.layout.fragment_profile_settings) {
    private lateinit var binding: FragmentProfileSettingsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var userInfoDbRef: DatabaseReference
    private lateinit var photoUri: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        database = Firebase.database
        userInfoDbRef = database.getReference("usersInfo")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileSettingsBinding.bind(view)

        var userInfo = userInfoDbRef.child(auth.currentUser!!.uid).get().addOnSuccessListener {
            val userInfo = it.getValue(UserInfo::class.java)
            photoUri = userInfo?.photoUri.toString()
            setText(userInfo)
        }

        var currentUser = auth.currentUser
        if(currentUser!=null){
            with(binding) {
                btnSave.setOnClickListener {
                    var age = 0
                    if(etAgeUserInfo.text.toString()!=""){
                        age = etAgeUserInfo.text.toString().toInt()
                    }

                    var weekdays = "null"
                    var weekend = "null"

                    if(etWorkWeekdaysUserInfo.text.toString() != ""){
                        weekdays = etWorkWeekdaysUserInfo.text.toString()
                    }

                    if(etWorkWeekendUserInfo.text.toString() != ""){
                        weekend = etWorkWeekendUserInfo.text.toString()
                    }

                    var userInfo = UserInfo(auth.currentUser!!.uid, etName.text.toString(), etSurname.text.toString(),
                        etAddressUserInfo.text.toString(), age, weekdays, weekend, photoUri)

                    userInfoDbRef.child(currentUser.uid).setValue(userInfo).addOnSuccessListener {
                        view.findNavController().navigate(R.id.action_profileSettingsFragment_to_profileFragment)
                    }
                }

                btnBack.setOnClickListener {
                    view.findNavController().navigate(R.id.action_profileSettingsFragment_to_profileFragment)
                }
            }
        }
    }

    private fun setText(userInfo: UserInfo?){
        with(binding){
            etName.setText(userInfo?.name)
            etSurname.setText(userInfo?.surname)
            etAddressUserInfo.setText(userInfo?.address)
            etAgeUserInfo.setText(userInfo?.age.toString())
            etWorkWeekdaysUserInfo.setText(userInfo?.hoursWeekend)
            etWorkWeekdaysUserInfo.setText(userInfo?.hoursWeek)
        }
    }
}