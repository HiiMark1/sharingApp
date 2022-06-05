package com.example.appproject.profile_settigns.data

import com.example.appproject.profile_settigns.domain.model.UserInfo
import com.example.appproject.profile_settigns.domain.repo.ProfileSettingsRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class ProfileSettingsRepositoryImpl(
    private val userInfoDbRef: DatabaseReference,
    private val auth: FirebaseAuth,
): ProfileSettingsRepository {
    lateinit var user: UserInfo

    override suspend fun getInfo(uid: String): UserInfo {
        var userInfo = userInfoDbRef.child(auth.currentUser!!.uid).get().addOnSuccessListener {
            val userInfo = it.getValue(UserInfo::class.java)
            if (userInfo != null) {
                user = userInfo
            }
        }

        return user
    }

    override suspend fun updateInfo(userInfo: UserInfo) {
        userInfoDbRef.child(auth.currentUser?.uid.toString()).setValue(userInfo)
    }
}