package com.example.appproject.features.main.adapter

import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.appproject.databinding.ItemThingBinding
import com.example.appproject.features.main.domain.ItemInList
import com.example.appproject.features.models.Settings

class ItemHolder(
    private val binding: ItemThingBinding,
    private val action: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var itemInList: ItemInList? = null

    init {
        itemView.setOnClickListener {
            itemInList?.id?.also(action)
        }
    }

    fun bind(item: ItemInList) {
        this.itemInList = item
        with(binding) {
            ivThingPhoto.load(Uri.parse(item.photoUri)) {
                transformations(CircleCropTransformation())
            }
            tvName.text = item.name
            tvAddressItem.text = item.address
            if(item.nowUserId!="null"){
                when(Settings.colorForTakenItems){
                    "red" -> itemView.setBackgroundColor(Color.RED)
                    "gray" -> itemView.setBackgroundColor(Color.GRAY)
                    "blue" -> itemView.setBackgroundColor(Color.BLUE)
                }
            } else {
                when(Settings.colorForFreeItems){
                    "red" -> itemView.setBackgroundColor(Color.RED)
                    "gray" -> itemView.setBackgroundColor(Color.GRAY)
                    "blue" -> itemView.setBackgroundColor(Color.BLUE)
                }
            }
        }
    }

    companion object {

        fun create(
            parent: ViewGroup,
            action: (String) -> Unit
        ) = ItemHolder(
            ItemThingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), action
        )
    }
}