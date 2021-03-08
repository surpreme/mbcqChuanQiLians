package com.mbcq.orderlibrary.fragment.waybillroad

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.util.system.TimeUtils
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.orderlibrary.R
import java.lang.Exception

class WaybillRoadBottomAdapter(context: Context?) : BaseRecyclerAdapter<WaybillRoadBottomBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_father_waybill_road_bottom, parent, false))


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (mDatas[position].content.contains("受理开单")) {
            (holder as ItemViewHolder).father_state_content_tv.text = "受理开单"
            context?.let {
                holder.father_state_tag_iv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_accept_billing_icon))
            }
        } else if (mDatas[position].content.contains("装车")) {
            (holder as ItemViewHolder).father_state_content_tv.text = "装车"
            context?.let {
                holder.father_state_tag_iv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_warehouse_icon))
            }
        } else if (mDatas[position].content.contains("卸车入库")) {
            (holder as ItemViewHolder).father_state_content_tv.text = "卸车入库"
            context?.let {
                holder.father_state_tag_iv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_transiting_icon))
            }
        } else {
            (holder as ItemViewHolder).father_state_content_tv.text = "未知进度"
            context?.let {
                holder.father_state_tag_iv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_transiting_icon))
            }
        }
        holder.father_state_content_remark_tv.text = mDatas[position].content
        try {
            holder.father_state_day_tv.text = TimeUtils.stampToDate1((TimeUtils.dateToStamp(mDatas[position].opeDate.replace("T", " "))).toLong())
            holder.father_state_hour_tv.text = TimeUtils.stampToDate3((TimeUtils.dateToStamp(mDatas[position].opeDate.replace("T", " "))).toLong())

        } catch (e: Exception) {
            e.printStackTrace()
        }

//        ToastUtils.showToast(context, TimeUtils.dateToStamp(mDatas[position].opeDate.replace("T", " ")))
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var father_state_day_tv = itemView.findViewById<TextView>(R.id.father_state_day_tv)
        var father_state_hour_tv = itemView.findViewById<TextView>(R.id.father_state_hour_tv)
        var father_state_content_tv = itemView.findViewById<TextView>(R.id.father_state_content_tv)
        var father_state_content_remark_tv = itemView.findViewById<TextView>(R.id.father_state_content_remark_tv)
        var father_state_tag_iv = itemView.findViewById<ImageView>(R.id.father_state_tag_iv)
    }
}