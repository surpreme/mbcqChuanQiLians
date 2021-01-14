package com.mbcq.orderlibrary.activity.addhomedelivery


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_add_home_delivery.*

/**
 * @author: lzy
 * @time: 2021-01-14 17:09:34 添加上门提货配置
 */

@Route(path = ARouterConstants.AddHomeDeliveryActivity)
class AddHomeDeliveryActivity : BaseMVPActivity<AddHomeDeliveryContract.View, AddHomeDeliveryPresenter>(), AddHomeDeliveryContract.View {
    override fun getLayoutId(): Int = R.layout.activity_add_home_delivery
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun onClick() {
        super.onClick()
        add_home_delivery_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }
}