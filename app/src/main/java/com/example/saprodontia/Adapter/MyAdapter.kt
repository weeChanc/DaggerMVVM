//package com.example.saprodontia.Adapter
//
//import android.content.Context
//import android.support.v7.widget.RecyclerView
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.CheckBox
//import android.widget.ImageView
//import android.widget.TextView
//import com.example.saprodontia.Application.App
//import com.example.saprodontia.R
//import com.example.saprodontia.modules.FileInfo
//import com.mobile.utils.AutoNotifyAdapter
//
///**
// * Created by steve on 17-11-22.
// */
//class MyAdapter(context: Context, private val mDatas: MutableList<FileInfo>) : AutoNotifyAdapter<FileInfo, MyAdapter.ViewHolder>(mDatas) {
//
////    private val sendDatas: ArrayList<FileInfo>
//    private val ctx: App
//    private var mOnSenDatasChangedListener: onSenDatasChangedListener? = null
//
//
//    init {
//        ctx = context.applicationContext as App
////        sendDatas = ctx.senDatas!!
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_exhibition, null)
//        return ViewHolder(view)
//
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//
//        holder.tv_appname.text = mDatas[holder.adapterPosition].name
//        holder.tv_appsize.text = mDatas[holder.adapterPosition].size
//        holder.image_app.setImageDrawable(mDatas[holder.adapterPosition].icon)
//
//
////        holder.checkBox.isChecked = belong(sendDatas, holder.adapterPosition)
////
////        holder.bt_check.setOnClickListener {
////            if (!holder.checkBox.isChecked) {
////                holder.checkBox.isChecked = true
////                sendDatas.add(mDatas[holder.adapterPosition])
////
////                if (mOnSenDatasChangedListener != null)
////                    mOnSenDatasChangedListener!!.onDataChanged(sendDatas)
////
////            } else {
////
////                holder.checkBox.isChecked = false
////                sendDatas.remove(mDatas[holder.adapterPosition])
////
////                if (mOnSenDatasChangedListener != null)
////                    mOnSenDatasChangedListener!!.onDataChanged(sendDatas)
////            }
////        }
//    }
//
//    override fun getItemCount(): Int {
//        return mDatas.size
//    }
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        internal var image_app: ImageView
//        internal var tv_appname: TextView
//        internal var tv_appsize: TextView
//        internal var bt_check: Button
//        internal var checkBox: CheckBox
//
//        init {
//            image_app = itemView.findViewById<View>(R.id.itemImage) as ImageView
//            tv_appname = itemView.findViewById<View>(R.id.itemName) as TextView
//            tv_appsize = itemView.findViewById<View>(R.id.itemSize) as TextView
//            bt_check = itemView.findViewById<View>(R.id.bt_check) as Button
//            checkBox = itemView.findViewById<View>(R.id.checkbox) as CheckBox
//        }
//
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return mDatas[position].type
//    }
//
//    private fun belong(sendDatas: List<FileInfo>, pos: Int): Boolean {
//        val str = mDatas[pos].location
//        for (i in sendDatas.indices) {
//            if (str == sendDatas[i].location)
//                return true
//        }
//        return false
//    }
//
//    interface onSenDatasChangedListener {
//        fun onDataChanged(fileInfos: List<FileInfo>)
//    }
//
//    fun setmOnSenDatasChangedListener(mOnSenDatasChangedListener: onSenDatasChangedListener) {
//        this.mOnSenDatasChangedListener = mOnSenDatasChangedListener
//    }
//}