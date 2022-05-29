package com.example.appproject.profile_settigns.domain

import android.net.Uri

data class UserInfo(
    val userId: String? = null,
    val name: String? = null,
    val surname: String? = null,
    val address: String? = null,
    val age: Int? = null,
    val hoursWeek: String? = null,
    val hoursWeekend: String? = null,
    val photoUri: String? = null,
)