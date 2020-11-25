package com.mbcq.amountlibrary.activity.loanissuance


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.mbcq.amountlibrary.R
import com.mbcq.amountlibrary.fragment.paymentconfirmation.PaymentConfirmationFragment
import com.mbcq.amountlibrary.fragment.schedulepaymentspending.SchedulePaymentsPendingFragment
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.system.TimeUtils
import com.mbcq.baselibrary.view.FragmentViewPagerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.baselibrary.view.TabBuilder
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.WebDbUtil
import com.mbcq.commonlibrary.WebsDbInterface
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterWithTimeDialog
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.activity_loan_issuance.*

/**
 * @author: lzy
 * @time: 2020-11-20 15:06:43 贷款发放
 */

@Route(path = ARouterConstants.LoanIssuanceActivity)
class LoanIssuanceActivity : BaseMVPActivity<LoanIssuanceContract.View, LoanIssuancePresenter>(), LoanIssuanceContract.View {
    var mStartDateTag = ""
    var mEndDateTag = ""
    var mShippingOutletsTag = ""

    override fun getLayoutId(): Int = R.layout.activity_loan_issuance

    override fun initExtra() {
        super.initExtra()
        mStartDateTag = "${TimeUtils.getLastdayStr(7)} 00:00:00"
        mEndDateTag = "${TimeUtils.getCurrentYYMMDD()} 23:59:59"
        mShippingOutletsTag = UserInformationUtil.getWebIdCode(mContext)

    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        initTab()
    }

    private fun initTab() {
        loan_issuance_tablayout.addTab(loan_issuance_tablayout.newTab().setText("货款回款确认"))
        loan_issuance_tablayout.addTab(loan_issuance_tablayout.newTab().setText("待发款明细表"))
        val fragments = ArrayList<Fragment>()
        fragments.add(PaymentConfirmationFragment())
        fragments.add(SchedulePaymentsPendingFragment())
        val mFragmentViewPagerAdapter = FragmentViewPagerAdapter(supportFragmentManager, fragments)
        loan_issuance_viewpager.adapter = mFragmentViewPagerAdapter
        loan_issuance_viewpager.offscreenPageLimit = loan_issuance_tablayout.tabCount
        //滑动绑定
        loan_issuance_viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(loan_issuance_tablayout))
        loan_issuance_tablayout.addOnTabSelectedListener(object : TabBuilder() {
            override fun onSelected(tab: TabLayout.Tab) {
                loan_issuance_viewpager.currentItem = tab.position
                hideKeyboard(loan_issuance_tablayout)
            }
        })
    }

    override fun onClick() {
        super.onClick()
        loan_issuance_toolbar.setRightButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                WebDbUtil.getDbWebId(application, object : WebsDbInterface {
                    override fun isNull() {

                    }

                    override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                        FilterWithTimeDialog(getScreenWidth(), Gson().toJson(list), "webid", "webidCode", true, "货款总账筛选", true, mClickInterface = object : OnClickInterface.OnClickInterface {
                            /**
                             * s1 网点
                             * s2  start@end
                             */


                            override fun onResult(s1: String, s2: String) {
                                mShippingOutletsTag = s1
                                val timeList = s2.split("@")
                                if (timeList.isNotEmpty() && timeList.size == 2) {
                                    mStartDateTag = timeList[0]
                                    mEndDateTag = timeList[1]
                                }
//                                refresh()

                            }

                        }).show(supportFragmentManager, "LoanIssuanceActivityFilterWithTimeDialog")
                    }
                })
            }
        })
        loan_issuance_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

}