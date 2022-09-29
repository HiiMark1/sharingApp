package com.example.appproject.features.item.domain.usecase

import com.example.appproject.features.item.domain.model.UserInfo
import com.example.appproject.features.item.domain.repository.UserRepositoryForItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetOwnerUseCase @Inject constructor(
    private val repository: UserRepositoryForItem
) {
    suspend operator fun invoke(id: String): UserInfo? {
        return withContext(Dispatchers.IO) {
            repository.getOwner(id)
        }
    }
}