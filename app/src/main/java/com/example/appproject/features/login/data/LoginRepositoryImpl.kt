package com.example.appproject.features.login.data

import com.example.appproject.features.login.domain.repository.LoginRepository
import com.example.appproject.firebase_service.FirebaseUserService
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val firebaseUserService: FirebaseUserService
) : LoginRepository {
    override suspend fun signInAndGetIsCompleted(email: String, password: String): Boolean {
        return firebaseUserService.signInAndGetIsCompleted(email, password)
    }
}