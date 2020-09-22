package com.mbcq.vehicleslibrary.activity.allarrivalrecord.unloadingwarehousing


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.R
import kotlinx.android.synthetic.main.fragment_arrival_short_feeder.view.*
import kotlinx.android.synthetic.main.activity_unloading_warehousing.*

/**
 * @author: lzy
 * @time: 2020-09-21 16:31:45
 * 卸车入库
 */
@Route(path = ARouterConstants.UnloadingWarehousingActivity)
class UnloadingWarehousingActivity : BaseMVPActivity<UnloadingWarehousingContract.View, UnloadingWarehousingPresenter>(), UnloadingWarehousingContract.View {
    override fun getLayoutId(): Int = R.layout.activity_unloading_warehousing
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun onClick() {
        super.onClick()
        unloading_warehousing_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })

    }

}