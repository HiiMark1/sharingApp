package com.example.appproject.di.modules

import com.example.appproject.firebase_service.FirebaseAvatarsService
import com.example.appproject.firebase_service.FirebaseItemService
import com.example.appproject.firebase_service.FirebaseUserService
import dagger.Module
import dagger.Provides

@Module
class FirebaseModule {
    @Provides
    fun provideFirebaseAuthService(): FirebaseUserService = FirebaseUserService.getInstance()

    @Provides
    fun provideFirebaseItemService(): FirebaseItemService = FirebaseItemService.getInstance()

    @Provides
    fun provideFirebaseAvatarsService(): FirebaseAvatarsService = FirebaseAvatarsService.getInstance()
}