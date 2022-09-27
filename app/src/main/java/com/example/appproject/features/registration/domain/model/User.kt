package com.example.appproject.features.registration.domain.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val nick: String? = null,
    val urlPhoto: String? = null,
)