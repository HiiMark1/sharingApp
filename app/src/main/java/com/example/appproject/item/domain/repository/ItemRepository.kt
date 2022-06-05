package com.example.appproject.item.domain.repository

import com.example.appproject.item.domain.model.Item

interface ItemRepository {
    suspend fun updateOwner(id: String, item: Item)

    suspend fun getItem(item_id: String): Item?
}