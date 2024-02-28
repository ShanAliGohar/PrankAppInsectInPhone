package com.example.prankappinsectinphone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.prankappinsectinphone.R
import com.example.prankappinsectinphone.databinding.FragmentSplashBinding
import kotlinx.coroutines.*

class SplashFragment : Fragment() {
    private val binding: FragmentSplashBinding by lazy {
        FragmentSplashBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        CoroutineScope(Dispatchers.IO).launch {
            delay(2000)
            navigateToNextScreen()
        }

        binding.horizontalProgressBar.animateProgress()

        return binding.root
    }



    private suspend fun navigateToNextScreen() {
        withContext(Dispatchers.Main) {
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        }
    }
}
