package com.example.prankappinsectinphone.service

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.DownloadManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.prankappinsectinphone.R
import com.example.prankappinsectinphone.utils.Constant
import java.io.File
import java.net.URI


class OverlayService : Service() {

    private var windowManager: WindowManager? = null

    private var overlayView: View? = null

    private var mediaPlayer: MediaPlayer? = null

    private var musicResource : Int? = null

    private var sharedPreferences : SharedPreferences? = null
    private val PREFS_NAME = "insect_prefs"

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("TAG", "onCreate: OverlayService created")
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null && intent.hasExtra("spider") && intent.hasExtra("soundEffect")) {
            val spiderResourceId = intent.getStringExtra("spider")
             musicResource = intent.getIntExtra("soundEffect", R.raw.snakesound)
            val constantResource = sharedPreferences?.getString("constant_resource", "")
            val constantResourceMusic = sharedPreferences?.getInt("constant_resource_music", 0)

            checkOverlayPermissionAndCreateOverlay(constantResource!!, constantResourceMusic!!)
        } else {
            Log.e("TAG", "Overlay permission not granted")
            Toast.makeText(this, "Overlay permission not granted", Toast.LENGTH_SHORT).show()
            stopSelf()

        }
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


    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkOverlayPermissionAndCreateOverlay(
        spiderResourceId: String,
        musicResource: Int
    ) {
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


    @RequiresApi(Build.VERSION_CODES.O)
    fun createOverlay(resource: String, musicResource: Int) {
        Log.d(TAG, "createOverlay: Creating overlay")

        overlayView?.let {
            windowManager?.removeView(it)
            overlayView = null
            mediaPlayer?.release()
        }

        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        overlayView = inflater.inflate(R.layout.overlay_layout, null)
        mediaPlayer = MediaPlayer.create(this, musicResource)


        val img = overlayView?.findViewById<ImageView>(R.id.img)
        img?.let {

            val filename = Constant.placeName
            val cachedFilePath = File(
                this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),resource
            )
            Log.d("TAG", "respourcelog: $resource")
            
            if (cachedFilePath.exists()) {
                Glide.with(this).load(cachedFilePath).addListener(object :
                    RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable?>,
                        isFirstResource: Boolean
                    ): Boolean {
                        Toast.makeText(this@OverlayService, "Please check your Internet Connection", Toast.LENGTH_SHORT).show()
                        return false
                    }

                    @SuppressLint("SuspiciousIndentation")
                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable?>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                            mediaPlayer?.start()
                            mediaPlayer?.isLooping = true
                        return false
                    }
                }).into(it)
            } else {
                Toast.makeText(this, "please download the $resource first ", Toast.LENGTH_SHORT).show()
            }

        }


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
        const val ACTION_DOWNLOAD_COMPLETE =
            "com.example.prankappinsectinphone.ACTION_DOWNLOAD_COMPLETE"
        const val EXTRA_DOWNLOAD_ID = "extra_download_id"
    }



}
