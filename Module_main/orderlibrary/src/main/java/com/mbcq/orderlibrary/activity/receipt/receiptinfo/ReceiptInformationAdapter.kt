package com.mbcq.orderlibrary.activity.receipt.receiptinfo

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.commonlibrary.adapter.BaseTextAdapterBean
import com.mbcq.orderlibrary.R
import org.w3c.dom.Text

class ReceiptInformationAdapter(context: Context) : BaseRecyclerAdapter<ReceiptInformationBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_receipt_information, parent, false))


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).sign_info_tv.text = mDatas[position].informationStr
        holder.sign_state_tv.text = mDatas[position].stateStr
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var sign_info_tv = itemView.findViewById<TextView>(R.id.sign_info_tv)
        var sign_state_tv = itemView.findViewById<TextView>(R.id.sign_state_tv)
    }
}