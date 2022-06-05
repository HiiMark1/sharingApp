package com.example.appproject.item.presentation

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import coil.load
import com.example.appproject.R
import com.example.appproject.item.domain.model.Item
import com.example.appproject.databinding.FragmentItemBinding
import com.example.appproject.di.DIContainer
import com.example.appproject.profile_settigns.domain.model.UserInfo
import com.example.appproject.profile_settigns.presentation.ProfileSettingsViewModel
import com.example.appproject.utils.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

private const val ARG_NAME = "item_id"
private const val ARG_NAME_USER_ID = "user_id"

class ItemFragment : Fragment(R.layout.fragment_item) {
    private lateinit var binding: FragmentItemBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var itemDbRef: DatabaseReference
    private lateinit var userInfoDbRef: DatabaseReference
    private lateinit var viewModel: ItemViewModel
    private lateinit var item: Item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        database = Firebase.database
        itemDbRef = database.getReference("item")
        userInfoDbRef = database.getReference("usersInfo")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentItemBinding.bind(view)
        val item_id = arguments?.getString(ARG_NAME).toString()
        var user = auth.currentUser

        initObjects()
        initObservers()

        if (user != null) {
            viewModel.getItem(item_id)
            if (item != null) {
                with(binding) {
                    if (item.nowUserId == "null") {
                        tvNameNowUser.visibility = View.INVISIBLE
                        tvOfficeNowUser.visibility = View.INVISIBLE
                        ivNowUserPhoto.visibility = View.INVISIBLE
                        tvNowUser.visibility = View.INVISIBLE
                        btnTakeItem.visibility = View.VISIBLE

                        btnTakeItem.setOnClickListener {
                            item.nowUserId = user.uid
                            viewModel.updateOwner(item_id, item)
                            view.findNavController().navigateUp()
                        }
                    } else {
                        userInfoDbRef.child(item.nowUserId.toString()).get()
                            .addOnSuccessListener {
                                val userNowInfo = it.getValue(UserInfo::class.java)
                                tvNameNowUser.text = userNowInfo?.name
                                tvOfficeNowUser.text = userNowInfo?.address
                                ivNowUserPhoto.load(Uri.parse(userNowInfo?.photoUri))

                                ivNowUserPhoto.setOnClickListener {
                                    view.findNavController().navigate(
                                        R.id.action_itemFragment_to_profileFragment,
                                        bundleOf(ARG_NAME to item.nowUserId)
                                    )
                                }

                                if (userNowInfo?.userId == user.uid) {
                                    btnBackItem.visibility = View.VISIBLE

                                    btnBackItem.setOnClickListener {
                                        item.nowUserId = "null"
                                        itemDbRef.child(item_id).setValue(item)
                                        view.findNavController().navigateUp()
                                    }
                                }
                            }
                    }
                }
            }
        }
    }

    private fun initObjects() {
        val factory = ViewModelFactory(DIContainer)
        viewModel = ViewModelProvider(
            this,
            factory
        )[ItemViewModel::class.java]
    }

    private fun initObservers() {
        viewModel.error.observe(viewLifecycleOwner) {
            Log.e("e", it.message.toString())
        }

        viewModel.item.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = { itemInfo ->
                if (itemInfo != null) {
                    item = itemInfo
                }
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }
    }
}