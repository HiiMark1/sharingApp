package com.example.appproject.features.main.data.impl

import com.example.appproject.features.main.data.mappers.ItemMapper
import com.example.appproject.features.main.domain.model.ItemInList
import com.example.appproject.features.main.domain.repository.ItemInListRepository
import com.example.appproject.firebase_service.FirebaseItemService
import javax.inject.Inject

class ItemInListRepositoryImpl @Inject constructor(
    private val firebaseItemService: FirebaseItemService,
    private val itemMapper: ItemMapper,
): ItemInListRepository {
    override suspend fun getItems(limit: Int): MutableList<ItemInList?> {
        return itemMapper.map(firebaseItemService.updateItems(limit))
    }
}