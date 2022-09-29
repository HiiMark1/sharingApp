package com.example.appproject.features.profile.data

import com.example.appproject.features.profile.domain.repository.ProfileRepository
import com.example.appproject.features.profile.domain.model.UserInfo
import com.example.appproject.firebase_service.FirebaseUserService
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val firebaseUserService: FirebaseUserService
): ProfileRepository {
    override suspend fun getUserInfo(uid: String): UserInfo? {
        return firebaseUserService.getUserInfoProfileById(uid)
    }

    override suspend fun getCurrentUserId(): String? {
        return firebaseUserService.getCurrentUserId()
    }

    override suspend fun signOut() {
        return firebaseUserService.signOut()
    }
}