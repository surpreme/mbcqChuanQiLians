package com.mbcq.orderlibrary.activity.goodsreceipt

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.orderlibrary.R
import com.mbcq.orderlibrary.activity.waybillrecord.WaybillRecordAdapter

class GoodsReceiptAdapter(context: Context?) : BaseRecyclerAdapter<GoodsReceiptBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_goods_receipt, parent, false))
//    var mItemClickInteface: OnClickInterface.OnRecyclerClickInterface? = null

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).waybill_number_tv.text = mDatas[position].billno
        holder.waybill_time_tv.text = mDatas[position].billDate
        holder.shipper_outlets_tv.text = mDatas[position].webidCodeStr
        holder.receiver_outlets_tv.text = mDatas[position].ewebidCodeStr.toString()
        holder.destination_tv.text = mDatas[position].ewebidCodeStr.toString()
        holder.receiver_tv.text = "收货人：${mDatas[position].consignee}"
        holder.withdraw_tv.text = "提  付： ${mDatas[position].accFetch}"
        holder.payment_tv.text = "货   款：${mDatas[position].accDaiShou}元                    货款变更：${mDatas[position].accHKChange}元"
        holder.receivable_tv.text = "应   收：${mDatas[position].accSum}元"
        holder.remarks_tv.text = "备注：${mDatas[position].remark}"
        holder.itemView.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                ARouter.getInstance().build(ARouterConstants.GoodsReceiptInfoActivity).withString("GoodsReceiptBean",Gson().toJson(mDatas[position])).navigation()
            }

        })


    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var waybill_number_tv: TextView = itemView.findViewById(R.id.waybill_number_tv)
        var waybill_time_tv: TextView = itemView.findViewById(R.id.waybill_time_tv)
        var shipper_outlets_tv: TextView = itemView.findViewById(R.id.shipper_outlets_tv)
        var receiver_outlets_tv: TextView = itemView.findViewById(R.id.receiver_outlets_tv)
        var destination_tv: TextView = itemView.findViewById(R.id.destination_tv)
        var receiver_tv: TextView = itemView.findViewById(R.id.receiver_tv)
        var bag_fee_tv: TextView = itemView.findViewById(R.id.bag_fee_tv)
        var withdraw_tv: TextView = itemView.findViewById(R.id.withdraw_tv)
        var payment_tv: TextView = itemView.findViewById(R.id.payment_tv)
        var receivable_tv: TextView = itemView.findViewById(R.id.receivable_tv)
        var remarks_tv: TextView = itemView.findViewById(R.id.remarks_tv)
    }
}