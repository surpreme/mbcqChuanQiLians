package com.mbcq.orderlibrary.activity.stowagealongway

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.orderlibrary.R

class StowageAlongWayAdapter(context: Context) : BaseRecyclerAdapter<StowageAlongWayBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_stowage_along_way, parent, false))


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).waybill_departure_tv.text = mDatas[position].inoneVehicleFlag
        holder.waybill_time_tv.text = mDatas[position].arrivedDate
        holder.outlets_info_tv.text = mDatas[position].vehicleInterval
        holder.driver_info_tv.text = "${mDatas[position].vehicleNo}   ${mDatas[position].chauffer}   ${mDatas[position].chaufferMb}"
        holder.order_info_tv.text = "${mDatas[position].vehicleNo}   ${mDatas[position].chauffer}   ${mDatas[position].chaufferMb}"
        holder.itemView.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View) {
                mClickInterface?.onItemClick(v, position, Gson().toJson(mDatas[position]))
            }

        })
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var waybill_departure_tv = itemView.findViewById<TextView>(R.id.waybill_departure_tv)
        var waybill_time_tv = itemView.findViewById<TextView>(R.id.waybill_time_tv)
        var outlets_info_tv = itemView.findViewById<TextView>(R.id.outlets_info_tv)
        var driver_info_tv = itemView.findViewById<TextView>(R.id.driver_info_tv)
        var order_info_tv = itemView.findViewById<TextView>(R.id.order_info_tv)
    }
}