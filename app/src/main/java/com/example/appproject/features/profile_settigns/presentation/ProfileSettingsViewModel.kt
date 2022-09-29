package com.example.appproject.features.profile_settigns.presentation

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appproject.features.profile_settigns.domain.model.UserInfo
import com.example.appproject.features.profile_settigns.domain.usecase.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileSettingsViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val uploadAvatarAndGetIsCompletedUseCase: UploadAvatarAndGetIsCompletedUseCase,
    private val getDownloadAvatarUriUseCase: GetDownloadAvatarUriUseCase,
): ViewModel() {
    private var _currentUserId: MutableLiveData<Result<String?>> = MutableLiveData()
    val currentUserId: LiveData<Result<String?>> = _currentUserId

    private var _user: MutableLiveData<Result<UserInfo?>> = MutableLiveData()
    val user: LiveData<Result<UserInfo?>> = _user

    private var _isCompletedUploadingAvatar: MutableLiveData<Result<Boolean>> = MutableLiveData()
    val isCompletedUploadingAvatar: LiveData<Result<Boolean>> = _isCompletedUploadingAvatar

    private var _downloadAvatarUri: MutableLiveData<Result<Uri?>> = MutableLiveData()
    val downloadAvatarUri: LiveData<Result<Uri?>> = _downloadAvatarUri

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

    fun updateUserInfo(userInfo: UserInfo){
        viewModelScope.launch {
            try {
                updateUserInfoUseCase(userInfo)
            } catch (ex: Exception) {
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

    fun uploadAvatarAndGetIsCompleted(uri: Uri) {
        viewModelScope.launch {
            try {
                val isCompleted = uploadAvatarAndGetIsCompletedUseCase(uri)
                _isCompletedUploadingAvatar.value = Result.success(isCompleted)
            } catch (ex: Exception) {
                _isCompletedUploadingAvatar.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

    fun onGetDownloadAvatarUri(uri: Uri) {
        viewModelScope.launch {
            try {
                val downloadAvatarUri = getDownloadAvatarUriUseCase(uri)
                _downloadAvatarUri.value = Result.success(downloadAvatarUri)
            } catch (ex: Exception) {
                _downloadAvatarUri.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }
}