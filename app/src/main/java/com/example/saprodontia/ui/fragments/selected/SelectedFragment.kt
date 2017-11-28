package com.example.saprodontia.ui.fragments.selected

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.saprodontia.Adapter.AlbumAdapter
import com.example.saprodontia.Adapter.SelectedItemAdapter
import com.example.saprodontia.R
import com.example.saprodontia.View.RecycleDecoration
import com.example.saprodontia.modules.FileInfo
import com.example.saprodontia.modules.SelectedFileManager
import kotlinx.android.synthetic.main.fragment_exhibition.view.*

/**
 * Created by steve on 17-11-27.
 */
class SelectedFragment : Fragment(){

    lateinit var checkdFile : MutableList<FileInfo>
    lateinit var adapter : SelectedItemAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_exhibition, container, false)
        val recyclerView = view.recyclerView

        view.toolbar.title = "已选择文件"
        checkdFile = SelectedFileManager.getCheckFile()


        adapter = SelectedItemAdapter(checkdFile)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        ItemTouchHelper(ItemTouchCallback()).attachToRecyclerView(recyclerView)
        return view
    }

    inner class ItemTouchCallback : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
            val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            return ItemTouchHelper.Callback.makeMovementFlags(0, swipeFlags)
        }

        override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
            SelectedFileManager.remove(checkdFile[viewHolder!!.adapterPosition])
            checkdFile.removeAt(viewHolder.adapterPosition)
            adapter.easyNotify{ f1, f2 -> f1.location == f2.location}
        }

    }

}