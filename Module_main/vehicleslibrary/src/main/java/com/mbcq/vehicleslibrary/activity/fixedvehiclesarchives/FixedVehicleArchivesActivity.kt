package com.mbcq.vehicleslibrary.activity.fixedvehiclesarchives


import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.R

/**
 * @author: lzy
 * @time: 2020-10-14 17:32:12 修改车辆档案
 */

@Route(path = ARouterConstants.FixedVehicleArchivesActivity)
class FixedVehicleArchivesActivity : BaseMVPActivity<FixedVehicleArchivesContract.View, FixedVehicleArchivesPresenter>(), FixedVehicleArchivesContract.View {
    override fun getLayoutId(): Int = R.layout.activity_fixed_vehicle_archives
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }
}