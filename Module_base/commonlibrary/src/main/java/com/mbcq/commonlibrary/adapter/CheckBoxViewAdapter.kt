package com.mbcq.commonlibrary.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.commonlibrary.R
class CheckBoxViewAdapter<T : BaseCheckedAdapterBean> : RecyclerView.Adapter<CheckBoxViewAdapter.ItemViewHolder> {
    private var context: Context?
    private val inflater: LayoutInflater
    private var mSonBean = ArrayList<T>()

    constructor(context: Context) {
        this.context = context
        this.inflater = LayoutInflater.from(context)
    }


    fun appendData(list: List<T>) {
        mSonBean.addAll(list)
        notifyDataSetChanged()
    }

    fun clearDatas() {
        mSonBean.clear()
        notifyDataSetChanged()
    }

    fun selectAll() {
        for (item in mSonBean) {
            item.checked = true
        }
        notifyDataSetChanged()
    }

    fun unSelectAll() {
        for (item in mSonBean) {
            item.checked = false
        }
        notifyDataSetChanged()
    }

    fun getDatas(): ArrayList<T> {
        return mSonBean
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var base_check_box: CheckBox = itemView.findViewById(R.id.base_check_box)


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(inflater.inflate(R.layout.base_checkbox, parent, false))
    }

    override fun getItemCount(): Int = mSonBean.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.setBackgroundColor(Color.WHITE)
        holder.base_check_box.text = mSonBean[position].title
        holder.base_check_box.textSize = 16f
        holder.base_check_box.isChecked = mSonBean[position].checked
        holder.base_check_box.setOnCheckedChangeListener { _, isChecked ->
            mSonBean[position].checked = isChecked
        }


    }
}