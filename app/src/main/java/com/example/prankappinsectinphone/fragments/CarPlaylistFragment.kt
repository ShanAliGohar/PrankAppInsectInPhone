package com.example.prankappinsectinphone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prankappinsectinphone.R
import com.example.prankappinsectinphone.adapters.CarPlaylistAdapter
import com.example.prankappinsectinphone.adapters.FartPlaylistAdapter
import com.example.prankappinsectinphone.databinding.FragmentCarPlaylistBinding
import com.example.prankappinsectinphone.utils.Constant


class CarPlaylistFragment : Fragment() {
    private val binding: FragmentCarPlaylistBinding by lazy {
        FragmentCarPlaylistBinding.inflate(layoutInflater)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.fartPlaylistRv.layoutManager = LinearLayoutManager(requireContext())

        // Sample data for demonstration

        // Initialize and set adapter
        val carPlaylistAdapter = CarPlaylistAdapter(requireContext(), Constant.carPlaylistItems)
        binding.fartPlaylistRv.adapter = carPlaylistAdapter
        binding.backIcon.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }
}