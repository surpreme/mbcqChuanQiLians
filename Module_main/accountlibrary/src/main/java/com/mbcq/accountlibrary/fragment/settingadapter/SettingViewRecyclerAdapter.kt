package com.mbcq.accountlibrary.fragment.settingadapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.mbcq.accountlibrary.R
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick


class SettingViewRecyclerAdapter(context: Context?) : BaseRecyclerAdapter<SettingIconBean>(context) {
    private val TITLE_ITEM_TAG = 1
    private val COMMONT_CONTENT_ITEM_TAG = 2
    private val CONTENT_ITEM_TAG = 3
    private val EXIT_CONTENT_ITEM_TAG = 4
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var viewHolder: RecyclerView.ViewHolder
        when (viewType) {
            TITLE_ITEM_TAG -> viewHolder = TitleViewHolder(inflater.inflate(R.layout.item_title_setting, parent, false))
            COMMONT_CONTENT_ITEM_TAG -> viewHolder = CommonItemViewHolder(inflater.inflate(R.layout.item_features_setting, parent, false))
            CONTENT_ITEM_TAG -> viewHolder = ItemViewHolder(inflater.inflate(R.layout.item_configuration_setting, parent, false))
            EXIT_CONTENT_ITEM_TAG -> viewHolder = ExitViewHolder(inflater.inflate(R.layout.textview, parent, false))

        }
        return viewHolder
    }

    fun notifyCommonlyItemChanged(position: Int, data: String) {
        mDatas[position].contentText = data
        notifyItemChanged(position)
    }

    var mOnClickInterface: OnClickInterface? = null

    interface OnClickInterface {
        fun onExitLogIn(v: View)
        fun onCommonly(v: View, position: Int, result: String)
    }

    override fun getItemViewType(position: Int): Int {
        return when (mDatas[position].tag) {
            1 -> TITLE_ITEM_TAG
            2 -> COMMONT_CONTENT_ITEM_TAG
            3 -> CONTENT_ITEM_TAG
            4 -> EXIT_CONTENT_ITEM_TAG
            else -> 0
        }
    }


    @SuppressLint("RtlHardcoded", "SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        when (viewType) {
            TITLE_ITEM_TAG -> {


            }
            COMMONT_CONTENT_ITEM_TAG -> {
                context?.let {
                    (holder as CommonItemViewHolder).features_chidren_setting_recycler.layoutManager = GridLayoutManager(it, 5)
                    holder.features_chidren_setting_recycler.adapter = SettingFeaturesViewRecyclerAdapter(it, mDatas[position].iconItemBean)
                }


            }
            CONTENT_ITEM_TAG -> {
                (holder as ItemViewHolder).setting_item_title_tv.text = "   ${mDatas[position].title}"
                if (position == 7 || position == 14 || position == 17) {
                    holder.configuration_line.visibility = View.INVISIBLE
                } else {
                    holder.configuration_line.visibility = View.VISIBLE
                }

                holder.setting_item_content_tv.text = mDatas[position].contentText
                holder.itemView.setOnClickListener(object : SingleClick() {
                    override fun onSingleClick(v: View) {
                        mOnClickInterface?.onCommonly(v, position, Gson().toJson(mDatas[position]))
                    }
                })
            }
            EXIT_CONTENT_ITEM_TAG -> {

                (holder as ExitViewHolder).textView.text = "退出登录"
                holder.textView.setTextColor(Color.RED)
                holder.itemView.setOnClickListener(object : SingleClick() {
                    override fun onSingleClick(v: View) {
                        mOnClickInterface?.onExitLogIn(v)
                    }

                })
                context?.let {
                    //设置宽高
                    val params: ViewGroup.LayoutParams = holder.itemView.layoutParams as ViewGroup.LayoutParams
                    params.height = ScreenSizeUtils.dp2px(it, 42f)
                    holder.itemView.layoutParams = params
                }
            }
        }

    }

    class ExitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.text)

    }

    class TitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var setting_title_tv: TextView = itemView.findViewById(R.id.setting_title_tv)

    }

    class CommonItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var features_chidren_setting_recycler: RecyclerView = itemView.findViewById(R.id.features_chidren_setting_recycler)

    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var setting_item_content_tv: TextView = itemView.findViewById(R.id.setting_item_content_tv)
        var setting_item_title_tv: TextView = itemView.findViewById(R.id.setting_item_title_tv)
        var configuration_line: View = itemView.findViewById(R.id.configuration_line)

    }

    private class SettingFeaturesViewRecyclerAdapter(var context: Context, mSonBean: List<SettingIconBean.ItemBean>) : RecyclerView.Adapter<SettingFeaturesViewRecyclerAdapter.FeaturesItemViewHolder>() {
        private val inflater: LayoutInflater = LayoutInflater.from(context)
        private var mSonBean = ArrayList<SettingIconBean.ItemBean>()

        init {
            this.mSonBean = mSonBean as ArrayList<SettingIconBean.ItemBean>
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturesItemViewHolder {
            return FeaturesItemViewHolder(inflater.inflate(R.layout.item_features_chirend_setting, parent, false))

        }

        override fun onBindViewHolder(holder: FeaturesItemViewHolder, position: Int) {
            holder.setting_more_text_tv.text = mSonBean[position].showTxt
            holder.setting_more_icon_iv.setImageDrawable(ContextCompat.getDrawable(context,mSonBean[position].imgId))
        }

        override fun getItemCount(): Int = mSonBean.size

        class FeaturesItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var setting_more_text_tv = itemView.findViewById<TextView>(R.id.setting_more_text_tv)
            var setting_more_icon_iv = itemView.findViewById<ImageView>(R.id.setting_more_icon_iv)
        }
    }

}