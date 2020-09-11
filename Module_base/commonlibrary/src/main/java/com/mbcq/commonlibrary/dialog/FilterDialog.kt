package com.mbcq.commonlibrary.dialog

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.dialog.dialogfragment.BaseDialogFragment
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.commonlibrary.R
import com.mbcq.commonlibrary.adapter.BaseTextAdapterBean
import com.mbcq.commonlibrary.adapter.TextViewAdapter
import org.json.JSONArray

/**
 * 仅需要把list的string 传过来 减少那边代码
 * 选中则会把当前的string传回去
 * 统一弹窗 请慎重修改
 */

class FilterDialog : BaseDialogFragment {
    var mScreenWidth: Int = 0
    var mDatas: List<BaseTextAdapterBean>
    var tips: String
    var isGridLayoutManager: Boolean = false
    var isShowOutSide: Boolean = false
    var mClickInterface: OnClickInterface.OnRecyclerClickInterface
    lateinit var filter_recycler_view: RecyclerView
    lateinit var top_title_tv: TextView
    lateinit var close_btn: Button
    override fun setDialogWidth(): Int {
        return mScreenWidth / 4 * 3
    }

    /**
     * mScreenWidth 为了适配屏幕宽度
     * mDatas 需要展示的数据list<Bean> 的json
     * showTag 需要展示给用户看的tag
     * tips 顶部显示的title
     * isGridLayoutManager 是否为表格布局 3
     * isShowOutSide 是否显示背景为边框线
     * mClickInterface 点击回调事件
     */
    constructor(mScreenWidth: Int, mDatas: String, showTag: String, tips: String, isGridLayoutManager: Boolean, isShowOutSide: Boolean, mClickInterface: OnClickInterface.OnRecyclerClickInterface) {
        val dataslist = JSONArray(mDatas)
        val showDatas = mutableListOf<BaseTextAdapterBean>()
        for (index in 0 until dataslist.length()) {
            val mBaseAdapterBean = BaseTextAdapterBean()
            val obj = dataslist.getJSONObject(index)
            mBaseAdapterBean.title = obj.optString(showTag)
            mBaseAdapterBean.tag = GsonUtils.toPrettyFormat(obj.toString())
            showDatas.add(mBaseAdapterBean)
        }
        this.mDatas = showDatas
        this.mScreenWidth = mScreenWidth
        this.tips = tips
        this.isGridLayoutManager = isGridLayoutManager
        this.isShowOutSide = isShowOutSide
        this.mClickInterface = mClickInterface
    }

    constructor(mScreenWidth: Int, mDatas: String, showTag: MutableList<String>, startString: MutableList<String>, endString: String, tips: String, isGridLayoutManager: Boolean, isShowOutSide: Boolean, mClickInterface: OnClickInterface.OnRecyclerClickInterface) {
        val dataslist = JSONArray(mDatas)
        val showDatas = mutableListOf<BaseTextAdapterBean>()
        for (index in 0 until dataslist.length()) {
            val mBaseAdapterBean = BaseTextAdapterBean()
            val obj = dataslist.getJSONObject(index)
            val mShowTag = StringBuilder()
            if (showTag.size == 0) {
                mShowTag.append(obj.optString(showTag[0]))
            } else if (showTag.size > 0) {
                if (startString.size == showTag.size) {
                    for ((mIndexI, oo) in showTag.withIndex()) {
                        mShowTag.append(startString[mIndexI]).append(obj.optString(oo)).append(endString)
                    }
                }

            }

            mBaseAdapterBean.title = mShowTag.toString()
            mBaseAdapterBean.tag = GsonUtils.toPrettyFormat(obj.toString())
            showDatas.add(mBaseAdapterBean)
        }
        this.mDatas = showDatas
        this.mScreenWidth = mScreenWidth
        this.tips = tips
        this.isGridLayoutManager = isGridLayoutManager
        this.isShowOutSide = isShowOutSide
        this.mClickInterface = mClickInterface
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        filter_recycler_view = view.findViewById(R.id.filter_recycler_view)
        close_btn = view.findViewById(R.id.close_btn)
        top_title_tv = view.findViewById(R.id.top_title_tv)
        top_title_tv.text = tips
        close_btn.setOnClickListener {
            dismiss()
        }
        if (isGridLayoutManager)
            filter_recycler_view.layoutManager = GridLayoutManager(mContext, 3)
        else
            filter_recycler_view.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)

        val mTextViewAdapter = TextViewAdapter<BaseTextAdapterBean>(mContext)
        mTextViewAdapter.mClick = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {

                mClickInterface.onItemClick(v, position, mResult)
                dismiss()
            }

        }
        mTextViewAdapter.setIsShowOutSide(isShowOutSide)
        filter_recycler_view.adapter = mTextViewAdapter
        mTextViewAdapter.appendData(mDatas)

    }

    override fun setContentView(): Int = R.layout.dialog_filter_recyclerview

}