package com.example.appproject.features.profile_settigns.data

import com.example.appproject.features.profile_settigns.domain.model.UserInfo
import com.example.appproject.features.profile_settigns.domain.repository.UserInfoRepository
import com.example.appproject.firebase_service.FirebaseUserService
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val firebaseUserService: FirebaseUserService
): UserInfoRepository {
    override suspend fun getUserInfo(uid: String): UserInfo? {
        return firebaseUserService.getUserInfoProfileSettingsById(uid)
    }

    override suspend fun updateUserInfo(userInfo: UserInfo) {
        return firebaseUserService.updateUserInfo(userInfo)
    }

    override suspend fun getCurrentUserId(): String? {
        return firebaseUserService.getCurrentUserId()
    }
}