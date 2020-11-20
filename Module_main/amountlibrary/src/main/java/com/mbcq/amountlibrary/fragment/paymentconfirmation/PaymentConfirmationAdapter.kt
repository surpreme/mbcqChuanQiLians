package com.mbcq.amountlibrary.fragment.paymentconfirmation

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.amountlibrary.R
import com.mbcq.baselibrary.view.BaseRecyclerAdapter

class PaymentConfirmationAdapter (context: Context):BaseRecyclerAdapter<PaymentConfirmationBean>(context){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_payment_confirmation, parent, false))


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}