package com.example.appproject.features.profile_settigns.domain.repository

import android.net.Uri
import com.example.appproject.features.profile_settigns.domain.model.UserInfo

interface UserInfoRepository {
    suspend fun updateUserInfo(userInfo: UserInfo)

    suspend fun getUserInfo(uid:String): UserInfo?

    suspend fun getCurrentUserId(): String?

    suspend fun uploadAvatarAndIsCompleted(uri: Uri): Boolean

    suspend fun getDownloadAvatarUri(uri: Uri): Uri?
}