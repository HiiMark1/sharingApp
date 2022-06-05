package com.example.appproject.di

import com.example.appproject.item.data.ItemRepositoryImpl
import com.example.appproject.item.domain.repository.ItemRepository
import com.example.appproject.item.domain.use_case.GetItemUseCase
import com.example.appproject.item.domain.use_case.UpdateOwnerUseCase
import com.example.appproject.login.data.LoginRepositoryImpl
import com.example.appproject.login.domain.repository.LoginRepository
import com.example.appproject.login.domain.use_case.SignInUseCase
import com.example.appproject.profile_settigns.data.ProfileSettingsRepositoryImpl
import com.example.appproject.profile_settigns.domain.repo.ProfileSettingsRepository
import com.example.appproject.profile_settigns.domain.use_case.GetInfoUseCase
import com.example.appproject.profile_settigns.domain.use_case.UpdateInfoUseCase
import com.example.appproject.registration.data.RegistrationUserRepositoryImpl
import com.example.appproject.registration.domain.repo.RegistrationUserRepository
import com.example.appproject.registration.domain.use_case.AddUserInfoInDbUseCase
import com.example.appproject.registration.domain.use_case.CreateUserUseCase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object DIContainer {
    private val userInfoDbRef: DatabaseReference = Firebase.database.getReference("usersInfo")
    private val itemDbRef: DatabaseReference = Firebase.database.getReference("item")
    private val auth = Firebase.auth

    private val registrationUserRepositoryImpl: RegistrationUserRepository =
        RegistrationUserRepositoryImpl(userInfoDbRef, auth)

    val createUserUseCase: CreateUserUseCase = CreateUserUseCase(
        registrationUserRepositoryImpl
    )

    val addUserInfoInDbUseCase: AddUserInfoInDbUseCase = AddUserInfoInDbUseCase(
        registrationUserRepositoryImpl
    )

    private val profileSettingsRepositoryImpl: ProfileSettingsRepository =
        ProfileSettingsRepositoryImpl(userInfoDbRef, auth)

    val getInfoUseCase: GetInfoUseCase = GetInfoUseCase(
        profileSettingsRepositoryImpl
    )
    val updateInfoUseCase: UpdateInfoUseCase = UpdateInfoUseCase(
        profileSettingsRepositoryImpl
    )

    private val loginRepository: LoginRepository =
        LoginRepositoryImpl(auth)

    val singInUseCase: SignInUseCase = SignInUseCase(
        loginRepository
    )

    private val itemRepository: ItemRepository =
        ItemRepositoryImpl(itemDbRef)

    val getItemUseCase = GetItemUseCase(itemRepository)
    val updateOwnerUseCase = UpdateOwnerUseCase(itemRepository)
}