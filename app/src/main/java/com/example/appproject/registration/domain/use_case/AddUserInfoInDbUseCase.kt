package com.example.appproject.registration.domain.use_case

import com.example.appproject.registration.domain.repo.RegistrationUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddUserInfoInDbUseCase(
    private val repository: RegistrationUserRepository
) {
    suspend operator fun invoke(uid: String, email: String) {
        return withContext(Dispatchers.Main) {
            repository.addUserInfoInDb(uid, email)
        }
    }
}