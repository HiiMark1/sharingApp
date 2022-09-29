package com.example.appproject.di.modules

import com.example.appproject.features.add_new_item.data.NewItemRepositoryImpl
import com.example.appproject.features.add_new_item.data.NewItemUserRepositoryImpl
import com.example.appproject.features.add_new_item.domain.repository.NewItemRepository
import com.example.appproject.features.add_new_item.domain.repository.NewItemUserRepository
import com.example.appproject.features.item.data.ItemRepositoryImpl
import com.example.appproject.features.item.data.UserRepositoryForItemImpl
import com.example.appproject.features.item.domain.repository.ItemRepository
import com.example.appproject.features.item.domain.repository.UserRepositoryForItem
import com.example.appproject.features.login.data.LoginRepositoryImpl
import com.example.appproject.features.login.domain.repository.LoginRepository
import com.example.appproject.features.main.data.impl.ItemInListRepositoryImpl
import com.example.appproject.features.main.domain.repository.ItemInListRepository
import com.example.appproject.features.profile.data.ProfileRepositoryImpl
import com.example.appproject.features.profile.domain.repository.ProfileRepository
import com.example.appproject.features.profile_settigns.data.UserInfoRepositoryImpl
import com.example.appproject.features.profile_settigns.domain.repository.UserInfoRepository
import com.example.appproject.features.registration.data.RegistrationRepositoryImpl
import com.example.appproject.features.registration.domain.repository.RegistrationRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun LoginRepository(
        impl: LoginRepositoryImpl
    ): LoginRepository

    @Binds
    fun newItemUserRepository(
        impl: NewItemUserRepositoryImpl
    ): NewItemUserRepository

    @Binds
    fun newItemRepository(
        impl: NewItemRepositoryImpl
    ): NewItemRepository

    @Binds
    fun userInfoRepository(
        impl: UserInfoRepositoryImpl
    ): UserInfoRepository

    @Binds
    fun itemRepository(
        impl: ItemRepositoryImpl
    ): ItemRepository

    @Binds
    fun userRepositoryForItem(
        impl: UserRepositoryForItemImpl
    ): UserRepositoryForItem

    @Binds
    fun registrationRepository(
        impl: RegistrationRepositoryImpl
    ): RegistrationRepository

    @Binds
    fun profileRepository(
        impl: ProfileRepositoryImpl
    ): ProfileRepository

    @Binds
    fun itemInListRepository(
        impl: ItemInListRepositoryImpl
    ): ItemInListRepository
}