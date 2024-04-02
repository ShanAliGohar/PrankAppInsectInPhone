package com.example.prankappinsectinphone

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.graphics.Color
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.example.prankappinsectinphone.adapters.HomeScreenAdapter
import com.example.prankappinsectinphone.databinding.ActivityMainBinding
import com.example.prankappinsectinphone.downloaders.AndroidDownloader
import com.example.prankappinsectinphone.fragments.InsectsHomeFragment
import com.example.prankappinsectinphone.service.OverlayService
import com.example.prankappinsectinphone.utils.Constant
import com.example.prankappinsectinphone.utils.Constant.isInHome
import org.w3c.dom.Text


class MainActivity : AppCompatActivity() {

    var binding: ActivityMainBinding? = null
    var ic: ImageView? = null
    var sharedPrefrence : SharedPreferences? = null
    val PREFS_NAME = "insect_prefs"
   // val downloader = AndroidDownloader(this)


    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
      //  downloader.downloadFile("https://drive.google.com/u/3/uc?id=1sA_RBAhOKLw9-BmdFu_VxpAnf5vlbAqb&export=download")

         ic = findViewById(R.id.volume_icon)

    }

    fun onVolumeUpdate(volumeValue  : Int){
        if ( volumeValue == 0 ){
           ic?.setImageResource(R.drawable.mutedvolumeicon)
        } else{
           ic?.setImageResource(R.drawable.volume_icon)

        }
    }
    override fun onBackPressed() {
        if (!isInHome) {
            findNavController(R.id.splashFragment).navigateUp()
        } else if (isInHome) {
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
            dialog.setContentView(R.layout.exit_dialouge)
            dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
            var yesBtn = dialog.findViewById<ImageView>(R.id.yesBtn)
            var noBtn = dialog.findViewById<ImageView>(R.id.no_btn)
            var yesText = dialog.findViewById<TextView>(R.id.yes_txt)
            var noTxt = dialog.findViewById<TextView>(R.id.no_txt)

            yesBtn.setOnClickListener {
                super.onBackPressed()
                yesText.setTextColor(ContextCompat.getColor(this, R.color.white))
                noTxt.setTextColor(ContextCompat.getColor(this, R.color.black))

                yesBtn.setBackgroundColor(ContextCompat.getColor(this, R.color.darkOrange))
                noBtn.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                dialog.dismiss()
            }

            noBtn.setOnClickListener {
                noTxt.setTextColor(ContextCompat.getColor(this, R.color.white))
                yesText.setTextColor(ContextCompat.getColor(this, R.color.black))
                noBtn.setBackgroundColor(ContextCompat.getColor(this, R.color.darkOrange))
                yesBtn.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                dialog.dismiss()
            }

            dialog.show()
            Log.d("TAG", "onBackPressed: you can exit me now ")
        }else{
            super.onBackPressed()

        }
    }

    override fun onDestroy() {
        super.onDestroy()



    }


}