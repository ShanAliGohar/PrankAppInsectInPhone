package com.example.prankappinsectinphone

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.example.prankappinsectinphone.databinding.ActivityMainBinding
import com.example.prankappinsectinphone.utils.Constant.isInHome


class MainActivity : AppCompatActivity() {

    var binding: ActivityMainBinding? = null
    var ic: ImageView? = null

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (!isInHome)
        {

            findNavController(R.id.splashFragment).navigateUp()

        }

        else if (isInHome)

        {
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
            dialog.setContentView(R.layout.exit_dialouge)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            val yesBtn = dialog.findViewById<ImageView>(R.id.yesBtn)
            val noBtn = dialog.findViewById<ImageView>(R.id.no_btn)
            val yesText = dialog.findViewById<TextView>(R.id.yes_txt)
            val noTxt = dialog.findViewById<TextView>(R.id.no_txt)

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
        }

        else

        {

            super.onBackPressed()

        }
    }

}