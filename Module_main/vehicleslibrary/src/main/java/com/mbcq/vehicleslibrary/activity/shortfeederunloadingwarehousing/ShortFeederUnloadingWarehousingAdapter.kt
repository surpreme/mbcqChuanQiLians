package com.mbcq.vehicleslibrary.activity.shortfeederunloadingwarehousing

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.vehicleslibrary.R

class ShortFeederUnloadingWarehousingAdapter(context: Context) : BaseRecyclerAdapter<ShortFeederUnloadingWarehousingBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_short_feeder_unloading_warehousing, parent, false))


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}