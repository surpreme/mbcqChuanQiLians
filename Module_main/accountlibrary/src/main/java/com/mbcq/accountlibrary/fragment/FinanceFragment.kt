package com.mbcq.accountlibrary.fragment

import android.view.View
import com.mbcq.accountlibrary.R
import com.mbcq.baselibrary.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_finance.*

/**
 * 财务
 */
class FinanceFragment :BaseFragment(){
    override fun getLayoutResId(): Int = R.layout.fragment_finance
    override fun initViews(view: View) {
        tool_text_tv.setPadding(0, getStatusBarHeight(), 0, 0)

    }


    override fun initDatas() {
        super.initDatas()

    }

}