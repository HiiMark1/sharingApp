package com.example.appproject.features.login.domain.usercase

import com.example.appproject.features.login.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend operator fun invoke(email: String, password: String): Boolean {
        return withContext(Dispatchers.Main) {
            loginRepository.signInAndGetIsCompleted(email, password)
        }
    }
}