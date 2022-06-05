package com.example.appproject.login.data

import com.example.appproject.login.domain.repository.LoginRepository
import com.google.firebase.auth.FirebaseAuth

class LoginRepositoryImpl(
    private val auth: FirebaseAuth,
) : LoginRepository {
    override suspend fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
    }
}