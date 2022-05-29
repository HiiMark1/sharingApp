package com.example.appproject.main.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.appproject.databinding.ItemThingBinding
import com.example.appproject.main.ItemInList
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

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