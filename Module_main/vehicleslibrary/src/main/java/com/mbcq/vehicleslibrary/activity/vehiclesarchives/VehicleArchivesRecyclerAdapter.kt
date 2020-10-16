package com.mbcq.vehicleslibrary.activity.vehiclesarchives

import android.annotation.SuppressLint
import android.content.Context
import android.media.Image
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.vehicleslibrary.R

class VehicleArchivesRecyclerAdapter(context: Context?) : BaseRecyclerAdapter<VehicleArchivesBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_vehicle_archives, parent, false))


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).number_plate_tv.text = mDatas[position].vehicleno
        holder.vehicle_type_tv.text = if (mDatas[position].vehicletype == 1) "大车" else "小车"
        when (mDatas[position].vusetype) {
            1 -> {
                holder.vehicle_host_tv.text = "挂靠"
            }
            2 -> {
                holder.vehicle_host_tv.text = "合同"
            }
            3 -> {
                holder.vehicle_host_tv.text = "外雇"
            }
            4 -> {
                holder.vehicle_host_tv.text = "自有"
            }
            else->{
                holder.vehicle_host_tv.text = "未知"

            }
        }
        holder.date_sure_tv.text = "年审日期：${mDatas[position].yeachedate}"
        //        android:text="驾驶员：吴轩  15995675861  车主：林仙"
        holder.information_tv.text = "驾驶员：${mDatas[position].chauffer}  ${mDatas[position].chauffermb}   车主：${mDatas[position].vowner}"
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
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var number_plate_tv: TextView = itemView.findViewById(R.id.number_plate_tv)
        var vehicle_type_tv: TextView = itemView.findViewById(R.id.vehicle_type_tv)
        var vehicle_host_tv: TextView = itemView.findViewById(R.id.vehicle_host_tv)
        var information_tv: TextView = itemView.findViewById(R.id.information_tv)
        var date_sure_tv: TextView = itemView.findViewById(R.id.date_sure_tv)
        var record_checkbox_iv: ImageView = itemView.findViewById(R.id.record_checkbox_iv)
    }
}