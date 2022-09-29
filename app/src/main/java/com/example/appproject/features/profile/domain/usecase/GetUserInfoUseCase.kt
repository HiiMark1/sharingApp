package com.example.appproject.features.profile.domain.usecase

import com.example.appproject.features.profile.domain.repository.ProfileRepository
import com.example.appproject.features.profile.domain.model.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(uid: String): UserInfo? {
        return withContext(Dispatchers.IO) {
            profileRepository.getUserInfo(uid)
        }
    }
}