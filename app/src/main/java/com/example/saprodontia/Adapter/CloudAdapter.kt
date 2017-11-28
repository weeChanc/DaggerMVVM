//package com.example.saprodontia.Adapter
//
//import android.support.v7.widget.RecyclerView
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import com.example.saprodontia.Constant.Constant
//import com.example.saprodontia.R
//import com.google.gson.Gson
//import com.google.gson.JsonArray
//import com.google.gson.JsonObject
//import com.google.gson.JsonPrimitive
//import com.mobile.utils.AutoNotifyAdapter
//import kotlinx.android.synthetic.main.item_dir.view.*
//import kotlinx.android.synthetic.main.item_exhibition.view.*
//import java.io.File
//
///**
// * Created by steve on 17-11-28.
// */
//class CloudAdapter() : AutoNotifyAdapter {
//    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getItemCount(): Int {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
////    var root = Gson().fromJson(createJson(Constant.STRUCTURE), Directory::class.java)
////
////    init {
////
////        Log.e("CloudAdapter",File(Constant.STRUCTURE).exists().toString())
////        Log.e("CloudAdapter",root.files.size.toString())
////    }
////
////
////    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
////        if (getItemViewType(holder.adapterPosition) == TYPE_DIR){
////            with(holder as DirHolder) {
////                dirName.text = root.directorys[holder.adapterPosition].path
////            }
////        }else{
////            with(holder as FileHolder){
////                fileNme.text = root.files[holder.adapterPosition - root.directorys.size ]
////            }
////        }
////    }
////
////    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
////        val view : View
////        val holder : RecyclerView.ViewHolder
////        if(viewType == TYPE_FILE) {
////            view = LayoutInflater.from(parent?.context).inflate(R.layout.item_exhibition, parent, false)
////            holder = FileHolder(view)
////
////        }else{
////            view = LayoutInflater.from(parent?.context).inflate(R.layout.item_dir, parent, false)
////            holder = DirHolder(view)
////            view.setOnClickListener {
////                root = Gson().fromJson(createJson(root.directorys[holder.adapterPosition].path!!),Directory::class.java)
////                notifyDataSetChanged()
////            }
////        }
////        return holder
////
////    }
////
////    override fun getItemCount(): Int {
////       return  root.directorys.size + root.files.size
////    }
////
////    val TYPE_FILE = 1;
////    val TYPE_DIR = 2;
////
////    override fun getItemViewType(position: Int): Int {
////        if(position < root.directorys.size){
////            return TYPE_DIR
////        }else{
////            return TYPE_FILE
////        }
////    }
////
////    class FileHolder(view : View) : RecyclerView.ViewHolder(view) {
////        val fileNme = view.itemName
////    }
////
////    class DirHolder(view : View) : RecyclerView.ViewHolder(view) {
////        val dirName = view.dirName
////    }
////
////
////        fun createJson(root: String): JsonObject {
////        val element = JsonPrimitive(root)
////        val filearray = JsonArray()
////        val dirarray = JsonArray()
////        val json = JsonObject()
////        val file = File(root)
////        json.add("path", element)
////        val files = file.listFiles()
////        for (f in files) {
////            if (f.isFile()) {
////                filearray.add(f.getPath())
////            } else {
////                dirarray.add(createJson(f.getPath()))
////            }
////        }
////        json.add("files", filearray)
////        json.add("directorys", dirarray)
////        return json
////    }
////
////    class Directory {
////        var path: String? = null
////        var files: List<String> = mutableListOf()
////        var directorys: List<Directory> = mutableListOf()
////    }
//
//
//}