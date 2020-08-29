package com.mbcq.orderlibrary.activity.location


import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.orderlibrary.R

/**
 * @author: lzy
 * @time: 2018.08.25
 */

class LocationActivity : BaseMVPActivity<LocationContract.View, LocationPresenter>(), LocationContract.View {
    override fun getLayoutId(): Int = R.layout.activity_location
}