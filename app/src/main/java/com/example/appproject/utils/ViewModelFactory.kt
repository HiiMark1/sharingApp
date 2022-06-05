package com.example.appproject.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appproject.di.DIContainer
import com.example.appproject.item.presentation.ItemViewModel
import com.example.appproject.login.presentation.LoginViewModel
import com.example.appproject.profile_settigns.presentation.ProfileSettingsViewModel
import com.example.appproject.registration.presentation.RegistrationViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory (
    private val di: DIContainer,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(RegistrationViewModel::class.java) ->
                RegistrationViewModel(di.addUserInfoInDbUseCase, di.createUserUseCase)
                        as? T ?: throw IllegalArgumentException("Unknown ViewModel class")
            modelClass.isAssignableFrom(ProfileSettingsViewModel::class.java) ->
                ProfileSettingsViewModel(di.getInfoUseCase, di.updateInfoUseCase)
                        as? T ?: throw IllegalArgumentException("Unknown ViewModel class")
            modelClass.isAssignableFrom(LoginViewModel::class.java) ->
                LoginViewModel(di.singInUseCase)
                        as? T ?: throw IllegalArgumentException("Unknown ViewModel class")
            modelClass.isAssignableFrom(ItemViewModel::class.java) ->
                ItemViewModel(di.getItemUseCase, di.updateOwnerUseCase)
                        as? T ?: throw IllegalArgumentException("Unknown ViewModel class")
            else ->
                throw IllegalArgumentException("Unknown ViewModel class")
        }
}