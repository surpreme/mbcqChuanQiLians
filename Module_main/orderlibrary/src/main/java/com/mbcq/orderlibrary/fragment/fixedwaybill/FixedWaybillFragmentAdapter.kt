package com.mbcq.orderlibrary.fragment.fixedwaybill

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.orderlibrary.R


class FixedWaybillFragmentAdapter(context: Context) : BaseRecyclerAdapter<FixedWaybillListBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_accept_billing_fragment_record, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var fixed_waybill_info_tv=itemView.findViewById<TextView>(R.id.fixed_waybill_info_tv)
        var fixed_waybill_content_left_tv=itemView.findViewById<TextView>(R.id.fixed_waybill_content_left_tv)
        var fixed_waybill_content_right_tv=itemView.findViewById<TextView>(R.id.fixed_waybill_content_right_tv)
    }
}