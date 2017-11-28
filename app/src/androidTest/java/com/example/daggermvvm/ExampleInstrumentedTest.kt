package com.example.daggermvvm

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.example.saprodontia.Utils.QiniuHelper

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        val perfix = "/home/steve/";
//        print("ExampleUnitTest" + .exists())
        QiniuHelper.upload("/home/steve/android_key.jks",perfix+"key.jks",{ print("success")},{})
    }
}
