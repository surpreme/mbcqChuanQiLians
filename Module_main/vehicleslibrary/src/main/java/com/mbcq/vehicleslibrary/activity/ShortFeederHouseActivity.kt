package com.mbcq.vehicleslibrary.activity


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.departurerecord.BaseShortFeederHouseActivity
import kotlinx.android.synthetic.main.activity_short_feeder_house.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-15 10:12:09
 * 短驳计划装车
 */
@Route(path = ARouterConstants.ShortFeederHouseActivity)
class ShortFeederHouseActivity : BaseShortFeederHouseActivity<ShortFeederHouseContract.View, ShortFeederHousePresenter>(), ShortFeederHouseContract.View {
    @Autowired(name = "ShortFeederHouse")
    @JvmField
    var mLastDataJson: String = ""


    override fun getLayoutId(): Int = R.layout.activity_short_feeder_house

    @SuppressLint("SetTextI18n")
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        initRestartFragment(savedInstanceState)
        initTab()
        val mLastData = JSONObject(mLastDataJson)
        departure_lot_tv.text = "发车批次: ${mLastData.optString("inOneVehicleFlag")}"
        setTabSelection(0)

    }

    override fun onClick() {
        super.onClick()
        short_feeder_house_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }
}