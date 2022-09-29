package com.example.appproject.features.main.presentation

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.appproject.R
import com.example.appproject.features.add_new_item.domain.model.Item
import com.example.appproject.databinding.FragmentMainBinding
import com.example.appproject.features.main.adapter.ItemListAdapter
import com.example.appproject.features.main.domain.model.ItemInList
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

private const val ARG_NAME = "item_id"

class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var itemDbRef: DatabaseReference
    private lateinit var itemListAdapter: ItemListAdapter
    private var itemLimit = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        database = Firebase.database
        itemDbRef = database.getReference("item")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.visibility = View.VISIBLE

        binding = FragmentMainBinding.bind(view)

        var user = auth.currentUser
        if (user != null) {
            updatePosts()

            with(binding) {
                swipeContainer.setOnRefreshListener {
                    updatePosts()
                    swipeContainer.isRefreshing = false
                }

                posts.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)

                        if (!recyclerView.canScrollVertically(1) && dy != 0) {
                            val items = itemDbRef.limitToLast(itemLimit)

                            items.addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    val itemList = mutableListOf<ItemInList?>()
                                    for (postSnapshot in dataSnapshot.children) {
                                        val item = postSnapshot.getValue(Item::class.java)
                                        itemList.add(
                                            ItemInList(
                                                postSnapshot.key,
                                                item?.userId,
                                                item?.name,
                                                item?.address,
                                                item?.chapter,
                                                item?.desc,
                                                item?.photoUri,
                                                item?.nowUserId,
                                                item?.isTaken
                                            )
                                        )
                                    }
                                    itemList.reverse()

                                    itemListAdapter?.submitList(itemList)
                                    items.removeEventListener(this)
                                    itemLimit += 30
                                }

                                override fun onCancelled(databaseError: DatabaseError) {}
                            })
                        }
                    }
                })
            }
        } else {
            view?.findNavController()?.navigate(R.id.action_mainFragment_to_loginFragment)
        }

        with(binding){
            btnAddNewItem.setOnClickListener {
                view.findNavController().navigate(R.id.action_mainFragment_to_addNewItemFragment)
            }
        }
    }

    private fun updatePosts() {
        val posts = itemDbRef.limitToLast(itemLimit)

        posts.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val itemList = mutableListOf<ItemInList?>()
                for (postSnapshot in dataSnapshot.children) {
                    val item = postSnapshot.getValue(Item::class.java)
                    itemList.add(
                        ItemInList(
                            postSnapshot.key,
                            item?.userId,
                            item?.name,
                            item?.address,
                            item?.chapter,
                            item?.desc,
                            item?.photoUri,
                            item?.nowUserId,
                            item?.isTaken
                        )
                    )
                }
                itemList.reverse()
                itemListAdapter = ItemListAdapter {
                    infoAboutItem(it)
                    itemListAdapter?.submitList(itemList)
                }

                val decorator = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)

                binding.posts.run {
                    adapter = itemListAdapter
                    addItemDecoration(decorator)
                }

                itemListAdapter?.submitList(itemList)
                posts.removeEventListener(this)
                itemLimit += 30
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun infoAboutItem(it: String) {
        view?.findNavController()
            ?.navigate(R.id.action_mainFragment_to_itemFragment,
                bundleOf(ARG_NAME to it)
            )
    }
}