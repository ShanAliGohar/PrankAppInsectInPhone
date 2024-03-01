package com.example.prankappinsectinphone.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.prankappinsectinphone.adapters.InsectHomeScreenAdapter
import com.example.prankappinsectinphone.databinding.FragmentInsectsHomeBinding
import com.example.prankappinsectinphone.service.OverlayService
import com.example.prankappinsectinphone.utils.Constant

class InsectsHomeFragment : Fragment() {
    private val binding: FragmentInsectsHomeBinding by lazy {
        FragmentInsectsHomeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val layoutManager = GridLayoutManager(context, 2)
        binding.gridRecyclerView.layoutManager = layoutManager

        val adapter =
            InsectHomeScreenAdapter(requireContext(), Constant.initializeInsectHomeScreenItem())
        binding.gridRecyclerView.adapter = adapter

        binding.counterLayout.bottomView.setOnClickListener {
            startService()
        }

        binding.backIcon.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }


    private fun startService() {
        val serviceIntent = Intent(requireContext(), OverlayService::class.java)
        serviceIntent.putExtra("spider", Constant.resource)
        serviceIntent.putExtra("soundEffect", Constant.musicResource)
        requireActivity().startService(serviceIntent)
    }

}
