package com.example.appproject.features.profile_settigns.domain.model

data class UserInfo(
    val userId: String? = null,
    val name: String? = null,
    val surname: String? = null,
    val address: String? = null,
    val age: Int? = null,
    val photoUri: String? = null,
)