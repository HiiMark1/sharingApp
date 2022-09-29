package com.example.appproject.features.add_new_item.domain.usecase

import com.example.appproject.features.add_new_item.domain.model.Item
import com.example.appproject.features.add_new_item.domain.repository.NewItemRepository
import com.example.appproject.features.add_new_item.domain.repository.NewItemUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddNewItemUseCase @Inject constructor(
    private val repo: NewItemRepository
) {
    suspend operator fun invoke(item: Item){
        return withContext(Dispatchers.IO) {
            repo.addNewItem(item)
        }
    }
}