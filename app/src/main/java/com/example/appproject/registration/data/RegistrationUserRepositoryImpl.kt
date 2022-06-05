package com.example.appproject.registration.data

import com.example.appproject.registration.domain.model.UserInfo
import com.example.appproject.registration.domain.repo.RegistrationUserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class RegistrationUserRepositoryImpl (
    private val userInfoDbRef: DatabaseReference,
    private val auth: FirebaseAuth
) : RegistrationUserRepository {
    override suspend fun createUser(email: String, password: String): String {
        auth.createUserWithEmailAndPassword(email, password)
        return auth.currentUser?.uid.toString()
    }

    override suspend fun addUserInfoInDb(uid: String, email: String) {
        val userInfo = UserInfo(
            uid, "Name1", "Surname1", "address1", 22, "10:00-18:00", "null",
            "https://firebasestorage.googleapis.com/v0/b/sharing-b7eaf.appspot.com/o/blank-profile-picture-973460_640.png?alt=media&token=cd210b22-0396-4db2-a410-f8fde87e4e30"
        )
        userInfoDbRef
            .child(uid)
            .setValue(userInfo)
    }
}