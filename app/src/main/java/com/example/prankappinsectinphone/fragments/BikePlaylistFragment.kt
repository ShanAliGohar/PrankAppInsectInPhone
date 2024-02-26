package com.example.prankappinsectinphone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prankappinsectinphone.R
import com.example.prankappinsectinphone.adapters.BikePlaylistAdapter
import com.example.prankappinsectinphone.adapters.CarPlaylistAdapter
import com.example.prankappinsectinphone.adapters.FartPlaylistAdapter
import com.example.prankappinsectinphone.databinding.FragmentBikePlaylistBinding
import com.example.prankappinsectinphone.databinding.FragmentCarPlaylistBinding
import com.example.prankappinsectinphone.utils.Constant


class BikePlaylistFragment : Fragment() {
    private val binding: FragmentBikePlaylistBinding by lazy {
        FragmentBikePlaylistBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.fartPlaylistRv.layoutManager = LinearLayoutManager(requireContext())

        // Sample data for demonstration

        // Initialize and set adapter
        val bikePlaylistAdapter = BikePlaylistAdapter(requireContext(), Constant.bikePlaylistItems)
        binding.fartPlaylistRv.adapter = bikePlaylistAdapter
        binding.backIcon.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }


}