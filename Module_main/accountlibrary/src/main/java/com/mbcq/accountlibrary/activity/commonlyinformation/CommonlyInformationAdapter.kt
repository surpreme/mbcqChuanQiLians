package com.mbcq.accountlibrary.activity.commonlyinformation

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.accountlibrary.R
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick

class CommonlyInformationAdapter(context: Context) : BaseRecyclerAdapter<CommonlyInformationBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_commonly_information, parent, false))


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).commonly_name_tv.text = mDatas[position].title
        (holder as ItemViewHolder).commonly_content_tv.text = mDatas[position].contentStr
        holder.itemView.setOnClickListener(object :SingleClick(){
            override fun onSingleClick(v: View) {
                mClickInterface?.onItemClick(v,position,mDatas[position].title)
            }

        })

    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var commonly_name_tv = itemView.findViewById<TextView>(R.id.commonly_name_tv)
        var commonly_content_tv = itemView.findViewById<TextView>(R.id.commonly_content_tv)
    }
}