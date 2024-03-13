package com.example.prankappinsectinphone.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.prankappinsectinphone.R
import com.example.prankappinsectinphone.databinding.FragmentFartDetailBinding
import com.example.prankappinsectinphone.reciver.VolumeReciver
import com.masoudss.lib.SeekBarOnProgressChanged
import com.masoudss.lib.WaveformSeekBar
import java.io.IOException


class FartDetailFragment : Fragment() {
    private val binding: FragmentFartDetailBinding by lazy {
        FragmentFartDetailBinding.inflate(layoutInflater)
    }

    private var mPlayer: MediaPlayer? = null
    private val mHandler = Handler()
    private var audioManager: AudioManager? = null
    private var rawResourceId: Int? = null
    private var isSpeakButtonLongPressed = false
    private lateinit var sharedPreferences: SharedPreferences


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupMediaPlayer()
        setupVolumeSeekBar()
        setupFartLotiClickListener()
        setupWaveformSeekBar()
        setupBackIconClickListener()
        volumeIconClickListners()

        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)




        activity?.let { activity ->
            val volumeChangeReceiver = VolumeReciver(activity, binding.seekBar,binding.volumeIcon)



            activity.registerReceiver(volumeChangeReceiver, IntentFilter().apply {
                addAction("android.media.VOLUME_CHANGED_ACTION")
            })
        }

        if (!sharedPreferences.getBoolean("fartClickAnimationSeen", false)) {
            binding.clickAnimation.visibility = View.VISIBLE
            // Mark animation as seen
            sharedPreferences.edit().putBoolean("fartClickAnimationSeen", true).apply()
        } else {
            binding.clickAnimation.visibility = View.GONE
        }

        binding.loop.setOnClickListener {
            startPlaying()
            mPlayer?.isLooping = true
            binding.clickAnimation.visibility = View.GONE
        }

        return binding.root
    }

    private fun volumeIconClickListners() {
        binding.volumeIcon.setOnClickListener {
            val currentVolume = audioManager?.getStreamVolume(AudioManager.STREAM_MUSIC) ?: 0
            if (currentVolume == 0) {
                // Volume is currently muted, unmute it
                audioManager?.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager?.getStreamMaxVolume(AudioManager.STREAM_MUSIC) ?: 0, 0)
                binding.volumeIcon.setImageResource(R.drawable.volume_icon)
            } else {
                // Volume is not muted, mute it
                audioManager?.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0)
                binding.volumeIcon.setImageResource(R.drawable.mutedvolumeicon)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        mPlayer?.pause()
       /* activity?.let { activity ->
            val volumeReceiver = VolumeReciver(activity,binding.seekBar)
            activity.unregisterReceiver(volumeReceiver)
        }*/


    }

    private fun setupMediaPlayer() {
        rawResourceId = arguments?.getInt("rawResourceId")
        mPlayer = rawResourceId?.let { MediaPlayer.create(requireContext(), it) }
        audioManager = activity?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    private fun setupVolumeSeekBar() {
        val maxVolume: Int? = audioManager?.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        maxVolume?.let { binding.seekBar.max = it }
        val currVolume: Int? = audioManager?.getStreamVolume(AudioManager.STREAM_MUSIC)
        currVolume?.let {
            binding.seekBar.progress = it
           // onVolumeUpdate(it)
            Log.d("TAG", "currvolume:$it ")

        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    audioManager?.setStreamVolume(
                        AudioManager.STREAM_MUSIC,
                        progress,
                        AudioManager.FLAG_PLAY_SOUND
                    )
                    Log.d("TAG", "myprogress:$progress ")
                //    onVolumeUpdate(progress)

                }
            }


            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }


    private fun setupFartLotiClickListener() {
        binding.fartLoti.setOnLongClickListener(speakHoldListener);
        binding.fartLoti.setOnTouchListener(speakTouchListener);
    }

    private fun setupWaveformSeekBar() {
        rawResourceId?.let { binding.waveformSeekBar.setSampleFrom(it) }
        mPlayer?.duration?.toFloat()?.let { binding.waveformSeekBar.maxProgress = it }
        mHandler.postDelayed(updateSeekBar, 100)

        binding.waveformSeekBar.onProgressChanged = object : SeekBarOnProgressChanged {
            override fun onProgressChanged(
                waveformSeekBar: WaveformSeekBar,
                progress: Float,
                fromUser: Boolean
            ) {
                if (mPlayer != null && fromUser) {
                    mPlayer?.seekTo(progress.toInt())
                } else {
                    Log.d("TAG", "onProgressChanged: i am not drawn ")
                }
            }
        }
    }

    private fun setupBackIconClickListener() {
        binding.backIcon.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private val updateSeekBar = object : Runnable {
        override fun run() {
            mPlayer?.let {
                val currentPosition = it.currentPosition
                val duration = it.duration
                binding.waveformSeekBar?.maxProgress = duration.toFloat()
                binding.waveformSeekBar?.progress = currentPosition.toFloat()
            }
            mHandler.postDelayed(this, 1) // Update SeekBar every 100 milliseconds
        }
    }

    private fun startPlaying() {
        if (mPlayer != null && mPlayer?.isPlaying == true) {
            mPlayer?.pause()
            binding.fartLoti.pauseAnimation()
            binding.fartLoti.loop(false)
        }
        else if (mPlayer != null) {
            mPlayer?.start()
            binding.fartLoti.playAnimation()
            binding.fartLoti.loop(true)
        } else {
            try {

                mPlayer = rawResourceId?.let { MediaPlayer.create(requireContext(), it) }
                mPlayer?.start()
                binding.fartLoti.playAnimation()
                binding.fartLoti.loop(true)            } catch (e: IOException) {
                Log.e("Log", "prepare() failed")
            }
        }
        mPlayer?.setOnCompletionListener {
            binding.fartLoti.pauseAnimation()

        }
    }
    private val speakHoldListener = View.OnLongClickListener {
        // Do something when your hold starts here.
        startPlaying()
        binding.fartLoti.animate()
        binding.clickAnimation.visibility = View.GONE
        isSpeakButtonLongPressed = true
        true
    }

    @SuppressLint("ClickableViewAccessibility")
    private val speakTouchListener = View.OnTouchListener { _, event ->
        // We're only interested in when the button is released.
        if (event.action == MotionEvent.ACTION_UP) {
            // We're only interested in anything if our speak button is currently pressed.
            if (isSpeakButtonLongPressed) {
                // Do something when the button is released.
                mPlayer?.pause()
                binding.fartLoti.pauseAnimation()
                isSpeakButtonLongPressed = false
            }
        }
        false
    }
}
