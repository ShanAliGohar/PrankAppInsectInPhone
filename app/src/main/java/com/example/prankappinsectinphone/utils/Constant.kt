package com.example.prankappinsectinphone.utils

import com.example.prankappinsectinphone.R
import com.example.prankappinsectinphone.models.HomeScreenItems
import com.example.prankappinsectinphone.models.InsectsScreenItems
import com.example.prankappinsectinphone.models.PlaylistItem

object Constant {
    var HOUSEFLY_SELECTED = false
    var BUTTERFLY_SELECTED = false
    var SPIDER_SELECTED = false
    var ANT_SELECTED = false
    var COCKROCH_SELECTED = false
    var resource :Int= R.raw.snake
    var musicResource :Int= R.raw.snakesound

    var homeScreenItem = arrayListOf(
        HomeScreenItems(
            R.drawable.fart_final_image,
        ), HomeScreenItems(
            R.drawable.insect_final_image,
        ), HomeScreenItems(
            R.drawable.bike_final_image,
        ), HomeScreenItems(
            R.drawable.car_final_image,
        )
    )
        //var insectHomeScreenItem = initializeInsectHomeScreenItem()

    fun initializeInsectHomeScreenItem(): List<InsectsScreenItems> {

          return  arrayListOf(
                InsectsScreenItems(
                    R.drawable.snake_img,true,
                ), InsectsScreenItems(
                    R.drawable.butterfly_image, false,
                ), InsectsScreenItems(
                    R.drawable.spider_image,false,
                ), InsectsScreenItems(
                    R.drawable.bed_bug_img, false,
            )
          )

    }
    var animationCompleted : Boolean = false


    var fartPlaylistItems = arrayListOf(
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image),
        PlaylistItem("Fart Sound", R.drawable.fart_playlist_image),

        // Add more items as needed

    )
    var bikePlaylistItems = arrayListOf(
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final),
        PlaylistItem("Super Bike Sound", R.drawable.bike_pl_final),
        // Add more items as needed

    )
    var carPlaylistItems = arrayListOf(
        PlaylistItem("Car Sounds", R.drawable.car_pl_final),
        PlaylistItem("Car Sounds", R.drawable.car_pl_final),
        PlaylistItem("Car Sounds", R.drawable.car_pl_final),
        PlaylistItem("Car Sounds", R.drawable.car_pl_final),
        PlaylistItem("Car Sounds", R.drawable.car_pl_final),
        PlaylistItem("Car Sounds", R.drawable.car_pl_final),
        PlaylistItem("Car Sounds", R.drawable.car_pl_final),
        PlaylistItem("Car Sounds", R.drawable.car_pl_final),
        PlaylistItem("Car Sounds", R.drawable.car_pl_final),
        PlaylistItem("Car Sounds", R.drawable.car_pl_final),
        PlaylistItem("Car Sounds", R.drawable.car_pl_final),
        PlaylistItem("Car Sounds", R.drawable.car_pl_final)
        // Add more items as needed
    )


}