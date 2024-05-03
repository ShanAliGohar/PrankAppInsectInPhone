package com.example.prankappinsectinphone.adapters

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.prankappinsectinphone.*
import com.example.prankappinsectinphone.`interface`.OnColorSelectionListner
import com.example.prankappinsectinphone.models.InsectsScreenItems
import com.example.prankappinsectinphone.utils.Constant
import com.example.prankappinsectinphone.utils.Constant.placeName
import java.io.File


class InsectHomeScreenAdapter(
    private val context: Context,
    private var gridItems: List<InsectsScreenItems>,
    private var isServiceRunning: Boolean,
    private var colorSelectionListener: OnColorSelectionListner

) : RecyclerView.Adapter<InsectHomeScreenAdapter.ViewHolder>() {
    private val preferencesName = "insect_prefs"
    private var sharedPref: SharedPreferences? = null
    var dm: DownloadManager? = null
    var downloadId: Long? = null
    var dialog: Dialog? = null
    private var percent: TextView? = null
    private var progressBar: ProgressBar? = null
    private var message: String? = "Internet connection lost. Please enable internet to download."


    private fun saveCheckedState(position: Int) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("checked_position", position)
        editor.apply()
    }

    private fun loadCheckedState(): Int {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
        return sharedPreferences.getInt("checked_position", -1)
    }


    init {
        val checkedPosition = loadCheckedState()
        if (checkedPosition != -1) {
            gridItems = gridItems.mapIndexed { index, item ->
                item.isChecked = index == checkedPosition
                item
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.insects_screen_item, parent, false)
        sharedPref = context.getSharedPreferences("START_SERVICE", Context.MODE_PRIVATE)
        return ViewHolder(view)
    }



    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = gridItems[position]
        holder.imageView.setImageResource(item.imageResource)

        if (gridItems[position].isChecked) {
            holder.tickIcons.visibility = View.VISIBLE
        } else {
            holder.tickIcons.visibility = View.GONE
        }
        placeName.add("Snake")
        placeName.add("Butterfly")
        placeName.add("Spider")
        placeName.add("Bedbug")
        placeName.add("Housefly")
        val fileName = placeName[position]
        val cachedFile =
            File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName)
        val downloadIconVisibility = loadDownloadIconVisibility(position)
        holder.downloadIcon.visibility = downloadIconVisibility
        holder.itemView.setOnClickListener {
            if (!Constant.isStart) {
                saveCheckedState(position)
                uncheckedAll(position)
                notifyDataSetChanged()
                resourceSelection(position)
                val startButtonColorResource = getStartButtonColorResource(position)
                val startButtonBackgroundColorResource =
                    getStartButtonBackgroundColorResource(position)
                Log.d(
                    "Adapter",
                    "Item clicked at position $position, startButtonColorResource: $startButtonColorResource, startButtonBackgroundColorResource: $startButtonBackgroundColorResource"
                )
                colorSelectionListener.onColorSelected(
                    startButtonColorResource,
                    startButtonBackgroundColorResource
                )
            }
            if (!cachedFile.exists()) {
                val imageUrl = when (position) {

                    0 -> "https://drive.google.com/uc?export=download&id=1teT7oxLtpgqvzePSN8BE7txYJI-vvtdU"
                    1 -> "https://drive.google.com/uc?export=download&id=1-CJ-fq_u5i5s37YRv6InALLNQEmasqBw"
                    2 -> "https://drive.google.com/uc?export=download&id=1OXw62Y3yFQCtlG-mOPpEuxSVIEH9ORlF"
                    3 -> "https://drive.google.com/uc?export=download&id=1fQq0y7H5mr1Ge5uY1EUgg4XQQoslgN87"
                    4 -> "https://drive.google.com/uc?export=download&id=1ZwIUETSzeZyZFnldjS65E9LLpk7ZwJDQ"
                    else -> ""
                }
                if (!isInternetAvailable(context)) {
                    message?.let { it1 ->
                        showToast(
                            context,
                            it1
                        )
                    }
                    promptToEnableInternet(context)
                } else if (imageUrl.isNotEmpty()) {
                    downloadPic(fileName, imageUrl, context, holder, position)
                    showDialog(context)
                    saveDownloadIconVisibility(position, View.VISIBLE)
                }
            } else {
                saveDownloadIconVisibility(position, View.GONE)
            }
            saveResourceToSharedPreferences(Constant.resource)
            saveMusicResourceToSharedPreferences(Constant.musicResource)
        }
    }
    private fun getStartButtonColorResource(position: Int): Int {
        return when (position) {
            0 -> R.color.darkPurple
            1 -> R.color.darkOrange
            2 -> R.color.darkPink
            3 -> R.color.darkRed
            4 -> R.color.darkPurple
            else -> R.color.darkPurple
        }
    }

    private fun getStartButtonBackgroundColorResource(position: Int): Int {
        return when (position) {
            0 -> R.color.lightPurple
            1 -> R.color.lightOrange
            2 -> R.color.lightPink
            3 -> R.color.lightRed
            4 -> R.color.lightPurple
            else -> R.color.lightPurple
        }
    }

    private fun resourceSelection(position: Int) {
        Constant.resource = when (position) {
            0 -> "Snake"
            1 -> "Butterfly"
            2 -> "Spider"
            3 -> "Bedbug"
            4 -> "Housefly"
            else -> Constant.resource
        }

        Constant.musicResource = when (position) {
            0 -> R.raw.snakesound
            1 -> R.raw.butterflysecondsound
            2 -> R.raw.spidersound
            3 -> R.raw.cockroachsound
            4 -> R.raw.flybgsound
            else -> Constant.musicResource
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun getItemCount(): Int {
        return gridItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val tickIcons: ImageView = itemView.findViewById(R.id.tick_icons)
        val downloadIcon: ImageView = itemView.findViewById(R.id.download_icons)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun uncheckedAll(position: Int) {
        for (i in gridItems.indices) {
            gridItems[i].isChecked = (position == i)
        }
        notifyDataSetChanged()
    }

    @SuppressLint("Range")
    private fun showDialog(context: Context) {
        dialog = Dialog(context)
        dialog?.setContentView(R.layout.download_dialog)
        percent = dialog?.findViewById(R.id.percent)
        progressBar = dialog?.findViewById(R.id.progressBar)
        dialog?.setCancelable(false)
        dialog?.show()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun downloadPic(
        placeName: String?,
        cityImage: String?,
        context: Context,
        holder: ViewHolder,
        position: Int
    ) {


        if (placeName == null || cityImage == null) {
            return
        }
        try {
            val cachedFile =
                File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), placeName)
            if (cachedFile.exists()) {
                holder.downloadIcon.visibility =
                    View.GONE
                return
            }
            dm =
                context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val downloadUri = Uri.parse(cityImage)
            val request: DownloadManager.Request = DownloadManager.Request(downloadUri)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(placeName)
                .setMimeType("image/gif")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalFilesDir(
                    context,
                    Environment.DIRECTORY_DOWNLOADS,
                    placeName
                )
            downloadId = dm?.enqueue(request)
            val receiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    val action = intent?.action
                    if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == action) {
                        val receivedDownloadId =
                            intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                        if (downloadId == receivedDownloadId) {
                            Handler(Looper.getMainLooper()).post {
                                dialog?.dismiss()
                                holder.downloadIcon.visibility = View.GONE
                            }

                        }
                    }
                }
            }

            context.registerReceiver(
                receiver,
                IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE),
                Context.RECEIVER_NOT_EXPORTED
            )

            checkDownloadStatus(holder, position)

        } catch (e: Exception) {
            Toast.makeText(context, "Image download failed.", Toast.LENGTH_SHORT).show()
        }
    }
/////////////////////////////////
    @SuppressLint("Range", "SetTextI18n")
    private fun checkDownloadStatus(holder: ViewHolder, position: Int) {
        val query = downloadId?.let { DownloadManager.Query().setFilterById(it) }
        Thread {
            var downloading = true
            while (downloading) {
                if (dm != null) {
                    val cursor = dm?.query(query)
                    if (cursor?.moveToFirst() == true) {
                        when (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
                            DownloadManager.STATUS_SUCCESSFUL -> {
                                downloading = false
                                Handler(Looper.getMainLooper()).post {
                                    holder.downloadIcon.visibility = View.GONE
                                    saveDownloadIconVisibility(position, View.GONE)
                                    dialog?.dismiss()
                                }
                            }
                            DownloadManager.STATUS_FAILED -> {
                                downloading = false
                                Handler(Looper.getMainLooper()).post {
                                    dialog?.let {
                                        if (it.isShowing) {
                                            it.dismiss()
                                        }
                                    }
                                }
                            }
                            else -> {
                                val bytesDownloaded =
                                    cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                                val bytesTotal =
                                    cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                                val progress =
                                    (bytesDownloaded.toFloat() / bytesTotal.toFloat() * 100).toInt()
                                Handler(Looper.getMainLooper()).post {
                                    percent?.text = "$progress%"
                                    progressBar?.progress = progress
                                }
                            }
                        }
                    }
                    cursor?.close()
                } else {
                }
            }
        }.start()
    }

    private fun saveDownloadIconVisibility(position: Int, visibility: Int) {
        val sharedPreferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("download_icon_$position", visibility)
        editor.apply()
    }

    private fun loadDownloadIconVisibility(position: Int): Int {
        val sharedPreferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(
            "download_icon_$position",
            View.VISIBLE
        )
    }
    @SuppressLint("MissingPermission")
    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    private fun promptToEnableInternet(context: Context) {
        // Prompt the user to enable internet connectivity
        val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
        context.startActivity(intent)
    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun saveResourceToSharedPreferences(resource: String) {
        val sharedPreferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("constant_resource", resource)
        editor.apply()
    }

    private fun saveMusicResourceToSharedPreferences(resource: Int) {
        val sharedPreferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("constant_resource_music", resource)
        editor.apply()
    }

}

