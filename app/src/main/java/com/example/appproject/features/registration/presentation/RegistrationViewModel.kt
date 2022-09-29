package com.example.appproject.features.registration.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appproject.features.registration.domain.usecase.AddUserInfoInDbUseCase
import com.example.appproject.features.registration.domain.usecase.CreateUserUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val addUserInfoInDbUseCase: AddUserInfoInDbUseCase,
    private val createUserUseCase: CreateUserUseCase,
): ViewModel() {
    private var _isCompleted: MutableLiveData<Result<Boolean>> = MutableLiveData()
    val isCompleted: LiveData<Result<Boolean>> = _isCompleted

    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error

    fun addUserInfoInDb(uid: String){
        viewModelScope.launch {
            try {
                addUserInfoInDbUseCase(uid)
            } catch (e: Exception){
                _error.value = e
            }
        }
    }

    fun createUser(email: String, password: String){
        viewModelScope.launch {
            try {
                val isCompleted = createUserUseCase(email, password)
                _isCompleted.value = Result.success(isCompleted)
            } catch (e: Exception) {
                _isCompleted.value = Result.failure(e)
                _error.value = e
            }
        }
    }
}