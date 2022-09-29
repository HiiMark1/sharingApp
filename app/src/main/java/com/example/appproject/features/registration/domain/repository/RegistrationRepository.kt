package com.example.appproject.features.registration.domain.repository

interface RegistrationRepository {
    suspend fun signUpAndGetIsCompleted(email: String, password: String): Boolean

    suspend fun addUserInDb(uid: String)
}