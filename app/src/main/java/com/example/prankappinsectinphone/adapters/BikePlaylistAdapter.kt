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


class BikePlaylistAdapter(private val context: Context, private val fartPlaylist: List<PlaylistItem>) :
    RecyclerView.Adapter<BikePlaylistAdapter.FartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FartViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.playlist_items, parent, false)
        return FartViewHolder(view)
    }

    override fun onBindViewHolder(holder: FartViewHolder, position: Int) {
        val fartItem = fartPlaylist[position]
        holder.fartText.text = fartItem.fartName
        holder.counting.text = fartItem.counting.toString()

        holder.fartImage.setImageResource(fartItem.imageResource)
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            when (position) {
                0 -> bundle.putInt("rawResourceIdBike", R.raw.bike1)
                1 -> bundle.putInt("rawResourceIdBike", R.raw.bike2)
                2 -> bundle.putInt("rawResourceIdBike", R.raw.bike3)
                3 -> bundle.putInt("rawResourceIdBike", R.raw.bike4)
                4 -> bundle.putInt("rawResourceIdBike", R.raw.bike5)
                5 -> bundle.putInt("rawResourceIdBike", R.raw.bike6)
                6 -> bundle.putInt("rawResourceIdBike", R.raw.bike7)
                7 -> bundle.putInt("rawResourceIdBike", R.raw.bike8)
                8 -> bundle.putInt("rawResourceIdBike", R.raw.bike9)
                9 -> bundle.putInt("rawResourceIdBike", R.raw.bike10)
                10 -> bundle.putInt("rawResourceIdBike", R.raw.bike11)
                11 -> bundle.putInt("rawResourceIdBike", R.raw.bike12)
                12 -> bundle.putInt("rawResourceIdBike", R.raw.bike13)
                13 -> bundle.putInt("rawResourceIdBike", R.raw.bike14)
            }
            holder.itemView.findNavController().navigate(
                R.id.action_bikePlaylistFragment_to_bikeDetailFragment,
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
        val counting: TextView = itemView.findViewById(R.id.counting)

    }
}
