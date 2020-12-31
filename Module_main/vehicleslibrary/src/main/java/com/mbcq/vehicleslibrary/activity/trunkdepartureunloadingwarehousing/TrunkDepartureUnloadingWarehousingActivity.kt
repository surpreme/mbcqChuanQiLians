package com.mbcq.vehicleslibrary.activity.trunkdepartureunloadingwarehousing


import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.R

/**
 * @author: lzy
 * @time: 2020-12-31 16:26:03 干线卸车入库
 */

@Route(path = ARouterConstants.TrunkDepartureUnloadingWarehousingActivity)
class TrunkDepartureUnloadingWarehousingActivity : BaseMVPActivity<TrunkDepartureUnloadingWarehousingContract.View, TrunkDepartureUnloadingWarehousingPresenter>(), TrunkDepartureUnloadingWarehousingContract.View {
    override fun getLayoutId(): Int = R.layout.activity_trunk_departure_unloading_warehousing
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }
}