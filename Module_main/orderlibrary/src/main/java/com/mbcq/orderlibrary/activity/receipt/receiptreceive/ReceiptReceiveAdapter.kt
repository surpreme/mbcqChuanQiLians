package com.mbcq.orderlibrary.activity.receipt.receiptreceive

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.orderlibrary.R

class ReceiptReceiveAdapter(context: Context) : BaseRecyclerAdapter<ReceiptReceiveBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_receipt_receive, parent, false))

    fun checkedAll(isC: Boolean) {
        for ((index, item) in mDatas.withIndex()) {
            item.isChecked = isC
            notifyItemChanged(index)

        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).waybill_number_tv.text = mDatas[position].billno
        holder.waybill_time_tv.text = "开单日期：${mDatas[position].sendOutDate}"
        holder.receipt_requirements_tv.text = "回单要求：${mDatas[position].backQty}"
        holder.shipper_outlets_tv.text = mDatas[position].webidCodeStr
        holder.receiver_outlets_tv.text = "xxx"
        holder.shipper_tv.text = mDatas[position].shipper
        holder.receiver_tv.text = mDatas[position].consignee
        context?.let {
            holder.record_checkbox_iv.setImageDrawable(ContextCompat.getDrawable(it, if (mDatas[position].isChecked) R.drawable.ic_checked_icon else R.drawable.ic_unchecked_icon))

        }
        holder.record_checkbox_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mDatas[position].isChecked = !mDatas[position].isChecked
                notifyItemChanged(position)
            }

        })
        holder.itemView.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View) {
                mClickInterface?.onItemClick(v, position, mDatas[position].billno)
            }

        })
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var waybill_time_tv: TextView = itemView.findViewById(R.id.waybill_time_tv)
        var receipt_requirements_tv: TextView = itemView.findViewById(R.id.receipt_requirements_tv)
        var waybill_info_tv: TextView = itemView.findViewById(R.id.waybill_info_tv)
        var record_checkbox_iv: ImageView = itemView.findViewById(R.id.record_checkbox_iv)
        var waybill_number_tv: TextView = itemView.findViewById(R.id.waybill_number_tv)
        var shipper_outlets_tv: TextView = itemView.findViewById(R.id.shipper_outlets_tv)
        var receiver_outlets_tv: TextView = itemView.findViewById(R.id.receiver_outlets_tv)
        var shipper_tv: TextView = itemView.findViewById(R.id.shipper_tv)
        var receiver_tv: TextView = itemView.findViewById(R.id.receiver_tv)
    }
}