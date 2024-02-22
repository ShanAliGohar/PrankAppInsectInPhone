package com.example.prankappinsectinphone.views
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

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
        color = Color.GRAY
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

    fun animateProgress() {
        val animator = ObjectAnimator.ofInt(this, "progress", 0, maxProgress)
        animator.duration = 2000 // 2 seconds
        animator.start()
    }
}
