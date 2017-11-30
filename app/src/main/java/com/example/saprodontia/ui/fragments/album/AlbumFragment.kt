package com.example.saprodontia.ui.fragments.album

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.saprodontia.Adapter.AlbumAdapter
import com.example.saprodontia.R
import com.example.saprodontia.Utils.MathUtil
import com.example.saprodontia.View.RecycleDecoration
import com.example.saprodontia.modules.FileInfo
import kotlinx.android.synthetic.main.fragment_exhibition.view.*
import java.io.File
import java.util.ArrayList

/**
 * Created by steve on 17-11-26.
 */
class AlbumFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_exhibition, container, false)

        val pictures : MutableList<FileInfo>
        val photoModel = ViewModelProviders.of(this).get(PhotoViewModel::class.java)
        val path = arguments!!.getString("path")

        if(photoModel.photos.size > 0){
            pictures = photoModel.photos
        }else{
            pictures = getPictureInPath(path)
        }

        view.toolbar.title = path!!.substring(path.lastIndexOf('/') + 1, path.length)

        view.recyclerView.layoutManager = GridLayoutManager(context, 3)
        val adapter = AlbumAdapter(pictures)
        view.recyclerView.adapter = adapter
        view.recyclerView.addItemDecoration(RecycleDecoration())
        return view

    }

    class PhotoViewModel : ViewModel() {
        val photos = mutableListOf<FileInfo>()
    }

    private fun getPictureInPath(path: String): MutableList<FileInfo> {
        val file = File(path)
        val files = file.listFiles()
        val pictures = ArrayList<FileInfo>()
        for (f in files) {
            val location = f.path
            if (isPic(location)) {
                val initSize = File(location).length()
                val name = location.substring(location.lastIndexOf('/') + 1, location.lastIndexOf('.'))
                val size = MathUtil.bytoKbOrMb(initSize)
                val pictureInfo = FileInfo(name,location,size,initSize)
                pictures.add(pictureInfo)
            }
        }
        return pictures
    }

    fun isPic(temp: String): Boolean =
            temp.endsWith("jpg") or
            temp.endsWith("gif") or
            temp.endsWith("png") or
            temp.endsWith("jpeg") or
            temp.endsWith("bmp")

}