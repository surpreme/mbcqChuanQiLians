package com.mbcq.vehicleslibrary.activity.fixtrunkdeparturehouse


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.interfaces.RxBus
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.R
import kotlinx.android.synthetic.main.activity_fixed_trunk_departure_house.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-18 11:33
 * 修改 干线发车
 */
@Route(path = ARouterConstants.FixedTrunkDepartureHouseActivity)
class FixedTrunkDepartureHouseActivity : BaseMVPActivity<FixedTrunkDepartureHouseContract.View, FixedTrunkDepartureHousePresenter>(), FixedTrunkDepartureHouseContract.View {
    @Autowired(name = "FixedTrunkDepartureHouse")
    @JvmField
    var mFixDataJson: String = ""
    var mInoneVehicleFlag = ""
    override fun getLayoutId(): Int = R.layout.activity_fixed_trunk_departure_house
    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)
    }

    @SuppressLint("SetTextI18n")
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        complete_btn.text = "取消完成本车"
        operating_btn.isClickable = false
        val mLastData = JSONObject(mFixDataJson)
        mInoneVehicleFlag = mLastData.optString("InoneVehicleFlag")
        departure_lot_tv.text = "发车批次: $mInoneVehicleFlag"
    }

    override fun onClick() {
        super.onClick()
        complete_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (complete_btn.text == "取消完成本车") {
                    TalkSureCancelDialog(mContext, getScreenWidth(), "您确定要取消完成本车${departure_lot_tv.text}吗？") {
                        val modifyData = JSONObject(mFixDataJson)
                        mPresenter?.modify(modifyData)
                    }.show()
                }


            }

        })
        fixed_trunk_departure_house_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    override fun modifyS() {
        TalkSureDialog(mContext, getScreenWidth(), "成功取消完成本车") {
            ARouter.getInstance().build(ARouterConstants.DepartureRecordActivity).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    .withBoolean("TrunkDepartureIsRefresh", true)
                    .navigation()
            this.finish()
        }.show()
    }
}