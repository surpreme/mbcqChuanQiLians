package com.mbcq.amountlibrary.fragment.schedulepaymentspending

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.amountlibrary.R
import com.mbcq.baselibrary.view.BaseRecyclerAdapter

class SchedulePaymentsPendingAdapter(context: Context) :BaseRecyclerAdapter<SchedulePaymentsPendingBean>(context){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_schedule_payments_pending, parent, false))


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}