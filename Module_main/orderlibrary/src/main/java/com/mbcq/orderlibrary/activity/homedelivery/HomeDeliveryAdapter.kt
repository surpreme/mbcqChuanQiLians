package com.mbcq.orderlibrary.activity.homedelivery

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.orderlibrary.R
import com.mbcq.orderlibrary.activity.acceptbillingrecording.AcceptBillingRecordingAdapter

class HomeDeliveryAdapter(context: Context) : BaseRecyclerAdapter<HomeDeliveryBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_accept_billing_record, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}