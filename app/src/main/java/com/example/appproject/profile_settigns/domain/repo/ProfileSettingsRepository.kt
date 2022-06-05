package com.example.appproject.profile_settigns.domain.repo

import com.example.appproject.profile_settigns.domain.model.UserInfo

interface ProfileSettingsRepository {
    suspend fun getInfo(uid: String): UserInfo

    suspend fun updateInfo(userInfo: UserInfo)
}