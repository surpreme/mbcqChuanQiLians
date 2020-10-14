package com.mbcq.vehicleslibrary.activity.vehiclesarchives


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.ui.BaseSmartMVPActivity
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.R
import kotlinx.android.synthetic.main.activity_vehicle_archives.*

/**
 * @author: lzy
 * @time: 2020-10-14 14:43:16 车辆档案
 */
@Route(path = ARouterConstants.VehicleArchivesActivity)
class VehicleArchivesActivity : BaseSmartMVPActivity<VehicleArchivesContract.View, VehicleArchivesPresenter, VehicleArchivesBean>(), VehicleArchivesContract.View {
    override fun getLayoutId(): Int = R.layout.activity_vehicle_archives
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun getPageDatas(mCurrentPage: Int) {
        super.getPageDatas(mCurrentPage)
        mPresenter?.getPage(mCurrentPage)

    }

    override fun onClick() {
        super.onClick()
        more_btn.setOnClickListener(object :SingleClick(){
            override fun onSingleClick(v: View?) {
                ARouter.getInstance().build(ARouterConstants.AddVehicleArchivesActivity).navigation()
            }


        })
        vehicle_archives_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    override fun getSmartLayoutId() = R.id.vehicle_archives_smart
    override fun getSmartEmptyId() = R.id.vehicle_archives_smart_frame
    override fun getRecyclerViewId(): Int = R.id.vehicle_archives_recycler

    override fun setAdapter(): BaseRecyclerAdapter<VehicleArchivesBean> = VehicleArchivesRecyclerAdapter(mContext)
    override fun getPageS(list: List<VehicleArchivesBean>) {
        appendDatas(list)
    }
}