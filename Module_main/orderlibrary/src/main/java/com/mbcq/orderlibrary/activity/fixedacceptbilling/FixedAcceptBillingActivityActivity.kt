package com.mbcq.orderlibrary.activity.fixedacceptbilling


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.RadioGroupUtil
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.*
import org.json.JSONArray

/**
 * @author: lzy
 * @time: 2020-10-17 13:32:00 改单申请
 */

@Route(path = ARouterConstants.FixedAcceptBillingActivityActivity)
class FixedAcceptBillingActivityActivity : BaseMVPActivity<FixedAcceptBillingActivityContract.View, FixedAcceptBillingActivityPresenter>(), FixedAcceptBillingActivityContract.View {
    var mTransneed = ""//运输类型编码
    var mTransneedStr = ""//运输类型

    var mAccType = ""//付款方式编码
    var mAccTypeStr = ""//付款方式

    override fun getLayoutId(): Int = R.layout.activity_fixed_accept_billing_activity
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getPaymentMode()
        mPresenter?.getTransportMode()
    }
    override fun onClick() {
        super.onClick()
        fixed_accept_billing_toolbar.setBackButtonOnClickListener(object:SingleClick(){
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }
    override fun getTransportModeS(result: String) {
        val mTransportModeArray = JSONArray(result)
        /**
         * 默认数据
         */
        if (!mTransportModeArray.isNull(0)) {
            mTransportModeArray.optJSONObject(0)?.let {
                mTransneed = it.optString("typecode")
                mTransneedStr = it.optString("tdescribe")
            }
        }
        /**
         * 添加数据到view
         */
        for (mIndex in 0 until mTransportModeArray.length()) {
            val obj = mTransportModeArray.optJSONObject(mIndex)
            obj?.let {
                transport_method_rg.addView(RadioGroupUtil.addSelectItem(mContext, it.optString("tdescribe"), mIndex))
            }
        }
        transport_method_rg.check(0)
        /**
         * 选中后的操作
         */
        transport_method_rg.setOnCheckedChangeListener { _, checkedId ->
            mTransneed = mTransportModeArray.getJSONObject(checkedId).optString("typecode")
            mTransneedStr = mTransportModeArray.getJSONObject(checkedId).optString("tdescribe")
        }
    }
    override fun getPaymentModeS(result: String) {
        val mPaymentModeArray = JSONArray(result)

        /**
         * 如果有提付默认选中 如果没有提付选中第一个
         */
        var mWithdrawIndex = 0
        /**
         * 添加数据到view
         */
        for (mIndex in 0 until mPaymentModeArray.length()) {
            val obj = mPaymentModeArray.optJSONObject(mIndex)
            obj?.let {
                if (obj.optString("tdescribe") == "提付") {
                    mWithdrawIndex = mIndex
                }
                pay_way_title_rg.addView(RadioGroupUtil.addSelectItem(mContext, it.optString("tdescribe"), mIndex))
            }
        }

        pay_way_title_rg.check(mWithdrawIndex)
        /**
         * 默认数据
         */
        if (!mPaymentModeArray.isNull(mWithdrawIndex)) {
            mPaymentModeArray.optJSONObject(mWithdrawIndex)?.let {
                mAccType = it.optString("typecode")
                mAccTypeStr = it.optString("tdescribe")
            }
        }
        /**
         * 选中后的操作
         */
        pay_way_title_rg.setOnCheckedChangeListener { _, checkedId ->
            mAccType = mPaymentModeArray.getJSONObject(checkedId).optString("typecode")
            mAccTypeStr = mPaymentModeArray.getJSONObject(checkedId).optString("tdescribe")
        }
    }
}