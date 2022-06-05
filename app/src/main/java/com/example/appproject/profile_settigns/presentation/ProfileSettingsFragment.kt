package com.example.appproject.profile_settigns.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.appproject.R
import com.example.appproject.databinding.FragmentProfileSettingsBinding
import com.example.appproject.di.DIContainer
import com.example.appproject.profile_settigns.domain.model.UserInfo
import com.example.appproject.registration.domain.model.User
import com.example.appproject.registration.presentation.RegistrationViewModel
import com.example.appproject.utils.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProfileSettingsFragment : Fragment(R.layout.fragment_profile_settings) {
    private lateinit var binding: FragmentProfileSettingsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var photoUri: String
    private lateinit var viewModel: ProfileSettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileSettingsBinding.bind(view)

        initObjects()
        initObservers()
        viewModel.getUserInfo(auth.currentUser?.uid.toString())

        var currentUser = auth.currentUser
        if (currentUser != null) {
            with(binding) {
                btnSave.setOnClickListener {
                    var age = 0
                    if (etAgeUserInfo.text.toString() != "") {
                        age = etAgeUserInfo.text.toString().toInt()
                    }

                    var weekdays = "null"
                    var weekend = "null"

                    if (etWorkWeekdaysUserInfo.text.toString() != "") {
                        weekdays = etWorkWeekdaysUserInfo.text.toString()
                    }

                    if (etWorkWeekendUserInfo.text.toString() != "") {
                        weekend = etWorkWeekendUserInfo.text.toString()
                    }

                    var userInfo = UserInfo(
                        auth.currentUser!!.uid, etName.text.toString(), etSurname.text.toString(),
                        etAddressUserInfo.text.toString(), age, weekdays, weekend, photoUri
                    )

                    viewModel.updateUserInfo(userInfo)
                    view.findNavController()
                        .navigate(R.id.action_profileSettingsFragment_to_profileFragment)

                }

                btnBack.setOnClickListener {
                    view.findNavController()
                        .navigate(R.id.action_profileSettingsFragment_to_profileFragment)
                }
            }
        }
    }

    private fun setText(userInfo: UserInfo?) {
        with(binding) {
            etName.setText(userInfo?.name)
            etSurname.setText(userInfo?.surname)
            etAddressUserInfo.setText(userInfo?.address)
            etAgeUserInfo.setText(userInfo?.age.toString())
            etWorkWeekdaysUserInfo.setText(userInfo?.hoursWeekend)
            etWorkWeekdaysUserInfo.setText(userInfo?.hoursWeek)
        }
    }

    private fun initObjects() {
        val factory = ViewModelFactory(DIContainer)
        viewModel = ViewModelProvider(
            this,
            factory
        )[ProfileSettingsViewModel::class.java]
    }

    private fun initObservers() {
        viewModel.error.observe(viewLifecycleOwner) {
            Log.e("e", it.message.toString())
        }

        viewModel.userInfo.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = { userInfo ->
                if (userInfo != null) {
                    photoUri = userInfo.photoUri.toString()
                    setText(userInfo)
                }
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }
    }
}