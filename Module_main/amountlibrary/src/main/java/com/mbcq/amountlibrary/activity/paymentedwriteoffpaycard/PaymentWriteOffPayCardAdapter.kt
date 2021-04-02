package com.mbcq.amountlibrary.activity.paymentedwriteoffpaycard

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.mbcq.amountlibrary.R
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.view.BaseRecyclerAdapter

class PaymentWriteOffPayCardAdapter(context: Context) : BaseRecyclerAdapter<PaymentInfoBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_payment_write_off_pay_card, parent, false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).top_info_tv.text = "源  单 号：${mDatas[position].billno}       金      额：${mDatas[position].accArrivedAfter}"
        holder.bottom_info_tv.text = "摘要：${mDatas[position].summary} \n凭证编号：${mDatas[position].documentNo}"
        holder.itemView.apply {
            onSingleClicks {
                mClickInterface?.onItemClick(it,position,Gson().toJson(mDatas[position]))
            }
        }

    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var top_info_tv = itemView.findViewById<TextView>(R.id.top_info_tv)
        var bottom_info_tv = itemView.findViewById<TextView>(R.id.bottom_info_tv)

    }
}