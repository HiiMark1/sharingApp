package com.example.appproject.features.registration.data

import com.example.appproject.features.registration.domain.repository.RegistrationRepository
import com.example.appproject.firebase_service.FirebaseUserService
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val firebaseUserService: FirebaseUserService
): RegistrationRepository {
    override suspend fun signUpAndGetIsCompleted(email: String, password: String): Boolean {
        return firebaseUserService.signUpAndGetIsCompleted(email, password)
    }

    override suspend fun addUserInDb(uid: String) {
        firebaseUserService.addUserInDb(uid)
    }
}