package com.example.saprodontia.ui.activities.main

import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.saprodontia.Constant.Constant
import com.example.saprodontia.Utils.QiniuHelper
import com.example.saprodontia.baseMVP.BasePresenter
import com.example.saprodontia.data.DirectoryContract
import com.example.saprodontia.di.scope.PerFragment
import com.example.saprodontia.modules.Directory
import com.example.saprodontia.modules.FileInfo
import com.google.gson.Gson
import com.mobile.utils.Preference
import com.mobile.utils.showToast
import com.mobile.utils.toast
import com.qiniu.android.storage.*
import com.qiniu.android.storage.persistent.FileRecorder
import com.qiniu.util.Auth
import org.jetbrains.anko.db.NULL
import java.io.File
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.ConcurrentSkipListSet
import java.util.concurrent.Semaphore
import javax.inject.Inject

/**
 * Created by steve on 17-11-24.
 */

class SendPresenter @Inject constructor() : BasePresenter<SendContract.View>() , SendContract.Presenter {

    override fun createNewDirectory(root : Directory , parent : Directory , dir: Directory) {
        parent.directories.add(dir)
        Preference.save("directory"){
            "value" - Gson().toJson(root)
        }
    }

    override fun getDirectory(): Directory {
        val raw = Preference.get("directory","value" to "") as String
        return if (raw != "")( Gson().fromJson(raw,Directory::class.java)) else Directory()
    }

    override fun onPresenterCreate() {
    }

    override fun onPresenterDestroy() {
    }


    override fun socketShare(infos: List<FileInfo>) {
        Log.e("SendPresenter","hello dagger2")

    }


    var finished = true
    val uploadQueue = ConcurrentLinkedQueue<FileInfo>()
    val semaphore = Semaphore(1)

    override fun upload(infos: List<FileInfo>) {

        infos.forEach {
            if (!uploadQueue.contains(it)) {
                uploadQueue.offer(it)
            }
        }

        if(finished) {
            Task().start()
        }
        finished = false

    }

    inner class Task : Thread() {
        override fun run() {
            while(uploadQueue.size > 0){
                semaphore.acquire()
                val info = uploadQueue.poll()
                QiniuHelper.upload(info.location,info.name,
                        { name -> semaphore.release() ; view?.uploadFinish(name)},
                        {})
            }
            finished = true
            view?.allTaskFinished()
        }
    }

    class SendModel : ViewModel() {

    }

}