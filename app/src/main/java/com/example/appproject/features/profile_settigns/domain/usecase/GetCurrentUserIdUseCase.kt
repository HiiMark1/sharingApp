package com.example.appproject.features.profile_settigns.domain.usecase

import com.example.appproject.features.profile_settigns.domain.repository.UserInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCurrentUserIdUseCase @Inject constructor(
    private val userInfoRepository: UserInfoRepository
) {
    suspend operator fun invoke(): String? {
        return withContext(Dispatchers.IO) {
            userInfoRepository.getCurrentUserId()
        }
    }
}