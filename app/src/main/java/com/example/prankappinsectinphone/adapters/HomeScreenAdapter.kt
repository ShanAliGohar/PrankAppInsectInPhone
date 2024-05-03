package com.example.prankappinsectinphone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.prankappinsectinphone.R
import com.example.prankappinsectinphone.models.HomeScreenItems

class HomeScreenAdapter(private val context: Context, private val gridItems: List<HomeScreenItems>) : RecyclerView.Adapter<HomeScreenAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.home_screen_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = gridItems[position]
        holder.imageView.setImageResource(item.imageResource)
        holder.itemView.setOnClickListener {
            when (position) {
                0 -> {
                    if ( holder.itemView.findNavController().currentDestination?.id == R.id.homeFragment){
                        holder.itemView.findNavController()
                            .navigate(R.id.action_homeFragment_to_fartPlaylistFragment)
                    }

                }
                1 -> {
                    if ( holder.itemView.findNavController().currentDestination?.id == R.id.homeFragment) {
                        holder.itemView.findNavController()
                            .navigate(R.id.action_homeFragment_to_insectsHomeFragment)
                    }
                }
                2 -> {
                    if ( holder.itemView.findNavController().currentDestination?.id == R.id.homeFragment) {
                        holder.itemView.findNavController()
                            .navigate(R.id.action_homeFragment_to_bikePlaylistFragment)
                    }
                }
                3 -> {
                   if ( holder.itemView.findNavController().currentDestination?.id == R.id.homeFragment) {
                        holder.itemView.findNavController()
                            .navigate(R.id.action_homeFragment_to_carPlaylistFragment)
                    }
                }
            }
        }
    }
    override fun getItemCount(): Int {
        return gridItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}