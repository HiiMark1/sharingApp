package com.example.appproject.registration.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val nick: String? = null,
    val urlPhoto: String? = null,
)