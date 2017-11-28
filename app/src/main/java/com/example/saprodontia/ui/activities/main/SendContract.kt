package com.example.saprodontia.ui.activities.main

import com.example.saprodontia.baseMVP.BaseContract
import com.example.saprodontia.data.DirectoryContract
import com.example.saprodontia.modules.Directory
import com.example.saprodontia.modules.FileInfo

/**
 * Created by steve on 17-11-24.
 */
interface SendContract {

    interface View : BaseContract.View{
        fun uploadStart()
        fun uploadFinish(name : String )
        fun allTaskFinished()
    }

    interface Presenter: BaseContract.Presenter<View> {
        fun upload(infos: List<FileInfo>)
        fun socketShare(infos : List<FileInfo>)
        fun getDirectory(): Directory?
        fun createNewDirectory(root : Directory ,parent : Directory ,dir : Directory)
    }

}