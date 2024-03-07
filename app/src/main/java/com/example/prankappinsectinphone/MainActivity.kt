package com.example.prankappinsectinphone

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.prankappinsectinphone.fragments.InsectsHomeFragment
import com.example.prankappinsectinphone.service.OverlayService
import com.example.prankappinsectinphone.utils.Constant.isInHome
import org.w3c.dom.Text


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    }

    override fun onBackPressed() {
        if (!isInHome) {
            findNavController(R.id.splashFragment).navigateUp()
        } else {
            super.onBackPressed()
        }
    }



}