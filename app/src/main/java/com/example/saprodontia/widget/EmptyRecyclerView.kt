package com.xt.directoryfragment

import android.content.Context
import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import java.util.jar.Attributes

/**
 * Created by steve on 18-1-30.
 */
class EmptyRecyclerView(ctx:Context, attributeSet: AttributeSet) : RecyclerView(ctx,attributeSet){


    var emptyView : View? = null
        set(value) { field = value ; value?.alpha=0f}

    val observer : AdapterDataObserver = object : AdapterDataObserver(){
        override fun onChanged() {
            super.onChanged()
            checkIfEmpty()
        }

    }

    fun checkIfEmpty(){
        if(adapter.itemCount == 0 ){
            emptyView?.apply {
                animate().alpha(1f).setDuration(300).start()
            }
        }else{
            emptyView?.apply {
                animate().alpha(0f).setDuration(300).start()
            }
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        val oldAdapter : Adapter<*>? = getAdapter()
        oldAdapter?.unregisterAdapterDataObserver(observer)
        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(observer)

    }


}