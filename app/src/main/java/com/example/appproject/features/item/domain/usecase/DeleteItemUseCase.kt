package com.example.appproject.features.item.domain.usecase

import com.example.appproject.features.item.domain.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteItemUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend operator fun invoke(itemId: String) {
        return withContext(Dispatchers.IO) {
            repository.deleteItem(itemId)
        }
    }
}