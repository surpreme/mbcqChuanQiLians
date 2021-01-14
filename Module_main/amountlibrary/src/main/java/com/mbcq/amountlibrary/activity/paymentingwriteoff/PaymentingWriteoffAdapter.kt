package com.mbcq.amountlibrary.activity.paymentingwriteoff

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.mbcq.amountlibrary.R
import com.mbcq.amountlibrary.activity.loanrecycle.LoanRecycleAdapter
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick

class PaymentingWriteoffAdapter(context: Context) : BaseRecyclerAdapter<PaymentingWriteoffBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_payment_write_off, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).waybill_number_tv.text = mDatas[position].billno
        holder.waybill_time_tv.text = mDatas[position].billDate
        holder.shipper_outlets_tv.text = mDatas[position].webidCodeStr
        holder.receiver_outlets_tv.text = mDatas[position].ewebidCodeStr
        holder.shipper_tv.text = mDatas[position].shipper
        holder.receiver_tv.text = mDatas[position].consignee
        holder.waybill_state_tv.text = mDatas[position].hxtype
        holder.phone_info_tv.text = "手机号  手机号"
        holder.unwrittened_ll.visibility = if (mDatas[position].hxtype == "未收未付") View.VISIBLE else View.GONE
        holder.writtened_ll.visibility = if (mDatas[position].hxtype == "未收未付") View.GONE else View.VISIBLE
        context?.let {
            holder.record_checkbox_iv.setImageDrawable(ContextCompat.getDrawable(it, if (mDatas[position].isChecked) R.drawable.ic_checked_icon else R.drawable.ic_unchecked_icon))

        }
        holder.itemView.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View) {
                mClickInterface?.onItemClick(v, position, Gson().toJson(mDatas[position]))
            }

        })
        holder.record_checkbox_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mDatas[position].isChecked = !mDatas[position].isChecked
                notifyItemChanged(position)
            }

        })
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
        var record_checkbox_iv = itemView.findViewById<ImageView>(R.id.record_checkbox_iv)
        var writtened_ll = itemView.findViewById<LinearLayout>(R.id.writtened_ll)
        var unwrittened_ll = itemView.findViewById<LinearLayout>(R.id.unwrittened_ll)
    }
}