package com.example.appproject.add_new_item

import android.net.Uri

data class Item(
    var userId: String? = null,
    var address: String? = null,
    var chapter: String? = null,
    var desc: String? = null,
    var count: Int? = 0,
    var photoUri: String? = null,
    var nowUserId: String? = null,
)