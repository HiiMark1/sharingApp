package com.example.appproject.features.add_new_item.domain.repository

interface NewItemUserRepository {
    suspend fun getCurrentUserId(): String?
}