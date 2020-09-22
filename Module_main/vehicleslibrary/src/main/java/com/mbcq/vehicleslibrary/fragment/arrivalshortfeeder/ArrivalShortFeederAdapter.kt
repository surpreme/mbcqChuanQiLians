package com.mbcq.vehicleslibrary.fragment.arrivalshortfeeder

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.fragment.shortfeeder.ShortFeederAdapter
import com.mbcq.vehicleslibrary.fragment.shortfeeder.ShortFeederBean

class ArrivalShortFeederAdapter(context: Context?) : BaseRecyclerAdapter<ShortFeederBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_arrival_short_feeder, parent, false))
    interface OnArrivalConfirmInterface {
        fun onResult(position: Int, data: ShortFeederBean)
    }

    var mOnArrivalConfirmInterface: OnArrivalConfirmInterface? = null
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).departure_number_tv.text = mDatas[position].inoneVehicleFlag
        holder.transport_type_tv.text = mDatas[position].transneedStr
        holder.short_feeder_time_tv.text = mDatas[position].sendDate
        holder.shipper_outlets_tv.text = mDatas[position].webidCodeStr
        holder.receiver_outlets_tv.text = mDatas[position].ewebidCodeStr
        holder.feeder_state_tv.text = mDatas[position].vehicleStateStr
        holder.feeder_confirm_btn.visibility = if (mDatas[position].vehicleState == 1) View.VISIBLE else View.GONE
//        holder.feeder_checkbox_iv.visibility = if (mDatas[position].vehicleState == 1) View.INVISIBLE else View.VISIBLE
        /* context?.let {
             holder.feeder_checkbox_iv.setImageDrawable(ContextCompat.getDrawable(it, if (mDatas[position].isChecked) R.drawable.ic_checked_icon else R.drawable.ic_unchecked_icon))
         }
         holder.feeder_checkbox_iv.setOnClickListener(object : SingleClick() {
             override fun onSingleClick(v: View?) {
                 for (index in 0 until mDatas.size) {
                     if (index != position)
                         mDatas[index].isChecked = false
                     else
                         mDatas[index].isChecked = !mDatas[index].isChecked
                 }
                 notifyDataSetChanged()
             }

         })*/
        holder.feeder_confirm_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mOnArrivalConfirmInterface?.onResult(position, mDatas[position])
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
        var receiver_outlets_tv: TextView = itemView.findViewById(R.id.receiver_outlets_tv)
        var feeder_confirm_btn: Button = itemView.findViewById(R.id.feeder_confirm_btn)
    }
}