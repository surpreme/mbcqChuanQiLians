package com.mbcq.accountlibrary.fragment.iconadapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.accountlibrary.R
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import kotlinx.android.synthetic.main.fragment_operation.*


class IconViewRecyclerAdapter(context: Context?) : BaseRecyclerAdapter<IconViewBean>(context) {
    private val TITLE_ITEM_TAG = 1
    private val CONTENT_ITEM_TAG = 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var viewHolder: RecyclerView.ViewHolder
        when (viewType) {
            TITLE_ITEM_TAG -> viewHolder = TitleViewHolder(inflater.inflate(R.layout.textview, parent, false))
            CONTENT_ITEM_TAG -> viewHolder = CardViewHolder(inflater.inflate(R.layout.card_recyclerview, parent, false))

        }
        return viewHolder
    }

    override fun getItemViewType(position: Int): Int {
        return when (mDatas[position].tag) {
            1 -> {
                TITLE_ITEM_TAG
            }
            2 -> CONTENT_ITEM_TAG
            else -> 0
        }
    }


    @SuppressLint("RtlHardcoded")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        when (viewType) {
            TITLE_ITEM_TAG -> {
                (holder as TitleViewHolder).tv_title.text = mDatas[position].title
                //居中类型
                holder.tv_title.gravity = Gravity.LEFT or Gravity.CENTER
                //字体大小
                holder.tv_title.textSize = 16f
                context?.let {
                    //距离上下左右
                    holder.itemView.setPadding(ScreenSizeUtils.dp2px(it, 11f), 0, 0, 0)

                    //设置宽高
                    val params: ViewGroup.LayoutParams = holder.itemView.layoutParams as ViewGroup.LayoutParams
                    params.height = ScreenSizeUtils.dp2px(it, 32f)
                    holder.itemView.layoutParams = params
                }

            }
            CONTENT_ITEM_TAG -> {
                val mI = ItemFootAdapterSon(context, mDatas[position].item, position)
                context?.let {

                    //距离上下左右
                    holder.itemView.setPadding(ScreenSizeUtils.dp2px(it, 8f), ScreenSizeUtils.dp2px(it, 7f), ScreenSizeUtils.dp2px(it, 8f), ScreenSizeUtils.dp2px(it, 7f))
                    val params: ViewGroup.LayoutParams = holder.itemView.layoutParams as ViewGroup.LayoutParams
                    val marginParams: MarginLayoutParams?
                    //获取view的margin设置参数
                    //获取view的margin设置参数
                    marginParams = if (params is MarginLayoutParams) {
                        params
                    } else {
                        //不存在时创建一个新的参数
                        //基于View本身原有的布局参数对象
                        MarginLayoutParams(params)
                    }
                    marginParams.setMargins(ScreenSizeUtils.dp2px(it, 11f), ScreenSizeUtils.dp2px(it, 4f), ScreenSizeUtils.dp2px(it, 11f), ScreenSizeUtils.dp2px(it, 4f))
                    holder.itemView.layoutParams = marginParams

                }
                mI.mClick = mClickInterface
                (holder as CardViewHolder).card_recycler_view.adapter = mI
                //isLayoutFrozen
//                holder.card_recycler_view.isLayoutFrozen=true

                holder.card_recycler_view.layoutManager = GridLayoutManager(context, 3)
            }
        }

    }

    class TitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_title: TextView = itemView.findViewById(R.id.text)

    }

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var card_recycler_view: RecyclerView = itemView.findViewById(R.id.card_recycler_view)

    }

    private class ItemFootAdapterSon : RecyclerView.Adapter<ItemFootAdapterSon.ItemViewHolder> {
        private var context: Context?
        private val inflater: LayoutInflater
        private var mSonBean = ArrayList<IconViewBean.ItemBean>()
        private var mIndexTag = 0

        constructor(context: Context?, mSonBean: List<IconViewBean.ItemBean>, mIndex: Int) {
            this.context = context
            this.mIndexTag = mIndex
            this.inflater = LayoutInflater.from(context)
            this.mSonBean = mSonBean as ArrayList<IconViewBean.ItemBean>
        }

        class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var icon_iv: ImageView = itemView.findViewById(R.id.icon_iv)
            var tool_text_tv: TextView = itemView.findViewById(R.id.tool_text_tv)


        }

        var mClick: OnClickInterface.OnRecyclerClickInterface? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            return ItemViewHolder(inflater.inflate(R.layout.item_operation_card, parent, false))
        }

        override fun getItemCount(): Int = mSonBean.size

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            holder.itemView.setBackgroundColor(Color.WHITE)
            holder.tool_text_tv.text = mSonBean[position].itemText
            holder.tool_text_tv.textSize = 16f

            holder.itemView.setOnClickListener {
                mClick?.onItemClick(it, position, mIndexTag.toString())
            }

            context?.let {
                //设置宽高
                val params: ViewGroup.LayoutParams = holder.icon_iv.layoutParams as ViewGroup.LayoutParams
                params.width = ScreenSizeUtils.dp2px(it, 58f)
                params.height = ScreenSizeUtils.dp2px(it, 58f)
                holder.icon_iv.layoutParams = params
                holder.itemView.setPadding(0, ScreenSizeUtils.dp2px(it, 10f), 0, ScreenSizeUtils.dp2px(it, 10f))

            }


        }
    }
}