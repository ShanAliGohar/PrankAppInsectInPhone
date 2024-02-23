package com.example.prankappinsectinphone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prankappinsectinphone.adapters.FartPlaylistAdapter
import com.example.prankappinsectinphone.databinding.FragmentFartPlaylistBinding
import com.example.prankappinsectinphone.utils.Constant

class FartPlaylistFragment : Fragment() {

    private val binding: FragmentFartPlaylistBinding by lazy {
        FragmentFartPlaylistBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding.fartPlaylistRv.layoutManager = LinearLayoutManager(requireContext())

        // Sample data for demonstration

        // Initialize and set adapter
        val fartAdapter = FartPlaylistAdapter(requireContext(), Constant.fartPlaylistItems)
        binding.fartPlaylistRv.adapter = fartAdapter

        binding.backIcon.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

}