package com.mbcq.vehicleslibrary.activity.addtrunkdeparture


import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.R

/**
 * @author: lzy
 * @time: 2020-09-14 11:02:45
 */
@Route(path = ARouterConstants.AddTrunkDepartureActivity)
class AddTrunkDepartureActivity : BaseMVPActivity<AddTrunkDepartureContract.View, AddTrunkDeparturePresenter>(), AddTrunkDepartureContract.View {
    override fun getLayoutId(): Int = R.layout.activity_add_trunk_departure
}