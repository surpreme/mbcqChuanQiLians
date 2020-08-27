package com.mbcq.accountlibrary.fragment

import android.view.View
import com.mbcq.accountlibrary.R
import com.mbcq.baselibrary.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_report.*

/**
 * 报表
 */
class ReportFragment :BaseFragment(){
    override fun getLayoutResId(): Int = R.layout.fragment_report

    override fun initViews(view: View) {
        report_toolbar.setPadding(0, getStatusBarHeight(), 0, 0)

    }

    override fun initDatas() {
        super.initDatas()

    }

}