package com.mbcq.orderlibrary.activity.addhomedelivery


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.util.system.TimeUtils
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.NumberPlateBean
import com.mbcq.commonlibrary.dialog.FilterDialog
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
        departure_date_tv.text = TimeUtils.getCurrentYYMMDD()
    }

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getBatch()
    }

    override fun onClick() {
        super.onClick()
        next_step_btn.apply {
            onSingleClicks {
                ARouter.getInstance().build(ARouterConstants.HomeDeliveryHouseActivity).navigation()
                finish()
            }
        }
        number_plate_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mPresenter?.getVehicles()

            }

        })
        add_home_delivery_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    override fun getBatchS(result: String) {
        if (result.isBlank()) {
            TalkSureDialog(mContext, getScreenWidth(), "获取发车批次失败，请稍后再试") {
                onBackPressed()
            }.show()
            return
        }
        departure_lot_tv.text = result
    }

    override fun getVehicleS(result: String) {
        FilterDialog(getScreenWidth(), result, "vehicleno", "选择车牌号", true, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            @SuppressLint("SetTextI18n")
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val mSelectData = Gson().fromJson<NumberPlateBean>(mResult, NumberPlateBean::class.java)
                number_plate_tv.text = mSelectData.vehicleno
                driver_name_ed.setText(mSelectData.chauffer)
                contact_number_ed.setText(mSelectData.chauffermb)
            }

        }).show(supportFragmentManager, "AddHomeDeliveryVehicleSFilterDialog")
    }
}