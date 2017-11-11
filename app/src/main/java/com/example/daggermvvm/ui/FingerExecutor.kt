package com.example.daggermvvm.ui

import java.io.OutputStream

/**
 * Created by 铖哥 on 2017/11/10.
 */
class FingerExecutor{
    companion object {

        fun simulateTap(x : Int, y : Int) {
            val os = getRootOS()
            os?.write("input tap $x $y".toByteArray())
            os?.flush()
            os?.close()
        }

        fun simulateDoubleTap(x : Int, y : Int) {
            simulateTap(x, y)
            simulateTap(x, y)
        }

        fun simulateSwipe(fromX : Int, fromY : Int , toX : Int , toY : Int){
            val os = getRootOS()
            os?.write("input swipe $fromX $fromY $toX $toY".toByteArray())
            os?.flush()
            os?.close()
        }



        private fun getRootOS(): OutputStream? {
            try {
                return Runtime.getRuntime().exec("su").outputStream
            }catch (e : Exception){
                return null
            }
        }
    }
}