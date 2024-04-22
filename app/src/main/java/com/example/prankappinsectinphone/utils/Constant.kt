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
    var resource: String = ""
    var musicResource: Int = R.raw.snakesound
    var isStart: Boolean = false
    var isInHome: Boolean = false

    val placeName : ArrayList<String> = ArrayList()

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
    fun initializeInsectHomeScreenItem(): List<InsectsScreenItems> {

        return arrayListOf(
            InsectsScreenItems(
                R.drawable.snake_img, false,
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


    var fartPlaylistItems = arrayListOf(

        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image, 1, R.raw.fart1),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image, 2, R.raw.fart2),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image, 3, R.raw.fart3),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image, 4, R.raw.fart4),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image, 5, R.raw.fart5),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image, 6, R.raw.fart6),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image, 7, R.raw.fart7),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image, 8, R.raw.fart8),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image, 9, R.raw.fart9),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image, 10, R.raw.fart10),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image, 11, R.raw.fart11),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image, 12, R.raw.fart12),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image, 13, R.raw.fart13),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image, 14, R.raw.fart18),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image, 15, R.raw.fart19),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image, 16, R.raw.fart20),


    )


    var bikePlaylistItems = arrayListOf(

        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final, 1,R.raw.bike1),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final, 2, R.raw.bike2),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final, 3, R.raw.bike3),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final, 4, R.raw.bike4),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final, 5, R.raw.bike5),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final, 6, R.raw.bike6),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final, 7, R.raw.bike7),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final, 8, R.raw.bike8),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final, 9, R.raw.bike9),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final, 10, R.raw.bike10),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final, 11, R.raw.bike11),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final, 12, R.raw.bike12),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final, 13, R.raw.bike13),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final, 14, R.raw.bike14),
    )

    var carPlaylistItems = arrayListOf(
        PlaylistItem("Car Sounds", R.drawable.car_pl_final, 1, R.raw.car1),
        PlaylistItem("Car Sounds", R.drawable.car_pl_final, 2, R.raw.car2),
        PlaylistItem("Car Sounds", R.drawable.car_pl_final, 3, R.raw.car3),
        PlaylistItem("Car Sounds", R.drawable.car_pl_final, 4, R.raw.car4),
        PlaylistItem("Car Sounds", R.drawable.car_pl_final, 5, R.raw.car5),
        PlaylistItem("Car Sounds", R.drawable.car_pl_final, 6, R.raw.car6),
        PlaylistItem("Car Sounds", R.drawable.car_pl_final, 7, R.raw.car7),
        PlaylistItem("Car Sounds", R.drawable.car_pl_final, 8, R.raw.car8),
        PlaylistItem("Car Sounds", R.drawable.car_pl_final, 9, R.raw.car9),
        PlaylistItem("Car Sounds", R.drawable.car_pl_final, 10, R.raw.car10),
        PlaylistItem("Car Sounds", R.drawable.car_pl_final, 11, R.raw.car11),
        PlaylistItem("Car Sounds", R.drawable.car_pl_final, 12, R.raw.car12)
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