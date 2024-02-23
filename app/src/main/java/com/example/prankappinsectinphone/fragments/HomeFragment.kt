package com.example.prankappinsectinphone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.prankappinsectinphone.R
import com.example.prankappinsectinphone.adapters.HomeScreenAdapter
import com.example.prankappinsectinphone.databinding.FragmentHomeBinding
import com.example.prankappinsectinphone.utils.Constant


class HomeFragment : Fragment() {

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val layoutManager = GridLayoutManager(context, 2) // Change the spanCount as needed
        binding.gridRecyclerView.layoutManager = layoutManager

        val adapter = HomeScreenAdapter(requireContext(), Constant.homeScreenItem)
        binding.gridRecyclerView.adapter = adapter

        binding.drawerIcon.setOnClickListener {
            toggleLeftDrawer()
        }
        return binding.root
    }
    private fun toggleLeftDrawer() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.END)
        } else {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }
}