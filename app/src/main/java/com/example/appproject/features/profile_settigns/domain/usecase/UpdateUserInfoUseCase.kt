package com.example.appproject.features.profile_settigns.domain.usecase

import com.example.appproject.features.profile_settigns.domain.model.UserInfo
import com.example.appproject.features.profile_settigns.domain.repository.UserInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateUserInfoUseCase @Inject constructor(
    private val userInfoRepository: UserInfoRepository
) {
    suspend operator fun invoke(userInfo: UserInfo) {
        return withContext(Dispatchers.IO) {
            userInfoRepository.updateUserInfo(userInfo)
        }
    }
}