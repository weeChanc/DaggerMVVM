package com.example.saprodontia.di.component

import android.support.v7.app.AppCompatActivity
import com.example.saprodontia.di.modules.ActivityModule
import com.example.saprodontia.di.modules.ApplicationModules
import com.example.saprodontia.di.modules.FragmentModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by steve on 17-11-24.
 */
@Singleton
@Component(modules = arrayOf(ApplicationModules::class))
interface ApplicationComponent{
    operator fun plus(activity: ActivityModule) : ActivityComponent
    operator fun plus(fragment : FragmentModule) : FragmentComponent
}