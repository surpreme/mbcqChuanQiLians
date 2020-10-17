package com.mbcq.orderlibrary.activity.controlmanagement

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.orderlibrary.R

class ControlManagementAdapter(context: Context?) : BaseRecyclerAdapter<ControlManagementBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(inflater.inflate(R.layout.item_control_management, parent, false))
    }


    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).waybill_number_tv.text = mDatas[position].billno
        holder.waybill_number_tv.text = mDatas[position].billno
        holder.waybill_time_tv.text = mDatas[position].billDate
        holder.shipper_outlets_tv.text = mDatas[position].webidCodeStr
        holder.receiver_outlets_tv.text = mDatas[position].ewebidCodeStr
        holder.shipper_tv.text = mDatas[position].shipper
        holder.receiver_tv.text = mDatas[position].consignee

        holder.information_tv.text = "${mDatas[position].product} ${mDatas[position].qty}件 ${mDatas[position].volumn}m³ ${mDatas[position].packages} ${mDatas[position].weight}Kg ${mDatas[position].accTypeStr}${mDatas[position].accSum}  "
        holder.waybill_state_tv.text = mDatas[position].billStateStr
        context?.let {
            //TODO
            when (mDatas[position].isWaitNotice) {
                "0" -> {
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(it,R.color.white))
                    holder.control_type_tv.text ="看不到我"
                }
                "1" ->{
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(it,R.color.white))
                    holder.control_type_tv.text ="控货"
                }
                "2" -> {
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(it,R.color.base_un_green))
                    holder.control_type_tv.text ="已放货"
                }
                else ->{
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(it,R.color.white))
                    holder.control_type_tv.text ="唾弃"
                }
            }
            holder.record_checkbox_iv.setImageDrawable(ContextCompat.getDrawable(it, if (mDatas[position].checked) R.drawable.ic_checked_icon else R.drawable.ic_unchecked_icon))
        }
        holder.record_checkbox_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                for (index in 0 until mDatas.size) {
                    if (index != position)
                        mDatas[index].checked = false
                    else
                        mDatas[index].checked = !mDatas[index].checked
                }
                notifyDataSetChanged()
            }

        })

    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var waybill_number_tv: TextView = itemView.findViewById(R.id.waybill_number_tv)
        var record_checkbox_iv: ImageView = itemView.findViewById(R.id.record_checkbox_iv)
        var information_tv: TextView = itemView.findViewById(R.id.information_tv)
        var waybill_state_tv: TextView = itemView.findViewById(R.id.waybill_state_tv)
        var control_type_tv: TextView = itemView.findViewById(R.id.control_type_tv)
        var waybill_time_tv: TextView = itemView.findViewById(R.id.waybill_time_tv)
        var shipper_outlets_tv: TextView = itemView.findViewById(R.id.shipper_outlets_tv)
        var receiver_outlets_tv: TextView = itemView.findViewById(R.id.receiver_outlets_tv)
        var shipper_tv: TextView = itemView.findViewById(R.id.shipper_tv)
        var receiver_tv: TextView = itemView.findViewById(R.id.receiver_tv)

    }
}