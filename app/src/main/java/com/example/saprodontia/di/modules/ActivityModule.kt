package com.example.saprodontia.di.modules

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import dagger.Module
import dagger.Provides

/**
 * Created by steve on 17-11-24.
 */
@Module
class ActivityModule(activity: AppCompatActivity){

    @Provides
    fun provideSomething() ="something"
}

