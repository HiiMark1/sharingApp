package com.example.appproject.item.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appproject.item.domain.model.Item
import com.example.appproject.item.domain.use_case.GetItemUseCase
import com.example.appproject.item.domain.use_case.UpdateOwnerUseCase
import kotlinx.coroutines.launch

class ItemViewModel(
    private val getItemUseCase: GetItemUseCase,
    private val updateOwnerUseCase: UpdateOwnerUseCase,
) : ViewModel() {
    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error

    private var _item: MutableLiveData<Result<Item?>> = MutableLiveData()
    val item: LiveData<Result<Item?>> = _item

    fun getItem(item_id: String) {
        viewModelScope.launch {
            try {
                val item = getItemUseCase(item_id)
                _item.value = Result.success(item)
            } catch (e: Exception) {
                _error.value = e
            }
        }
    }

    fun updateOwner(item_id: String, item: Item) {
        viewModelScope.launch {
            try {
                updateOwnerUseCase(item_id, item)
            } catch (e: Exception) {
                _error.value = e
            }
        }
    }
}