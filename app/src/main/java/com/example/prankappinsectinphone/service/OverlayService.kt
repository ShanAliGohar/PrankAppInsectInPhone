package com.example.prankappinsectinphone.service

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.prankappinsectinphone.R
import com.example.prankappinsectinphone.receiver.DownloadCompleteReceiver
import java.io.File

class OverlayService : Service() {
    private var windowManager: WindowManager? = null
    private var overlayView: View? = null
    private var mediaPlayer: MediaPlayer? = null
    private var musicResource : Int? = null



    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("TAG", "onCreate: OverlayService created")
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null && intent.hasExtra("spider") && intent.hasExtra("soundEffect")) {
            val spiderResourceId = intent.getStringExtra("spider")
             musicResource = intent.getIntExtra("soundEffect", R.raw.snakesound)
            checkOverlayPermissionAndCreateOverlay(spiderResourceId!!, musicResource!!)
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


    fun createOverlay(resource: String, musicResource: Int) {
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

            val cachedFilePath = File(
                this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
                "cached_image_${resource.hashCode()}"
            )
            if (cachedFilePath.exists()) {
                Glide.with(this).load(cachedFilePath).addListener(object :
                    RequestListener<Drawable?> {

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable?>,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable?>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        val imgGif = overlayView?.findViewById<ProgressBar>(R.id.progressBar)
                        imgGif?.visibility = View.GONE
                        return false

                    }
                }).into(it)
            } else {
                // Image not cached, download and then load

                downloadPic("cached_image_${resource.hashCode()}", resource)

            }

        }//https://drive.google.com/uc?export=download&id=1Bz9yoW7Ov5LfZTbOO56tcfUwgkThX0gN
        //downloadPic("downloadgif","https://drive.google.com/uc?export=download&id=1sA_RBAhOKLw9-BmdFu_VxpAnf5vlbAqb")
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
        const val ACTION_DOWNLOAD_COMPLETE =
            "com.example.prankappinsectinphone.ACTION_DOWNLOAD_COMPLETE"
        const val EXTRA_DOWNLOAD_ID = "extra_download_id"
    }


    private fun downloadPic(placeName: String?, cityImage: String?) {
        if (placeName == null || cityImage == null) {
            Log.e(TAG, "Invalid parameters for downloading picture")
            return
        }

        try {
            val cachedFile =
                File(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), placeName)
            if (cachedFile.exists()) {
                Log.d(TAG, "Image already cached, skipping download")
                return
            }

            val dm: DownloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val downloadUri = Uri.parse(cityImage)
            val request: DownloadManager.Request = DownloadManager.Request(downloadUri)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
                .setAllowedOverRoaming(false)
                .setTitle(placeName)
                .setMimeType("image/gif")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, placeName)
            dm.enqueue(request)
            Toast.makeText(this, "Image download started.", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e(TAG, "DownloadPic: ${e.localizedMessage}")
            Toast.makeText(this, "Image download failed.", Toast.LENGTH_SHORT).show()
        }
    }
}
