package com.example.prankappinsectinphone.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.prankappinsectinphone.R
import com.example.prankappinsectinphone.models.HomeScreenItems
import com.example.prankappinsectinphone.models.InsectsScreenItems
import com.example.prankappinsectinphone.utils.Constant

class InsectHomeScreenAdapter(
    private val context: Context,
    private var gridItems: List<InsectsScreenItems>
) : RecyclerView.Adapter<InsectHomeScreenAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.insects_screen_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = gridItems[position]
        holder.imageView.setImageResource(item.imageResource)
        //  holder.textView.text = item.text

        if (gridItems[position].isChecked) {
            holder.tickIcons.visibility = View.VISIBLE
        } else {

            holder.tickIcons.visibility = View.GONE

        }

        holder.itemView.setOnClickListener {
            uncheckedAll(position)

            // start service here
        }
        // notifyDataSetChanged()

    }

    @SuppressLint("NotifyDataSetChanged")


    override fun getItemCount(): Int {
        return gridItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val tickIcons: ImageView = itemView.findViewById(R.id.tick_icons)

    }

    fun uncheckedAll(position: Int) {

        for (i in gridItems.indices) {
            if (position == i) {
                gridItems[i].isChecked = true
            } else {
                gridItems[i].isChecked = false
            }
            notifyDataSetChanged()
        }
    }
}