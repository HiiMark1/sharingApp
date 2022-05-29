package com.example.appproject.item

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import coil.load
import com.example.appproject.R
import com.example.appproject.add_new_item.Item
import com.example.appproject.databinding.FragmentItemBinding
import com.example.appproject.profile_settigns.UserInfo
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

        if (user != null) {
            itemDbRef.child(item_id).get().addOnSuccessListener {
                val item = it.getValue(Item::class.java)
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
                                itemDbRef.child(item_id).setValue(item)
                            }
                        } else {
                            userInfoDbRef.child(item.nowUserId.toString()).get()
                                .addOnSuccessListener {
                                    val userNowInfo = it.getValue(UserInfo::class.java)
                                    tvNameNowUser.text = userNowInfo?.name
                                    tvOfficeNowUser.text = userNowInfo?.address
                                    ivNowUserPhoto.load(Uri.parse(userNowInfo?.photoUri))

                                    ivNowUserPhoto.setOnClickListener {
                                        view.findNavController().navigate(R.id.action_itemFragment_to_profileFragment,
                                        bundleOf(ARG_NAME to item.nowUserId))
                                    }
                                }
                        }
                    }
                }
            }
        }
    }
}