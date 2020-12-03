package com.mbcq.orderlibrary.activity.acceptbilling.billingvolumecalculator

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.orderlibrary.R

class BillingVolumeCalculatorAdapter(context: Context) : BaseRecyclerAdapter<BillingVolumeCalculatorBean>(context) {
    var mOnRemoveInterface: OnRemoveInterface? = null

    interface OnRemoveInterface {
        fun onRemove(position: Int, result: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_billing_volume_calculator, parent, false))


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).singleLong_tv.text=mDatas[position].singleLong
        (holder as ItemViewHolder).singleWidth_tv.text=mDatas[position].singleWidth
        (holder as ItemViewHolder).singleHigh_tv.text=mDatas[position].singleHeight
        (holder as ItemViewHolder).number_tv.text=mDatas[position].number
        (holder as ItemViewHolder).totalWeight_tv.text=mDatas[position].totalWeight
        holder.remove_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                removeItem(position)
                mOnRemoveInterface?.onRemove(position, "")
            }

        })
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var remove_iv = itemView.findViewById<ImageView>(R.id.remove_iv)
        var singleLong_tv = itemView.findViewById<TextView>(R.id.singleLong_tv)
        var singleWidth_tv = itemView.findViewById<TextView>(R.id.singleWidth_tv)
        var singleHigh_tv = itemView.findViewById<TextView>(R.id.singleHigh_tv)
        var number_tv = itemView.findViewById<TextView>(R.id.number_tv)
        var totalWeight_tv = itemView.findViewById<TextView>(R.id.totalWeight_tv)

    }
}