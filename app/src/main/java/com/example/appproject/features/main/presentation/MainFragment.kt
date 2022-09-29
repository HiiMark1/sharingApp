package com.example.appproject.features.main.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.appproject.MainActivity
import com.example.appproject.R
import com.example.appproject.features.add_new_item.domain.model.Item
import com.example.appproject.databinding.FragmentMainBinding
import com.example.appproject.features.main.presentation.adapter.ItemListAdapter
import com.example.appproject.features.main.domain.model.ItemInList
import com.example.appproject.utils.AppViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

private const val ARG_NAME = "item_id"

class MainFragment : Fragment(R.layout.fragment_main) {
    @Inject
    lateinit var factory: AppViewModelFactory
    private lateinit var binding: FragmentMainBinding
    private var itemListAdapter: ItemListAdapter? = null
    var limit = 40
    private val viewModel: MainViewModel by viewModels {
        factory
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var itemDbRef: DatabaseReference
    private var itemLimit = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as MainActivity).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        database = Firebase.database
        itemDbRef = database.getReference("item")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.visibility = View.VISIBLE

        binding = FragmentMainBinding.bind(view)

        observeItems()

        viewModel.onGetPosts(limit)

        with(binding) {
            swipeContainer.setOnRefreshListener {
                limit = 30
                observeItems()
                swipeContainer.isRefreshing = false
            }

            posts.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (!recyclerView.canScrollVertically(1) && dy != 0) {
                        viewModel.onGetPosts(limit)
                        observeMoreItems()
                    }
                }
            })
        }

        with(binding){
            btnAddNewItem.setOnClickListener {
                view.findNavController().navigate(R.id.action_mainFragment_to_addNewItemFragment)
            }
        }
    }

    private fun observeItems() {
        viewModel.items.observe(viewLifecycleOwner) { it ->
            it?.fold(onSuccess = { posts ->
                itemListAdapter = ItemListAdapter {
                    infoAboutItem(it)
                    itemListAdapter?.submitList(posts)
                }

                val decorator = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)

                binding.posts.run {
                    adapter = itemListAdapter
                    addItemDecoration(decorator)
                }

                itemListAdapter?.submitList(posts)
                viewModel.clearPostsLiveData()
                limit += 30
                viewModel.items.removeObservers(viewLifecycleOwner)
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }
    }

    private fun observeMoreItems() {
        viewModel.items.observe(viewLifecycleOwner) { it ->
            it?.fold(onSuccess = {
                itemListAdapter?.submitList(it)
                viewModel.clearPostsLiveData()
                limit += 30
                viewModel.items.removeObservers(viewLifecycleOwner)
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }
    }

    private fun infoAboutItem(it: String) {
        view?.findNavController()
            ?.navigate(R.id.action_mainFragment_to_itemFragment,
                bundleOf(ARG_NAME to it)
            )
    }
}