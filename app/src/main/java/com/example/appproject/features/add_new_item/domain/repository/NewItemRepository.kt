package com.example.appproject.features.add_new_item.domain.repository

import com.example.appproject.features.add_new_item.domain.model.Item


interface NewItemRepository {
    suspend fun addNewItem(item: Item)
}