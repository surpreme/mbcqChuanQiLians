package com.mbcq.orderlibrary.activity.choiceshipper


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_choice_shipper.*

/**
 * @author: lzy
 * @time: 2020-11-02 11:06:45  选择发货人
 */

@Route(path = ARouterConstants.ChoiceShipperActivity)
class ChoiceShipperActivity : BaseMVPActivity<ChoiceShipperContract.View, ChoiceShipperPresenter>(), ChoiceShipperContract.View {
    override fun getLayoutId(): Int = R.layout.activity_choice_shipper
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun onClick() {
        super.onClick()
        choice_shipper_toolbar.setBackButtonOnClickListener(object :SingleClick(){
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }
}