package com.mbcq.accountlibrary.house


import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.accountlibrary.R
import com.mbcq.commonlibrary.ARouterConstants

/**
 * 主页面
 */
@Route(path = ARouterConstants.HouseActivity)
class HouseActivity : BaseHouseFingerActivity(){

    override fun getLayoutId(): Int = R.layout.activity_house

    override fun initViews() {
        super.initViews()
        setStatusBar(0)
    }


}
