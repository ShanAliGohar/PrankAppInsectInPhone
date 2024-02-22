package com.example.prankappinsectinphone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.prankappinsectinphone.R
import com.example.prankappinsectinphone.models.HomeScreenItems

class InsectHomeScreenAdapter(private val context: Context, private val gridItems: List<HomeScreenItems>) : RecyclerView.Adapter<InsectHomeScreenAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_screen_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = gridItems[position]
        holder.imageView.setImageResource(item.imageResource)
        holder.textView.text = item.text

        holder.itemView.setOnClickListener {
       // start service here
        }

    }

    override fun getItemCount(): Int {
        return gridItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textView: TextView = itemView.findViewById(R.id.textView)

    }
}