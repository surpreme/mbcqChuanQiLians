package com.mbcq.vehicleslibrary.activity.addtrunkdeparture


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.R
import kotlinx.android.synthetic.main.activity_add_trunk_departure.*

/**
 * @author: lzy
 * @time: 2020-09-14 11:02:45
 */
@Route(path = ARouterConstants.AddTrunkDepartureActivity)
class AddTrunkDepartureActivity : BaseMVPActivity<AddTrunkDepartureContract.View, AddTrunkDeparturePresenter>(), AddTrunkDepartureContract.View {
    override fun getLayoutId(): Int = R.layout.activity_add_trunk_departure
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun onClick() {
        super.onClick()
        add_trunk_departure_toolbar.setBackButtonOnClickListener (object:SingleClick(){
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }
        })
    }
}