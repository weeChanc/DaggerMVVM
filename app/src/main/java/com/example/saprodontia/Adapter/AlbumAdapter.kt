package com.example.saprodontia.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.saprodontia.R
import com.example.saprodontia.modules.SelectedFileManager
import com.example.saprodontia.modules.FileInfo
import com.mobile.utils.AutoNotifyAdapter
import com.mobile.utils.toggleVisibility

/**
 * Created by steve on 17-11-26.
 */

/**
 * Created by 铖哥 on 2017/7/26.
 */

class AlbumAdapter(private val pictures: MutableList<FileInfo>) : AutoNotifyAdapter<FileInfo, AlbumAdapter.AlbumHolder>(pictures) {

    private lateinit var context : Context



    override fun onBindViewHolder(holder: AlbumHolder, position: Int) {

        Glide.with(context).load(pictures[holder.adapterPosition].location).into(holder.image_head)

        with(holder.checkBox){
            isChecked = SelectedFileManager.contains(pictures[holder.adapterPosition])
            visibility = if(isChecked) View.VISIBLE else View.GONE
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AlbumHolder {
        this.context = parent?.context!!
        val holder: AlbumHolder
        val view = LayoutInflater.from(parent.context).inflate(R.layout.type_two, parent, false)
        holder = AlbumHolder(view)
        view.setOnClickListener {
            if(holder.checkBox.isChecked) {
                SelectedFileManager.remove(pictures[holder.adapterPosition])
            }else{
                SelectedFileManager.add(pictures[holder.adapterPosition])
            }
            holder.checkBox.toggle()
            holder.checkBox.toggleVisibility()
        }
        return holder
    }

    override fun getItemCount(): Int = pictures.size

    inner class AlbumHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var image_head: ImageView
        var checkBox: CheckBox

        init {
            image_head = itemView.findViewById<View>(R.id.imageView) as ImageView
            checkBox = itemView.findViewById<View>(R.id.check_photo) as CheckBox
        }

    }

}
