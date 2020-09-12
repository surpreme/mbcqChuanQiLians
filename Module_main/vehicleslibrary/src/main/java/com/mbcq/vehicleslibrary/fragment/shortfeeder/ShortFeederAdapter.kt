package com.mbcq.vehicleslibrary.fragment.shortfeeder

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
import com.mbcq.vehicleslibrary.R

class ShortFeederAdapter(context: Context?) : BaseRecyclerAdapter<ShortFeederBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_short_feeder, parent, false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).departure_number_tv.text = mDatas[position].inoneVehicleFlag
        holder.transport_type_tv.text = mDatas[position].transneedStr
        holder.short_feeder_time_tv.text = mDatas[position].sendDate
        holder.shipper_outlets_tv.text = mDatas[position].webidCodeStr
        holder.receiver_outlets_tv.text = mDatas[position].ewebidCodeStr
        holder.feeder_state_tv.text = mDatas[position].vehicleStateStr
        holder.vehicler_info_tv.text = "${mDatas[position].vehicleNo} ${mDatas[position].chauffer} ${mDatas[position].chaufferMb}"
        context?.let {
            holder.feeder_checkbox_iv.setImageDrawable(ContextCompat.getDrawable(it, if (mDatas[position].isChecked) R.drawable.ic_checked_icon else R.drawable.ic_unchecked_icon))
        }
        holder.feeder_checkbox_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mDatas[position].isChecked = !mDatas[position].isChecked
                notifyItemChanged(position)
            }

        })
//        holder.information_tv.text = "${mDatas[position].vehicleNo} ${mDatas[position].chauffer} ${mDatas[position].chaufferMb}"
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var feeder_checkbox_iv: ImageView = itemView.findViewById(R.id.feeder_checkbox_iv)
        var departure_number_tv: TextView = itemView.findViewById(R.id.departure_number_tv)
        var transport_type_tv: TextView = itemView.findViewById(R.id.transport_type_tv)
        var short_feeder_time_tv: TextView = itemView.findViewById(R.id.short_feeder_time_tv)
        var shipper_outlets_tv: TextView = itemView.findViewById(R.id.shipper_outlets_tv)
        var vehicler_info_tv: TextView = itemView.findViewById(R.id.vehicler_info_tv)
        var feeder_state_tv: TextView = itemView.findViewById(R.id.feeder_state_tv)
        var information_tv: TextView = itemView.findViewById(R.id.information_tv)
        var modify_tv: TextView = itemView.findViewById(R.id.modify_tv)
        var receiver_outlets_tv: TextView = itemView.findViewById(R.id.receiver_outlets_tv)
    }
}