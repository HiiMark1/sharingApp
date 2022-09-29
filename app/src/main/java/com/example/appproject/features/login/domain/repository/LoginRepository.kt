package com.example.appproject.features.login.domain.repository

interface LoginRepository {
    suspend fun signInAndGetIsCompleted(email: String, password: String): Boolean
}