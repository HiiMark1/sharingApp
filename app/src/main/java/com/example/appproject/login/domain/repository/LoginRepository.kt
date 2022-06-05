package com.example.appproject.login.domain.repository

interface LoginRepository {
    suspend fun signIn(email: String, password: String)
}