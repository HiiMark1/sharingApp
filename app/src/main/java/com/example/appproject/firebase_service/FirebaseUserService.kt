package com.example.appproject.firebase_service

import com.example.appproject.features.registration.domain.model.UserInfo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

private const val DEFAULT_AVATAR =
    "https://firebasestorage.googleapis.com/v0/b/sharing-b7eaf.appspot.com/o/blank-profile-picture-973460_640.png?alt=media&token=cd210b22-0396-4db2-a410-f8fde87e4e30"

class FirebaseUserService {

    val userInfoDbRef = Firebase.database.getReference("usersInfo")
    var auth = Firebase.auth

    fun getCurrentUserId(): String? {
        val auth = Firebase.auth
        return auth.currentUser?.uid
    }

    suspend fun getUserInfoById(id: String): com.example.appproject.features.item.domain.model.UserInfo? {
        return userInfoDbRef
            .child(id)
            .get()
            .await()
            .getValue(com.example.appproject.features.item.domain.model.UserInfo::class.java)
    }

    suspend fun getUserInfoProfileById(id: String): com.example.appproject.features.profile.domain.model.UserInfo?{
        return userInfoDbRef
            .child(id)
            .get()
            .await()
            .getValue(com.example.appproject.features.profile.domain.model.UserInfo::class.java)
    }

    suspend fun getUserInfoProfileSettingsById(id: String): com.example.appproject.features.profile_settigns.domain.model.UserInfo? {
        return userInfoDbRef
            .child(id)
            .get()
            .await()
            .getValue(com.example.appproject.features.profile_settigns.domain.model.UserInfo::class.java)
    }

    suspend fun addUserInDb(uid: String) {

        val userInfo = UserInfo(
            uid, "Name1", "Surname1", "address1", 22,
            DEFAULT_AVATAR
        )
        userInfoDbRef
            .child(uid)
            .setValue(userInfo)
    }

    suspend fun signInAndGetIsCompleted(email: String, password: String): Boolean {
        var isCompleted = false

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    isCompleted = true
                }
            }.await()

        return isCompleted
    }

    suspend fun signUpAndGetIsCompleted(email: String, password: String): Boolean {
        var isCompleted = false

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    isCompleted = true
                }
            }.await()

        return isCompleted
    }

    suspend fun updateUserInfo(userInfo: com.example.appproject.features.profile_settigns.domain.model.UserInfo) {
        userInfoDbRef
            .child(userInfo.userId.toString())
            .setValue(userInfo)
            .await()
    }

    fun signOut() {
        val auth = Firebase.auth
        auth.signOut()
    }

    companion object {
        var firebaseUserService = FirebaseUserService()

        fun getInstance(): FirebaseUserService {
            return firebaseUserService
        }
    }
}