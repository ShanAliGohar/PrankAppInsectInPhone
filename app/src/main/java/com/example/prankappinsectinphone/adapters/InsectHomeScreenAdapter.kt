package com.example.prankappinsectinphone.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.media.Image
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.prankappinsectinphone.R
import com.example.prankappinsectinphone.`interface`.ColorSelectionListener
import com.example.prankappinsectinphone.models.InsectsScreenItems
import com.example.prankappinsectinphone.utils.Constant

class InsectHomeScreenAdapter(
    private val context: Context,
    private var gridItems: List<InsectsScreenItems>,
    isServiceRunning: Boolean,
    var colorSelectionListener: ColorSelectionListener

) : RecyclerView.Adapter<InsectHomeScreenAdapter.ViewHolder>() {

    private val PREFS_NAME = "insect_prefs"


    private fun saveCheckedState(position: Int) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("checked_position", position)
        editor.apply()
    }

    private fun loadCheckedState(): Int {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt("checked_position", -1)
    }

    private fun checkDownloadedItem(holder: ViewHolder, position: Int){
        when (position) {
            0 -> if (Constant.hashCode == "687930742") holder.downloadIcon.visibility = View.GONE
            1 -> if (Constant.hashCode == "2") holder.downloadIcon.visibility = View.GONE
            4 -> if (Constant.hashCode == "3") holder.downloadIcon.visibility = View.GONE
        }
    }
    init {

        val checkedPosition = loadCheckedState()
        if (checkedPosition != -1) {

            gridItems = gridItems.mapIndexed { index, item ->
                if (index == checkedPosition) {
                    item.isChecked = true
                } else {
                    item.isChecked = false
                }
                item
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.insects_screen_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = gridItems[position]
        holder.imageView.setImageResource(item.imageResource)

        if (gridItems[position].isChecked) {
            holder.tickIcons.visibility = View.VISIBLE

        } else {
            holder.tickIcons.visibility = View.GONE

        }
        checkDownloadedItem(holder,position)
        holder.itemView.setOnClickListener {

            if (!Constant.isStart) {
                saveCheckedState(position)
                uncheckedAll(position, holder.itemView)
/*                showDownloadIconOnAll(position,holder.itemView)
                setDownloadIconVisibility(position,holder)*/
                notifyDataSetChanged()
                resourceSelection(position)
                val startButtonColorResource = getStartButtonColorResource(position)
                val startButtonBackgroundColorResource = getStartButtonBackgroundColorResource(position)
                Log.d("Adapter", "Item clicked at position $position, startButtonColorResource: $startButtonColorResource, startButtonBackgroundColorResource: $startButtonBackgroundColorResource")
                colorSelectionListener.onColorSelected(
                    startButtonColorResource,
                    startButtonBackgroundColorResource
                )
            }
        }

    }

    private fun setDownloadIconVisibility(position:Int,holder: ViewHolder){
        if (!Constant.isDownloadStarted){
            if (gridItems[position].isDownloading) {
                holder.downloadIcon.visibility = View.GONE

            } else {
                holder.downloadIcon.visibility  = View.VISIBLE

            }
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

    fun resourceSelection(position: Int) {
        Constant.resource = when (position) {
            0 -> "https://drive.google.com/uc?export=download&id=1teT7oxLtpgqvzePSN8BE7txYJI-vvtdU" //
            1 -> "https://drive.google.com/uc?export=download&id=1-CJ-fq_u5i5s37YRv6InALLNQEmasqBw"
            2 -> "https://drive.google.com/uc?export=download&id=1sA_RBAhOKLw9-BmdFu_VxpAnf5vlbAqb"
            3 -> "https://drive.google.com/uc?export=download&id=1fQq0y7H5mr1Ge5uY1EUgg4XQQoslgN87" //
            4 -> "https://drive.google.com/uc?export=download&id=1ZwIUETSzeZyZFnldjS65E9LLpk7ZwJDQ"
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
        val progress : ProgressBar = itemView.findViewById<ProgressBar>(R.id.adapterProgressView)
        val downloadIcon : ImageView = itemView.findViewById<ImageView>(R.id.download_icons)

    }

    fun uncheckedAll(position: Int, view: View) {
        for (i in gridItems.indices) {
            gridItems[i].isChecked = (position == i)
        }
        notifyDataSetChanged()

    }
    fun showDownloadIconOnAll(position: Int, view: View) {
        for (i in gridItems.indices) {
            gridItems[i].isDownloading = (position == i)
        }
        notifyDataSetChanged()
    }




}
