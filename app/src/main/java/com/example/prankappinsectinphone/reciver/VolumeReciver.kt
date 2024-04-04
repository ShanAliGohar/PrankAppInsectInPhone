package com.example.prankappinsectinphone.reciver

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.AUDIO_SERVICE
import android.content.Intent
import android.media.AudioManager
import android.view.KeyEvent
import android.widget.ImageView
import android.widget.SeekBar
import androidx.core.content.ContextCompat.getSystemService
import com.example.prankappinsectinphone.MainActivity
import com.example.prankappinsectinphone.R
import com.example.prankappinsectinphone.fragments.FartDetailFragment


class VolumeReciver(private val activity: Activity, private val seekBar: SeekBar, private val volumeIcon : ImageView) : BroadcastReceiver() {

    private val audioManager: AudioManager by lazy {
        activity.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.media.VOLUME_CHANGED_ACTION") {
            val currVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
            seekBar.progress = currVolume
            if (currVolume == 0){
                volumeIcon.setImageResource(R.drawable.mutedvolumeicon)
            }else {
                volumeIcon.setImageResource(R.drawable.volume_icon)

            }
        }
    }
}