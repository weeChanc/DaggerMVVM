package com.example.saprodontia.di.component

import com.example.saprodontia.di.modules.FragmentModule
import com.example.saprodontia.di.scope.PerFragment
import com.example.saprodontia.ui.fragments.media.MediaFragment

import dagger.Subcomponent

/**
 * Created by steve on 17-11-25.
 */
@PerFragment
@Subcomponent(modules = arrayOf(FragmentModule::class))
interface FragmentComponent {

    fun inject(fragment: MediaFragment)
}