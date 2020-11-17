package com.mbcq.orderlibrary.activity.claimsettlement

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.orderlibrary.R

class ClaimSettlementAdapter(context: Context) : BaseRecyclerAdapter<ClaimSettlementBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_claim_settlement, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

}