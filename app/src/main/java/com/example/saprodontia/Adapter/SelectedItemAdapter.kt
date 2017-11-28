package com.example.saprodontia.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.support.v7.widget.helper.ItemTouchUIUtil
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.saprodontia.R
import com.example.saprodontia.modules.FileInfo
import com.mobile.utils.AutoNotifyAdapter
import com.mobile.utils.autoNotify
import kotlinx.android.synthetic.main.item_exhibition.view.*

/**
 * Created by steve on 17-11-27.
 */
class SelectedItemAdapter(val mDatas : MutableList<FileInfo>) :
        AutoNotifyAdapter<FileInfo,SelectedItemAdapter.SelectedHolder>(mDatas){

     var context : Context? = null



    override fun onBindViewHolder(holder: SelectedHolder, position: Int) {
        with(holder){
            itemImage.setImageDrawable(mDatas[adapterPosition].icon)
            itemName.text = mDatas[adapterPosition].name
            itemSize.text = "文件大小: " + mDatas[adapterPosition].size
            if(mDatas[adapterPosition].icon == null){
                Glide.with(context).load(mDatas[adapterPosition].location).into(itemImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SelectedHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_exhibition,parent,false)
        context = parent?.context
        return SelectedHolder(view)
    }


    inner class SelectedHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        val itemName = itemView.itemName
        val itemSize = itemView.itemSize
        val itemImage = itemView.itemImage
    }



}