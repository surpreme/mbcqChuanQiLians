package com.mbcq.orderlibrary.activity.receipt.receiptcancel

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.orderlibrary.R

class ReceiptCancelAdapter(context: Context) : BaseRecyclerAdapter<ReceiptCancelBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_receipt_cancel, parent, false))


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).waybill_number_tv.text = mDatas[position].billno
        holder.state_tv.text = mDatas[position].cztype
        holder.waybill_time_tv.text = mDatas[position].czdate
        holder.information_tv.text = "${mDatas[position].czgs}  签收网点：${mDatas[position].czwebid}"
        holder.fixer_tv.text = "经办人：${mDatas[position].czman}"
        holder.record_checkbox_iv.visibility = if (position == mDatas.size-1) View.VISIBLE else View.INVISIBLE
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var waybill_number_tv = itemView.findViewById<TextView>(R.id.waybill_number_tv)
        var state_tv = itemView.findViewById<TextView>(R.id.state_tv)
        var waybill_time_tv = itemView.findViewById<TextView>(R.id.waybill_time_tv)
        var information_tv = itemView.findViewById<TextView>(R.id.information_tv)
        var fixer_tv = itemView.findViewById<TextView>(R.id.fixer_tv)
        var record_checkbox_iv = itemView.findViewById<ImageView>(R.id.record_checkbox_iv)
    }
}