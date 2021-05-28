package com.mbcq.orderlibrary.activity.deliverysomethingmaphouse

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.commonlibrary.R

class DeliverySomethingMapHouseAdapter(context: Context) : BaseRecyclerAdapter<DeliverySomethingMapHouseBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.base_empty_imageview, parent, false))


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}