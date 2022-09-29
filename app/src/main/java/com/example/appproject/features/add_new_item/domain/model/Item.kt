package com.example.appproject.features.add_new_item.domain.model

data class Item(
    var userId: String? = null,
    var name: String? = null,
    var address: String? = null,
    var chapter: String? = null,
    var desc: String? = null,
    var photoUri: String? = null,
    var nowUserId: String? = null,
    var isTaken: Boolean? = false,
)