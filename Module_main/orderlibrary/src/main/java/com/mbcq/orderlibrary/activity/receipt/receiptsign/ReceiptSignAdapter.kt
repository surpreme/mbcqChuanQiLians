package com.mbcq.orderlibrary.activity.receipt.receiptsign

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.orderlibrary.R

class ReceiptSignAdapter(context: Context) : BaseRecyclerAdapter<ReceiptSignBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_receipt_sign, parent, false))


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).waybill_number_tv.text = mDatas[position].billno
        holder.waybill_time_tv.text = "开单日期：${mDatas[position].billDate}"
        holder.receipt_requirements_tv.text = "回单要求：${mDatas[position].backQty}"
        holder.shipper_outlets_tv.text = mDatas[position].webidCodeStr
        holder.receiver_outlets_tv.text = "xxx"
        holder.shipper_tv.text = mDatas[position].shipper
        holder.receiver_tv.text = mDatas[position].consignee
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var waybill_time_tv: TextView = itemView.findViewById(R.id.waybill_time_tv)
        var receipt_requirements_tv: TextView = itemView.findViewById(R.id.receipt_requirements_tv)
        var waybill_info_tv: TextView = itemView.findViewById(R.id.waybill_info_tv)
        var waybill_number_tv: TextView = itemView.findViewById(R.id.waybill_number_tv)
        var shipper_outlets_tv: TextView = itemView.findViewById(R.id.shipper_outlets_tv)
        var receiver_outlets_tv: TextView = itemView.findViewById(R.id.receiver_outlets_tv)
        var shipper_tv: TextView = itemView.findViewById(R.id.shipper_tv)
        var receiver_tv: TextView = itemView.findViewById(R.id.receiver_tv)
    }
}