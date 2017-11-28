package com.example.saprodontia.di.modules

import android.support.v4.app.Fragment
import dagger.Module
import dagger.Provides

/**
 * Created by steve on 17-11-25.
 */
@Module
class FragmentModule(fragment: Fragment){

    @Provides
    fun provideSomthing() = "fragment"

}