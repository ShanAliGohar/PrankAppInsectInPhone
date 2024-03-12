package com.example.prankappinsectinphone.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.MotionEvent
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
    var sharedPref: SharedPreferences? = null
    private var isServiceRunning: Boolean = false
    var adapter : InsectHomeScreenAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPref = activity?.getSharedPreferences("START_SERVICE", Context.MODE_PRIVATE)
        var editor = sharedPref?.edit()
        binding.counterLayout.startTxt.text =
            sharedPref?.getString("stop/start", binding.counterLayout.startTxt.text as String)
        val layoutManager = GridLayoutManager(context, 2)
        binding.gridRecyclerView.layoutManager = layoutManager

         adapter = InsectHomeScreenAdapter(requireContext(), Constant.initializeInsectHomeScreenItem(), isServiceRunning)

        binding.gridRecyclerView.adapter = adapter

        binding.counterLayout.bottomView.setOnClickListener {
            if (hasOverlayPermission()) {

                if (!Constant.isStart) {
                    Constant.isStart = true
                    startService()
                    binding.counterLayout.startTxt.text = "Stop"


                } else {
                    stopService()
                    binding.counterLayout.startTxt.text = "Start"
                    Constant.isStart = false

                }
                editor?.putString("stop/start", binding.counterLayout.startTxt.text as String)
                editor?.apply()
            } else{
                requestOverlayPermission()
            }
        }

        binding.backIcon.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

    private fun hasOverlayPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // For Android versions 8.0 (API level 26) to Android 12 (API level 31)
                Settings.canDrawOverlays(requireContext())

            } else
            {
            true
        }
    }
    private fun requestOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Request overlay permission
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:${context?.packageName}")
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
    private fun startService() {


        val serviceIntent = Intent(requireContext(), OverlayService::class.java)
        serviceIntent.putExtra("spider", Constant.resource)
        serviceIntent.putExtra("soundEffect", Constant.musicResource)
        requireActivity().startService(serviceIntent)


        isServiceRunning = true
        adapter?.notifyDataSetChanged()

    }

    private fun stopService() {


        val stopService = Intent(requireContext(), OverlayService::class.java)
        requireActivity().stopService(stopService)

        isServiceRunning = false
        adapter?.notifyDataSetChanged()

    }



}
