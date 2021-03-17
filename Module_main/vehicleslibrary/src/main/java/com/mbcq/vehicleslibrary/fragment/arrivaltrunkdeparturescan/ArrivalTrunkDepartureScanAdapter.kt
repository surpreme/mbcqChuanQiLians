package com.mbcq.vehicleslibrary.fragment.arrivaltrunkdeparturescan

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.vehicleslibrary.R

class ArrivalTrunkDepartureScanAdapter(context: Context) : BaseRecyclerAdapter<ArrivalTrunkDepartureScanBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_arrival_trunk_departure_scan, parent, false))

    var mSureArrivalClickInterface: OnClickInterface.OnRecyclerClickInterface? = null

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).departure_number_tv.text = mDatas[position].inoneVehicleFlag
        holder.transport_type_tv.text = mDatas[position].transneedStr
        holder.short_feeder_time_tv.text = mDatas[position].sendDate
        holder.shipper_outlets_tv.text = mDatas[position].webidCodeStr
        holder.receiver_outlets_tv.text = mDatas[position].ewebidCodeStr
        holder.feeder_state_tv.text = mDatas[position].vehicleStateStr
        holder.feeder_confirm_btn.visibility = if (mDatas[position].vehicleState == 1) View.VISIBLE else View.GONE
        holder.scan_rate_tv.visibility = if (mDatas[position].vehicleState != 1) View.VISIBLE else View.GONE
        holder.operating_progressbar.visibility = if (mDatas[position].vehicleState != 1) View.VISIBLE else View.GONE
        holder.operating_progressbar.progress = if (mDatas[position].xcScanPercentage.isNullOrEmpty()) 0 else mDatas[position].xcScanPercentage.toInt()
        holder.scan_rate_tv.text = "扫描率：${if (mDatas[position].xcScanPercentage.isNullOrEmpty()) "0" else mDatas[position].xcScanPercentage}%"
        holder.feeder_confirm_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View) {
                mSureArrivalClickInterface?.onItemClick(v, position, Gson().toJson(mDatas[position]))
            }

        })

        holder.itemView.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View) {
                mClickInterface?.onItemClick(v, position, Gson().toJson(mDatas[position]))
            }

        })

    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        var feeder_checkbox_iv: ImageView = itemView.findViewById(R.id.feeder_checkbox_iv)
        var departure_number_tv: TextView = itemView.findViewById(R.id.departure_number_tv)
        var transport_type_tv: TextView = itemView.findViewById(R.id.transport_type_tv)
        var short_feeder_time_tv: TextView = itemView.findViewById(R.id.short_feeder_time_tv)
        var shipper_outlets_tv: TextView = itemView.findViewById(R.id.shipper_outlets_tv)
        var feeder_state_tv: TextView = itemView.findViewById(R.id.feeder_state_tv)
        var scan_rate_tv: TextView = itemView.findViewById(R.id.scan_rate_tv)
        var receiver_outlets_tv: TextView = itemView.findViewById(R.id.receiver_outlets_tv)
        var feeder_confirm_btn: Button = itemView.findViewById(R.id.feeder_confirm_btn)
        var operating_progressbar: ProgressBar = itemView.findViewById(R.id.operating_progressbar)
    }
}