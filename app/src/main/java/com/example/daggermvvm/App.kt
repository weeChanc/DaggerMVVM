package com.example.daggermvvm

import android.app.Application
import com.mobile.utils.Utils
import kotlin.properties.Delegates

/**
 * Created by 铖哥 on 2017/11/9.
 */
class App : Application(){


    companion object {
         var app :Application by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        Utils.init(this)
    }
}