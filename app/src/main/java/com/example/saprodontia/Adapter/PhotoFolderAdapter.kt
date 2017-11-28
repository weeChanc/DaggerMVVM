package com.example.saprodontia.Adapter

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.saprodontia.R
import com.example.saprodontia.modules.FileInfo
import com.example.saprodontia.ui.fragments.album.AlbumFragment
import com.mobile.utils.AutoNotifyAdapter
import com.mobile.utils.directoryFileCount
import java.io.File

/**
 * Created by steve on 17-11-26.
 */
class PhotoFolderAdapter(private val context: Context , val fileInfos: MutableList<FileInfo>) :
        AutoNotifyAdapter<FileInfo,PhotoFolderAdapter.PhotoFolderHolder>(fileInfos) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoFolderHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.pic_folder, parent, false)
        val holder = PhotoFolderHolder(view)

        view.setOnClickListener {
            val albumFragment = AlbumFragment()
            val bundle = Bundle()

            bundle.putString("path", fileInfos[holder.adapterPosition].location)
            albumFragment.arguments = bundle

            val fm = (context as AppCompatActivity).supportFragmentManager
            val transaction = fm.beginTransaction()
            transaction.addToBackStack(null)
            transaction.replace(R.id.con, albumFragment)
            transaction.commit()
        }

        return holder
    }

    override fun onBindViewHolder(holder: PhotoFolderHolder, position: Int) {
        val pos = holder.adapterPosition
        holder.count.text = File(fileInfos[pos].location).directoryFileCount().toString()
        holder.folderName.text = fileInfos[pos].name
        Glide.with(context).load(File(fileInfos.get(pos).location).listFiles()[0].absolutePath).into(holder.image)
    }

    override fun getItemCount(): Int = fileInfos.size

    inner class PhotoFolderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var count: TextView
        internal var folderName: TextView
        internal var image: ImageView

        init {
            count = itemView.findViewById<View>(R.id.tv_count) as TextView
            folderName = itemView.findViewById<View>(R.id.tv_folder_name) as TextView
            image = itemView.findViewById<View>(R.id.imge_first) as ImageView
        }
    }


}