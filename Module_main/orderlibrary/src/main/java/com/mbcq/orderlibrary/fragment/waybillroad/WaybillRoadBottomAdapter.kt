package com.mbcq.orderlibrary.fragment.waybillroad

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.orderlibrary.R
import com.mbcq.orderlibrary.activity.goodsreceipt.GoodsReceiptAdapter

class WaybillRoadBottomAdapter(context: Context?) : BaseRecyclerAdapter<WaybillRoadBottomBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_father_waybill_road_bottom, parent, false))


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}