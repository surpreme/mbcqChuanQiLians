package com.mbcq.amountlibrary.activity.generaledger

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.amountlibrary.R
import com.mbcq.baselibrary.view.BaseRecyclerAdapter

class GeneralLedgerAdapter(context: Context) : BaseRecyclerAdapter<GeneralLedgerBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_general_ledger, parent, false))


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).waybill_number_tv.text = mDatas[position].billno
        holder.waybill_time_tv.text = "签收时间:${mDatas[position].hkArrivedDate}"
        holder.shipper_outlets_tv.text = mDatas[position].webidCodeStr
        holder.receiver_outlets_tv.text = mDatas[position].ewebidCodeStr
        holder.shipper_tv.text = mDatas[position].shipper
        holder.receiver_tv.text = mDatas[position].consignee
        holder.loan_info_one_tv.text = "代收货款：${mDatas[position].accDaiShou} \n扣手续费：${mDatas[position].accDaiShou}  \n应付货款：${mDatas[position].accDaiShou}"//TODO
        holder.loan_info_two_tv.text = "货款变更：${mDatas[position].accHKChange} \n扣  运 费：${mDatas[position].accDaiShou}  \n实付货款：${mDatas[position].accDaiShou}"//TODO
        holder.information_tv.text = "开户名：${mDatas[position].bankMan}        开户行：${mDatas[position].bankName}  \n银行卡号：${mDatas[position].bankCode}"

    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var waybill_number_tv = itemView.findViewById<TextView>(R.id.waybill_number_tv)
        var waybill_time_tv = itemView.findViewById<TextView>(R.id.waybill_time_tv)
        var shipper_outlets_tv = itemView.findViewById<TextView>(R.id.shipper_outlets_tv)
        var receiver_outlets_tv = itemView.findViewById<TextView>(R.id.receiver_outlets_tv)
        var shipper_tv = itemView.findViewById<TextView>(R.id.shipper_tv)
        var receiver_tv = itemView.findViewById<TextView>(R.id.receiver_tv)
        var loan_info_one_tv = itemView.findViewById<TextView>(R.id.loan_info_one_tv)
        var loan_info_two_tv = itemView.findViewById<TextView>(R.id.loan_info_two_tv)
        var information_tv = itemView.findViewById<TextView>(R.id.information_tv)

    }

}