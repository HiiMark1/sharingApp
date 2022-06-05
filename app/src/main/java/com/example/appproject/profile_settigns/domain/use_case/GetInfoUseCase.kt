package com.example.appproject.profile_settigns.domain.use_case

import com.example.appproject.profile_settigns.data.ProfileSettingsRepositoryImpl
import com.example.appproject.profile_settigns.domain.model.UserInfo
import com.example.appproject.profile_settigns.domain.repo.ProfileSettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetInfoUseCase(
    private val repository: ProfileSettingsRepository
) {
    suspend operator fun invoke(uid: String): UserInfo? {
        return withContext(Dispatchers.Main) {
            repository.getInfo(uid)
        }
    }
}