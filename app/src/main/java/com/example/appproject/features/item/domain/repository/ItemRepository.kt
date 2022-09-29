package com.example.appproject.features.item.domain.repository

import com.example.appproject.features.item.domain.model.Item
import com.example.appproject.features.item.domain.model.UserInfo

interface ItemRepository {
    suspend fun takeItem(uid: String, item: Item, itemId: String)

    suspend fun getItem(itemId: String): Item?

    suspend fun freeItem(item: Item, itemId: String)

    suspend fun deleteItem(itemId: String)
}