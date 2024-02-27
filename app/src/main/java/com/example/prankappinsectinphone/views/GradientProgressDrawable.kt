package com.example.prankappinsectinphone.views

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable

class GradientProgressDrawable(colors: IntArray, positions: FloatArray?) : LayerDrawable(
    arrayOf(
        GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors), // Correct constructor usage
        GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors) // Optional for finer control
    )
) {
    init {
        setLayerInset(0, 0, 0, 0,3) // Adjust insets if needed
        setLayerWidth(0, 1) // Adjust weights for desired gradient distribution
        setLayerWidth(1, 0.5f.toInt())
    }
}
