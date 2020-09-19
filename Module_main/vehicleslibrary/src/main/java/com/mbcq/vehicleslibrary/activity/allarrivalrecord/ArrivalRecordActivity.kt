package com.mbcq.vehicleslibrary.activity.allarrivalrecord


import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.vehicleslibrary.R

/**
 * @author: lzy
 * @time: 2020-09-09 17:22:09
 * 到车记录
 */

class ArrivalRecordActivity : BaseMVPActivity<ArrivalRecordContract.View, ArrivalRecordPresenter>(), ArrivalRecordContract.View {
    override fun getLayoutId(): Int = R.layout.activity_arrival_record
}