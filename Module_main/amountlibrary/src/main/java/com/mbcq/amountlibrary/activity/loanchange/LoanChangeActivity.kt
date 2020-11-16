package com.mbcq.amountlibrary.activity.loanchange


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.amountlibrary.R
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import kotlinx.android.synthetic.main.activity_loan_change.*

/**
 * @author: lzy
 * @time: 2020-11-16 10:16:03 贷款变更
 */

@Route(path = ARouterConstants.LoanChangeActivity)
class LoanChangeActivity : BaseMVPActivity<LoanChangeContract.View, LoanChangePresenter>(), LoanChangeContract.View {
    override fun getLayoutId(): Int = R.layout.activity_loan_change
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun onClick() {
        super.onClick()
        loan_change_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }
}