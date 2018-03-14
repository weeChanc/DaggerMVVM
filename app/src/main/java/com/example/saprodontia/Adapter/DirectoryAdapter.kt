package com.xt.directoryfragment

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.saprodontia.R
import com.xt.directoryfragment.MFile.Companion.DIR
import kotlinx.android.synthetic.main.item_dir.view.*
import java.util.*
import kotlin.properties.Delegates

/**
 * Created by steve on 17-11-28.
 */
class DirectoryAdapter(val ctx : Context, val root: MFile, val displayFile: Boolean = false) : RecyclerView.Adapter<DirectoryAdapter.Holder>() {

    private val excel = ctx.getDrawable(R.drawable.excel)
    private val word = ctx.getDrawable(R.drawable.word)
    private val pdf = ctx.getDrawable(R.drawable.pdf)
    private val ppt = ctx.getDrawable(R.drawable.powerpoint)
    private val unknow = ctx.getDrawable(R.drawable.unknow)
    private val image = ctx.getDrawable(R.drawable.image)
    private val music = ctx.getDrawable(R.drawable.music)
    private val movie = ctx.getDrawable(R.drawable.movie)
    private val app = ctx.getDrawable(R.drawable.app)

    fun getIconbyFormat(format : String): Drawable {
        when (format) {
            ".xls","xlsx" -> return  excel
            ".doc",".docx" -> return word
            ".pdf" -> return pdf
            ".ppt",".pptx" -> return ppt
            ".mp4",".avi",".mpeg" -> return movie
            ".mp3",".wav",".midi",".wma",".flac" -> return music
            else -> return  unknow
        }
    }



    var currentDir by Delegates.observable(root) { _, _, new ->
        sort(new.directories)
    }

    init {
        sort(root.directories)
    }

    var longclickListener: ((View,Int)->Boolean)? = null

    fun setItemLongClickListener(listener: ((View,Int)->Boolean)?){
        this.longclickListener = listener
    }

    private fun sort(dirs : List<MFile>){
        Collections.sort(dirs) { o1, o2 ->
            if (o1.type == DIR ) -1 else 1
        }

        var divide = 0

        for(i in 0 until dirs.size){
            if (dirs[i].type != DIR) {
                divide = i
                break
            }
        }

        Collections.sort(dirs.subList(0,divide))
        Collections.sort(dirs.subList(divide,dirs.size))
    }

    private var backStack = Stack<MFile>()



    override fun getItemCount(): Int = currentDir.directories.size


    override fun onBindViewHolder(holder: Holder, position: Int) {

        val pos = holder.adapterPosition
        val rawPath = currentDir.directories[pos].path
        val path = rawPath.substring(0, rawPath.length - 1)


        with(currentDir.directories[pos]){

            if(type== DIR){
                holder.size.text = "文件个数"+ size
                holder.itemName.text = rawPath.substring(path.lastIndexOf("/") + 1, rawPath.length-1)
                holder.icon.setImageDrawable(ctx.getDrawable(R.drawable.folder))
            }else{
                holder.icon.setImageDrawable(getIconbyFormat(type))
                holder.itemName.text = rawPath.substring(path.lastIndexOf("/") + 1, rawPath.length)
                holder.size.text = "文件大小" + size
            }

        }

    }


    fun consumeBackPressed(): Boolean {

        if (backStack.empty()) return false
        else {
            currentDir = backStack.pop()
            pathListener?.invoke(currentDir.path)
            notifyDataSetChanged()
        }
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_dir, parent, false)
        val holder = Holder(view)
        view.setOnClickListener {
            if (currentDir.directories[holder.adapterPosition].type == DIR) {
                backStack.push(currentDir)
                currentDir = currentDir.directories[holder.adapterPosition]
                pathListener?.invoke(currentDir.path)
                notifyDataSetChanged()
                (parent as RecyclerView).scheduleLayoutAnimation()
            }
        }

        view.setOnLongClickListener {
            view ->
            longclickListener?.invoke(view,holder.adapterPosition)?:false
        }
        return holder
    }

    private var pathListener: ((currentPath: String) -> Unit)? = null
    fun setOnPathChangListenner(pathListener: (String) -> Unit) {
        this.pathListener = pathListener
    }


    class Holder(val view: View) : RecyclerView.ViewHolder(view) {
        val itemName = view.dirName
        val icon = view.icon
        val size = view.size
    }

}

