package com.example.appproject.features.profile.presentation

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.example.appproject.MainActivity
import com.example.appproject.R
import com.example.appproject.databinding.FragmentProfileBinding
import com.example.appproject.features.profile.domain.model.UserInfo
import com.example.appproject.utils.AppViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

private const val ARG_NAME_USER_ID = "user_id"
private const val ARG_NAME = "item_id"

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    @Inject
    lateinit var factory: AppViewModelFactory
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels {
        factory
    }
    private var userId: String = "null"
    private lateinit var uid: String
    private lateinit var userInfo: UserInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as MainActivity).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.visibility = View.VISIBLE

        binding = FragmentProfileBinding.bind(view)
        initObservers()

        if (arguments?.getString(ARG_NAME_USER_ID) != null) {
            userId = arguments?.getString(ARG_NAME_USER_ID).toString()
        }

        viewModel.getCurrentUserId()
    }

    private fun signOut() {
        Firebase.auth.signOut()
        view?.findNavController()?.navigate(R.id.action_profileFragment_to_loginFragment)
    }

    private fun initObservers() {
        viewModel.currentUserId.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = {
                if (it != null) {
                    uid = it
                    if (userId == "null") {
                        viewModel.getUserInfo(uid)
                    } else {
                        viewModel.getUserInfo(userId)
                    }
                }
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }

        viewModel.user.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = {
                if (it != null) {
                    userInfo = it
                    with(binding) {
                        setInfo(userInfo)

                        if (userInfo.userId == uid) {
                            ivSettings.setOnClickListener {
                                view?.findNavController()?.navigate(R.id.action_profileFragment_to_profileSettingsFragment)
                            }
                            btnExit.setOnClickListener {
                                viewModel.signOut()
                                view?.findNavController()?.navigate(R.id.loginFragment)
                            }
                        } else {
                            ivSettings.visibility = View.INVISIBLE
                            btnExit.visibility = View.INVISIBLE
                        }
                    }
                }
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }
    }

    private fun setInfo(userInfo: UserInfo) {
        with(binding) {
            tvName.text = userInfo.name
            tvSurname.text = userInfo.surname
            tvAgeUserInfo.text = userInfo.age.toString()
            tvAddressUserInfo.text = userInfo.address
            ivAvatar.load(Uri.parse(userInfo.photoUri)) {
                transformations(CircleCropTransformation())
            }
        }
    }
}