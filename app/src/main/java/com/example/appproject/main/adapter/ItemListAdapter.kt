package com.example.appproject.main.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.appproject.main.domain.ItemInList
import com.example.appproject.main.diffutils.ItemDiffItemCallback

class ItemListAdapter(
    private val action: (String) -> Unit,
) : ListAdapter<ItemInList, ItemHolder>(ItemDiffItemCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemHolder = ItemHolder.create(parent, action)

    override fun onBindViewHolder(holder: ItemHolder, position: Int) =
        holder.bind(getItem(position))

    override fun submitList(list: MutableList<ItemInList?>?) {
        super.submitList(if (list == null) null else ArrayList(list))
    }
}