package com.mbcq.amountlibrary.fragment.schedulepaymentspending

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.amountlibrary.R
import com.mbcq.amountlibrary.activity.generaledger.GeneralLedgerAdapter
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick

class SchedulePaymentsPendingAdapter(context: Context) : BaseRecyclerAdapter<SchedulePaymentsPendingBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_schedule_payments_pending, parent, false))


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).waybill_number_tv.text = mDatas[position].billno
        holder.waybill_time_tv.text = "签收时间:${mDatas[position].fetchdate}"
        holder.shipper_outlets_tv.text = mDatas[position].webidCodeStr
        holder.receiver_outlets_tv.text = mDatas[position].ewebidCodeStr
        holder.shipper_tv.text = mDatas[position].shipper
        holder.receiver_tv.text = mDatas[position].consignee
        holder.loan_info_one_tv.text = "代收货款：${mDatas[position].accDaiShou} \n扣手续费：${mDatas[position].accDaiShou}  \n应付货款：${mDatas[position].accDaiShou}"//TODO
        holder.loan_info_two_tv.text = "货款变更：${mDatas[position].accHKChange} \n扣  运 费：${mDatas[position].accDaiShou}  \n实付货款：${mDatas[position].accDaiShou}"//TODO
        holder.information_tv.text = "开户名：${mDatas[position].bankMan}        开户行：${mDatas[position].bankName}  \n银行卡号：${mDatas[position].bankCode}"
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
        var waybill_state_tv = itemView.findViewById<TextView>(R.id.waybill_state_tv)
        var shipper_tv = itemView.findViewById<TextView>(R.id.shipper_tv)
        var receiver_tv = itemView.findViewById<TextView>(R.id.receiver_tv)
        var loan_info_one_tv = itemView.findViewById<TextView>(R.id.loan_info_one_tv)
        var loan_info_two_tv = itemView.findViewById<TextView>(R.id.loan_info_two_tv)
        var information_tv = itemView.findViewById<TextView>(R.id.information_tv)
        var record_checkbox_iv = itemView.findViewById<ImageView>(R.id.record_checkbox_iv)

    }
}