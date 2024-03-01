package com.example.prankappinsectinphone.fragments

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.navigation.fragment.findNavController
import com.example.prankappinsectinphone.databinding.FragmentBikeDetailBinding
import com.masoudss.lib.SeekBarOnProgressChanged
import com.masoudss.lib.WaveformSeekBar
import java.io.IOException

class BikeDetailFragment : Fragment() {
    private val binding: FragmentBikeDetailBinding by lazy {
        FragmentBikeDetailBinding.inflate(layoutInflater)
    }

    private var mPlayer: MediaPlayer? = null
    private val mHandler = Handler()
    private var audioManager: AudioManager? = null
    private var rawResourceId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rawResourceId = arguments?.getInt("rawResourceIdBike")

        setupMediaPlayer()
        setupVolumeSeekBar()
        setupFartLotiClickListener()
        setupWaveformSeekBar()
        setupBackIconClickListener()

        return binding.root
    }

    private fun setupMediaPlayer() {
        mPlayer = rawResourceId?.let { MediaPlayer.create(requireContext(), it) }
        audioManager = activity?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    private fun setupVolumeSeekBar() {
        val maxVolume: Int? = audioManager?.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val currVolume: Int? = audioManager?.getStreamVolume(AudioManager.STREAM_MUSIC)

        maxVolume?.let { binding.seekBar.max = it }
        currVolume?.let { binding.seekBar.progress = it }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    audioManager?.setStreamVolume(
                        AudioManager.STREAM_MUSIC,
                        progress,
                        AudioManager.FLAG_PLAY_SOUND
                    )
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun setupFartLotiClickListener() {
        binding.fartLoti.setOnClickListener {
            startPlaying()
        }
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
}
