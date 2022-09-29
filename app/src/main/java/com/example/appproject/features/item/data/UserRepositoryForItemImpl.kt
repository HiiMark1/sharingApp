package com.example.appproject.features.item.data

import com.example.appproject.features.item.domain.model.UserInfo
import com.example.appproject.features.item.domain.repository.UserRepositoryForItem
import com.example.appproject.firebase_service.FirebaseUserService
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class UserRepositoryForItemImpl  @Inject constructor(
    private val firebaseUserService: FirebaseUserService,
) : UserRepositoryForItem {
    override suspend fun getOwner(ownerId: String): UserInfo? {
        return firebaseUserService.getUserInfoById(ownerId)
    }

    override suspend fun getCurrentUserId(): String? {
        return firebaseUserService.getCurrentUserId()
    }
}