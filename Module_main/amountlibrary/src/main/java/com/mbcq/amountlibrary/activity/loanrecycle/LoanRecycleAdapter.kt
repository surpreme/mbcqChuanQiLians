package com.mbcq.amountlibrary.activity.loanrecycle

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.amountlibrary.R
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import kotlinx.android.synthetic.main.activity_loan_change.*

class LoanRecycleAdapter(context: Context) : BaseRecyclerAdapter<LoanRecycleBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_loan_change, parent, false))


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).waybill_number_tv.text = mDatas[position].billno
        holder.waybill_time_tv.text = mDatas[position].billDate
        holder.shipper_outlets_tv.text = mDatas[position].webidCodeStr
        holder.receiver_outlets_tv.text = mDatas[position].ewebidCodeStr
        holder.shipper_tv.text = mDatas[position].shipper
        holder.receiver_tv.text = mDatas[position].consignee
        holder.waybill_state_tv.text = mDatas[position].reportStateStr
        holder.phone_info_tv.text = "${mDatas[position].shipperMb}  ${mDatas[position].consigneeMb}"
        var showPrice = 0.00
        if (mDatas[position].accHKChange.startsWith("-")) {
            if (mDatas[position].accHKChange.length > 1)
                showPrice = mDatas[position].accDaiShou - (mDatas[position].accHKChange.substring(1, mDatas[position].accHKChange.length)).toDouble()

        } else {
            showPrice = mDatas[position].accDaiShou + (mDatas[position].accHKChange.toDouble())
        }
        holder.loan_info_tv.text = "代收货款：${mDatas[position].accDaiShou}  货款变更：${mDatas[position].accHKChange}  实收货款：${showPrice}"

    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var waybill_number_tv = itemView.findViewById<TextView>(R.id.waybill_number_tv)
        var waybill_time_tv = itemView.findViewById<TextView>(R.id.waybill_time_tv)
        var shipper_outlets_tv = itemView.findViewById<TextView>(R.id.shipper_outlets_tv)
        var receiver_outlets_tv = itemView.findViewById<TextView>(R.id.receiver_outlets_tv)
        var shipper_tv = itemView.findViewById<TextView>(R.id.shipper_tv)
        var receiver_tv = itemView.findViewById<TextView>(R.id.receiver_tv)
        var waybill_state_tv = itemView.findViewById<TextView>(R.id.waybill_state_tv)
        var phone_info_tv = itemView.findViewById<TextView>(R.id.phone_info_tv)
        var loan_info_tv = itemView.findViewById<TextView>(R.id.loan_info_tv)
        var writtened_ll = itemView.findViewById<LinearLayout>(R.id.writtened_ll)
        var unwrittened_ll = itemView.findViewById<LinearLayout>(R.id.unwrittened_ll)
    }
}