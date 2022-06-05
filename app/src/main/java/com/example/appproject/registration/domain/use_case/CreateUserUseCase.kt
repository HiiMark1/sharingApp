package com.example.appproject.registration.domain.use_case

import com.example.appproject.registration.domain.repo.RegistrationUserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateUserUseCase(
    private val repository: RegistrationUserRepository
) {
    suspend operator fun invoke(email: String, password: String): String {
        return withContext(Dispatchers.Main) {
            repository.createUser(email, password)
        }
    }
}