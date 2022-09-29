package com.example.appproject.firebase_service

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class FirebaseAvatarsService {
    private val storageRef = Firebase.storage.reference

    suspend fun uploadAvatarAndIsCompleted(uri: Uri) : Boolean {
        var isCompleted = false
        val path = "avatars/${uri.lastPathSegment}"
        val uploadTask = storageRef.child(path).putFile(uri)

        uploadTask.addOnFailureListener {
            return@addOnFailureListener
        }.addOnSuccessListener {
            isCompleted = true
        }.await()
        return isCompleted
    }

    suspend fun getDownloadAvatarUri(uri: Uri): Uri? {
        var downloadUri: Uri? = null
        val path = "avatars/${uri.lastPathSegment}"

        storageRef.child(path).downloadUrl.addOnSuccessListener {
            downloadUri = it
        }.addOnFailureListener {
            return@addOnFailureListener
        }.await()
        return downloadUri
    }

    companion object {
        var firebaseAvatarsService = FirebaseAvatarsService()

        fun getInstance(): FirebaseAvatarsService {
            return FirebaseAvatarsService()
        }
    }
}