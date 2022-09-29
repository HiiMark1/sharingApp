package com.example.appproject.features.item.presentation

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.example.appproject.MainActivity
import com.example.appproject.R
import com.example.appproject.databinding.FragmentItemBinding
import com.example.appproject.features.item.domain.model.Item
import com.example.appproject.utils.AppViewModelFactory
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

private const val ARG_NAME = "item_id"
private const val ARG_NAME_USER_ID = "user_id"

class ItemFragment : Fragment(R.layout.fragment_item) {
    @Inject
    lateinit var factory: AppViewModelFactory
    private lateinit var binding: FragmentItemBinding
    private val viewModel: ItemViewModel by viewModels {
        factory
    }
    private lateinit var itemId: String
    private lateinit var uid: String
    private lateinit var item: Item
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as MainActivity).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentItemBinding.bind(view)
        val item_id = arguments?.getString(ARG_NAME).toString()
        itemId = item_id

        initObservers()
        viewModel.getItem(itemId)
    }

    private fun initObservers() {
        viewModel.item.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = {
                with(binding) {
                    if (it != null) {
                        item = it
                        tvNameOfItemm.text = it.name
                        tvAddressOfItemm.text = it.address
                        tvDescriptionOfItemm.text = it.desc
                        tvChapterInfo.text = it.chapter
                        ivItemPhoto.load(Uri.parse(it?.photoUri)){
                            transformations(CircleCropTransformation())
                        }
                        viewModel.getCurrentUserId()
                    }
                }
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }

        viewModel.currentUserId.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = {
                with(binding) {
                    if (it != null) {
                        uid = it
                            viewModel.getUserInfo(item.nowUserId!!)
                    }
                }
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }

        viewModel.user.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = {
                with(binding) {
                    if (it != null) {
                        tvNowUser.visibility = View.VISIBLE
                        tvNameNowUser.visibility = View.VISIBLE
                        tvOfficeNowUser.visibility = View.VISIBLE
                        tvNameNowUser.text = it.name
                        tvOfficeNowUser.text = it.address

                        ivNowUserPhoto.visibility = View.VISIBLE
                        ivNowUserPhoto.load(Uri.parse(it?.photoUri)){
                            transformations(CircleCropTransformation())
                        }
                        ivNowUserPhoto.setOnClickListener {
                            view?.findNavController()?.navigate(
                                R.id.action_itemFragment_to_profileFragment,
                                bundleOf(ARG_NAME_USER_ID to item.nowUserId)
                            )
                        }

                        if(it.userId==item.nowUserId){
                            btnBackItem.visibility = View.VISIBLE
                            btnBackItem.setOnClickListener {
                                viewModel.freeItem(item, itemId)
                                viewModel.getItem(itemId)
                            }
                        }

                        if(item.nowUserId != "null") {
                            btnTakeItem.visibility = View.VISIBLE
                            btnTakeItem.setOnClickListener {
                                viewModel.takeItem(uid, item, itemId)
                                viewModel.getItem(itemId)
                            }
                        }

                        if(it.userId==uid){
                            ivDelete.visibility = View.VISIBLE
                            ivDelete.setOnClickListener {
                                viewModel.deletePost(itemId)
                                view?.findNavController()?.navigateUp()
                                showMessage(R.string.item_success_deleted)
                            }
                        }

                    }
                }
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }
    }

    private fun showMessage(stringId: Int) {
        Snackbar.make(
            requireView(),
            stringId,
            Snackbar.LENGTH_LONG
        ).show()
    }
}