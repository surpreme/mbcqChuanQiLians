package com.mbcq.baselibrary.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.interfaces.OnClickInterface

abstract class BaseRecyclerAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected val context: Context?
    protected val inflater: LayoutInflater
    protected var mDatas = ArrayList<T>()
    open var mClickInterface: OnClickInterface.OnRecyclerClickInterface? = null

    constructor(context: Context?) {
        this.context = context
        this.inflater = LayoutInflater.from(context)
    }

    override fun getItemCount(): Int = mDatas.size
    fun appendData(list: List<T>) {
        mDatas.addAll(list)
        notifyDataSetChanged()
    }

    fun notifyItemChangeds(position: Int, data: T) {
        mDatas[position] = data
        notifyItemChanged(position)
//        notifyItemRangeChanged(position , position + 1)
    }

    fun getAllData(): ArrayList<T> {
        return mDatas
    }

   open fun removeItem(position: Int) {
        mDatas.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, mDatas.size - position)
    }


    fun clearData() {
        mDatas.clear()
        notifyDataSetChanged()
    }
}