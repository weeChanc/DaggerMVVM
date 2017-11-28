package com.example.saprodontia.ui.fragments.dir

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.saprodontia.Adapter.DirectoryAdapter
import com.example.saprodontia.Adapter.SelectedItemAdapter
import com.example.saprodontia.R
import com.example.saprodontia.modules.SelectedFileManager
import kotlinx.android.synthetic.main.fragment_exhibition.view.*

/**
 * Created by steve on 17-11-28.
 */
class DirectroyFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_exhibition, container, false)
        val recyclerView = view.recyclerView

        view.toolbar.title = "选择文件夹"

        recyclerView.layoutManager = LinearLayoutManager(context)
//        recyclerView.adapter = DirectoryAdapter()

        return view
    }
}