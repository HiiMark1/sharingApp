package com.example.appproject.features.main.domain.usecase

import com.example.appproject.features.main.domain.model.ItemInList
import com.example.appproject.features.main.domain.repository.ItemInListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetItemsUseCase @Inject constructor(
    private val repo: ItemInListRepository
) {
    suspend operator fun invoke(limit: Int): MutableList<ItemInList?> {
        return withContext(Dispatchers.IO) {
            repo.getItems(limit)
        }
    }
}