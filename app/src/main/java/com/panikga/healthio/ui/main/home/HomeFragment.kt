package com.panikga.healthio.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.panikga.healthio.data.local.entity.Category
import com.panikga.healthio.databinding.FragmentHomeBinding
import com.panikga.healthio.ui.main.home.category.CategoryAdapter
import com.panikga.healthio.ui.main.home.category.CategoryData

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var categoryList: ArrayList<Category> = arrayListOf()
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        binding.categoryRecyclerView.setHasFixedSize(true)
        categoryList.addAll(CategoryData.listData)
        showRecylerView()
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            binding.textHome.text = it
        })
        return binding.root
    }

    private fun showRecylerView() {
        binding.categoryRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.categoryRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL ,false)
        val categoryAdapter = CategoryAdapter(categoryList)
        binding.categoryRecyclerView.adapter = categoryAdapter
    }
}