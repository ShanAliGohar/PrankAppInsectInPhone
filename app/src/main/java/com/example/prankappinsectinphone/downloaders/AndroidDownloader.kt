package com.example.prankappinsectinphone.downloaders

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import android.os.Environment.getDownloadCacheDirectory
import androidx.core.net.toUri
import com.example.prankappinsectinphone.R

class AndroidDownloader(private val context: Context): Downloader {

    val downloadManager = context.getSystemService(DownloadManager::class.java)

    override fun downloadFile(url: String): Long {

        val request = DownloadManager.Request(url.toUri())
            .setMimeType("image/gif")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle("spider.gif")
            .addRequestHeader("Authorization", "Bearer <Token>")
            .setDestinationInExternalFilesDir(context,Environment.DIRECTORY_DOWNLOADS,"spider/gif")
        return downloadManager.enqueue(request)

    }
}