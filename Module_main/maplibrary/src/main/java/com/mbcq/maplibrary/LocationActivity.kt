package com.mbcq.maplibrary


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import kotlinx.android.synthetic.main.activity_location.*


/**
 * @author: lzy
 * @time: 2018.08.25
 */

@Route(path = ARouterConstants.LocationActivity)
 class LocationActivity : BaseLocationActivity<LocationContract.View, LocationPresenter>(), LocationContract.View {


    override fun getLayoutId(): Int = R.layout.activity_location
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(0)
        initGaoDeAmapView(savedInstanceState)
    }

    override fun onClick() {
        super.onClick()
        iv_back.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }
}