package com.mbcq.vehicleslibrary.activity.shorttrunkdeparturescanoperatingmoreinfo

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.vehicleslibrary.R

class ShortTrunkDepartureScanOperatingMoreInfoAdapter(context: Context) : BaseRecyclerAdapter<ShortTrunkDepartureScanOperatingMoreInfoBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.textview, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.setBackgroundColor(Color.WHITE)
        (holder as ItemViewHolder).item_text.text = mDatas[position].lableNo
        holder.item_text.textSize = 16f
        holder.item_text.gravity = Gravity.CENTER_VERTICAL
        holder.item_text.setBackgroundResource(if (mDatas[position].isScaned) R.color.base_green else R.drawable.hollow_out_gray)
        holder.itemView.setOnClickListener {
            mClickInterface?.onItemClick(it, position, mDatas[position].mResultTag)
        }


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
            holder.itemView.setPadding(0, ScreenSizeUtils.dp2px(it, 8f), 0, ScreenSizeUtils.dp2px(it, 8f))

        }

    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var item_text: TextView = itemView.findViewById(R.id.text)

    }
}