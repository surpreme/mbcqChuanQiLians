package com.mbcq.amountlibrary.fragment.paymentconfirmation

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.amountlibrary.R
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick

class PaymentConfirmationAdapter(context: Context) : BaseRecyclerAdapter<PaymentConfirmationBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_payment_confirmation, parent, false))


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).waybill_number_tv.text = mDatas[position].billno
        holder.waybill_time_tv.text = mDatas[position].conRepDat
        holder.shipper_outlets_tv.text = mDatas[position].webidcodeStr
        holder.shipper_tv.text = mDatas[position].shipper
        holder.receiver_outlets_tv.text = mDatas[position].ewebidcodeStr
        holder.receiver_tv.text = "xxxx"
        holder.receiver_outlets_area_tv.text = mDatas[position].destination
        var showPrice = 0.00
        if (mDatas[position].accHKChange.startsWith("-")) {
            if (mDatas[position].accHKChange.length > 1)
                showPrice = mDatas[position].accdaishou - (mDatas[position].accHKChange.substring(1, mDatas[position].accHKChange.length)).toDouble()

        } else {
            showPrice = mDatas[position].accdaishou + (mDatas[position].accHKChange.toDouble())
        }
        holder.loan_info_tv.text = "代收货款：${mDatas[position].accdaishou}  货款变更：${mDatas[position].accHKChange}  实收货款：${showPrice}"
        context?.let {
            holder.record_checkbox_iv.setImageDrawable(ContextCompat.getDrawable(it, if (mDatas[position].isChecked) R.drawable.ic_checked_icon else R.drawable.ic_unchecked_icon))

        }
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
        var receiver_outlets_area_tv = itemView.findViewById<TextView>(R.id.receiver_outlets_area_tv)
        var loan_info_tv = itemView.findViewById<TextView>(R.id.loan_info_tv)
        var information_tv = itemView.findViewById<TextView>(R.id.information_tv)
        var record_checkbox_iv = itemView.findViewById<ImageView>(R.id.record_checkbox_iv)
    }
}