package com.example.appproject.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appproject.di.ViewModelKey
import com.example.appproject.features.add_new_item.presentation.AddNewItemViewModel
import com.example.appproject.features.item.presentation.ItemViewModel
import com.example.appproject.features.login.presentation.LoginViewModel
import com.example.appproject.features.profile.presentation.ProfileViewModel
import com.example.appproject.features.profile_settigns.presentation.ProfileSettingsViewModel
import com.example.appproject.features.registration.presentation.RegistrationViewModel
import com.example.appproject.utils.AppViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(
        factory: AppViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(
        viewModel: LoginViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileSettingsViewModel::class)
    fun bindProfileSettingsViewModel(
        viewModel: ProfileSettingsViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun bindProfileViewModel(
        viewModel: ProfileViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ItemViewModel::class)
    fun bindItemViewModel(
        viewModel: ItemViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    fun bindRegistrationViewModel(
        viewModel: RegistrationViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddNewItemViewModel::class)
    fun bindAddNewItemViewModel(
        viewModel: AddNewItemViewModel
    ): ViewModel
}