package com.example.appproject.features.registration.domain.usecase

import com.example.appproject.features.registration.domain.repository.RegistrationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddUserInfoInDbUseCase @Inject constructor(
    private val registrationRepository: RegistrationRepository
) {
    suspend operator fun invoke(uid: String, email: String) {
        return withContext(Dispatchers.IO) {
            registrationRepository.addUserInDb(uid, email)
        }
    }
}