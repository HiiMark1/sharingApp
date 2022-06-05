package com.example.appproject.profile_settigns.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appproject.profile_settigns.domain.model.UserInfo
import com.example.appproject.profile_settigns.domain.use_case.GetInfoUseCase
import com.example.appproject.profile_settigns.domain.use_case.UpdateInfoUseCase
import kotlinx.coroutines.launch

class ProfileSettingsViewModel(
    private val getInfoUseCase: GetInfoUseCase,
    private val updateInfoUseCase: UpdateInfoUseCase,
): ViewModel() {
    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error

    private var _userInfo: MutableLiveData<Result<UserInfo?>> = MutableLiveData()
    val userInfo: LiveData<Result<UserInfo?>> = _userInfo

    fun getUserInfo(uid: String) {
        viewModelScope.launch {
            try {
                val userInfo = getInfoUseCase(uid)
                _userInfo.value = Result.success(userInfo)
            } catch (e: Exception){
                _error.value = e
            }
        }
    }

    fun updateUserInfo(userInfo: UserInfo){
        viewModelScope.launch {
            try {
                updateInfoUseCase(userInfo)
            } catch (e: Exception) {
                _error.value = e
            }
        }
    }
}