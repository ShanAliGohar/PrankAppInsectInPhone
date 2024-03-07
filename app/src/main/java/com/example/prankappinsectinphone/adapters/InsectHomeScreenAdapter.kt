package com.example.prankappinsectinphone.adapters
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.prankappinsectinphone.R
import com.example.prankappinsectinphone.models.InsectsScreenItems
import com.example.prankappinsectinphone.utils.Constant

class InsectHomeScreenAdapter(
    private val context: Context,
    private var gridItems: List<InsectsScreenItems>
) : RecyclerView.Adapter<InsectHomeScreenAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.insects_screen_item, parent, false)
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

        holder.itemView.setOnClickListener {
            uncheckedAll(position,holder.itemView)
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
                else -> Constant.musicResource
            }
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

    fun uncheckedAll(position: Int,view:View) {
        for (i in gridItems.indices) {
            gridItems[i].isChecked = (position == i)

        }
        notifyDataSetChanged()
    }


}
