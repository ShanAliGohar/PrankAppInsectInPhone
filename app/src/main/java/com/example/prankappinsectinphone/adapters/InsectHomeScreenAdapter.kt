package com.example.prankappinsectinphone.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.prankappinsectinphone.R
import com.example.prankappinsectinphone.`interface`.ColorSelectionListener
import com.example.prankappinsectinphone.models.InsectsScreenItems
import com.example.prankappinsectinphone.utils.Constant

class InsectHomeScreenAdapter(
    private val context: Context,
    private var gridItems: List<InsectsScreenItems>,
    private var isServiceRunning: Boolean,
    var colorSelectionListener: ColorSelectionListener

) : RecyclerView.Adapter<InsectHomeScreenAdapter.ViewHolder>() {

    private val PREFS_NAME = "insect_prefs"


    // Function to save checked state to SharedPreferences
    private fun saveCheckedState(position: Int) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("checked_position", position)
        editor.apply()
    }

    // Function to load checked state from SharedPreferences

    private fun loadCheckedState(): Int {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt("checked_position", -1)
    }


    init {
        // Retrieve the saved checked position when the adapter is initialized
        val checkedPosition = loadCheckedState()
        if (checkedPosition != -1) {
            // Set the isChecked property of the corresponding item
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
            resourceSelection(position)
        } else {
            holder.tickIcons.visibility = View.GONE
            // holder.imageView.isClickable = false

        }
        /*
                if (isServiceRunning && !gridItems[position].isChecked) {
                    holder.imageView.isClickable = false
                }*/

        holder.itemView.setOnClickListener {



            if (!Constant.isStart) {
                saveCheckedState(position)
                uncheckedAll(position, holder.itemView)
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

    fun colorSelection(position: Int) {
        when (position) {
            0 -> {
                Constant.startButtonColorResource = R.color.darkPurple
                Constant.startButtonBackgroundColorResource = R.color.lightPurple
            }
            1 -> {
                Constant.startButtonColorResource = R.color.darkOrange
                Constant.startButtonBackgroundColorResource = R.color.lightOrange
            }
            2 -> {
                Constant.startButtonColorResource = R.color.darkPink
                Constant.startButtonBackgroundColorResource = R.color.lightPink
            }
            3 -> {
                Constant.startButtonColorResource = R.color.darkPurple
                Constant.startButtonBackgroundColorResource = R.color.lightPurple
            }
            4 -> {
                Constant.startButtonColorResource = R.color.darksky
                Constant.startButtonBackgroundColorResource = R.color.lightSky
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
            else -> R.color.darkPurple // Handle default color
        }
    }

    private fun getStartButtonBackgroundColorResource(position: Int): Int {
        return when (position) {
            0 -> R.color.lightPurple
            1 -> R.color.lightOrange
            2 -> R.color.lightPink
            3 -> R.color.lightRed
            4 -> R.color.lightPurple
            else -> R.color.lightPurple // Handle default background color
        }
    }

    fun resourceSelection(position: Int) {
        Constant.resource = when (position) {
            0 -> R.raw.snakenew
            1 -> R.raw.butterfly
            2 -> R.raw.spiderbig
            3 -> R.raw.bugsbed
            4 -> R.raw.fly
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
    }

    fun uncheckedAll(position: Int, view: View) {
        for (i in gridItems.indices) {
            gridItems[i].isChecked = (position == i)
        }
        notifyDataSetChanged()
    }



}
