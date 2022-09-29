package com.example.appproject.features.add_new_item.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appproject.features.add_new_item.domain.model.Item
import com.example.appproject.features.add_new_item.domain.usecase.AddNewItemUseCase
import com.example.appproject.features.add_new_item.domain.usecase.GetCurrentUserIdUseCase
import com.example.appproject.features.item.domain.usecase.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddNewItemViewModel @Inject constructor(
    private val addNewItemUseCase: AddNewItemUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
) : ViewModel()  {
    private var _currentUserId: MutableLiveData<Result<String?>> = MutableLiveData()
    val currentUserId: LiveData<Result<String?>> = _currentUserId

    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error

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

    fun addNewItem(item: Item){
        viewModelScope.launch {
            try {
                addNewItemUseCase(item)
            } catch (ex: Exception) {
                _error.value = ex
            }
        }
    }
}