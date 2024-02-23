package com.example.prankappinsectinphone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.prankappinsectinphone.R
import com.example.prankappinsectinphone.models.HomeScreenItems
class HomeScreenAdapter(private val context: Context, private val gridItems: List<HomeScreenItems>) : RecyclerView.Adapter<HomeScreenAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_screen_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = gridItems[position]
        holder.imageView.setImageResource(item.imageResource)
//        holder.textView.text = item.text

        holder.itemView.setOnClickListener {
            if (position == 0) {
                holder.itemView.findNavController()
                    .navigate(R.id.action_homeFragment_to_fartPlaylistFragment)
            } else if (position == 1) {
                holder.itemView.findNavController()
                    .navigate(R.id.action_homeFragment_to_insectsHomeFragment)
            } else if (position == 2) {
                holder.itemView.findNavController()
                    .navigate(R.id.action_homeFragment_to_bikePlaylistFragment)
            } else if (position == 3) {
                holder.itemView.findNavController()
                    .navigate(R.id.action_homeFragment_to_carPlaylistFragment)
            }

        }

    }

    override fun getItemCount(): Int {
        return gridItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
      //  val textView: TextView = itemView.findViewById(R.id.textView)

    }
}