package com.example.appproject.di

import android.content.Context
import com.example.appproject.MainActivity
import com.example.appproject.di.modules.FirebaseModule
import com.example.appproject.di.modules.RepositoryModule
import com.example.appproject.di.modules.ViewModelModule
import com.example.appproject.features.registration.presentation.RegistrationFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
//        AppModule::class,
//        NetModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
//        DbModule::class,
        FirebaseModule::class
    ]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)

//    fun inject(articleAboutCityFragment: ArticleAboutCityFragment)
//
//    fun inject(citiesFragment: CitiesFragment)
//
//    fun inject(notesFragment: NotesFragment)
//
//    fun inject(loginFragment: LoginFragment)
//
//    fun inject(editProfileFragment: EditProfileFragment)
//
//    fun inject(mainFragment: MainFragment)
//
//    fun inject(myProfileFragment: MyProfileFragment)
//
//    fun inject(newPostFragment: NewPostFragment)
//
//    fun inject(otherFragment: OtherFragment)
//
//    fun inject(otherProfileFragment: OtherProfileFragment)
//
//    fun inject(postFragment: PostFragment)
//
    fun inject(registrationFragment: RegistrationFragment)
//
//    fun inject(settingsFragment: SettingsFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}