package com.mbcq.orderlibrary.activity.choicereceiver

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.orderlibrary.R

class ChoiceReceiverAdapter(context: Context) :BaseRecyclerAdapter<ChoiceReceiverBean>(context){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_choice_receiver, parent, false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).basic_info_tv.text = "${mDatas[position].contactMan}    ${mDatas[position].contactMb}"
        holder.address_info_tv.text = "地址：${mDatas[position].address}"
        holder.company_info_tv.text = "公司：${mDatas[position].companyName}"
        holder.telphone_info_tv.text = "固定电话：${mDatas[position].contactTel}"
        holder.itemView.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View) {
                mClickInterface?.onItemClick(v, position, Gson().toJson(mDatas[position]))
            }

        })
    }
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var basic_info_tv = itemView.findViewById<TextView>(R.id.basic_info_tv)
        var address_info_tv = itemView.findViewById<TextView>(R.id.address_info_tv)
        var company_info_tv = itemView.findViewById<TextView>(R.id.company_info_tv)
        var telphone_info_tv = itemView.findViewById<TextView>(R.id.telphone_info_tv)
        var modify_info_tv = itemView.findViewById<ImageView>(R.id.modify_info_tv)
    }
}