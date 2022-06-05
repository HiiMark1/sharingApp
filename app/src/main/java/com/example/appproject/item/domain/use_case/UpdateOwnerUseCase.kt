package com.example.appproject.item.domain.use_case

import com.example.appproject.item.domain.model.Item
import com.example.appproject.item.domain.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateOwnerUseCase(
    private val repository: ItemRepository
) {
    suspend operator fun invoke(id: String, item: Item) {
        return withContext(Dispatchers.Main) {
            repository.updateOwner(id, item)
        }
    }
}