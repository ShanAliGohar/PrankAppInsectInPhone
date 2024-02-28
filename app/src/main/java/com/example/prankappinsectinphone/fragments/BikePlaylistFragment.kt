package com.example.prankappinsectinphone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prankappinsectinphone.adapters.BikePlaylistAdapter
import com.example.prankappinsectinphone.databinding.FragmentBikePlaylistBinding
import com.example.prankappinsectinphone.utils.Constant

class BikePlaylistFragment : Fragment() {
    private val binding: FragmentBikePlaylistBinding by lazy {
        FragmentBikePlaylistBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupRecyclerView()
        setupBackIconClickListener()
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.fartPlaylistRv.layoutManager = LinearLayoutManager(requireContext())
        val bikePlaylistAdapter = BikePlaylistAdapter(requireContext(), Constant.bikePlaylistItems)
        binding.fartPlaylistRv.adapter = bikePlaylistAdapter
    }

    private fun setupBackIconClickListener() {
        binding.backIcon.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}
