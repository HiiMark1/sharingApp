package com.example.appproject.features.main.presentation.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.example.appproject.features.main.domain.model.ItemInList

class ItemDiffItemCallback: DiffUtil.ItemCallback<ItemInList>() {
    override fun areItemsTheSame(oldItem: ItemInList, newItem: ItemInList): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ItemInList, newItem: ItemInList): Boolean = oldItem == newItem

}