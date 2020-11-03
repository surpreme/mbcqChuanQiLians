package com.mbcq.orderlibrary.activity.acceptbilling.billingweightcalculator

import android.content.Context
import android.media.Image
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.orderlibrary.R

class BillingWeightCalculatorAdapter(context: Context) : BaseRecyclerAdapter<BillingWeightCalculatorBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_billing_weight_calculator, parent, false))
    var mOnRemoveInterface: OnRemoveInterface? = null

    interface OnRemoveInterface {
        fun onRemove(position: Int, result: String)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).qty_tv.text = mDatas[position].number
        holder.total_weight_tv.text = mDatas[position].totalWeight
        holder.weight_tv.text = mDatas[position].singleWeight
        holder.remove_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                removeItem(position)
                mOnRemoveInterface?.onRemove(position, "")
            }

        })
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var total_weight_tv = itemView.findViewById<TextView>(R.id.total_weight_tv)
        var remove_iv = itemView.findViewById<ImageView>(R.id.remove_iv)
        var weight_tv = itemView.findViewById<TextView>(R.id.weight_tv)
        var qty_tv = itemView.findViewById<TextView>(R.id.qty_tv)
    }
}