package com.mbcq.vehicleslibrary.activity.alllocalagent.addlocalgentshortfeeder


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.dialog.FilterDialog
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.alldeparturerecord.addshortfeeder.NumberPlateBean
import kotlinx.android.synthetic.main.activity_add_local_gent_short_feeder.*

/**
 * @author: lzy
 * @time: 2020-09-22 15:48
 */

@Route(path = ARouterConstants.AddLocalGentShortFeederActivity)
class AddLocalGentShortFeederActivity : BaseMVPActivity<AddLocalGentShortFeederContract.View, AddLocalGentShortFeederPresenter>(), AddLocalGentShortFeederContract.View {
    override fun getLayoutId(): Int = R.layout.activity_add_local_gent_short_feeder
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getDepartureBatchNumber()

    }

    override fun onClick() {
        super.onClick()

        next_step_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                ARouter.getInstance().build(ARouterConstants.LocalGentShortFeederHouseActivity).navigation()
            }

        })
        number_plate_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mPresenter?.getVehicles()

            }

        })
        add_local_short_feeder_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }


    override fun getDepartureBatchNumberS(inOneVehicleFlag: String) {
        dispatch_number_tv.text = inOneVehicleFlag
    }

    override fun getVehicleS(result: String) {
        FilterDialog(getScreenWidth(), result, "vehicleno", "选择车牌号", true, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            @SuppressLint("SetTextI18n")
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val mSelectData = Gson().fromJson<NumberPlateBean>(mResult, NumberPlateBean::class.java)
                number_plate_tv.text = mSelectData.vehicleno
                driver_name_ed.setText(mSelectData.chauffer)
                contact_number_ed.setText(mSelectData.chauffermb)
                vehicle_type_ed.setText(if (mSelectData.vehicletype == 1) "大车" else if (mSelectData.vehicletype == 2) "小车" else "未知车型")
            }

        }).show(supportFragmentManager, "AddLocalGentShortFeederActivitygetVehicleSFilterDialog")
    }
}