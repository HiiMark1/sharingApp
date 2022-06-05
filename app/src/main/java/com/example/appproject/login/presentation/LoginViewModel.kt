package com.example.appproject.login.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appproject.login.domain.use_case.SignInUseCase
import com.example.appproject.registration.domain.use_case.AddUserInfoInDbUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val signInUseCase: SignInUseCase,
): ViewModel() {
    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error

    fun signIn(email: String, password: String){
        viewModelScope.launch {
            try {
                signInUseCase(email, password)
            } catch (e: Exception){
                _error.value = e
            }
        }
    }
}