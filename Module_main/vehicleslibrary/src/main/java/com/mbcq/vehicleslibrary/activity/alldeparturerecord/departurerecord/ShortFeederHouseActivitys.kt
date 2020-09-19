package com.mbcq.vehicleslibrary.activity.alldeparturerecord.departurerecord


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.tabs.TabLayout
import com.mbcq.baselibrary.ui.BaseActivity
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.vehicleslibrary.R
import kotlinx.android.synthetic.main.activity_short_feeder_house.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-15 10:12:09
 */
//@Route(path = ARouterConstants.ShortFeederHouseActivity)
class ShortFeederHouseActivitys : BaseActivity(){


    @Autowired(name = "ShortFeederHouse")
    @JvmField
    var mLastDataJson: String = ""

    override fun getLayoutId(): Int = R.layout.activity_short_feeder_house
    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)

    }





    @SuppressLint("SetTextI18n")
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        short_feeder_house_tabLayout.addTab(short_feeder_house_tabLayout.newTab().setText("库存清单(12)"))
        short_feeder_house_tabLayout.addTab(short_feeder_house_tabLayout.newTab().setText("配载清单(2)"))
        val mLastData = JSONObject(mLastDataJson)
        departure_lot_tv.text = "发车批次: ${mLastData.optString("inOneVehicleFlag")}"
        short_feeder_house_tabLayout.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                if(tab.text.toString().contains("库存清单")){

                }else if(tab.text.toString().contains("配载清单")){
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
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