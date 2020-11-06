package com.mbcq.vehicleslibrary.activity.shorttrunkdeparturescanoperating

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.vehicleslibrary.R

class ShortTrunkDepartureScanOperatingAdapter(context: Context) : BaseRecyclerAdapter<ShortTrunkDepartureScanOperatingBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_arrival_trunk_short_scan_operating, parent, false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).billno_tv.text = mDatas[position].billno
        holder.receiver_tv.text = mDatas[position].consignee
        holder.address_tv.text = "${mDatas[position].webidCodeStrDb}---${mDatas[position].ewebidCodeStrDb}"
        holder.goods_name_tv.text = mDatas[position].product
        holder.goods_number_ifo_tv.text ="已扫:x     本车:xxx    剩余:xx     总件数:${mDatas[position].totalQty}"
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var billno_tv = itemView.findViewById<TextView>(R.id.billno_tv)
        var receiver_tv = itemView.findViewById<TextView>(R.id.receiver_tv)
        var address_tv = itemView.findViewById<TextView>(R.id.address_tv)
        var goods_name_tv = itemView.findViewById<TextView>(R.id.goods_name_tv)
        var goods_number_ifo_tv = itemView.findViewById<TextView>(R.id.goods_number_ifo_tv)
    }


}