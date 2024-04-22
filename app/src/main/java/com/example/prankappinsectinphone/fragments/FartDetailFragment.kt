package com.example.prankappinsectinphone.fragments

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.prankappinsectinphone.R
import com.example.prankappinsectinphone.databinding.FragmentFartDetailBinding
import com.example.prankappinsectinphone.reciver.VolumeReceiver
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


    private var isLooping: Boolean = false


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
            val volumeChangeReceiver = VolumeReceiver(activity, binding.seekBar, binding.volumeIcon)
            activity.registerReceiver(volumeChangeReceiver, IntentFilter().apply {
                addAction("android.media.VOLUME_CHANGED_ACTION")
            })
        }


        binding.loop.setOnClickListener {
            if (!isLooping) {
                startPlaying()
                mPlayer?.isLooping = true
                isLooping = true
                binding.loop.setImageResource(R.drawable.highlightedloop)
            } else {
                binding.loop.setImageResource(R.drawable.dim_loop)
                mPlayer?.isLooping = false
                isLooping = false
            }
        }

        return binding.root
    }

    private fun volumeIconClickListners() {
        binding.volumeIcon.setOnClickListener {
            val currentVolume = audioManager?.getStreamVolume(AudioManager.STREAM_MUSIC) ?: 0
            if (currentVolume == 0) {
                audioManager?.setStreamVolume(
                    AudioManager.STREAM_MUSIC,
                    audioManager?.getStreamMaxVolume(AudioManager.STREAM_MUSIC) ?: 0,
                    0
                )
                binding.volumeIcon.setImageResource(R.drawable.volume_icon)
            } else {
                audioManager?.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0)
                binding.volumeIcon.setImageResource(R.drawable.mutedvolumeicon)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isLooping) {
            startPlaying()
            binding.loop.setImageResource(R.drawable.highlightedloop)
            mPlayer?.isLooping = true
            isLooping = true
        }
    }

    override fun onPause() {
        super.onPause()
        mPlayer?.pause()
        binding.fartLotti.pauseAnimation()
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
                }
            }


            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun setupFartLotiClickListener() {
        binding.fartLotti.setOnLongClickListener(speakHoldListener);
        binding.fartLotti.setOnTouchListener(speakTouchListener);
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
                binding.waveformSeekBar.maxProgress = duration.toFloat()
                binding.waveformSeekBar.progress = currentPosition.toFloat()
            }
            mHandler.postDelayed(this, 1) // Update SeekBar every 100 milliseconds
        }
    }

    private fun startPlaying() {
        if (mPlayer != null && mPlayer?.isPlaying == true) {
            mPlayer?.pause()
            binding.fartLotti.pauseAnimation()
            binding.fartLotti.loop(false)
        } else if (mPlayer != null) {
            mPlayer?.start()
            binding.fartLotti.playAnimation()
            binding.fartLotti.loop(true)
        } else {
            try {
                mPlayer = rawResourceId?.let { MediaPlayer.create(requireContext(), it) }
                mPlayer?.start()
                binding.fartLotti.playAnimation()
                binding.fartLotti.loop(true)
            } catch (e: IOException) {
                Log.e("Log", "prepare() failed")
            }
        }

        mPlayer?.setOnCompletionListener {
            binding.fartLotti.pauseAnimation()
            if (!isLooping) {
                mPlayer?.seekTo(0)
                binding.fartLotti.progress = 0.0f

            }
        }
    }

    private val speakHoldListener = View.OnLongClickListener {
        startPlaying()
        binding.fartLotti.animate()
        isSpeakButtonLongPressed = true
        true
    }

    @SuppressLint("ClickableViewAccessibility")
    private val speakTouchListener = View.OnTouchListener { _, event ->
        if (event.action == MotionEvent.ACTION_UP) {
            if (isSpeakButtonLongPressed) {
                mPlayer?.pause()
                binding.fartLotti.pauseAnimation()
                isSpeakButtonLongPressed = false
            }
        }
        false
    }

}
