package com.example.prankappinsectinphone.service

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.prankappinsectinphone.R

class OverlayService : Service() {
    private var windowManager: WindowManager? = null
    private var overlayView: View? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: OverlayService created")
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        if (hasOverlayPermission()) {
            createOverlay()
        } else {
            Log.e(TAG, "Overlay permission not granted")
            Toast.makeText(this, "Overlay permission not granted", Toast.LENGTH_SHORT).show()
            stopSelf() // Stop the service if overlay permission is not granted
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: OverlayService destroyed")
        overlayView?.let {
            windowManager?.removeView(it)
            overlayView = null
        }
    }

    private fun hasOverlayPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)) {
                true
            } else {
                // Request overlay permission
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                false
            }
        } else {
            true
        }
    }

    private fun createOverlay() {
        Log.d(TAG, "createOverlay: Creating overlay")
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        overlayView = inflater.inflate(R.layout.overlay_layout, null)
        //  overlayView = MovingInsectView(this)
        // Set up WindowManager LayoutParams
        val img = overlayView?.findViewById<ImageView>(R.id.img)
        if (img != null) {
            Glide.with(this).load(R.raw.snake).into(img)
        }
        val layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
            },
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        )
        layoutParams.gravity = Gravity.CENTER

        // Add the view to the WindowManager
        windowManager?.addView(overlayView, layoutParams)
    }

    companion object {
        private const val TAG = "OverlayService"
    }
}
