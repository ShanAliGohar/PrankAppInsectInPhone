package com.example.prankappinsectinphone.utils

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.airbnb.lottie.Lottie
import com.airbnb.lottie.LottieAnimationView
import com.example.prankappinsectinphone.R
import com.example.prankappinsectinphone.models.HomeScreenItems
import com.example.prankappinsectinphone.models.InsectsScreenItems
import com.example.prankappinsectinphone.models.PlaylistItem
import java.io.IOException

object Constant {
    var HOUSEFLY_SELECTED = false
    var BUTTERFLY_SELECTED = false
    var SPIDER_SELECTED = false
    var ANT_SELECTED = false
    var COCKROCH_SELECTED = false
    var resource: Int = R.raw.snakenew
    var musicResource: Int = R.raw.snakesound
    var isStart: Boolean = false
    var isInHome: Boolean = false


    var startButtonColorResource : Int = R.color.darkPurple
    var startButtonBackgroundColorResource : Int = R.color.lightPurple

    var homeScreenItem = arrayListOf(
        HomeScreenItems(
            R.drawable.fart_final_image,
        ), HomeScreenItems(
            R.drawable.insect_final_image,
        ), HomeScreenItems(
            R.drawable.bike_final_image,
        ), HomeScreenItems(
            R.drawable.carfinalimage,
        )
    )

    var bikeClickLotiAnimationHasBeenSeen : Boolean = false

    //var insectHomeScreenItem = initializeInsectHomeScreenItem()
    fun initializeInsectHomeScreenItem(): List<InsectsScreenItems> {

        return arrayListOf(
            InsectsScreenItems(
                R.drawable.snake_img, true,
            ), InsectsScreenItems(
                R.drawable.butterfly_image, false,
            ), InsectsScreenItems(
                R.drawable.spider_image, false,
            ), InsectsScreenItems(
                R.drawable.bed_bug_img, false,
            ), InsectsScreenItems(
                R.drawable.housefly_image, false,
            )
        )

    }

    var animationCompleted: Boolean = false


    var fartPlaylistItems = arrayListOf(
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image, 1),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image, 2),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image, 3),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image, 4),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image, 5),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image, 6),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image, 7),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image, 8),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image, 9),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image, 10),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image, 11),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image, 12),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image, 13),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image, 14),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image, 15),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image, 16),

        // Add more items as needed

    )
    var bikePlaylistItems = arrayListOf(
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final, 1),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final, 2),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final, 3),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final, 4),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final, 5),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final, 6),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final, 7),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final, 8),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final, 9),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final, 10),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final, 11),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final, 12),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final, 13),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final, 14),
        // Add more items as needed

    )
    var carPlaylistItems = arrayListOf(
        PlaylistItem("Car Sounds", R.drawable.car_pl_final, 1),
        PlaylistItem("Car Sounds", R.drawable.car_pl_final, 2),
        PlaylistItem("Car Sounds", R.drawable.car_pl_final, 3),
        PlaylistItem("Car Sounds", R.drawable.car_pl_final, 4),
        PlaylistItem("Car Sounds", R.drawable.car_pl_final, 5),
        PlaylistItem("Car Sounds", R.drawable.car_pl_final, 6),
        PlaylistItem("Car Sounds", R.drawable.car_pl_final, 7),
        PlaylistItem("Car Sounds", R.drawable.car_pl_final, 8),
        PlaylistItem("Car Sounds", R.drawable.car_pl_final, 9),
        PlaylistItem("Car Sounds", R.drawable.car_pl_final, 10),
        PlaylistItem("Car Sounds", R.drawable.car_pl_final, 11),
        PlaylistItem("Car Sounds", R.drawable.car_pl_final, 12),
        // Add more items as needed
    )

    private const val PREFS_NAME = "PrankAppPrefs"

    // Function to save isStart to SharedPreferences
    fun saveIsStart(context: Context, isStart: Boolean) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isStart", isStart)
        editor.apply()
    }

    // Function to retrieve isStart from SharedPreferences
    fun getIsStart(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isStart", false) // Default value is false if key not found
    }

}