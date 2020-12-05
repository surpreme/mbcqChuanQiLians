package com.mbcq.commonlibrary.dialog

import android.annotation.SuppressLint
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
import com.mbcq.baselibrary.db.SharePreferencesHelper
import com.mbcq.baselibrary.dialog.dialogfragment.BaseDialogFragment
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.util.system.pinyin.PinYinUtil
import com.mbcq.commonlibrary.Constant
import com.mbcq.commonlibrary.R
import com.mbcq.commonlibrary.adapter.BaseTextAdapterBean
import com.mbcq.commonlibrary.adapter.TextViewAdapter
import kotlinx.android.synthetic.main.dialog_filter_recyclerview.*
import org.json.JSONArray
import org.json.JSONObject


/**
 * 仅需要把list的string 传过来 减少那边代码
 * 选中则会把当前的string传回去
 * 统一弹窗 请慎重修改
 */

class FilterDialog : BaseDialogFragment {
    var mScreenWidth: Int = 0
    var mDatas: List<BaseTextAdapterBean>
    var tips: String
    var showTipsTag: String = ""
    var showBarTipsStr: String = ""
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

    /**
     * mScreenWidth 为了适配屏幕宽度
     * mDatas 需要展示的数据list<Bean> 的json
     * showTag 需要展示给用户看的tag
     * tips 顶部显示的title
     * isGridLayoutManager 是否为表格布局 3
     * isShowOutSide 是否显示背景为边框线
     * showTipsTag 常用配置的数据库名字
     * showBarTipsStr 常用配置的名字
     * mClickInterface 点击回调事件
     */
    constructor(mScreenWidth: Int, mDatas: String, showTag: String, tips: String, isGridLayoutManager: Boolean, isShowOutSide: Boolean, showTipsTag: String, showBarTipsStr: String, mClickInterface: OnClickInterface.OnRecyclerClickInterface) {
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
        this.showTipsTag = showTipsTag
        this.showBarTipsStr = showBarTipsStr
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

    var mSharePreferencesHelper: SharePreferencesHelper? = null

    @SuppressLint("SetTextI18n")
    override fun initView(view: View, savedInstanceState: Bundle?) {
        filter_search_ed = view.findViewById(R.id.filter_search_ed)
        filter_recycler_view = view.findViewById(R.id.filter_recycler_view)
        close_btn = view.findViewById(R.id.close_btn)
        top_title_tv = view.findViewById(R.id.top_title_tv)
        top_title_tv.text = tips
        if (showBarTipsStr.isNotBlank()) {
            commonly_bar_title_tv.text = "常用$showBarTipsStr"
            more_bar_title_tv.text = "所有$showBarTipsStr"
            commonly_configuration_ll.visibility = View.VISIBLE
            val mCommonlyViewAdapter = TextViewAdapter<BaseTextAdapterBean>(mContext)
            if (isGridLayoutManager)
                commonly_filter_recycler_view.layoutManager = GridLayoutManager(mContext, 3)
            else
                commonly_filter_recycler_view.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
            commonly_filter_recycler_view.adapter = mCommonlyViewAdapter
            if (mSharePreferencesHelper == null)
                mSharePreferencesHelper = SharePreferencesHelper(mContext, Constant.COMMON_CONFIGURATION_PREFERENCESMODE)
            mSharePreferencesHelper?.contain(showTipsTag)?.let {
                val mShareStr = mSharePreferencesHelper?.getSharePreference(showTipsTag, "{list:[]}") as String
                val mShareObj = JSONObject(mShareStr)
                val mXTextAdapterListBean = mutableListOf<BaseTextAdapterBean>()
                mShareObj.optJSONArray("list")?.let {
                    for (index in 0 until it.length()) {
                        val mSonObj = it.getJSONObject(index)
                        if (showBarTipsStr == "目的地") {
                            for (mSonS in mDatas) {
                                if (mSonS.title == mSonObj.optString("mTitle")) {
                                    val mBaseAdapterBean = BaseTextAdapterBean()
                                    mBaseAdapterBean.title = mSonObj.optString("mTitle")
                                    mBaseAdapterBean.tag = mSonObj.optString("mContentStr")
                                    mXTextAdapterListBean.add(mBaseAdapterBean)
                                }
                            }
                        } else {
                            val mBaseAdapterBean = BaseTextAdapterBean()
                            mBaseAdapterBean.title = mSonObj.optString("mTitle")
                            mBaseAdapterBean.tag = mSonObj.optString("mContentStr")
                            mXTextAdapterListBean.add(mBaseAdapterBean)
                        }

                    }
                }
                mCommonlyViewAdapter.appendData(mXTextAdapterListBean)
                mCommonlyViewAdapter.mClick = object : OnClickInterface.OnRecyclerClickInterface {
                    override fun onItemClick(v: View, position: Int, mResult: String) {

                        mClickInterface.onItemClick(v, position, mResult)
                        dismiss()
                    }

                }
                if (mXTextAdapterListBean.isNullOrEmpty()) {
                    commonly_configuration_ll.visibility = View.GONE
                }
            }

        } else {
            commonly_configuration_ll.visibility = View.GONE
        }
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
                if (s.toString().isBlank()) {
                    mTextViewAdapter.clearDatas()
                    mTextViewAdapter.appendData(mDatas)
                    return
                }
                val mSearchDatas = mutableListOf<BaseTextAdapterBean>()
                for (item in mTextViewAdapter.getAllData()) {
                    if (item.title.startsWith(s.toString()) or item.title.contains(s.toString())) {
                        mSearchDatas.add(item)
                    } else {
                        if (PinYinUtil.getFirstSpell(item.title).contains(s.toString().toLowerCase()) or PinYinUtil.getFullSpell(item.title).contains(s.toString().toLowerCase()))
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