package com.example.appproject.item.domain.use_case

import com.example.appproject.item.domain.model.Item
import com.example.appproject.item.domain.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetItemUseCase(
    private val repository: ItemRepository
) {
    suspend operator fun invoke(item_id: String): Item? {
        return withContext(Dispatchers.Main) {
            repository.getItem(item_id)
        }
    }
}