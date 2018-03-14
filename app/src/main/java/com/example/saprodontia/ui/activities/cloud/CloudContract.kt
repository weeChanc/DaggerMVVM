package com.example.saprodontia.ui.activities.cloud

import android.provider.ContactsContract
import com.example.saprodontia.baseMVP.BaseContract

import com.xt.directoryfragment.MFile

/**
 * Created by steve on 17-11-28.
 */
interface CloudContract{

    interface View : BaseContract.View{
        fun onFinish(root  :MFile)
    }

    interface Presenter : BaseContract.Presenter<View>{
        fun getCloudDirectory()
    }

}