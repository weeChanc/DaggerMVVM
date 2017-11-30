package com.example.saprodontia.ui.activities.cloud

import com.example.saprodontia.Utils.QiniuHelper
import com.example.saprodontia.Utils.QiniuUtils
import com.example.saprodontia.baseMVP.BasePresenter
import com.example.saprodontia.data.Directory
import com.example.saprodontia.data.Directory.Companion.FILE
import com.example.saprodontia.data.DirectoryContract
import com.example.saprodontia.data.DirectoryManager
import com.mobile.utils.inUiThread
import com.mobile.utils.showToast
import com.mobile.utils.toast
import javax.inject.Inject
import kotlin.concurrent.thread

/**
 * Created by steve on 17-11-28.
 */
class CloudPresenter @Inject constructor() : BasePresenter<CloudContract.View>(), CloudContract.Presenter {

    val manager = DirectoryManager(DirectoryManager.REMOTE)

    override fun getCloudDirectory() {
        thread {
            if(manager.isDirty()) {
                getDir(manager.root)
                manager.save()
                manager.setDirty(false)
            }
            inUiThread { view?.onFinish(manager.root) }
        }

    }

    private fun getDir(root: Directory) {

        QiniuHelper.listFile(root.path)?.forEach {
            showToast(it.key)
            root.directories.add(Directory(root.path + it.key,type = it.mimeType))
        }

        root.directories.forEach {
            getDir(it)
        }

    }

    override fun onPresenterCreate() {
    }

    override fun onPresenterDestroy() {
    }

}