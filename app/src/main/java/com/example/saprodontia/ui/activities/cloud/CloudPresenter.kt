package com.example.saprodontia.ui.activities.cloud

import com.example.saprodontia.baseMVP.BasePresenter
import javax.inject.Inject

/**
 * Created by steve on 17-11-28.
 */
class CloudPresenter @Inject constructor() : BasePresenter<CloudContract.View>(), CloudContract.Presenter  {
    override fun onPresenterCreate() {
    }

    override fun onPresenterDestroy() {
    }

}