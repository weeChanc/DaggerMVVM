package com.example.saprodontia.di.component

import com.example.saprodontia.di.modules.ActivityModule
import com.example.saprodontia.di.scope.PerActivity
import com.example.saprodontia.ui.activities.cloud.CloudActivity
import com.example.saprodontia.ui.activities.main.SendActivity
import dagger.Subcomponent

/**
 * Created by steve on 17-11-24.
 */

@PerActivity
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent{

    fun inject(activity: SendActivity)

    fun inject(activity:CloudActivity)


}
