package com.example.prankappinsectinphone.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.prankappinsectinphone.adapters.InsectHomeScreenAdapter
import com.example.prankappinsectinphone.databinding.FragmentInsectsHomeBinding
import com.example.prankappinsectinphone.`interface`.ColorSelectionListener
import com.example.prankappinsectinphone.service.OverlayService
import com.example.prankappinsectinphone.utils.Constant


class InsectsHomeFragment : Fragment(), ColorSelectionListener {
    private val binding: FragmentInsectsHomeBinding by lazy {
        FragmentInsectsHomeBinding.inflate(layoutInflater)
    }
    var sharedPref: SharedPreferences? = null
    private var isServiceRunning: Boolean = false
    var adapter: InsectHomeScreenAdapter? = null
    private val PREFS_NAME = "insect_prefs"
    private var startButtonColorResourceID :Int? = null
    private var startButtonBackgroundColorResourceID :Int? = null


    var startButtonColorEditor : SharedPreferences.Editor? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        startButtonColorResourceID = arguments?.getInt("startButtonColorResource")
        startButtonBackgroundColorResourceID = arguments?.getInt("startButtonBackgroundColorResource")
        sharedPref = activity?.getSharedPreferences("START_SERVICE", Context.MODE_PRIVATE)
        var editor = sharedPref?.edit()
        binding.startTxt.text =
            sharedPref?.getString("stop/start", binding.startTxt.text as String)

        startButtonColorEditor =sharedPref?.edit()
        val layoutManager = GridLayoutManager(context, 2)
        binding.gridRecyclerView.layoutManager = layoutManager
        // adapter = InsectHomeScreenAdapter(requireContext(), Constant.initializeInsectHomeScreenItem(), isServiceRunning)

        val savedStartButtonColor = sharedPref?.getInt("selected_start_button_color", -1)
        val savedBackgroundColor = sharedPref?.getInt("selected_background_color", -1)

        if (savedStartButtonColor != -1 && savedBackgroundColor != -1) {
            val buttonColor =
                savedStartButtonColor?.let { ContextCompat.getColor(requireContext(), it) }
            binding.bottomView.backgroundTintList = buttonColor?.let { ColorStateList.valueOf(it) }

            val backgroundColor =
                savedBackgroundColor?.let { ContextCompat.getColor(requireContext(), it) }
            binding.initConstrainLO.backgroundTintList = backgroundColor?.let {
                ColorStateList.valueOf(
                    it
                )
            }
        }
        var lastPos = loadCheckedState()
        if (Constant.getIsStart(requireContext())) {
            val list = Constant.initializeInsectHomeScreenItem()
            for (i in list.indices) {
                if (i == lastPos) {
                    list[i].isChecked = true
                } else {
                    list[i].isChecked = false
                }
            }

            adapter = InsectHomeScreenAdapter(requireContext(), list, isServiceRunning,this)
        } else {
            adapter = InsectHomeScreenAdapter(
                requireContext(),
                Constant.initializeInsectHomeScreenItem(),
                isServiceRunning,this
            )
        }





        binding.gridRecyclerView.adapter = adapter

        startButtonColorResourceID?.let { binding.bottomView.resources.getColor(it) }

        binding.bottomView.setOnClickListener {
            if (hasOverlayPermission()) {

                if (!Constant.isStart) {
                    Constant.isStart = true
                    Constant.saveIsStart(requireContext(), Constant.isStart)
                    startService()
                    binding.startTxt.text = "Stop"
                } else {
                    stopService()
                    binding.startTxt.text = "Start"
                    Constant.isStart = false
                    Constant.saveIsStart(requireContext(), Constant.isStart)
                }
                editor?.putString("stop/start", binding.startTxt.text as String)
                editor?.apply()
            } else {
                requestOverlayPermission()
            }
        }

        binding.backIcon.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

    private fun loadCheckedState(): Int {
        val sharedPreferences: SharedPreferences =
            requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt("checked_position", -1)
    }

    private fun hasOverlayPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // For Android versions 8.0 (API level 26) to Android 12 (API level 31)
            Settings.canDrawOverlays(requireContext())

        } else {
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
    override fun onColorSelected(startButtonColorResource: Int, startButtonBackgroundColorResource: Int) {
        Log.d("Fragment", "onColorSelected: startButtonColorResource: $startButtonColorResource, startButtonBackgroundColorResource: $startButtonBackgroundColorResource")
        val buttonColor = ContextCompat.getColor(requireContext(), startButtonColorResource)
        binding.bottomView.backgroundTintList= ColorStateList.valueOf(buttonColor)


        val backgroundColor = ContextCompat.getColor(requireContext(), startButtonBackgroundColorResource)
        binding.initConstrainLO.backgroundTintList= ColorStateList.valueOf(backgroundColor)


        startButtonColorEditor?.putInt("selected_start_button_color", startButtonColorResource);
        startButtonColorEditor?.putInt("selected_background_color", startButtonBackgroundColorResource);
        startButtonColorEditor?.apply(); // Apply changes to SharedPreferences
    }
}
