package com.mbcq.vehicleslibrary.activity.alllocalagent.localgentshortfeederhouse

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.vehicleslibrary.R

class LocalGentShortFeederHouseLoadingAdapter(context: Context?) : BaseRecyclerAdapter<LocalGentShortFeederHouseBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_local_gent_short_bycar, parent, false))
    fun checkedAll(isC: Boolean) {
        for ((index, item) in mDatas.withIndex()) {
            item.isChecked = isC
            notifyItemChanged(index)

        }
    }

    var mOnRemoveInterface: OnRemoveInterface? = null

    interface OnRemoveInterface {
        fun onClick(position: Int, item: LocalGentShortFeederHouseBean)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).waybill_number_tv.text = mDatas[position].billno
        holder.waybill_number_tv.text = mDatas[position].billno
        holder.cargo_No_tv.text = mDatas[position].goodsNum
        holder.waybill_time_tv.text = mDatas[position].billDate
        holder.shipper_outlets_tv.text = mDatas[position].webidCodeStr
        holder.receiver_outlets_tv.text = mDatas[position].ewebidCodeStr
        holder.shipper_tv.text = mDatas[position].shipper
        holder.receiver_tv.text = mDatas[position].consignee
        holder.transit_company_info_tv.text = "${if (mDatas[position].outCygs.isNotBlank()) "${mDatas[position].outCygs}\n" else ""}${if (mDatas[position].outacc.isNotBlank()) "中转费${mDatas[position].outacc}" else ""}"
//        holder.transit_company_info_tv.text = /*if (mDatas[position].outCygs.isNotBlank()) "${mDatas[position].outCygs}" else "" +*/ if (mDatas[position].outacc.isNotBlank()) mDatas[position].outacc else ""
        holder.information_tv.text = "${mDatas[position].product} ${mDatas[position].qty}件 ${mDatas[position].volumn}m³ ${mDatas[position].packages} ${mDatas[position].weight}Kg ${mDatas[position].accTypeStr}${mDatas[position].accSum}  "

        context?.let {
            holder.record_checkbox_iv.setImageDrawable(ContextCompat.getDrawable(it, if (mDatas[position].isChecked) R.drawable.ic_checked_icon else R.drawable.ic_unchecked_icon))

        }
        holder.record_checkbox_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mDatas[position].isChecked = !mDatas[position].isChecked
                notifyItemChanged(position)
            }

        })
        holder.itemView.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View) {
                mClickInterface?.onItemClick(v, position, Gson().toJson(mDatas[position]))
            }

        })
        holder.waybill_move_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mOnRemoveInterface?.onClick(position, mDatas[position])
            }

        })

    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var waybill_number_tv: TextView = itemView.findViewById(R.id.waybill_number_tv)
        var record_checkbox_iv: ImageView = itemView.findViewById(R.id.record_checkbox_iv)
        var information_tv: TextView = itemView.findViewById(R.id.information_tv)
        var waybill_move_iv: ImageView = itemView.findViewById(R.id.waybill_move_iv)
        var cargo_No_tv: TextView = itemView.findViewById(R.id.cargo_No_tv)
        var waybill_time_tv: TextView = itemView.findViewById(R.id.waybill_time_tv)
        var shipper_outlets_tv: TextView = itemView.findViewById(R.id.shipper_outlets_tv)
        var receiver_outlets_tv: TextView = itemView.findViewById(R.id.receiver_outlets_tv)
        var shipper_tv: TextView = itemView.findViewById(R.id.shipper_tv)
        var receiver_tv: TextView = itemView.findViewById(R.id.receiver_tv)
        var transit_company_info_tv: TextView = itemView.findViewById(R.id.transit_company_info_tv)
    }
}