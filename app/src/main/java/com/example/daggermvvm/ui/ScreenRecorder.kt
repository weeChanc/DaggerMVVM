package com.example.daggermvvm.ui

import android.graphics.Point
import android.media.MediaRecorder
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.util.DisplayMetrics
import android.util.Log
import android.view.Surface
import com.example.daggermvvm.App
import org.jetbrains.anko.mediaProjectionManager
import org.jetbrains.anko.windowManager
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by 铖哥 on 2017/11/11.
 */
class ScreenRecorder(private val width : Int , private  val height : Int, val outputFile : String){

    private val mRecorder = MediaRecorder()

    init {
        prepare()
    }

    private fun prepare(){
        with(mRecorder) {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setVideoSource(MediaRecorder.VideoSource.SURFACE)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setVideoEncoder(MediaRecorder.VideoEncoder.H264)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            //帧率 帧数简单地说，帧数就是在1秒钟时间里传输的图片的帧数，
            // 也可以理解为图形处理器每秒钟能够刷新几次，通常用fps（Frames Per Second）表示。
            setVideoFrameRate(30)
            //码率 码率：影响体积，与体积成正比：码率越大，体积越大；码率越小，体积越小。
            //码率就是数据传输时单位时间传送的数据位数,一般我们用的单位是kbps即千位每秒。也就是取样率
            setVideoEncodingBitRate(500*1000)
            setOutputFile(outputFile)

            val display = App.app.windowManager.defaultDisplay
            val dm = DisplayMetrics()
            display.getMetrics(dm)
            setVideoSize(width, height)
        }
    }


    fun getSurface(): Surface = { mRecorder.prepare(); mRecorder.surface}.invoke()

    enum class Status {
        PREPARE,START,DONE
    }

    var status = Status.PREPARE

    fun startRecord(){
        if(status == Status.PREPARE) {
            mRecorder.start()
            status = Status.START
        }
    }

    fun stopRecord(){
        Thread {
            Thread.sleep(1000)
            if(status == Status.START) {
                mRecorder.stop()
                status = Status.DONE
            }
        }.start()
    }

}