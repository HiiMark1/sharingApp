package com.example.appproject.features.main.domain.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ItemInList(
    var id: String? = null,
    var userId: String? = null,
    var name: String? = null,
    var address: String? = null,
    var chapter: String? = null,
    var desc: String? = null,
    var photoUri: String? = null,
    var nowUserId: String? = null,
    var isTaken: Boolean? = false,
)