package com.example.prankappinsectinphone.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.media.MediaCodec
import android.media.MediaExtractor
import android.media.MediaFormat
import android.util.AttributeSet
import android.view.View
import kotlin.concurrent.thread

class AudioVisulizerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()
    private val backgroundGradient = LinearGradient(
        0f, 0f, 0f, height.toFloat(),
        Color.parseColor("#FF7E5F"), Color.parseColor("#FEB47B"), Shader.TileMode.CLAMP
    )

    init {
        paint.shader = backgroundGradient
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Draw the background gradient
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        // Draw the audio visualization
        // Implement drawing logic here
    }

    fun visualizeAudio(audioFilePath: String) {
        thread {
            val mediaExtractor = MediaExtractor()
            mediaExtractor.setDataSource(audioFilePath)
            val audioTrackIndex = selectAudioTrack(mediaExtractor)
            if (audioTrackIndex < 0) {
                // Audio track not found
                return@thread
            }

            mediaExtractor.selectTrack(audioTrackIndex)
            val mediaFormat = mediaExtractor.getTrackFormat(audioTrackIndex)
            val audioSampleRate = mediaFormat.getInteger(MediaFormat.KEY_SAMPLE_RATE)
            val audioChannelCount = mediaFormat.getInteger(MediaFormat.KEY_CHANNEL_COUNT)

            val mediaCodec = MediaCodec.createDecoderByType(mediaFormat.getString(MediaFormat.KEY_MIME)!!)
            mediaCodec.configure(mediaFormat, null, null, 0)
            mediaCodec.start()

            val inputBuffers = mediaCodec.inputBuffers
            val bufferInfo = MediaCodec.BufferInfo()

            val bufferSize = 4096 // Adjust buffer size as needed

            while (true) {
                val inputBufferIndex = mediaCodec.dequeueInputBuffer(1000)
                if (inputBufferIndex >= 0) {
                    val inputBuffer = inputBuffers[inputBufferIndex]
                    val sampleSize = mediaExtractor.readSampleData(inputBuffer, 0)
                    if (sampleSize < 0) {
                        mediaCodec.queueInputBuffer(inputBufferIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM)
                        break
                    } else {
                        mediaCodec.queueInputBuffer(inputBufferIndex, 0, sampleSize, mediaExtractor.sampleTime, 0)
                        mediaExtractor.advance()
                    }
                }

                val outputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, 1000)
                if (outputBufferIndex >= 0) {
                    val outputBuffer = mediaCodec.outputBuffers[outputBufferIndex]
                    val chunk = ByteArray(bufferSize.coerceAtMost(bufferInfo.size))
                    outputBuffer.get(chunk)
                    // Process audio chunk for visualization
                    // Implement visualization logic here
                    outputBuffer.clear()
                    mediaCodec.releaseOutputBuffer(outputBufferIndex, false)
                } else if (outputBufferIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                    // Format changed
                }
            }

            mediaCodec.stop()
            mediaCodec.release()
            mediaExtractor.release()
        }
    }

    private fun selectAudioTrack(extractor: MediaExtractor): Int {
        for (i in 0 until extractor.trackCount) {
            val format = extractor.getTrackFormat(i)
            val mime = format.getString(MediaFormat.KEY_MIME)
            if (mime?.startsWith("audio/") == true) {
                return i
            }
        }
        return -1
    }
}