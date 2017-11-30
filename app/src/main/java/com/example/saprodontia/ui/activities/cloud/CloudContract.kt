package com.example.saprodontia.ui.activities.cloud

import android.provider.ContactsContract
import com.example.saprodontia.baseMVP.BaseContract
import com.example.saprodontia.data.Directory

/**
 * Created by steve on 17-11-28.
 */
interface CloudContract{

    interface View : BaseContract.View{
        fun onFinish(root  :Directory)
    }

    interface Presenter : BaseContract.Presenter<View>{
        fun getCloudDirectory()
    }

}