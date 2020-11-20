package com.mbcq.amountlibrary.fragment.generaledger

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.amountlibrary.R
import com.mbcq.baselibrary.view.BaseRecyclerAdapter

class GeneralLedgerAdapter (context: Context):BaseRecyclerAdapter<GeneralLedgerBean>(context){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_general_ledger, parent, false))


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}