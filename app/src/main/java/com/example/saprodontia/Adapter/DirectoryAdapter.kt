package com.example.saprodontia.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.saprodontia.R
import com.example.saprodontia.Utils.QiniuHelper
import com.example.saprodontia.data.Directory
import com.example.saprodontia.data.Directory.Companion.DIR
import com.example.saprodontia.data.Directory.Companion.FILE
import com.example.saprodontia.data.DirectoryManager
import com.mobile.utils.AutoNotifyAdapter
import kotlinx.android.synthetic.main.item_dir.view.*
import kotlinx.android.synthetic.main.item_exhibition.view.*
import java.util.*

/**
 * Created by steve on 17-11-28.
 */
class DirectoryAdapter(val root: Directory , val displayFile : Boolean = false) : RecyclerView.Adapter<DirectoryAdapter.Holder>() {

    var currentDir = root
    private var backStack = Stack<Directory>()

    override fun getItemCount(): Int {
        Collections.sort(currentDir.directories){
            o1 ,o2 -> o1.path.compareTo(o2.path)
        }
        return currentDir.directories.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val rawPath = currentDir.directories[holder.adapterPosition].path
        val path = rawPath.substring(0, rawPath.length - 1)
        holder.itemName.text = rawPath.substring(path.lastIndexOf("/")+1, rawPath.length)
        Log.e("DirectoryAdapter",currentDir.directories.get(position).type)

    }

     fun consumeBackPressed(): Boolean {
         Log.e("DirectoryAdapter",backStack.size.toString())
        if(backStack.empty()) return false
        else{
            currentDir = backStack.pop()
            pathListener?.invoke(currentDir.path)
            notifyDataSetChanged()
        }
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_dir, parent, false)
        val holder = Holder(view)
        view.setOnClickListener {
            if(currentDir.directories[holder.adapterPosition].type == DIR) {
                Log.e("DirectoryAdapter","PUSH")
                backStack.push(currentDir)
                Log.e("DirectoryAdapter",backStack.size.toString())
                currentDir = currentDir.directories[holder.adapterPosition]
                pathListener?.invoke(currentDir.path)
                notifyDataSetChanged()
                (parent as RecyclerView).scheduleLayoutAnimation()
            }
        }
        return holder
    }

    private var pathListener : ((currentPath : String)->Unit)? = null
    fun setOnPathChangListenner(pathListener:(String)->Unit){
        this.pathListener = pathListener
    }


    class Holder(val view: View) : RecyclerView.ViewHolder(view) {
        val itemName = view.dirName
        val count = view.count
    }

}

