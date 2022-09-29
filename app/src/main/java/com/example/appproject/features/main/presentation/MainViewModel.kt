package com.example.appproject.features.main.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appproject.features.main.domain.model.ItemInList
import com.example.appproject.features.main.domain.usecase.GetItemsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase
): ViewModel() {
    private var _items: MutableLiveData<Result<MutableList<ItemInList?>?>?> = MutableLiveData()
    val items: MutableLiveData<Result<MutableList<ItemInList?>?>?> = _items

    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error

    fun onGetPosts(limit: Int) {
        viewModelScope.launch {
            try {
                val items = getItemsUseCase(limit)
                _items.value = Result.success(items)
            } catch (ex: Exception) {
                _items.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

    fun clearPostsLiveData() {
        _items.value = null
    }
}