package com.mbcq.vehicleslibrary.activity.vehiclesarchives


import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.R

/**
 * @author: lzy
 * @time: 2020-10-14 14:43:16 车辆档案
 */
@Route(path = ARouterConstants.VehicleArchivesActivity)
class VehicleArchivesActivity : BaseMVPActivity<VehicleArchivesContract.View, VehicleArchivesPresenter>(), VehicleArchivesContract.View {
    override fun getLayoutId(): Int = R.layout.activity_vehicle_archives
}