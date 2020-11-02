package com.mbcq.vehicleslibrary.activity.arrivaltrunkdeparturescanoperating

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.vehicleslibrary.R

class ArrivalTrunkDepartureScanOperatingAdapter(context: Context) : BaseRecyclerAdapter<ArrivalTrunkDepartureScanOperatingBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_arrival_trunk_departure_scan_operating, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var billno_tv=itemView.findViewById<TextView>(R.id.billno_tv)
        var receiver_tv=itemView.findViewById<TextView>(R.id.receiver_tv)
        var address_tv=itemView.findViewById<TextView>(R.id.address_tv)
        var goods_name_tv=itemView.findViewById<TextView>(R.id.goods_name_tv)
        var goods_number_ifo_tv=itemView.findViewById<TextView>(R.id.goods_number_ifo_tv)
    }


}