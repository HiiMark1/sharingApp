package com.example.appproject.features.item.data

import com.example.appproject.features.item.domain.model.Item
import com.example.appproject.features.item.domain.model.UserInfo
import com.example.appproject.features.item.domain.repository.ItemRepository
import com.example.appproject.firebase_service.FirebaseItemService
import com.example.appproject.firebase_service.FirebaseUserService
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val firebaseItemService: FirebaseItemService,
) : ItemRepository {
    override suspend fun takeItem(uid: String, item: Item, itemId: String) {
        firebaseItemService.takeItem(uid, item, itemId)
    }

    override suspend fun getItem(itemId: String): Item? {
        return firebaseItemService.getItem(itemId)
    }

    override suspend fun freeItem(item: Item, itemId: String){
        firebaseItemService.freeItem(item, itemId)
    }

    override suspend fun deleteItem(itemId: String) {
        firebaseItemService.deleteItem(itemId)
    }
}