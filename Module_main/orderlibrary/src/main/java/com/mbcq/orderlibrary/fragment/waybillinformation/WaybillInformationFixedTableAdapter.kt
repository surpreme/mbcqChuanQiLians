package com.mbcq.orderlibrary.fragment.waybillinformation

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.orderlibrary.R

class WaybillInformationFixedTableAdapter (context: Context):BaseRecyclerAdapter<WaybillInformationFixedTableBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_waybill_fixed_information_table, parent, false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).modify_content_tv.text = mDatas[position].contentStr
        holder.before_modify_content_tv.text = mDatas[position].beforeStr
        holder.end_modify_content_tv.text = mDatas[position].endStr
        holder.itemView.setBackgroundColor(if (mDatas[position].isTitles) Color.parseColor("#686C74")else  Color.parseColor("#F0EEEE"))
        context?.let {
            holder.modify_content_tv.setTextColor(it.resources.getColor(if (mDatas[position].isTitles) R.color.white else R.color.black))
            holder.before_modify_content_tv.setTextColor(it.resources.getColor(if (mDatas[position].isTitles) R.color.white else R.color.black))
            holder.end_modify_content_tv.setTextColor(it.resources.getColor(if (mDatas[position].isTitles) R.color.white else R.color.black))
        }
    }
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var modify_content_tv = itemView.findViewById<TextView>(R.id.modify_content_tv)//
        var before_modify_content_tv = itemView.findViewById<TextView>(R.id.before_modify_content_tv)//
        var end_modify_content_tv = itemView.findViewById<TextView>(R.id.end_modify_content_tv)//
        var line2 = itemView.findViewById<View>(R.id.line2)//
        var line1 = itemView.findViewById<View>(R.id.line1)//

    }
}