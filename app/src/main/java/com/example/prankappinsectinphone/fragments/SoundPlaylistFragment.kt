package com.example.prankappinsectinphone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.prankappinsectinphone.R
import com.example.prankappinsectinphone.databinding.FragmentSoundDetailBinding
import com.example.prankappinsectinphone.databinding.FragmentSoundPlaylistBinding

class SoundPlaylistFragment : Fragment() {

    private val binding: FragmentSoundPlaylistBinding by lazy {
        FragmentSoundPlaylistBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val audioFilePath = "android.resource://${requireActivity().packageName}/${R.raw.airhorn1}"
        binding.audioVisualizerView.visualizeAudio(audioFilePath)

        return binding.root
    }


}