package com.example.prankappinsectinphone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.prankappinsectinphone.R
import com.example.prankappinsectinphone.databinding.FragmentInsectsHomeBinding
import com.example.prankappinsectinphone.databinding.FragmentSoundDetailBinding


class SoundDetailFragment : Fragment() {

    private val binding: FragmentSoundDetailBinding by lazy {
        FragmentSoundDetailBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

}