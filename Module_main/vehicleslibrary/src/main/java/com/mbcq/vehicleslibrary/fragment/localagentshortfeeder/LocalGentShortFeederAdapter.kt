package com.mbcq.vehicleslibrary.fragment.localagentshortfeeder

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

class LocalGentShortFeederAdapter(context: Context?) : BaseRecyclerAdapter<LocalGentShortFeederBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_local_short_feeder, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).departure_number_tv.text = mDatas[position].agentBillno
        holder.short_feeder_time_tv.text = mDatas[position].agentDate
        context?.let {
            holder.feeder_checkbox_iv.setImageDrawable(ContextCompat.getDrawable(context, if (mDatas[position].isChecked) R.drawable.ic_checked_icon else R.drawable.ic_unchecked_icon))

        }
        holder.feeder_checkbox_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                for (index in 0 until mDatas.size){
                    if (index!=position)
                        mDatas[index].isChecked=false
                    else
                        mDatas[index].isChecked = !mDatas[index].isChecked
                }
//                mDatas[position].isChecked = !mDatas[position].isChecked
                notifyDataSetChanged()
            }

        })
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var feeder_checkbox_iv: ImageView = itemView.findViewById(R.id.feeder_checkbox_iv)
        var departure_number_tv: TextView = itemView.findViewById(R.id.departure_number_tv)
        var short_feeder_time_tv: TextView = itemView.findViewById(R.id.short_feeder_time_tv)

    }
}