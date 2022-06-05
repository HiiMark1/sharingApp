package com.example.appproject.login.domain.use_case

import com.example.appproject.login.domain.repository.LoginRepository
import com.example.appproject.profile_settigns.domain.model.UserInfo
import com.example.appproject.profile_settigns.domain.repo.ProfileSettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SignInUseCase(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(email: String, password: String) {
        return withContext(Dispatchers.Main) {
            repository.signIn(email, password)
        }
    }
}