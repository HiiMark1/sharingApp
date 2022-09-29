package com.example.appproject.features.profile_settigns.domain.usecase

import com.example.appproject.features.profile_settigns.domain.model.UserInfo
import com.example.appproject.features.profile_settigns.domain.repository.UserInfoRepository
import com.example.appproject.features.registration.domain.repository.RegistrationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val userInfoRepository: UserInfoRepository
) {
    suspend operator fun invoke(uid: String): UserInfo? {
        return withContext(Dispatchers.IO) {
            userInfoRepository.getUserInfo(uid)
        }
    }
}