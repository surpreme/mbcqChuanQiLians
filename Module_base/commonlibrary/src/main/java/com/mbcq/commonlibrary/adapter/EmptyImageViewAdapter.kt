package com.mbcq.commonlibrary.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.R

class EmptyImageViewAdapter (context: Context?):BaseRecyclerAdapter<ImageViewBean>(context){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder= ItemViewHolder(inflater.inflate(R.layout.base_empty_imageview, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        context?.let {
            Glide.with(context).load(mDatas[position].imageUris).into((holder as ItemViewHolder).base_image_view)

        }
        holder.itemView.setOnClickListener(object:SingleClick(){
            override fun onSingleClick(v: View) {
                mClickInterface?.onItemClick(v,position,"")
            }

        })

    }
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var base_image_view: ImageView = itemView.findViewById(R.id.base_image_view)


    }
}