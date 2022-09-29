package com.example.appproject.features.profile.domain.repository

import com.example.appproject.features.profile.domain.model.UserInfo

interface ProfileRepository {
    suspend fun getUserInfo(uid: String): UserInfo?

    suspend fun getCurrentUserId(): String?

    suspend fun signOut()
}