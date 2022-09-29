package com.example.appproject.features.item.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appproject.features.item.domain.model.Item
import com.example.appproject.features.item.domain.model.UserInfo
import com.example.appproject.features.item.domain.usecase.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class ItemViewModel @Inject constructor(
    private val deleteItemUseCase: DeleteItemUseCase,
    private val freeItemUseCase: FreeItemUseCase,
    private val getItemUseCase: GetItemUseCase,
    private val takeItemUseCase: TakeItemUseCase,
    private val getOwnerUseCase: GetOwnerUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
) : ViewModel() {
    private var _item: MutableLiveData<Result<Item?>> = MutableLiveData()
    val item: LiveData<Result<Item?>> = _item

    private var _user: MutableLiveData<Result<UserInfo?>> = MutableLiveData()
    val user: LiveData<Result<UserInfo?>> = _user

    private var _currentUserId: MutableLiveData<Result<String?>> = MutableLiveData()
    val currentUserId: LiveData<Result<String?>> = _currentUserId

    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error

    fun getItem(itemId: String) {
        viewModelScope.launch {
            try {
                val item = getItemUseCase(itemId)
                _item.value = Result.success(item)
            } catch (ex: Exception) {
                _item.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

    fun getUserInfo(uid: String) {
        viewModelScope.launch {
            try {
                val user = getOwnerUseCase(uid)
                _user.value = Result.success(user)
            } catch (ex: Exception) {
                _user.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

    fun deletePost(itemId: String) {
        viewModelScope.launch {
            try {
                deleteItemUseCase(itemId)
            } catch (ex: Exception) {
                _error.value = ex
            }
        }
    }

    fun freeItem(item: Item, itemId: String) {
        viewModelScope.launch {
            try {
                freeItemUseCase(item, itemId)
            } catch (ex: Exception) {
                _error.value = ex
            }
        }
    }

    fun takeItem(uid: String, item: Item, itemId: String) {
        viewModelScope.launch {
            try {
                takeItemUseCase(uid, item, itemId)
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
}