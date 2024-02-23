package com.example.prankappinsectinphone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.prankappinsectinphone.R
import com.example.prankappinsectinphone.adapters.HomeScreenAdapter
import com.example.prankappinsectinphone.adapters.InsectHomeScreenAdapter
import com.example.prankappinsectinphone.databinding.FragmentHomeBinding
import com.example.prankappinsectinphone.databinding.FragmentInsectsHomeBinding
import com.example.prankappinsectinphone.models.HomeScreenItems
import com.example.prankappinsectinphone.utils.Constant


class InsectsHomeFragment : Fragment() {
    private val binding: FragmentInsectsHomeBinding by lazy {
        FragmentInsectsHomeBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val layoutManager = GridLayoutManager(context, 2) // Change the spanCount as needed
        binding.gridRecyclerView.layoutManager = layoutManager

        val adapter = InsectHomeScreenAdapter(requireContext(), Constant.initializeInsectHomeScreenItem())





        binding.backIcon.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }


}