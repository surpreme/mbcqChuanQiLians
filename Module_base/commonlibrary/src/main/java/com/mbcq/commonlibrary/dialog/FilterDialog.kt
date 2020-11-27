package com.mbcq.commonlibrary.dialog

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.dialog.dialogfragment.BaseDialogFragment
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
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
    lateinit var filter_search_ed: EditText
    override fun setDialogWidth(): Int {
        return mScreenWidth / 4 * 3
    }

    /* override fun setDialogHeight(): Int {
         return if (activity == null) 200 else ScreenSizeUtils.getScreenHeight(activity!!) / 10 * 3
     }*/

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
        filter_search_ed = view.findViewById(R.id.filter_search_ed)
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
        filter_search_ed.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isBlank()){
                    mTextViewAdapter.clearDatas()
                    mTextViewAdapter.appendData(mDatas)
                    return
                }
                val mSearchDatas = mutableListOf<BaseTextAdapterBean>()
                for (item in mTextViewAdapter.getAllData()) {
                    if (item.title.startsWith(s.toString()) or item.title.contains(s.toString())) {
                        mSearchDatas.add(item)
                    }
                }
                if (mSearchDatas.isNotEmpty()) {
                    mTextViewAdapter.clearDatas()
                    mTextViewAdapter.appendData(mSearchDatas)
                }



            }

        })
    }

    override fun setContentView(): Int = R.layout.dialog_filter_recyclerview

}