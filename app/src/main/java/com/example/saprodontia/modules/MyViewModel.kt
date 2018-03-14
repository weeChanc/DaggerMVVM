//package com.example.saprodontia.modules
//
//import android.ctx.Application
//import android.arch.lifecycle.AndroidViewModel
//import android.arch.lifecycle.MutableLiveData
//import android.arch.lifecycle.ViewModel
//import android.os.FileObserver
//import android.provider.MediaStore
//import android.util.Log
//import io.reactivex.Observable
//import io.reactivex.Observer
//import io.reactivex.Scheduler
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.disposables.Disposable
//import io.reactivex.schedulers.Schedulers
//import java.util.TreeSet
//
///**
// * Created by steve on 17-11-22.
// */
//class MyViewModel(application: Application) : AndroidViewModel(application) {
//
//    private var fileInfos : MutableLiveData<MutableList<FileInfo>>? = null
//
//    fun getInfos(onNext : ()->Unit) : MutableList<FileInfo>? {
//        if (fileInfos == null){
//            fileInfos = MutableLiveData()
//            loadInfos()
//        }
//        return fileInfos?.value
//    }
//
//    private fun loadInfos(){
////
//        getMusic().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ e -> Log.e("MyViewModel ${Thread.currentThread().name}",e.toString())})
////        Log.e("MyViewModel","i execute")
//    }
//
//    fun getMusic(): Observable<FileInfo> {
//
////        val projection = arrayOf(MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.SIZE)
////        val cursor = .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
////                projection, null, null, MediaStore.Audio.Media.DATE_MODIFIED + " desc")
//
//
//        return Observable.just(FileInfo())
//
//
////            return Observable.create<FileInfo> { emitter ->
////
////                cursor.moveToFirst()
////
////
//////                do {
//////                    val info = FileInfo()
//////                    val initSize = (cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE))).toLong()
//////
//////                    info.initSize = initSize
//////                    info.size = MathUtil.bytoKbOrMb(initSize)
//////                    info.name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
//////                    info.icon = mContext.getDrawable(R.drawable.music)
//////                    info.location = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
//////
//////                    emitter.onNext(info)
//////
//////
//////                } while (cursor.moveToNext())
////
////                emitter.onComplete()
////                cursor.close()
////
////            }
//    }
//}