package com.example.saprodontia.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides

/**
 * Created by steve on 17-11-24.
 */
@Module
class ApplicationModules{

    @Provides
    fun application(application: Application) = application

}