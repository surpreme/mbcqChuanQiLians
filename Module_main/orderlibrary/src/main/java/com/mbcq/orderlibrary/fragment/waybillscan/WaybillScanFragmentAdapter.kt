package com.mbcq.orderlibrary.fragment.waybillscan

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.orderlibrary.R

class WaybillScanFragmentAdapter(context: Context) : BaseRecyclerAdapter<WaybillScanFragmentListBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_waybill_record_scan_info, parent, false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).item_text.text = mDatas[position].lableNo
//        holder.dismantle_info_tv.text = mDatas[position].mDismantleInfo
        holder.scan_inOneVehicleFlag_info_tv.text ="扫描网点：${mDatas[position].webidCodeStr}  ${mDatas[position].inOneVehicleFlag}"
        holder.scan_name_tv.text = "${mDatas[position].opeMan}  ${mDatas[position].scanTypeStr}  ${mDatas[position].scanOpeTypeStr}"
        holder.scan_time_tv.text = mDatas[position].recordDate
        holder.item_text.textSize = 16f
        holder.item_text.gravity = Gravity.CENTER_VERTICAL
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var item_text: TextView = itemView.findViewById(R.id.text)
        var scan_time_tv: TextView = itemView.findViewById(R.id.scan_time_tv)
        var scan_name_tv: TextView = itemView.findViewById(R.id.scan_name_tv)
        var scan_inOneVehicleFlag_info_tv: TextView = itemView.findViewById(R.id.scan_inOneVehicleFlag_info_tv)
        var scan_info_father_cl: ConstraintLayout = itemView.findViewById(R.id.scan_info_father_cl)
    }
}