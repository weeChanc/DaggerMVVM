package com.example.saprodontia.Adapter

import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by steve on 17-11-30.
 */
abstract class AbstractAdapter<T>(var mDatas : MutableList<T>,@LayoutRes private val id : Int) : RecyclerView.Adapter<AbstractAdapter.Holder>() {

    override fun getItemCount(): Int {
        return mDatas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(id,parent,false)
        val holder = Holder(view)

        view.setOnClickListener {
            if(holder.adapterPosition != RecyclerView.NO_POSITION){
                onItemClick(view,holder.adapterPosition)
            }
        }

        return holder
    }

    fun update(items: List<T>) {
        updateAdapterWithDiffResult(calculateDiff(items))
    }

    private fun updateAdapterWithDiffResult(result: DiffUtil.DiffResult) {
        result.dispatchUpdatesTo(this)
    }

    private fun calculateDiff(newItems: List<T>) =
            DiffUtil.calculateDiff(DiffUtilCallback(mDatas, newItems))


    fun clear(){
        mDatas.clear()
        notifyItemRangeRemoved(0,mDatas.size)
    }

    fun removeAt(pos: Int){
        mDatas.removeAt(pos)
        notifyItemRemoved(pos)
    }

    fun add( datas : MutableList<T>){
        val start = mDatas.size - 1
        mDatas.addAll(datas)
        notifyItemRangeInserted(start , datas.size)
    }

    fun add( data : T , pos: Int){
        mDatas.add(pos,data)
        notifyItemInserted(pos)
    }


    @CallSuper
    override fun onBindViewHolder(holder: Holder, position: Int) {
        Log.e("AbstractAdapter","onbind")
        holder.itemView.bind(mDatas[holder.adapterPosition])
    }

    abstract fun View.bind(item:T)

    open protected fun onItemClick(view : View , pos : Int){

    }

    open class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    internal class DiffUtilCallback<T>(private val oldItems: List<T>,
                                       private val newItems: List<T>) : DiffUtil.Callback() {

        override fun getOldListSize() = oldItems.size

        override fun getNewListSize() = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                oldItems[oldItemPosition] == newItems[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                oldItems[oldItemPosition] == newItems[newItemPosition]
    }

}