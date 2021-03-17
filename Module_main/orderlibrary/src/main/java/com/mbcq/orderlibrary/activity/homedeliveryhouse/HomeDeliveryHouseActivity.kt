package com.mbcq.orderlibrary.activity.homedeliveryhouse


import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_home_delivery_house.*

/**
 * @author: lzy
 * @time: 2021-01-14 17:59:02 上门提货
 */

@Route(path = ARouterConstants.HomeDeliveryHouseActivity)
class HomeDeliveryHouseActivity : BaseMVPActivity<HomeDeliveryHouseContract.View, HomeDeliveryHousePresenter>(), HomeDeliveryHouseContract.View {
    override fun getLayoutId(): Int = R.layout.activity_home_delivery_house
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun onClick() {
        super.onClick()
        home_delivery_house_toolbar.apply {
            onSingleClicks {
                onBackPressed()
            }
        }
    }
}