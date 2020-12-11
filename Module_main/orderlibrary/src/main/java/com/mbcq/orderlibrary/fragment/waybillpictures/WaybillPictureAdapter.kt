package com.mbcq.orderlibrary.fragment.waybillpictures

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.orderlibrary.R

class WaybillPictureAdapter(context: Context) : BaseRecyclerAdapter<WaybillPictureBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_waybill_picture, parent, false))


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).waybill_picture_information_tv.text="上传人：${mDatas[position].uploadman}\n上传时间：${mDatas[position].uploaddate}"
        context?.let {
            Glide.with(context).load(ApiInterface.BASE_URIS+mDatas[position].imgpath).into(holder.waybill_picture_iv)
        }
      /*  if (mDatas.size==1){
            holder.waybill_picture_card.preventCornerOverlap = false
            holder.waybill_picture_card.useCompatPadding = false
            holder.waybill_picture_card.cardElevation = 0f

        }else{
            holder.waybill_picture_card.preventCornerOverlap = true
            holder.waybill_picture_card.useCompatPadding = true
            holder.waybill_picture_card.cardElevation = 6f
        }*/
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var waybill_picture_card = itemView.findViewById<CardView>(R.id.waybill_picture_card)
        var waybill_picture_iv = itemView.findViewById<ImageView>(R.id.waybill_picture_iv)
        var waybill_picture_information_tv = itemView.findViewById<TextView>(R.id.waybill_picture_information_tv)
    }
}