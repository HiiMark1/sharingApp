package com.example.appproject.features.profile.domain.usecase

import com.example.appproject.features.profile.domain.repository.ProfileRepository
import com.example.appproject.features.profile_settigns.domain.repository.UserInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCurrentUserIdUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(): String? {
        return withContext(Dispatchers.IO) {
            profileRepository.getCurrentUserId()
        }
    }
}