package com.example.appproject.firebase_service

import com.example.appproject.features.item.domain.model.Item
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await

class FirebaseItemService {

    val itemDbRef = Firebase.database.getReference("item")

    suspend fun takeItem(uid: String, item: Item, itemId: String) {
        item.nowUserId = uid
        item.isTaken = true
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
        item.isTaken = false
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

    suspend fun updateItems(postLimit: Int): Iterable<DataSnapshot> {
        val posts = itemDbRef.limitToLast(postLimit)

        val task: Task<DataSnapshot> = posts.get()
        val deferredDataSnapshot: Deferred<DataSnapshot> = task.asDeferred()
        return deferredDataSnapshot.await().children
    }

    companion object {
        var firebaseItemService = FirebaseItemService()

        fun getInstance(): FirebaseItemService {
            return firebaseItemService
        }
    }
}