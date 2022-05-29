package com.example.appproject.add_new_item

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.appproject.R
import com.example.appproject.databinding.FragmentAddItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class addNewItemFragment : Fragment(R.layout.fragment_add_item) {
    private lateinit var binding: FragmentAddItemBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var itemDbRef: DatabaseReference
    private lateinit var chapter: String
    private var count = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        database = Firebase.database
        itemDbRef = database.getReference("item")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentUser = auth.currentUser
        binding = FragmentAddItemBinding.bind(view)
        if (currentUser != null) {
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

                btnSave.setOnClickListener {
                    if(isEnoughItemInfo()){
                        val item = Item(
                            auth.currentUser?.uid,
                            etOffice.text.toString(),
                            chapter,
                            etItemDesc.text.toString(),
                            count,
                           "https://firebasestorage.googleapis.com/v0/b/sharing-b7eaf.appspot.com/o/the-sledge.webp?alt=media&token=e2a8566c-1e6b-4ff2-891c-1e4363d71952",
                            ""
                        )

                        val key = itemDbRef
                            .push().key
                        if(key!=null){
                            itemDbRef.child(key).setValue(item).addOnSuccessListener {
                                view.findNavController().navigateUp()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun isEnoughItemInfo(): Boolean{
        with(binding){
            var ccount = etItemCount.text.toString()
            if(ccount==""){
                return false
            }
            if(etItemDesc.text.toString() == "" || etOffice.text.toString() == "" || ccount.toInt() < 1) {
                return false
            }
            count = ccount.toInt()
            return true
        }
    }
}