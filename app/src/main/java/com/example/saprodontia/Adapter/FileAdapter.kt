//package com.example.saprodontia.Adapter
//
//import android.support.v7.widget.RecyclerView
//import android.util.Log
//import android.view.View
//import android.view.ViewGroup
//import com.example.saprodontia.data.Directory
//import kotlinx.android.synthetic.main.item_dir.view.*
//import java.util.*
//
///**
// * Created by steve on 17-11-30.
// */
//class FileAdapter(var items : MutableList<Directory>,id : Int) : RecyclerView.Adapter<FileAdapter.ViewHolder>() {
//
//
//
//
////    override fun View.bind(item: Directory) {
////        Log.e("FileAdapter","bind + ${item.path}")
////        var path = item.path
////        path =  item.path.substring(0, path.length - 1)
////        dirName.text = path.substring(path.lastIndexOf("/"), path.length)
////    }
////
////
////    override fun onItemClick(view: View, pos: Int) {
////        click?.invoke(view,pos)
////    }
////
////    var click : ((View , Int) -> Unit)? = null
////    fun setItemClickListener( click : ((View , Int) -> Unit)?){
////        this.click= click
////    }
//
//    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
//
//    }
//
//
//
//
//}