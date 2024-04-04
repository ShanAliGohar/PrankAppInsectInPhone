package com.example.prankappinsectinphone.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.prankappinsectinphone.adapters.HomeScreenAdapter
import com.example.prankappinsectinphone.databinding.FragmentHomeBinding
import com.example.prankappinsectinphone.utils.Constant
import com.example.prankappinsectinphone.utils.Constant.isInHome

class HomeFragment : Fragment() {

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupRecyclerView()
        setupDrawerIconClickListener()

        binding.myDrawer.privacyPolicyText.setOnClickListener {
            val myIntent: Intent = Intent.parseUri("https://sites.google.com/view/prank-insect-privacy-policy/home", Intent.URI_INTENT_SCHEME)
            startActivity(myIntent)
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.gridRecyclerView.layoutManager = GridLayoutManager(context, 2)
        val adapter = HomeScreenAdapter(requireContext(), Constant.homeScreenItem)
        binding.gridRecyclerView.adapter = adapter
    }

    private fun setupDrawerIconClickListener() {
        binding.drawerIcon.setOnClickListener {
            toggleLeftDrawer()
        }
    }

    private fun toggleLeftDrawer() {
        val gravity = if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            GravityCompat.END
        } else {
            GravityCompat.START
        }
        binding.drawerLayout.openDrawer(gravity)
    }

    override fun onResume() {
        super.onResume()
        isInHome = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isInHome = false
    }

    override fun onDestroy() {
        super.onDestroy()
        isInHome = false
    }
}
