package com.example.appproject.features.item.domain.usecase

import com.example.appproject.features.item.domain.model.Item
import com.example.appproject.features.item.domain.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FreeItemUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend operator fun invoke(item: Item, itemId: String) {
        return withContext(Dispatchers.IO) {
            repository.freeItem(item, itemId)
        }
    }
}