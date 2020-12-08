package com.mbcq.accountlibrary.activity.housesearch


import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.accountlibrary.R
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.commonlibrary.ARouterConstants
import kotlinx.android.synthetic.main.activity_house_search.*

/**
 * @author: lzy
 * @time: 2020-012-08 16:04:45 主页搜索页面
 */

@Route(path = ARouterConstants.HouseSearchActivity)
class HouseSearchActivity : BaseMVPActivity<HouseSearchContract.View, HouseSearchPresenter>(), HouseSearchContract.View {
    override fun getLayoutId(): Int = R.layout.activity_house_search
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.white)
    }

    override fun onClick() {
        super.onClick()
        back_iv.apply {
            onSingleClicks {
                onBackPressed()
            }
        }
    }
}