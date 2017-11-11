package com.example.daggermvvm.ui

import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.media.Image
import android.media.ImageReader
import android.util.Log
import java.io.File
import java.net.URI
import javax.inject.Inject

/**
 * Created by 铖哥 on 2017/11/10.
 */


class ScreenCapture constructor(private val width : Int ,private  val height : Int ){

    private val mImageReader : ImageReader = ImageReader.newInstance(width,height, PixelFormat.RGBA_8888,7)

    fun getSurface() = mImageReader.surface

    fun startCapture() : Bitmap?{
        var image: Image? = null

        //当未开始录制的时候先调用此方法会报错，不知如何解决只好抓异常
        //java.lang.IllegalStateException: mImageReader.acquireLatestImage() must not be null
        try {
            image = mImageReader.acquireLatestImage()

            //此高度和宽度似乎与ImageReader构造方法中的高和宽一致
            val iWidth = image.width
            val iHeight = image.height
            //panles的数量与图片的格式有关
            val plane = image.planes[0]
            val bytebuffer = plane.buffer


            //http://www.jianshu.com/p/d184131cef2d
            //计算偏移量 不懂！
            val pixelStride = plane.pixelStride
            val rowStride = plane.rowStride;
            val rowPadding = rowStride - pixelStride * iWidth;


            val bitmap = Bitmap.createBitmap(iWidth + rowPadding / pixelStride, iHeight, Bitmap.Config.ARGB_8888);

            bitmap.copyPixelsFromBuffer(bytebuffer)

                   //必须要有这一步，不如图片会有黑边
            return Bitmap.createBitmap(bitmap,0,0,iWidth,iHeight)
        }catch (e : Exception){
            return null
        }finally {
            image?.close()
        }
    }

}