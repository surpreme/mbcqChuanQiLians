package com.mbcq.orderlibrary.activity.deliverysomething

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

class DeliverySomeThingAdapter(context: Context?) : BaseRecyclerAdapter<DeliverySomeThingBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_delivety_something, parent, false))


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).waybill_number_tv.text = mDatas[position].sendInOneFlag
        holder.waybill_number_tv.text = mDatas[position].sendInOneFlag
        holder.waybill_time_tv.text = mDatas[position].sendDate
        context?.let {
            holder.record_checkbox_iv.setImageDrawable(ContextCompat.getDrawable(it, if (mDatas[position].isChecked) R.drawable.ic_checked_icon else R.drawable.ic_unchecked_icon))
        }
        holder.record_checkbox_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                for (index in 0 until mDatas.size) {
                    if (index != position)
                        mDatas[index].isChecked = false
                    else
                        mDatas[index].isChecked = !mDatas[index].isChecked
                }
                notifyDataSetChanged()
            }

        })
        holder.vehicle_info_tv.text = "${mDatas[position].sendVehicleNo}  ${mDatas[position].sendMan}  ${mDatas[position].sendManMb}  "

    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var waybill_number_tv: TextView = itemView.findViewById(R.id.waybill_number_tv)
        var waybill_time_tv: TextView = itemView.findViewById(R.id.waybill_time_tv)
        var vehicle_info_tv: TextView = itemView.findViewById(R.id.vehicle_info_tv)
        var information_tv: TextView = itemView.findViewById(R.id.information_tv)
        var record_checkbox_iv: ImageView = itemView.findViewById(R.id.record_checkbox_iv)
    }
}