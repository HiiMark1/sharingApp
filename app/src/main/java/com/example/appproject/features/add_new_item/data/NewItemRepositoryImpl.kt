package com.example.appproject.features.add_new_item.data

import com.example.appproject.features.add_new_item.domain.model.Item
import com.example.appproject.features.add_new_item.domain.repository.NewItemRepository
import com.example.appproject.firebase_service.FirebaseItemService
import javax.inject.Inject

class NewItemRepositoryImpl @Inject constructor(
    private val firebaseItemService: FirebaseItemService,
) : NewItemRepository {
    override suspend fun addNewItem(item: Item) {
        firebaseItemService.addNewItem(item)
    }
}