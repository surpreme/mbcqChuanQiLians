package com.mbcq.vehicleslibrary.activity.shorttrunkdeparturescanoperatingmoreinfo

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.vehicleslibrary.R

class ShortTrunkDepartureScanOperatingMoreInfoAdapter(context: Context) : BaseRecyclerAdapter<ShortTrunkDepartureScanOperatingMoreInfoBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_short_feeder_scan_info, parent, false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        holder.itemView.setBackgroundColor(Color.WHITE)
        (holder as ItemViewHolder).item_text.text = mDatas[position].lableNo
        holder.dismantle_info_tv.text = mDatas[position].mDismantleInfo
        holder.scan_inOneVehicleFlag_info_tv.text = mDatas[position].mScanInOneVehicleFlag
        holder.scan_name_tv.text = "${mDatas[position].scanName}  ${mDatas[position].scanTypeStr}"
        holder.scan_time_tv.text = mDatas[position].time
        holder.item_text.textSize = 16f
        holder.item_text.gravity = Gravity.CENTER_VERTICAL
        holder.scan_info_father_cl.setBackgroundResource(if (mDatas[position].isScaned) R.color.base_scan_background_green else R.drawable.hollow_out_gray)
        holder.itemView.setOnClickListener {
            mClickInterface?.onItemClick(it, position, mDatas[position].mResultTag)
        }


    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var item_text: TextView = itemView.findViewById(R.id.text)
        var dismantle_info_tv: TextView = itemView.findViewById(R.id.dismantle_info_tv)
        var scan_time_tv: TextView = itemView.findViewById(R.id.scan_time_tv)
        var scan_name_tv: TextView = itemView.findViewById(R.id.scan_name_tv)
        var scan_inOneVehicleFlag_info_tv: TextView = itemView.findViewById(R.id.scan_inOneVehicleFlag_info_tv)
        var scan_info_father_cl: ConstraintLayout = itemView.findViewById(R.id.scan_info_father_cl)

    }
}