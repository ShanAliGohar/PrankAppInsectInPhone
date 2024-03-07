package com.example.prankappinsectinphone.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.prankappinsectinphone.R

class OverlayService : Service() {
    private var windowManager: WindowManager? = null
    private var overlayView: View? = null
    private var mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("TAG", "onCreate: OverlayService created")
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null && intent.hasExtra("spider") && intent.hasExtra("soundEffect")) {
            val spiderResourceId = intent.getIntExtra("spider", R.raw.snake)
            val musicResource = intent.getIntExtra("soundEffect", R.raw.snakesound)
            checkOverlayPermissionAndCreateOverlay(spiderResourceId, musicResource)

            } else {
                Log.e("TAG", "Overlay permission not granted")
                Toast.makeText(this, "Overlay permission not granted", Toast.LENGTH_SHORT).show()
                stopSelf() // Stop the service if overlay permission is not granted
            }


        // Continue with your service logic here...

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: OverlayService destroyed")
        overlayView?.let {
            windowManager?.removeView(it)
            overlayView = null
            mediaPlayer?.release()
        }
    }

    private fun checkOverlayPermissionAndCreateOverlay(spiderResourceId: Int, musicResource: Int) {
        if (hasOverlayPermission()) {
            createOverlay(spiderResourceId, musicResource)
        } else {
            Log.e(TAG, "Overlay permission not granted")
            Toast.makeText(this, "Overlay permission not granted", Toast.LENGTH_SHORT).show()
            requestOverlayPermission()
        }
    }

    private fun requestOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Request overlay permission
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    private fun hasOverlayPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Settings.canDrawOverlays(this)
        } else {
            true
        }
    }


    private fun createOverlay(resource: Int, musicResource: Int) {
        Log.d(TAG, "createOverlay: Creating overlay")

        overlayView?.let {
            windowManager?.removeView(it)
            overlayView = null
            mediaPlayer?.release()
        }

        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        overlayView = inflater.inflate(R.layout.overlay_layout, null)

        val img = overlayView?.findViewById<ImageView>(R.id.img)
        img?.let {
            Glide.with(this).load(resource).into(it)
        }

        mediaPlayer = MediaPlayer.create(this, musicResource)
        mediaPlayer?.start()
        mediaPlayer?.isLooping = true

        val layoutParams = WindowManager.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
            },
            WindowManager.LayoutParams.FLAG_FULLSCREEN or
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        )

        windowManager?.addView(overlayView, layoutParams)
    }

    companion object {
        private const val TAG = "OverlayService"
    }
}
