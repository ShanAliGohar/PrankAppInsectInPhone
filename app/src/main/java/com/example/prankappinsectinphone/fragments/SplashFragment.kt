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
    private var onpauseLogic = false
    private var splashJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       /* splashJob = CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
            onpauseLogic = true
            delay(2500)
            navigateToNextScreen()
        }
*/
        binding.horizontalProgressBar.animateProgress({ findNavController().navigate(R.id.action_splashFragment_to_homeFragment) })

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        onpauseLogic = true
    }

  /*  private suspend fun navigateToNextScreen() {
        withContext(Dispatchers.Main) {
            if (onpauseLogic) {
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        splashJob?.cancel() // Cancel the job when the view is destroyed to avoid leaks
    }*/
}
