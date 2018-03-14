package com.example.saprodontia.ui.fragments.media

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import com.example.saprodontia.baseMVP.BasePresenter
import com.example.saprodontia.di.scope.PerFragment
import com.example.saprodontia.modules.MediaRepositroy
import com.example.saprodontia.modules.FileInfo
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
                MediaFragmentType.APPS -> source = MediaRepositroy.getApplications()
                MediaFragmentType.DOCUMENT -> source = MediaRepositroy.getDocument()
                MediaFragmentType.VIDEO -> source = MediaRepositroy.getVideo()
                MediaFragmentType.MUSIC -> source = MediaRepositroy.getMusic()
                MediaFragmentType.PHOTO -> source = MediaRepositroy.getPhotos()
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