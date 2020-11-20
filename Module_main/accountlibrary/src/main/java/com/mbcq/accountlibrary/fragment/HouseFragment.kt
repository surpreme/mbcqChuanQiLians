package com.mbcq.accountlibrary.fragment

import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.accountlibrary.R
import com.mbcq.baselibrary.ui.BaseFragment
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import kotlinx.android.synthetic.main.fragment_house.*

/***
 * 首页
 */
class HouseFragment :BaseFragment(){
    override fun getLayoutResId(): Int = R.layout.fragment_house
    override fun initViews(view: View) {
        type_tab_layout.addTab(type_tab_layout.newTab().setText("我寄的"))
        type_tab_layout.addTab(type_tab_layout.newTab().setText("我收的"))
    }

    override fun onClick() {
        super.onClick()
        scan_tv.setOnClickListener(object :SingleClick(){
            override fun onSingleClick(v: View?) {
                ARouter.getInstance().build(ARouterConstants.ScanActivity).navigation()

            }

        })
    }

}