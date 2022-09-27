package com.example.appproject.firebase_service

import android.net.Uri
import com.example.appproject.features.registration.domain.model.UserInfo
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

private const val DEFAULT_AVATAR = "https://firebasestorage.googleapis.com/v0/b/sharing-b7eaf.appspot.com/o/blank-profile-picture-973460_640.png?alt=media&token=cd210b22-0396-4db2-a410-f8fde87e4e30"

class FirebaseUserService {

    val userInfoDbRef = Firebase.database.getReference("usersInfo")

    fun getCurrentUser(): FirebaseUser? {
        val auth = Firebase.auth
        return auth.currentUser
    }

    suspend fun addUserInDb(uid: String, email: String){
        val user = Firebase.auth.currentUser

        if(user!=null){
            val userInfo = UserInfo(user.uid, "Name1", "Surname1", "address1", 22, "10:00-18:00",
                DEFAULT_AVATAR)
            userInfoDbRef
                .child(user.uid)
                .setValue(userInfo)
        }
    }

    suspend fun signUpAndGetIsCompleted(email: String, password: String): Boolean {
        val auth = Firebase.auth
        var isCompleted = false

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    isCompleted=true
                }
            }.await()

        return isCompleted
    }

    suspend fun updateNick(nick: String): Boolean {
        val auth = Firebase.auth
        val currentUser = auth.currentUser
        var isCompleted = false

        val profileUpdates = userProfileChangeRequest {
            displayName = nick
        }

        currentUser!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    isCompleted = true
                }
            }.await()
        return isCompleted
    }

    suspend fun updateAvatarUriAndGetIsCompleted(uri: Uri): Boolean {
        val auth = Firebase.auth
        val currentUser = auth.currentUser
        var isCompleted = false


        val profileUpdates = userProfileChangeRequest {
            photoUri = uri
        }

        currentUser!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    isCompleted = true
                }
            }.await()
        return isCompleted
    }

    fun signOut() {
        val auth = Firebase.auth
        auth.signOut()
    }

    suspend fun createUserAndGetUid(email: String, password: String, nick: String): String? {
        val auth = Firebase.auth
        var uid: String? = null
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = Firebase.auth.currentUser
                    uid = user?.uid
                    val profileUpdates = userProfileChangeRequest {
                        displayName = nick
                        photoUri =
                            Uri.parse(DEFAULT_AVATAR)
                    }

                    user!!.updateProfile(profileUpdates)
                }
            }.await()
        return uid
    }

    fun changePassword(password: String) {
        val auth = Firebase.auth
        val currentUser = auth.currentUser
        currentUser?.updatePassword(password)
    }

    companion object {
        var firebaseUserService = FirebaseUserService()

        fun getInstance(): FirebaseUserService {
            return firebaseUserService
        }
    }
}