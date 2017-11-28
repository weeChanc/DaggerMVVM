package com.example.saprodontia.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.saprodontia.R
import com.example.saprodontia.modules.Directory
import com.mobile.utils.AutoNotifyAdapter
import kotlinx.android.synthetic.main.item_dir.view.*
import kotlinx.android.synthetic.main.item_exhibition.view.*

/**
 * Created by steve on 17-11-28.
 */
class DirectoryAdapter(var directory: Directory) : RecyclerView.Adapter<DirectoryAdapter.Holder>() {
    override fun getItemCount(): Int = directory.directories.size


    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.itemName.text  = directory.directories[holder.adapterPosition].path
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_dir,parent,false)
        val holder = Holder(view)
        view.setOnClickListener {
            directory = directory.directories[holder.adapterPosition]
            notifyDataSetChanged()
        }
        return holder
    }


    class Holder( val view:View) : RecyclerView.ViewHolder(view){
        val itemName = view.itemName
        val count = view.count
    }
}