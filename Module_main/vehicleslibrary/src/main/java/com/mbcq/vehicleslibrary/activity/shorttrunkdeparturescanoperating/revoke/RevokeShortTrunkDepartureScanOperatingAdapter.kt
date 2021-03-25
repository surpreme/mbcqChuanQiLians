package com.mbcq.vehicleslibrary.activity.shorttrunkdeparturescanoperating.revoke

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.vehicleslibrary.R

class RevokeShortTrunkDepartureScanOperatingAdapter (context: Context) : BaseRecyclerAdapter<RevokeShortTrunkDepartureScanOperatingBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_roke_arrival_trunk_short_scan_operating, parent, false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).billno_tv.text = mDatas[position].billno
        context?.let {
            holder.operating_progressbar.progressDrawable = ContextCompat.getDrawable(context, if (mDatas[position].unLoadQty == mDatas[position].totalQty) R.drawable.progress_indeterminate_green_horizontal else R.drawable.progress_indeterminate_horizontal)
        }
        holder.receiver_tv.text = mDatas[position].consignee
        holder.address_tv.text = "${mDatas[position].webidCodeStrDb}---${mDatas[position].ewebidCodeStrDb}"
        holder.goods_name_tv.text = mDatas[position].product
        holder.goods_number_ifo_tv.text = "已扫:${mDatas[position].unLoadQty}     本车:${mDatas[position].totalQty}    剩余:${mDatas[position].totalQty - mDatas[position].unLoadQty}     总件数:${mDatas[position].totalQty}"
        holder.operating_progressbar.progress = if (mDatas[position].unLoadQty == 0) 0 else if (mDatas[position].unLoadQty == mDatas[position].totalQty) 100 else ((mDatas[position].unLoadQty * 100) / mDatas[position].totalQty)

    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var operating_progressbar = itemView.findViewById<ProgressBar>(R.id.operating_progressbar)
        var billno_tv = itemView.findViewById<TextView>(R.id.billno_tv)
        var receiver_tv = itemView.findViewById<TextView>(R.id.receiver_tv)
        var address_tv = itemView.findViewById<TextView>(R.id.address_tv)
        var goods_name_tv = itemView.findViewById<TextView>(R.id.goods_name_tv)
        var goods_number_ifo_tv = itemView.findViewById<TextView>(R.id.goods_number_ifo_tv)
    }


}