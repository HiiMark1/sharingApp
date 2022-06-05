package com.example.appproject.registration.domain.repo

interface RegistrationUserRepository {
    suspend fun createUser(email: String, password: String): String

    suspend fun addUserInfoInDb(uid: String, email: String)
}