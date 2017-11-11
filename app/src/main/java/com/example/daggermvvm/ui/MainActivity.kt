package com.example.daggermvvm.ui

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.hardware.display.DisplayManager
import android.hardware.display.DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC
import android.media.MediaCodec
import android.media.MediaCodecInfo
import android.media.MediaFormat
import android.media.MediaMuxer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.Surface
import com.example.daggermvvm.R
import com.mobile.utils.PermissionCompatActivity
import com.mobile.utils.PermissionMan
import dagger.Component
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.mediaProjectionManager
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import java.io.File
import javax.inject.Inject
import kotlin.properties.Delegates

class MainActivity : PermissionCompatActivity(), MainContract.View {

    val width = 1440
    val height = 2560

    var capturor: ScreenCapture = ScreenCapture(width, height)

    val mRecorder = ScreenRecorder(width, height, outputFile = Environment.getExternalStorageDirectory().path + "/output.mp4")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val metrics = DisplayMetrics()
        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        Log.e("MainActivity", metrics.toString())

        startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), 1)

        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO),
                2);

        imageView.setOnClickListener {
            if (mRecorder.status == ScreenRecorder.Status.PREPARE)
                mRecorder.startRecord()
            if (mRecorder.status == ScreenRecorder.Status.START)
                mRecorder.stopRecord()
        }

    }


    private var mMuxer: MediaMuxer? = null

    private var allow: Boolean = true

    private val mBufferInfo = MediaCodec.BufferInfo()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, data)

        if (mediaProjection == null) {
            Log.e("@@", "media projection is null")
            return
        }

        mediaProjection.createVirtualDisplay("test", width, height, 1,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, mRecorder.getSurface(), null, null)


    }


}

