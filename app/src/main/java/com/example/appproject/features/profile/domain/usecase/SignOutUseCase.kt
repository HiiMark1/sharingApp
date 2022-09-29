package com.example.appproject.features.profile.domain.usecase


import com.example.appproject.features.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke() {
        return withContext(Dispatchers.Main) {
            profileRepository.signOut()
        }
    }
}