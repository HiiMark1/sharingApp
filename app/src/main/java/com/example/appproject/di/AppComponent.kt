package com.example.appproject.di

import android.content.Context
import com.example.appproject.MainActivity
import com.example.appproject.di.modules.*
import com.example.appproject.features.add_new_item.presentation.AddNewItemFragment
import com.example.appproject.features.item.presentation.ItemFragment
import com.example.appproject.features.login.presentation.LoginFragment
import com.example.appproject.features.main.presentation.MainFragment
import com.example.appproject.features.profile.presentation.ProfileFragment
import com.example.appproject.features.profile_settigns.presentation.ProfileSettingsFragment
import com.example.appproject.features.registration.presentation.RegistrationFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        MapperModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        FirebaseModule::class,
    ]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(loginFragment: LoginFragment)

    fun inject(profileSettingsFragment: ProfileSettingsFragment)

    fun inject(itemFragment: ItemFragment)

    fun inject(registrationFragment: RegistrationFragment)

    fun inject(addNewItemFragment: AddNewItemFragment)

    fun inject(profileFragment: ProfileFragment)

    fun inject(mainFragment: MainFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}