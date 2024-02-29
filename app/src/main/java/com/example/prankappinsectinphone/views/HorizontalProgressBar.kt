package com.example.prankappinsectinphone.views
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.provider.SyncStateContract.Constants
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import com.example.prankappinsectinphone.utils.Constant

class HorizontalProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progress = 0
    private var maxProgress = 100
    private val progressPaint = Paint().apply {
        color = Color.BLACK
        isAntiAlias = true
    }
    private val backgroundPaint = Paint().apply {
        color = Color.WHITE
        isAntiAlias = true
    }
    private val roundedRect = RectF()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()

        // Draw background
        canvas.drawRoundRect(0f, 0f, width, height, height / 2, height / 2, backgroundPaint)

        // Draw progress
        val progressWidth = (progress.toFloat() / maxProgress) * width
        roundedRect.set(0f, 0f, progressWidth, height)
        canvas.drawRoundRect(roundedRect, height / 2, height / 2, progressPaint)

    }


    fun setProgress(progress: Int) {
        this.progress = progress
        invalidate() // Redraw the view
    }

    fun setMaxProgress(maxProgress: Int) {
        this.maxProgress = maxProgress
    }

    fun animateProgress(completion: () -> Unit) {
        val animator = ObjectAnimator.ofInt(this, "progress", 0, maxProgress)
        animator.duration = 2000 // 2 seconds
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator, isReverse: Boolean) {
                completion.invoke() // Invoke the completion listener when the animation ends
            }
        })
        animator.start()
    }
}
