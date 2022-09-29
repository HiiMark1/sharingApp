package com.example.appproject.features.add_new_item.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.appproject.MainActivity
import com.example.appproject.R
import com.example.appproject.databinding.FragmentAddItemBinding
import com.example.appproject.features.add_new_item.domain.model.Item
import com.example.appproject.utils.AppViewModelFactory
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class AddNewItemFragment : Fragment(R.layout.fragment_add_item) {
    @Inject
    lateinit var factory: AppViewModelFactory
    private lateinit var binding: FragmentAddItemBinding
    private val viewModel: AddNewItemViewModel by viewModels {
        factory
    }

    private lateinit var uid: String
    private lateinit var chapter: String

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as MainActivity).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddItemBinding.bind(view)

        with(binding) {
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    chapter = parent?.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
        initObservers()
        viewModel.getCurrentUserId()
    }

    private fun initObservers() {
        viewModel.currentUserId.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = {
                if (it != null) {
                    uid = it
                    with(binding) {
                        btnSave.setOnClickListener {
                            if(checkInfo()){
                                val item = Item(
                                    uid,
                                    etNameOfItem.text.toString(),
                                    etOffice.text.toString(),
                                    chapter,
                                    etItemDesc.text.toString(),
                                    "",
                                    "null",
                                    false
                                )
                                viewModel.addNewItem(item)
                                view?.findNavController()?.navigateUp()
                            } else {
                                showMessage(R.string.error_not_enough_info_for_new_item)
                            }
                        }
                    }
                }
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }
    }

    private fun checkInfo(): Boolean {
        with(binding) {
            return (etNameOfItem.text.toString() != "" && etOffice.text.toString() != "" && etItemDesc.text.toString() != "")
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