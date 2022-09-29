package com.example.appproject.firebase_service

import androidx.navigation.findNavController
import com.example.appproject.features.item.domain.model.Item
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirebaseItemService {

    val itemDbRef = Firebase.database.getReference("item")

    suspend fun takeItem(uid: String, item: Item, itemId: String) {
        item.nowUserId = uid
        itemDbRef
            .child(itemId)
            .setValue(item)
            .await()
    }

    suspend fun getItem(itemId: String): Item? {
        return itemDbRef.child(itemId).get().await().getValue(Item::class.java)
    }

    suspend fun freeItem(item: Item, itemId: String) {
        item.nowUserId = "null"
        itemDbRef
            .child(itemId)
            .setValue(item)
            .await()
    }

    suspend fun deleteItem(itemId: String) {
        itemDbRef
            .child(itemId)
            .removeValue()
            .await()
    }

    suspend fun addNewItem(item: com.example.appproject.features.add_new_item.domain.model.Item){
        val key = itemDbRef
            .push().key
        if(key!=null){
            itemDbRef
                .child(key)
                .setValue(item)
                .await()
        }
    }

    companion object {
        var firebaseItemService = FirebaseItemService()

        fun getInstance(): FirebaseItemService {
            return firebaseItemService
        }
    }
}