package com.mbcq.commonlibrary.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.commonlibrary.R

class TextViewAdapter<T : BaseTextAdapterBean> : RecyclerView.Adapter<TextViewAdapter.ItemViewHolder> {
    private var context: Context?
    private val inflater: LayoutInflater
    private var mSonBean = ArrayList<T>()
    private var isShowOutSide = true
    private var mTextGravity = 0

    constructor(context: Context) {
        this.context = context
        this.inflater = LayoutInflater.from(context)
    }

    constructor(context: Context, textGravity: Int) {
        this.context = context
        this.mTextGravity = textGravity
        this.inflater = LayoutInflater.from(context)
    }

    fun setIsShowOutSide(isShow: Boolean) {
        this.isShowOutSide = isShow

    }
    fun getAllData(): ArrayList<T> {
        return mSonBean
    }
    fun appendData(list: List<T>) {
        mSonBean.addAll(list)
        notifyDataSetChanged()
    }

    fun clearDatas() {
        mSonBean.clear()
        notifyDataSetChanged()
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var item_text: TextView = itemView.findViewById(R.id.text)


    }

    var mClick: OnClickInterface.OnRecyclerClickInterface? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(inflater.inflate(R.layout.textview, parent, false))
    }

    override fun getItemCount(): Int = mSonBean.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.setBackgroundColor(Color.WHITE)
        holder.item_text.text = mSonBean[position].title
        holder.item_text.textSize = 16f
        if (mTextGravity != 0) {
            holder.item_text.gravity = mTextGravity
        }
        holder.item_text.setBackgroundResource(if (isShowOutSide) R.drawable.hollow_out_gray else R.color.white)
        holder.itemView.setOnClickListener {
            mClick?.onItemClick(it, position, mSonBean[position].tag)
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
            holder.itemView.setPadding(0, ScreenSizeUtils.dp2px(it, 5f), 0, ScreenSizeUtils.dp2px(it, 5f))

        }


    }
}