package com.example.saprodontia.ui.fragments.media

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.saprodontia.Adapter.ItemAdapter
import com.example.saprodontia.Adapter.PhotoFolderAdapter
import com.example.saprodontia.R
import com.example.saprodontia.baseMVP.BaseMVPFragment
import com.example.saprodontia.modules.FileInfo
import com.example.saprodontia.modules.SelectedFileManager
import com.mobile.utils.AutoNotifyAdapter
import com.mobile.utils.permission.Permission
import com.mobile.utils.permission.PermissionGetCallBack
import com.mobile.utils.permission.PermissionMan
import com.mobile.utils.permission.PermissionTask
import java.util.*
import javax.inject.Inject

/**
 * Created by steve on 17-11-26.
 */
class MediaFragment() : BaseMVPFragment<MediaContract.Presenter, MediaContract.View>(), MediaContract.View {


    lateinit var type: MediaFragmentType

    @Inject
    lateinit var presenter: MediaPresenter

    private val fileInfos = mutableListOf<FileInfo>()
    private lateinit var adapter: AutoNotifyAdapter<FileInfo,*>

    override fun injectPresenter(): MediaContract.Presenter = presenter

    override fun injectDependencies() {
        fragmentComponent.inject(this)
    }

    override fun onPrepared() {
        Permission.STORAGE.get(activity){ isPassed ->
            if(isPassed && mPresenter != null){
                mPresenter?.getMediaInfos(type)
            }

        }

    }

    override fun onFinish(videoInfos: List<FileInfo>) {
        if (fileInfos.size == 0) {
            fileInfos.addAll(videoInfos)
            Collections.sort(fileInfos) { o1, o2 -> o1.name.compareTo(o2.name) }
            adapter.easyNotify { file1, file2 -> file1.location == file2.location }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.item_pager, container, false)
        val recyclerView = view.findViewById<View>(R.id.recyclerView) as RecyclerView
        type = arguments.getSerializable("type") as MediaFragmentType

        if(type == MediaFragmentType.PHOTO){
            adapter = PhotoFolderAdapter(activity,fileInfos)
        }else{
            adapter = ItemAdapter(fileInfos)
        }

        SelectedFileManager.addOnCountChangedListener { old,new->
            if(old > new ){
                adapter.notifyDataSetChanged()
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        return view
    }

}