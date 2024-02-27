package com.example.prankappinsectinphone.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.icu.text.Transliterator.Position
import android.util.AttributeSet
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.prankappinsectinphone.R
import com.masoudss.lib.WaveformSeekBar
import com.masoudss.lib.utils.Utils
import com.masoudss.lib.utils.WaveGravity
import kotlin.math.floor
import kotlin.math.roundToInt

class GradientWaveFormSeekbar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : WaveformSeekBar(context, attrs, defStyleAttr) {

    protected var mMaxValue = Utils.dp(context, 2).toInt()
    private val mWavePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var colors: IntArray? = null
    private var positions: FloatArray? = null
    private var gradientDrawable: Drawable? = null

    fun setGradientDrawable(drawable: Drawable, possition: FloatArray) {
        this.gradientDrawable = drawable
        this.positions = possition

        invalidate() // Invalidate the view to trigger redraw
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        sample?.let { waveSample ->
            if (waveSample.isNotEmpty()) {
                val totalWaveWidth = waveGap + waveWidth
                var step = waveSample.size / (width / totalWaveWidth)

                var previousWaveRight = paddingLeft.toFloat() + wavePaddingLeft
                var sampleItemPosition: Int

                val barsToDraw = (width / totalWaveWidth).toInt()
                val start: Int
                val progressXPosition: Float

                if (visibleProgress > 0) {
                    step *= visibleProgress / maxProgress
                    val barsForProgress = barsToDraw + 1
                    val intFactor = (((barsForProgress + 1) % 2))
                    previousWaveRight += (width * 0.5F) % totalWaveWidth
                    previousWaveRight += intFactor * 0.5F * totalWaveWidth - totalWaveWidth
                    previousWaveRight -= ((progress + intFactor * visibleProgress / barsForProgress * 0.5f) % (visibleProgress / barsForProgress)) / (visibleProgress / barsForProgress) * totalWaveWidth
                    start = (progress * barsForProgress / visibleProgress - (barsForProgress / 2F)).roundToInt() - 1
                    progressXPosition = width * 0.5F
                } else {
                    start = 0
                    progressXPosition = width * progress / maxProgress
                }

                for (i in start until barsToDraw + start + 3) {
                    sampleItemPosition = floor(i * step).roundToInt()
                    var waveHeight =
                        if (sampleItemPosition in waveSample.indices && mMaxValue != 0)
                            (height - wavePaddingTop - wavePaddingBottom) * (waveSample[sampleItemPosition].toFloat() / mMaxValue)
                        else 0F

                    if (waveHeight < waveMinHeight) waveHeight = waveMinHeight

                    val top: Float = when (waveGravity) {
                        WaveGravity.TOP -> paddingTop.toFloat() + wavePaddingTop
                        WaveGravity.CENTER -> (paddingTop + wavePaddingTop + height) / 2F - waveHeight / 2F
                        WaveGravity.BOTTOM -> height - paddingBottom - wavePaddingBottom - waveHeight
                    }

                    val waveRectLeft = previousWaveRight
                    val waveRectRight = previousWaveRight + waveWidth

                    val waveRect = RectF(waveRectLeft, top, waveRectRight, top + waveHeight)

                    if (waveRectRight <= progressXPosition) {
                        if (gradientDrawable != null) {
                            gradientDrawable!!.setBounds(
                                waveRectLeft.toInt(), top.toInt(), waveRectRight.toInt(), (top + waveHeight).toInt()
                            )
                            gradientDrawable!!.draw(canvas)
                        }
                    } else if (waveRectLeft < progressXPosition && progressXPosition <= waveRectRight) {
                        // Calculate the width of the progressed area
                        val progressedWidth = progressXPosition - waveRectLeft

                        // Calculate the width ratio of the progressed area to the total wave width
                        val progressRatio = progressedWidth / totalWaveWidth

                        // Apply gradient only to the progressed area
                        val gradientPaint = Paint().apply {
                            shader = LinearGradient(
                                waveRectLeft, top, progressXPosition, top + waveHeight,
                                colors ?: intArrayOf(Color.RED, Color.YELLOW), positions, Shader.TileMode.MIRROR
                            )
                        }

                        // Draw rounded rectangle for the progressed wave with gradient
                        val cornerRadius = waveHeight // Adjust the corner radius based on the wave height
                        canvas.drawRoundRect(waveRect, cornerRadius, cornerRadius, gradientPaint)
                    } else {
                        // Draw regular wave
                        mWavePaint.color = waveBackgroundColor
                        canvas.drawRoundRect(waveRect, 0F, 0F, mWavePaint) // No rounded corners
                    }

                    previousWaveRight = waveRectRight + waveGap
                }
            }
        }
    }
}
