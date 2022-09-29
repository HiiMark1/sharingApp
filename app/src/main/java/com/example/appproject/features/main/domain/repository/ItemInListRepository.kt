package com.example.appproject.features.main.domain.repository

import com.example.appproject.features.main.domain.model.ItemInList

interface ItemInListRepository {
    suspend fun getItems(limit: Int): MutableList<ItemInList?>
}