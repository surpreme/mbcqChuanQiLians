package com.mbcq.orderlibrary.activity.acceptbillingrecording

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.orderlibrary.R

class AcceptBillingRecordingAdapter(context: Context?) : BaseRecyclerAdapter<AcceptBillingRecordingBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_accept_billing_record, parent, false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as ItemViewHolder).waybill_number_tv.text=mDatas[position].billno
        holder.waybill_time_tv.text=mDatas[position].yyCheckDate
        holder.applicant_tv.text="申请人：${mDatas[position].opeMan}"
        holder.apply_outlets_tv.text="申请网点：${mDatas[position].opeWebidCodeStr}"
        holder.modify_reason_tv.text="修改原因：${mDatas[position].updateRemark}"
        //修改内容
        holder.modify_content_tv.text= mDatas[position].updateContent
//        holder.modify_content_tv.text="申请网点：${mDatas[position].cwCheWebidCodeStr}"
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var waybill_number_tv = itemView.findViewById<TextView>(R.id.waybill_number_tv)
        var waybill_time_tv = itemView.findViewById<TextView>(R.id.waybill_time_tv)
        var applicant_tv = itemView.findViewById<TextView>(R.id.applicant_tv)
        var modify_content_tv = itemView.findViewById<TextView>(R.id.modify_content_tv)
        var modify_reason_tv = itemView.findViewById<TextView>(R.id.modify_reason_tv)
        var apply_outlets_tv = itemView.findViewById<TextView>(R.id.apply_outlets_tv)
        var review_btn = itemView.findViewById<Button>(R.id.review_btn)
    }
}