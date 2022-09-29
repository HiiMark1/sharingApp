package com.example.appproject.features.item.domain.usecase

import com.example.appproject.features.item.domain.repository.UserRepositoryForItem
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCurrentUserIdUseCase @Inject constructor(
    private val repo: UserRepositoryForItem
) {
    suspend operator fun invoke(): String? {
        return withContext(Dispatchers.IO) {
            repo.getCurrentUserId()
        }
    }
}