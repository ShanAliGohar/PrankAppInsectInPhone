package com.example.prankappinsectinphone.fragments

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.prankappinsectinphone.R
import com.example.prankappinsectinphone.adapters.HomeScreenAdapter
import com.example.prankappinsectinphone.databinding.FragmentHomeBinding
import com.example.prankappinsectinphone.utils.Constant
import com.example.prankappinsectinphone.utils.Constant.isInHome

class HomeFragment : Fragment() {

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }
    private var isSubmitClicked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupRecyclerView()
        setupDrawerIconClickListener()


        binding.myDrawer.privacyPolicyText.setOnClickListener {
            binding.drawerLayout.close()
            val myIntent: Intent = Intent.parseUri("https://sites.google.com/view/prank-insect-privacy-policy/home", Intent.URI_INTENT_SCHEME)
            startActivity(myIntent)
        }
        binding.myDrawer.rateUSText.setOnClickListener {
            binding.drawerLayout.close()
            isSubmitClicked = false
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.setContentView(R.layout.rate_us_item)
            val starONe = dialog.findViewById<ImageView>(R.id.star_one)
            val starTwo = dialog.findViewById<ImageView>(R.id.start_two)
            val starThree = dialog.findViewById<ImageView>(R.id.start_three)
            val starFour = dialog.findViewById<ImageView>(R.id.start_four)
            val starFive = dialog.findViewById<ImageView>(R.id.start_five)
            val submit = dialog.findViewById<ImageView>(R.id.submit_btn)
            starONe.setOnClickListener {
                isSubmitClicked = false
                starONe.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.rate_star_fill
                    )
                )
                starTwo.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.rate_star_empty
                    )
                )
                starThree.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.rate_star_empty
                    )
                )
                starFour.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.rate_star_empty
                    )
                )
                starFive.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.rate_star_empty
                    )
                )

            }

            starTwo.setOnClickListener {
                isSubmitClicked = false

                starONe.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.rate_star_fill
                    )
                )
                starTwo.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.rate_star_fill
                    )
                )
                starThree.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.rate_star_empty
                    )
                )
                starFour.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.rate_star_empty
                    )
                )
                starFive.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.rate_star_empty
                    )
                )

            }
            starThree.setOnClickListener {
                isSubmitClicked = false
                starONe.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.rate_star_fill
                    )
                )
                starTwo.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.rate_star_fill
                    )
                )
                starThree.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.rate_star_fill
                    )
                )
                starFour.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.rate_star_empty
                    )
                )
                starFive.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.rate_star_empty
                    )
                )

            }
            starFour.setOnClickListener {
                isSubmitClicked = true
                starONe.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.rate_star_fill
                    )
                )
                starTwo.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.rate_star_fill
                    )
                )
                starThree.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.rate_star_fill
                    )
                )
                starFour.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.rate_star_fill
                    )
                )
                starFive.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.rate_star_empty
                    )
                )

            }
            starFive.setOnClickListener {
                isSubmitClicked = true
                starONe.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.rate_star_fill
                    )
                )
                starTwo.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.rate_star_fill
                    )
                )
                starThree.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.rate_star_fill
                    )
                )
                starFour.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.rate_star_fill
                    )
                )
                starFive.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.rate_star_fill
                    )
                )
            }
            submit.setOnClickListener {
                if (isSubmitClicked) {
                    binding
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data =
                        Uri.parse("market://details?id=" + "com.prankapp.insectonscreen.pranksounds")


                    // If the Play Store app is not available, open the Play Store website
                    if (intent.resolveActivity(requireActivity().packageManager) != null) {
                        startActivity(intent)
                    } else {
                        val webIntent = Intent(Intent.ACTION_VIEW)
                        webIntent.data =
                            Uri.parse("market://details?id=" + "com.prankapp.insectonscreen.pranksounds")
                        startActivity(webIntent)
                    }
                }
                dialog.dismiss()
            }
            dialog.show()
        }

        binding.myDrawer.shareAppText.setOnClickListener {
            shareApp(requireContext())
        }

        return binding.root
    }
    fun shareApp(mContext: Context) {
        try {
            val shareAppIntent = Intent(Intent.ACTION_SEND)
            shareAppIntent.type = "text/plain"
            val shareSub = "Check out this application on play store!"
            val shareBody = mContext.getString(R.string.share_app_link)
            shareAppIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub)
            shareAppIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            mContext.startActivity(Intent.createChooser(shareAppIntent, "Share App using..."))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
    private fun setupRecyclerView() {
        binding.gridRecyclerView.layoutManager = GridLayoutManager(context, 2)
        val adapter = HomeScreenAdapter(requireContext(), Constant.homeScreenItem)
        binding.gridRecyclerView.adapter = adapter
    }

    private fun setupDrawerIconClickListener() {
        binding.drawerIcon.setOnClickListener {
            toggleLeftDrawer()
        }
    }

    private fun toggleLeftDrawer() {
        val gravity = if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            GravityCompat.END
        } else {
            GravityCompat.START
        }

        binding.drawerLayout.openDrawer(gravity)
    }

    override fun onResume() {
        super.onResume()
        isInHome = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isInHome = false
    }

    override fun onDestroy() {
        super.onDestroy()
        isInHome = false
    }
}
