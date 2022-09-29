package com.example.appproject.features.main.data.mappers

import com.example.appproject.features.add_new_item.domain.model.Item
import com.example.appproject.features.main.domain.model.ItemInList
import com.google.firebase.database.DataSnapshot

class ItemMapper {
    fun map(itemsData: Iterable<DataSnapshot>): MutableList<ItemInList?> {
        val itemList = mutableListOf<ItemInList?>()
        for (itemSnapshot in itemsData) {
            val item = itemSnapshot.getValue(Item::class.java)
            itemList.add(
                ItemInList(
                    itemSnapshot.key,
                    item?.userId,
                    item?.name,
                    item?.address,
                    item?.chapter,
                    item?.desc,
                    item?.photoUri,
                    item?.nowUserId,
                    item?.isTaken
                )
            )
        }
        itemList.reverse()
        return itemList
    }
}