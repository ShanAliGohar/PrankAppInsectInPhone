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
import com.example.prankappinsectinphone.R
import com.example.prankappinsectinphone.databinding.FragmentBikeDetailBinding
import com.example.prankappinsectinphone.databinding.FragmentCarPlaylistBinding
import com.masoudss.lib.SeekBarOnProgressChanged
import com.masoudss.lib.WaveformSeekBar
import java.io.IOException


class BikeDetailFragment : Fragment() {
    private val binding: FragmentBikeDetailBinding by lazy {
        FragmentBikeDetailBinding.inflate(layoutInflater)
    }

    var mPlayer: MediaPlayer? = null
    val mHandler = Handler()
    var audioManger: AudioManager? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mPlayer = MediaPlayer.create(requireContext(), R.raw.fartsound1)
        audioManger = activity?.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        val maxVolume: Int? = audioManger?.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        if (maxVolume != null) {
            binding.seekBar.max = maxVolume
        }
        val currVolume: Int? = audioManger?.getStreamVolume(AudioManager.STREAM_MUSIC)
        if (currVolume != null) {
            binding.seekBar.progress = currVolume
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    audioManger?.setStreamVolume(
                        AudioManager.STREAM_MUSIC,
                        progress,
                        AudioManager.FLAG_PLAY_SOUND
                    )
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })


        binding.fartLoti.setOnClickListener {
            startPlaying()

        }

        binding.waveformSeekBar.setSampleFrom(R.raw.fartsound1)
        binding.waveformSeekBar.maxProgress = mPlayer?.duration?.toFloat()!!
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
        binding.backIcon.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
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
        if (mPlayer != null && mPlayer?.isPlaying() == true) {
            mPlayer?.pause()
            binding.fartLoti.pauseAnimation() // Pause animation when MediaPlayer is paused
        } else if (mPlayer != null) {
            mPlayer?.start()
            binding.fartLoti.playAnimation() // Start animation when MediaPlayer starts playing

        } else {
            //   mPlayer = MediaPlayer()


            try {

                mPlayer = MediaPlayer.create(requireContext(), R.raw.fartsound1)
                mPlayer?.start()
                binding.fartLoti.playAnimation()


            } catch (e: IOException) {
                Log.e("Log", "prepare() failed")
            }
        }
    }

}