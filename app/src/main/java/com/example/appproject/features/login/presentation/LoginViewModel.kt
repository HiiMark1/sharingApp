package com.example.appproject.features.login.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appproject.features.login.domain.usercase.SignInUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {
    private var _isCompleted: MutableLiveData<Result<Boolean>> = MutableLiveData()
    val isCompleted: LiveData<Result<Boolean>> = _isCompleted

    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            try {
                val isCompleted = signInUseCase(email, password)
                _isCompleted.value = Result.success(isCompleted)
            } catch (ex: Exception) {
                _isCompleted.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }
}