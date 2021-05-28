package com.mbcq.amountlibrary.activity.allwriteoffreceivepayment.commonwriteoffreceivepaycard

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

class CommonWriteOffReceivePayCardAdapter (context: Context) : BaseRecyclerAdapter<CommonWriteOffReceivePayCardInfoBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_payment_write_off_receive_pay_card, parent, false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).top_info_tv.text = "车 次 号：${mDatas[position].inoneVehicleFlag}  金    额：${mDatas[position].accArrivedAfter}"
        holder.center_info_tv.text = "司      机：${mDatas[position].chauffer}       车  牌  号：${mDatas[position].vehicleNo}"
        holder.bottom_info_tv.text = "摘要：${mDatas[position].summary} \n凭证编号：${mDatas[position].receiptNo}"
//        context?.resources?.getColor(R.color.base_blue)?.let { holder.itemView.se tBackgroundColor(it) }
        holder.itemView.apply {
            onSingleClicks {
                mClickInterface?.onItemClick(it,position, Gson().toJson(mDatas[position]))
            }
        }

    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var top_info_tv = itemView.findViewById<TextView>(R.id.top_info_tv)
        var bottom_info_tv = itemView.findViewById<TextView>(R.id.bottom_info_tv)
        var center_info_tv = itemView.findViewById<TextView>(R.id.center_info_tv)

    }
}