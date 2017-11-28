package com.example.saprodontia.ui.fragments.media

import com.example.saprodontia.baseMVP.BaseContract
import com.example.saprodontia.modules.FileInfo

/**
 * Created by steve on 17-11-26.
 */
interface MediaContract {

    interface View : BaseContract.View{
        fun onFinish(videoInfos:List<FileInfo>)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun getMediaInfos(type: MediaFragmentType)
    }
}