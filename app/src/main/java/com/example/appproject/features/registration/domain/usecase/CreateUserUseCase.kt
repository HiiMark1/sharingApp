package com.example.appproject.features.registration.domain.usecase

import com.example.appproject.features.registration.domain.repository.RegistrationRepository
import com.example.appproject.firebase_service.FirebaseUserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val registrationRepository: RegistrationRepository
) {

    suspend operator fun invoke(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            registrationRepository.signUpAndGetIsCompleted(email, password)
        }
    }
}