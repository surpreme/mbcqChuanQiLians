package com.mbcq.commonlibrary.dialog

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.mbcq.baselibrary.dialog.dialogfragment.BaseDialogFragment
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.R
import com.mbcq.commonlibrary.adapter.BaseOutCheckedAdapterBean
import com.mbcq.commonlibrary.adapter.CheckBoxOutViewAdapter
import kotlinx.android.synthetic.main.dialog_more_checkbox_pagekeage.*
import org.json.JSONArray
import java.lang.StringBuilder

class MoreCheckBoxPackageDialog : BaseDialogFragment {
    var mScreenWidth: Int = 0
    var mDatas: List<BaseOutCheckedAdapterBean>
    var tips: String
    var mClickInterface: OnClickInterface.OnClickInterface? = null

    override fun setDialogWidth(): Int {
        return mScreenWidth / 5 * 4
    }

    constructor(mScreenWidth: Int, tips: String, mDatas: String, showTag: String, resultTag: String, mClickInterface: OnClickInterface.OnClickInterface) {
        val dataslist = JSONArray(mDatas)
        val showDatas = mutableListOf<BaseOutCheckedAdapterBean>()
        for (index in 0 until dataslist.length()) {
            val mBaseOutCheckedAdapterBean = BaseOutCheckedAdapterBean()
            val obj = dataslist.getJSONObject(index)
            mBaseOutCheckedAdapterBean.title = obj.optString(showTag)
            mBaseOutCheckedAdapterBean.tag = if (resultTag.isNotEmpty()) obj.optString(resultTag) else GsonUtils.toPrettyFormat(obj.toString())
            showDatas.add(mBaseOutCheckedAdapterBean)
        }
        this.mScreenWidth = mScreenWidth
        this.mDatas = showDatas
        this.tips = tips
        this.mClickInterface = mClickInterface

    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        filter_recycler_view.layoutManager = LinearLayoutManager(mContext)
        val mCheckBoxOutViewAdapter = CheckBoxOutViewAdapter<BaseOutCheckedAdapterBean>(mContext, true)
        filter_recycler_view.adapter = mCheckBoxOutViewAdapter
        mCheckBoxOutViewAdapter.appendData(mDatas)
        top_title_tv.text = tips
        sure_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                val selectStr = StringBuilder()
                for (item in mCheckBoxOutViewAdapter.getDatas()) {
                    if (item.checked) {
                        selectStr.append(if (item.outNum.isNotBlank()) item.outNum else item.title)

                    }
                }
                mClickInterface?.onResult(selectStr.toString(), "")
                dismiss()
            }

        })
        cancel_btn.setOnClickListener {
            dismiss()
        }
    }

    override fun setContentView(): Int = R.layout.dialog_more_checkbox_pagekeage

}