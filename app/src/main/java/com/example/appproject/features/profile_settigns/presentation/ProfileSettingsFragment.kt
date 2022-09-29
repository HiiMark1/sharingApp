package com.example.appproject.features.profile_settigns.presentation

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.appproject.MainActivity
import com.example.appproject.R
import com.example.appproject.databinding.FragmentProfileSettingsBinding
import com.example.appproject.features.profile_settigns.domain.model.UserInfo
import com.example.appproject.utils.AppViewModelFactory
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class ProfileSettingsFragment : Fragment(R.layout.fragment_profile_settings) {
    @Inject
    lateinit var factory: AppViewModelFactory
    private lateinit var binding: FragmentProfileSettingsBinding
    private val viewModel: ProfileSettingsViewModel by viewModels {
        factory
    }

    private lateinit var userInfo: UserInfo
    private lateinit var selectedAvatarUri: Uri
    private var photoUri: String = ""

    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                selectedAvatarUri = it
                viewModel.uploadAvatarAndGetIsCompleted(it)
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as MainActivity).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileSettingsBinding.bind(view)

        initObservers()
        viewModel.getCurrentUserId()

        with(binding) {
            btnBack.setOnClickListener {
                view?.findNavController()?.navigateUp()
            }

            btnChangeAvatar.setOnClickListener {
                selectImageFromGallery()
            }
        }
    }

    private fun initObservers() {
        viewModel.currentUserId.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = {
                if (it != null) {
                    viewModel.getUserInfo(it)
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
                        setText(userInfo)

                        btnSave.setOnClickListener {
                            saveUserInfo()
                        }
                    }
                }
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }

        viewModel.isCompletedUploadingAvatar.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = {
                if (it) {
                    viewModel.onGetDownloadAvatarUri(selectedAvatarUri)
                }
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }

        viewModel.downloadAvatarUri.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = {
                if (it != null) {
                    photoUri = it.toString()
                }
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }
    }

    private fun setText(userInfo: UserInfo?) {
        with(binding) {
            etName.setText(userInfo?.name)
            etSurname.setText(userInfo?.surname)
            etAddressUserInfo.setText(userInfo?.address)
            etAgeUserInfo.setText(userInfo?.age.toString())
        }
    }

    private fun saveUserInfo() {
        with(binding) {
            var name = userInfo.name
            if (etName.text != null && etName.text.toString().length != 0 && etName.text.length < 32) {
                name = etName.text.toString()
            }

            var surname = userInfo.surname
            if (etSurname.text != null && etSurname.text.toString().length != 0 && etSurname.text.length < 32) {
                surname = etSurname.text.toString()
            }

            var address = userInfo.address
            if (etAddressUserInfo.text != null && etAddressUserInfo.text.toString().length != 0 && etAddressUserInfo.text.length < 64) {
                address = etAddressUserInfo.text.toString()
            }

            var age = userInfo.age
            if (etAgeUserInfo.text.toString() != "" && isNumeric(etAddressUserInfo.text.toString())) {
                age = etAgeUserInfo.text.toString().toInt()
            }

            if(photoUri==""){
                viewModel.updateUserInfo(
                    UserInfo(
                        userInfo.userId,
                        name,
                        surname,
                        address,
                        age,
                        userInfo.photoUri
                    )
                )
            } else {
                viewModel.updateUserInfo(
                    UserInfo(
                        userInfo.userId,
                        name,
                        surname,
                        address,
                        age,
                        photoUri
                    )
                )
            }
            showMessage(R.string.success_saved_info)
            view?.findNavController()?.navigateUp()
        }
    }

    private fun showMessage(stringId: Int) {
        Snackbar.make(
            requireView(),
            stringId,
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun isNumeric(toCheck: String): Boolean {
        return toCheck.all { char -> char.isDigit() }
    }

    private fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")
}