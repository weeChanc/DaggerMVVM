package com.example.saprodontia.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.example.saprodontia.R
import com.example.saprodontia.View.MyProgressBar
import com.example.saprodontia.modules.SelectedFileManager
import com.example.saprodontia.modules.FileInfo
import com.mobile.utils.AutoNotifyAdapter
import kotlinx.android.synthetic.main.item_exhibition_checkbox.view.*

/**
 * Created by steve on 17-11-22.
 */
class ItemAdapter( private val mDatas: MutableList<FileInfo>) : AutoNotifyAdapter<FileInfo, ItemAdapter.ViewHolder>(mDatas) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_exhibition_checkbox, parent, false)
        val holder = ViewHolder(view)
        view.setOnClickListener {
            with(holder.itemCheckBox) {

                visibility = if(visibility == View.INVISIBLE) View.VISIBLE else View.INVISIBLE

                if (isChecked ) SelectedFileManager.remove(mDatas[holder.adapterPosition])
                else SelectedFileManager.add(mDatas[holder.adapterPosition])

                isChecked = !isChecked
            }
        }


        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val fileInfo = mDatas[holder.adapterPosition]

        with(holder) {
            itemSize.text = fileInfo.size
            itemName.text = fileInfo.name
            itemImage.setImageDrawable(fileInfo.icon)


            with(holder.itemCheckBox){
                isChecked = SelectedFileManager.contains(mDatas[holder.adapterPosition])
                visibility = if(isChecked) View.VISIBLE else View.INVISIBLE
            }
        }
    }

    override fun getItemCount(): Int = mDatas.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemImage: ImageView = itemView.itemImage
        var itemName: TextView = itemView.itemName
        var itemSize: TextView = itemView.itemSize
        var itemCheckBox: CheckBox = itemView.itemCheckBox
        var progress_bar: MyProgressBar = itemView.itemPrgressBar


    }


}