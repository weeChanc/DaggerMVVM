package com.example.saprodontia.ui.activities.cloud

import com.example.saprodontia.Utils.QiniuHelper
import com.example.saprodontia.baseMVP.BasePresenter

import com.mobile.utils.inUiThread
import com.mobile.utils.showToast
import java.util.*
import java.util.concurrent.Executors
import javax.inject.Inject
import kotlin.concurrent.thread

/**
 * Created by steve on 17-11-28.
 */
class CloudPresenter @Inject constructor() : BasePresenter<CloudContract.View>(), CloudContract.Presenter {


    override fun getCloudDirectory() {
         view?.onFinish(com.xt.directoryfragment.DirectoryManager.root)
    }



    override fun onPresenterCreate() {

    }

    override fun onPresenterDestroy() {

    }

}