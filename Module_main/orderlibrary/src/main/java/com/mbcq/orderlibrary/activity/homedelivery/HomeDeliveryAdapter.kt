package com.mbcq.orderlibrary.activity.homedelivery

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.orderlibrary.R
import com.mbcq.orderlibrary.activity.acceptbillingrecording.AcceptBillingRecordingAdapter

class HomeDeliveryAdapter(context: Context) : BaseRecyclerAdapter<HomeDeliveryBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_home_delivery_record, parent, false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).delete_delivery_btn.setOnClickListener(object :SingleClick(){
            override fun onSingleClick(v: View?) {

            }

        })
        holder.complete_delivery_btn.setOnClickListener(object :SingleClick(){
            override fun onSingleClick(v: View?) {

            }

        })
        holder.vehicle_number_tv.text=mDatas[position].inOneVehicleFlag
        holder.waybill_time_tv.text=mDatas[position].pickUpDate
        holder.vehicle_info_tv.text="${mDatas[position].vehicleNo} ${mDatas[position].chauffer} ${mDatas[position].chaufferTel}"
        holder.delivery_fee_content_tv.text=mDatas[position].accPickUp
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var vehicle_number_tv=itemView.findViewById<TextView>(R.id.vehicle_number_tv)
        var waybill_time_tv=itemView.findViewById<TextView>(R.id.waybill_time_tv)
        var vehicle_info_tv=itemView.findViewById<TextView>(R.id.vehicle_info_tv)
        var delivery_fee_content_tv=itemView.findViewById<TextView>(R.id.delivery_fee_content_tv)
        var complete_delivery_btn=itemView.findViewById<Button>(R.id.complete_delivery_btn)
        var delete_delivery_btn=itemView.findViewById<Button>(R.id.delete_delivery_btn)
    }
}