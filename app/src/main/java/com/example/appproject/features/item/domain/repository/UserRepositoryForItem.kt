package com.example.appproject.features.item.domain.repository

import com.example.appproject.features.item.domain.model.UserInfo
import com.google.firebase.auth.FirebaseUser

interface UserRepositoryForItem {
    suspend fun getCurrentUserId(): String?

    suspend fun getOwner(ownerId: String): UserInfo?
}