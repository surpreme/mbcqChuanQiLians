package com.mbcq.vehicleslibrary.activity.addvehiclesarchives


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.R
import kotlinx.android.synthetic.main.activity_add_vehicle_archives.*

/**
 * @author: lzy
 * @time: 2020-10-14 17:20:45 添加车辆档案
 */
@Route(path = ARouterConstants.AddVehicleArchivesActivity)
class AddVehicleArchivesActivity : BaseMVPActivity<AddVehicleArchivesContract.View, AddVehicleArchivesPresenter>(), AddVehicleArchivesContract.View {
    override fun getLayoutId(): Int = R.layout.activity_add_vehicle_archives
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun onClick() {
        super.onClick()
        add_vehicle_archives_toolbar.setBackButtonOnClickListener( object :SingleClick(){
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }
}