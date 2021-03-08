package com.mbcq.vehicleslibrary.fragment.arrivaltrunkdeparture

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.fragment.shortfeeder.ShortFeederAdapter
import com.mbcq.vehicleslibrary.fragment.shortfeeder.ShortFeederBean
import com.mbcq.vehicleslibrary.fragment.trunkdeparture.TrunkDepartureBean

class ArrivalTrunkDepartureAdapter(context: Context?) : BaseRecyclerAdapter<TrunkDepartureBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_arrival_trunk_departure, parent, false))
    interface OnArrivalConfirmInterface {
        fun onResult(position: Int, data: TrunkDepartureBean)
        fun onCancel(position: Int, data: TrunkDepartureBean)
        fun onUnloadingWarehousing(position: Int, data: TrunkDepartureBean)
        fun onItemClick(position: Int, data: TrunkDepartureBean)
    }

    var mOnArrivalConfirmInterface: OnArrivalConfirmInterface? = null
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).departure_number_tv.text = mDatas[position].inoneVehicleFlag
        holder.transport_type_tv.text = mDatas[position].transneedStr
        holder.short_feeder_time_tv.text = mDatas[position].sendDate
        holder.outlets_info_tv.text = mDatas[position].vehicleInterval
        holder.feeder_state_tv.text = mDatas[position].vehicleStateStr
        holder.vehicler_info_tv.text = "${mDatas[position].vehicleNo}  ${mDatas[position].chauffer}  ${mDatas[position].chaufferMb}"
        //车辆状态0代表发车计划中 1代表发货2代表到货 3到货处理结束
        holder.feeder_confirm_btn.visibility = if (mDatas[position].vehicleState == 1) View.VISIBLE else View.GONE
        holder.overed_car_ll.visibility = if (mDatas[position].vehicleState == 2) View.VISIBLE else View.GONE
        holder.feeder_confirm_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mOnArrivalConfirmInterface?.onResult(position, mDatas[position])
            }

        })
        holder.feeder_cancel_confirm_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mOnArrivalConfirmInterface?.onCancel(position, mDatas[position])
            }

        })
        holder.itemView.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mOnArrivalConfirmInterface?.onItemClick(position, mDatas[position])
            }

        })
        holder.unloading_warehousing_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mOnArrivalConfirmInterface?.onUnloadingWarehousing(position, mDatas[position])
            }

        })
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var departure_number_tv: TextView = itemView.findViewById(R.id.departure_number_tv)
        var transport_type_tv: TextView = itemView.findViewById(R.id.transport_type_tv)
        var short_feeder_time_tv: TextView = itemView.findViewById(R.id.short_feeder_time_tv)
        var feeder_state_tv: TextView = itemView.findViewById(R.id.feeder_state_tv)
        var feeder_confirm_btn: TextView = itemView.findViewById(R.id.feeder_confirm_btn)
        var outlets_info_tv: TextView = itemView.findViewById(R.id.outlets_info_tv)
        var overed_car_ll: LinearLayout = itemView.findViewById(R.id.overed_car_ll)
        var feeder_cancel_confirm_btn: Button = itemView.findViewById(R.id.feeder_cancel_confirm_btn)
        var unloading_warehousing_btn: Button = itemView.findViewById(R.id.unloading_warehousing_btn)
        var vehicler_info_tv: TextView = itemView.findViewById(R.id.vehicler_info_tv)

    }
}