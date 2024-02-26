package com.example.prankappinsectinphone.views

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.AnimationDrawable
import android.util.AttributeSet
import android.view.View
import com.example.prankappinsectinphone.R

class MovingInsectView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private lateinit var animationDrawable1: AnimationDrawable
    private lateinit var animationDrawable2: AnimationDrawable
    private lateinit var animationDrawable3: AnimationDrawable
    private lateinit var animator1: ObjectAnimator
    private lateinit var animator2: ObjectAnimator
    private lateinit var animator3: ObjectAnimator

    init {
        setBackgroundResource(R.drawable.animation)
        animationDrawable1 = background as AnimationDrawable
        animationDrawable1.start()

        setBackgroundResource(R.drawable.animation)
        animationDrawable2 = background as AnimationDrawable
        animationDrawable2.start()

        setBackgroundResource(R.drawable.animation)
        animationDrawable3 = background as AnimationDrawable
        animationDrawable3.start()

        animator1 = ObjectAnimator.ofFloat(this, "translationX", 0f, 200f)
        setupAnimator(animator1)

        animator2 = ObjectAnimator.ofFloat(this, "translationY", 0f, 200f)
        setupAnimator(animator2)

        animator3 = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f)
        setupAnimator(animator3)
    }

    private fun setupAnimator(animator: ObjectAnimator) {
        animator.apply {
            duration = 1000
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            start()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val desiredWidth = width / 3 // Adjust this factor as needed
        val scaleFactor = desiredWidth.toFloat() / animationDrawable1.intrinsicWidth.toFloat()
        val newHeight = (animationDrawable1.intrinsicHeight * scaleFactor).toInt()

        // Draw the first animation drawable
        // Get the dimensions of the view
        val viewWidth = width
        val viewHeight = height

        // Calculate the maximum translation values
        val maxXTranslation = viewWidth / 2
        val maxYTranslation = viewHeight / 2

        // Calculate random translations


        // Draw the first animation drawable with random translation
        animationDrawable1.setBounds(
            maxXTranslation.toInt(),
            maxYTranslation.toInt(),
            desiredWidth.toInt() + desiredWidth,
            desiredWidth.toInt() + newHeight
        )
        animationDrawable1.draw(canvas)

        // Draw the second animation drawable with random translation
        animationDrawable2.setBounds(
            scaleFactor.toInt(),
            maxYTranslation.toInt(),
            newHeight.toInt() + desiredWidth,
            newHeight.toInt() + newHeight
        )
        animationDrawable2.draw(canvas)

        // Draw the third animation drawable with random translation
        animationDrawable3.setBounds(
            desiredWidth.toInt(),
            maxYTranslation.toInt(),
            newHeight.toInt() + desiredWidth,
            scaleFactor.toInt() + newHeight
        )
        animationDrawable3.draw(canvas)
    }
}
