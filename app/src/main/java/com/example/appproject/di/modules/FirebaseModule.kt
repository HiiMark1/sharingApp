package com.example.appproject.di.modules

import com.example.appproject.firebase_service.FirebaseUserService
import dagger.Module
import dagger.Provides

@Module
class FirebaseModule {
    @Provides
    fun provideFirebaseAuthService(): FirebaseUserService = FirebaseUserService.getInstance()

//    @Provides
//    fun provideDbRefService(): DbRefService = DbRefService.getInstance()
//
//    @Provides
//    fun provideStorageRefService(): StorageRefService = StorageRefService.getInstance()
}