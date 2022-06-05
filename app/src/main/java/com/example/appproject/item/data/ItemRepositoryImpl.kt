package com.example.appproject.item.data

import com.example.appproject.item.domain.model.Item
import com.example.appproject.item.domain.repository.ItemRepository
import com.example.appproject.profile_settigns.domain.model.UserInfo
import com.google.firebase.database.DatabaseReference

class ItemRepositoryImpl(
    private val itemDbRef: DatabaseReference
) : ItemRepository {
    lateinit var item: Item

    override suspend fun updateOwner(id: String, item: Item) {
        itemDbRef.child(id).setValue(item)
    }

    override suspend fun getItem(item_id: String): Item {
        var item = itemDbRef.child(item_id).get().addOnSuccessListener {
            val item = it.getValue(Item::class.java)
            if (item != null) {
                this.item = item
            }
        }
        return this.item
    }
}