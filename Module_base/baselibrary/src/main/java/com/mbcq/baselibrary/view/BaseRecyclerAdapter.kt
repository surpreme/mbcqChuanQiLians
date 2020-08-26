package com.mbcq.baselibrary.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected val context: Context?
    protected val inflater: LayoutInflater
    protected var mDatas = ArrayList<T>()

    constructor(context: Context?) {
        this.context = context
        this.inflater = LayoutInflater.from(context)
    }

    override fun getItemCount(): Int = mDatas.size
    fun appendData(list: List<T>) {
        mDatas.addAll(list)
    }

}