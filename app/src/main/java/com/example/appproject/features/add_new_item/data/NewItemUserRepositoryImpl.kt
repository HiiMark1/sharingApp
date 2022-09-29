package com.example.appproject.features.add_new_item.data

import com.example.appproject.features.add_new_item.domain.repository.NewItemUserRepository
import com.example.appproject.firebase_service.FirebaseUserService
import javax.inject.Inject

class NewItemUserRepositoryImpl @Inject constructor(
    private val firebaseUserService: FirebaseUserService,
) : NewItemUserRepository {
    override suspend fun getCurrentUserId(): String? {
        return firebaseUserService.getCurrentUserId()
    }
}