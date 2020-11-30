package com.mbcq.accountlibrary.activity.commonlyinformationconfiguration

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.accountlibrary.R
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.adapter.BaseTextAdapterBean

class CommonlyInformationConfigurationAdapter(context: Context) : BaseRecyclerAdapter<BaseTextAdapterBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.textview, parent, false))


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).item_text.text = mDatas[position].title
        holder.item_text.setBackgroundResource(R.drawable.hollow_out_gray)
        holder.item_text.textSize = 16f

        holder.itemView.setOnClickListener(object :SingleClick(){
            override fun onSingleClick(v: View) {
                mClickInterface?.onItemClick(v,position,mDatas[position].tag)
            }

        })
        context?.let {
            //设置宽高
            val params: ViewGroup.LayoutParams = holder.item_text.layoutParams as ViewGroup.LayoutParams
            //   params.width = ScreenSizeUtils.dp2px(it, 58f)
//            params.height = ScreenSizeUtils.dp2px(it, 30f)
            holder.item_text.layoutParams = params


            /**
             * itemView
             */
            val itemViewParams: ViewGroup.LayoutParams = holder.itemView.layoutParams as ViewGroup.LayoutParams
            val marginParams: ViewGroup.MarginLayoutParams?
            //获取view的margin设置参数
            //获取view的margin设置参数
            marginParams = if (itemViewParams is ViewGroup.MarginLayoutParams) {
                itemViewParams
            } else {
                //不存在时创建一个新的参数
                //基于View本身原有的布局参数对象
                ViewGroup.MarginLayoutParams(itemViewParams)
            }
            marginParams.setMargins(ScreenSizeUtils.dp2px(it, 1f), ScreenSizeUtils.dp2px(it, 1f), ScreenSizeUtils.dp2px(it, 1f), ScreenSizeUtils.dp2px(it, 1f))
            holder.itemView.layoutParams = marginParams
            holder.itemView.setPadding(0, ScreenSizeUtils.dp2px(it, 5f), 0, ScreenSizeUtils.dp2px(it, 5f))

        }

    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var item_text: TextView = itemView.findViewById(com.mbcq.commonlibrary.R.id.text)

    }
}