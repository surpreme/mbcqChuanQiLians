package com.mbcq.vehicleslibrary.activity.alldeparturerecord.shipmentinventory

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.vehicleslibrary.R

class ShipmentInventoryAdapter(context: Context?) : BaseRecyclerAdapter<ShipmentInventoryBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_arrival_inventory, parent, false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).waybill_number_tv.text = mDatas[position].billno
        holder.waybill_number_tv.text = mDatas[position].billno
        holder.cargo_No_tv.text = mDatas[position].goodsNum
        holder.waybill_time_tv.text = mDatas[position].billDate
        holder.shipper_outlets_tv.text = mDatas[position].webidCodeStr
        holder.receiver_outlets_tv.text = mDatas[position].ewebidCodeStr
        holder.shipper_tv.text = mDatas[position].shipper
        holder.receiver_tv.text = mDatas[position].consignee
        holder.destination_tv.text = mDatas[position].destination
        holder.information_tv.text = "${mDatas[position].product} ${mDatas[position].qty}件 ${mDatas[position].volumn}m³ ${mDatas[position].packages} ${mDatas[position].weight}Kg ${mDatas[position].accTypeStr}${mDatas[position].accSum}  "
        holder.waybill_state_tv.text = mDatas[position].billStateStr
        holder.itemView.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View) {
                mClickInterface?.onItemClick(v, position, mDatas[position].billno)
            }

        })
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var destination_tv: TextView = itemView.findViewById(R.id.destination_tv)
        var waybill_number_tv: TextView = itemView.findViewById(R.id.waybill_number_tv)
        var record_checkbox_iv: ImageView = itemView.findViewById(R.id.record_checkbox_iv)
        var information_tv: TextView = itemView.findViewById(R.id.information_tv)
        var waybill_state_tv: TextView = itemView.findViewById(R.id.waybill_state_tv)
        var cargo_No_tv: TextView = itemView.findViewById(R.id.cargo_No_tv)
        var waybill_time_tv: TextView = itemView.findViewById(R.id.waybill_time_tv)
        var shipper_outlets_tv: TextView = itemView.findViewById(R.id.shipper_outlets_tv)
        var receiver_outlets_tv: TextView = itemView.findViewById(R.id.receiver_outlets_tv)
        var shipper_tv: TextView = itemView.findViewById(R.id.shipper_tv)
        var receiver_tv: TextView = itemView.findViewById(R.id.receiver_tv)
    }
}