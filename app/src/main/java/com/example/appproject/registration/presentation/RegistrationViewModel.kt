package com.example.appproject.registration.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appproject.registration.domain.use_case.AddUserInfoInDbUseCase
import com.example.appproject.registration.domain.use_case.CreateUserUseCase
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val addUserInfoInDbUseCase: AddUserInfoInDbUseCase,
    private val createUserUseCase: CreateUserUseCase,
): ViewModel() {
    private var _uid: MutableLiveData<Result<String?>> = MutableLiveData()
    val uid: LiveData<Result<String?>> = _uid

    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error

    fun addUserInfoInDb(uid: String, email: String){
        viewModelScope.launch {
            try {
                addUserInfoInDbUseCase(uid, email)
            } catch (e: Exception){
                _error.value = e
            }
        }
    }

    fun createUser(email: String, password: String){
        viewModelScope.launch {
            try {
                createUserUseCase(email, password)
            } catch (e: Exception) {
                _error.value = e
            }
        }
    }
}