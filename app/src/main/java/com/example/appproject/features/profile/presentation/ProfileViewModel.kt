package com.example.appproject.features.profile.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appproject.features.profile.domain.model.UserInfo
import com.example.appproject.features.profile.domain.usecase.GetCurrentUserIdUseCase
import com.example.appproject.features.profile.domain.usecase.GetUserInfoUseCase
import com.example.appproject.features.profile.domain.usecase.SignOutUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val signOutUseCase: SignOutUseCase,
): ViewModel() {
    private var _currentUserId: MutableLiveData<Result<String?>> = MutableLiveData()
    val currentUserId: LiveData<Result<String?>> = _currentUserId

    private var _user: MutableLiveData<Result<UserInfo?>> = MutableLiveData()
    val user: LiveData<Result<UserInfo?>> = _user

    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error

    fun getUserInfo(uid: String){
        viewModelScope.launch {
            try {
                val user = getUserInfoUseCase(uid)
                _user.value = Result.success(user)
            } catch (ex: Exception) {
                _user.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

    fun getCurrentUserId() {
        viewModelScope.launch {
            try {
                val currentUserId = getCurrentUserIdUseCase()
                _currentUserId.value = Result.success(currentUserId)
            } catch (ex: Exception) {
                _currentUserId.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            try{
                signOutUseCase
            } catch (ex: Exception){
                _error.value = ex
            }
        }
    }
}