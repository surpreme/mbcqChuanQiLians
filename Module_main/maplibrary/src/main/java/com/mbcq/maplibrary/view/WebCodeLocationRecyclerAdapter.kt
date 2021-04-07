package com.mbcq.maplibrary.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.maplibrary.R

class WebCodeLocationRecyclerAdapter(context: Context) : BaseRecyclerAdapter<WebCodeLocationBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_webcode_location, parent, false))
    interface OnLocationInterface {
        fun onSelected(v: View, position: Int, result: String)
        fun onCall(v: View, position: Int, result: String)
    }

    var mOnLocationInterface: OnLocationInterface? = null

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).location_name_tv.text = "${position + 1} .${mDatas[position].webid}"
        holder.location_address_tv.text = "${mDatas[position].city} ${mDatas[position].county} ${mDatas[position].street}"
        holder.location_distance_tv.text = "${mDatas[position].distance}km"
        holder.call_information_tv.text = if (mDatas[position].webTel.isNullOrBlank()) mDatas[position].webMb else mDatas[position].webTel
        context?.let {
            holder.itemView.setBackgroundColor(it.resources.getColor(if (mDatas[position].isChecked) R.color.base_gray else R.color.white))

        }
        holder.itemView.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View) {
                mOnLocationInterface?.onSelected(v, position, Gson().toJson(mDatas[position]))
                /* for (index in 0 until mDatas.size) {
                     if (index != position)
                         mDatas[index].isChecked = false
                     else
                         mDatas[index].isChecked = !mDatas[index].isChecked
                 }
 //                mDatas[position].isChecked = !mDatas[position].isChecked
                 notifyDataSetChanged()*/

            }

        })
        holder.call_information_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View) {
                mOnLocationInterface?.onCall(v, position, Gson().toJson(mDatas[position]))

//                mOnCallInterface?.onItemClick(v, position, Gson().toJson(mDatas[position]))
            }

        })
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var location_name_tv = itemView.findViewById<TextView>(R.id.location_name_tv)
        var location_address_tv = itemView.findViewById<TextView>(R.id.location_address_tv)
        var location_distance_tv = itemView.findViewById<TextView>(R.id.location_distance_tv)
        var call_information_tv = itemView.findViewById<TextView>(R.id.call_information_tv)

    }
}