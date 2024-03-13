package com.example.prankappinsectinphone

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.prankappinsectinphone.databinding.ActivityMainBinding
import com.example.prankappinsectinphone.fragments.InsectsHomeFragment
import com.example.prankappinsectinphone.service.OverlayService
import com.example.prankappinsectinphone.utils.Constant.isInHome
import org.w3c.dom.Text


class MainActivity : AppCompatActivity() {

    var binding: ActivityMainBinding? = null
    var ic: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


         ic = findViewById(R.id.volume_icon)

    }
    fun onVolumeUpdate(volumeValue  : Int){
        if ( volumeValue == 0 ){
           ic?.setImageResource(R.drawable.muteicon)
        } else{
           ic?.setImageResource(R.drawable.volume_icon)

        }
    }
    override fun onBackPressed() {
        if (!isInHome) {
            findNavController(R.id.splashFragment).navigateUp()
        } else {
            super.onBackPressed()
        }
    }



}