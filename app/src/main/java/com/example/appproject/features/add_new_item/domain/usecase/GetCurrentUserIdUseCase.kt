package com.example.appproject.features.add_new_item.domain.usecase

import com.example.appproject.features.add_new_item.domain.repository.NewItemUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCurrentUserIdUseCase @Inject constructor(
    private val repo: NewItemUserRepository
) {
    suspend operator fun invoke(): String? {
        return withContext(Dispatchers.IO) {
            repo.getCurrentUserId()
        }
    }
}