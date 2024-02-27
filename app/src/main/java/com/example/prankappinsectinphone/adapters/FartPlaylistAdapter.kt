package com.example.prankappinsectinphone.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.prankappinsectinphone.R
import com.example.prankappinsectinphone.models.PlaylistItem

class FartPlaylistAdapter(private val context: Context, private val fartPlaylist: List<PlaylistItem>) :
    RecyclerView.Adapter<FartPlaylistAdapter.FartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FartViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.playlist_items, parent, false)
        return FartViewHolder(view)
    }

    override fun onBindViewHolder(holder: FartViewHolder, position: Int) {
        val fartItem = fartPlaylist[position]
        holder.fartText.text = fartItem.fartName
        holder.fartImage.setImageResource(fartItem.imageResource)
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            when (position) {
                0 -> bundle.putInt("rawResourceId", R.raw.fartsound1)
                1 -> bundle.putInt("rawResourceId", R.raw.fartsound2)
                2 -> bundle.putInt("rawResourceId", R.raw.fartsound3)
                3 -> bundle.putInt("rawResourceId", R.raw.fartsound4)
            }
            holder.itemView.findNavController().navigate(
                R.id.action_fartPlaylistFragment_to_fartDetailFragment,
                bundle
            )
        }

    }

    override fun getItemCount(): Int {
        return fartPlaylist.size
    }

    inner class FartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fartText: TextView = itemView.findViewById(R.id.fartText)
        val fartImage: ImageView = itemView.findViewById(R.id.image_fart)
    }
}
