package com.example.prankappinsectinphone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prankappinsectinphone.adapters.CarPlaylistAdapter
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
        setupRecyclerView()
        setupBackIconClickListener()
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.fartPlaylistRv.layoutManager = LinearLayoutManager(requireContext())
        val carPlaylistAdapter = CarPlaylistAdapter(requireContext(), Constant.carPlaylistItems)
        binding.fartPlaylistRv.adapter = carPlaylistAdapter
    }

    private fun setupBackIconClickListener() {
        binding.backIcon.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}
