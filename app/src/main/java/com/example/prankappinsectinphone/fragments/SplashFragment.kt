package com.example.prankappinsectinphone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.prankappinsectinphone.MainActivity
import com.example.prankappinsectinphone.R
import com.example.prankappinsectinphone.databinding.FragmentSplashBinding
import kotlinx.coroutines.*
class SplashFragment : Fragment() {
    private val binding: FragmentSplashBinding by lazy {
        FragmentSplashBinding.inflate(layoutInflater)
    }
    private var onpauseLogic = false
    private var splashJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.horizontalProgressBar.animateProgress { findNavController().navigate(R.id.action_splashFragment_to_homeFragment) }

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        binding.horizontalProgressBar.pauseAnimator()
    }

    override fun onResume() {
        super.onResume()
        binding.horizontalProgressBar.resumeAnimator()
    }

}
