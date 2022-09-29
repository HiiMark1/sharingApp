package com.example.appproject.features.profile_settigns.domain.repository

import com.example.appproject.features.profile_settigns.domain.model.UserInfo

interface UserInfoRepository {
    suspend fun updateUserInfo(userInfo: UserInfo)

    suspend fun getUserInfo(uid:String): UserInfo?

    suspend fun getCurrentUserId(): String?
}