package com.example.appproject.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appproject.di.ViewModelKey
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
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(ArticleAboutCityViewModel::class)
//    fun bindArticleAboutCityViewModel(
//        viewModel: ArticleAboutCityViewModel
//    ): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(CitiesViewModel::class)
//    fun bindCitiesViewModel(
//        viewModel: CitiesViewModel
//    ): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(NotesViewModel::class)
//    fun bindNotesViewModel(
//        viewModel: NotesViewModel
//    ): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(LoginViewModel::class)
//    fun bindLoginViewModel(
//        viewModel: LoginViewModel
//    ): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(EditProfileViewModel::class)
//    fun bindEditProfileViewModel(
//        viewModel: EditProfileViewModel
//    ): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(MainViewModel::class)
//    fun bindMainViewModel(
//        viewModel: MainViewModel
//    ): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(MyProfileViewModel::class)
//    fun bindMyProfileViewModel(
//        viewModel: MyProfileViewModel
//    ): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(NewPostViewModel::class)
//    fun bindNewPostViewModel(
//        viewModel: NewPostViewModel
//    ): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(OtherViewModel::class)
//    fun bindOtherViewModel(
//        viewModel: OtherViewModel
//    ): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(OtherProfileViewModel::class)
//    fun bindOtherProfileViewModel(
//        viewModel: OtherProfileViewModel
//    ): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(PostViewModel::class)
//    fun bindPostViewModel(
//        viewModel: PostViewModel
//    ): ViewModel
//
    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    fun bindRegistrationViewModel(
        viewModel: RegistrationViewModel
    ): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(SettingsViewModel::class)
//    fun bindSettingsViewModel(
//        viewModel: SettingsViewModel
//    ): ViewModel
}