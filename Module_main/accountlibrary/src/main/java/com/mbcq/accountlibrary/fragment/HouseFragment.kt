package com.mbcq.accountlibrary.fragment

import android.view.View
import com.mbcq.accountlibrary.R
import com.mbcq.baselibrary.ui.BaseFragment
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


    override fun initDatas() {
        super.initDatas()

    }

}