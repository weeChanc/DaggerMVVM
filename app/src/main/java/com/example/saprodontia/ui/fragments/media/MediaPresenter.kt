package com.example.saprodontia.ui.fragments.media

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import com.example.saprodontia.baseMVP.BasePresenter
import com.example.saprodontia.di.scope.PerFragment
import com.example.saprodontia.modules.ContentModules
import com.example.saprodontia.modules.FileInfo
import com.mobile.utils.permission.Permission
import com.mobile.utils.permission.PermissionMan
import com.mobile.utils.permission.PermissionTask
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by steve on 17-11-26.
 */
@PerFragment
class MediaPresenter @Inject constructor() : MediaContract.Presenter, BasePresenter<MediaContract.View>() {


    lateinit var musicModel: MediaModel

    override fun getMediaInfos(type: MediaFragmentType) {

        if (musicModel.mediaInfos.value == null) {
            val source: Observable<FileInfo>

            when (type) {
                MediaFragmentType.APPS -> source = ContentModules.getApplications()
                MediaFragmentType.DOCUMENT -> source = ContentModules.getDocument()
                MediaFragmentType.VIDEO -> source = ContentModules.getVideo()
                MediaFragmentType.MUSIC -> source = ContentModules.getMusic()
                MediaFragmentType.PHOTO -> source = ContentModules.getPhotos()
            }

            val fileInfos = mutableListOf<FileInfo>()
            fecher.fetchIO(source,
                    onNext = { fileInfo -> fileInfos.add(fileInfo) },
                    onComplete = { musicModel.mediaInfos.value = fileInfos })
        } else {
            //has observe it //don't need any operation
        }
    }

    override fun onPresenterCreate() {

        if (view is Fragment) {
            musicModel = ViewModelProviders.of(view as Fragment).get(MediaModel::class.java)
            musicModel.mediaInfos.observe(view as Fragment,
                    android.arch.lifecycle.Observer { if (it != null) view?.onFinish(it) })
        } else {
            // fuck ? what the hell ?
            throw RuntimeException("View is NOT Fragment")
        }
    }

    override fun onPresenterDestroy() {

    }


    class MediaModel() : ViewModel() {
        val mediaInfos: MutableLiveData<List<FileInfo>> = MutableLiveData()
    }

}