package com.mbcq.amountlibrary.activity.loanchange


import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.amountlibrary.R
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.KeyBoardUtil
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import kotlinx.android.synthetic.main.activity_loan_change.*
import org.json.JSONObject


/**
 * @author: lzy
 * @time: 2020-11-16 10:16:03 贷款变更
 */

@Route(path = ARouterConstants.LoanChangeActivity)
class LoanChangeActivity : BaseMVPActivity<LoanChangeContract.View, LoanChangePresenter>(), LoanChangeContract.View {
    private var mKeyBoardUtil: KeyBoardUtil? = null

    override fun getLayoutId(): Int = R.layout.activity_loan_change
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        mKeyBoardUtil = KeyBoardUtil(this)
        mKeyBoardUtil?.attachTo(order_number_ed, true)

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onClick() {
        super.onClick()
        order_number_ed.setOnTouchListener { v, event ->
            order_number_ed.isFocusable = true
            hideKeyboard(order_number_ed)
            mKeyBoardUtil?.showSoftKeyboard()
            false

        }
        order_number_ed.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(s: Editable?) {
                if (order_before_tv.text.toString().isNotBlank()) {
                    if (order_number_ed.text.toString().isBlank()) {
                        order_end_tv.text = order_before_tv.text.toString()
                        return
                    }

                    if (order_number_ed.text.toString().startsWith("-")) {
                        if (order_number_ed.text.toString().length > 1)
                            order_end_tv.text = haveTwoDouble(order_before_tv.text.toString().toDouble() - (order_number_ed.text.toString().substring(1, order_number_ed.text.toString().length)).toDouble())
                    } else {
                        order_end_tv.text = haveTwoDouble(order_before_tv.text.toString().toDouble() + (order_number_ed.text.toString().toDouble()))

                    }

                }


            }

        })
        search_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (billno_ed.text.toString().isBlank()) {
                    showToast("请检查您输入的运单信息")
                    return
                }
                mPresenter?.getWaybillDetail(billno_ed.text.toString())
            }

        })
        commit_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                when {
                    billno_ed.text.toString().isBlank() -> {
                        showToast("请输入运单号")
                        return
                    }
                    order_number_ed.text.toString().isBlank() -> {
                        showToast("请输入变更金额")
                        return
                    }
                    order_change_reason_tv.text.toString().isBlank() -> {
                        showToast("请输入变更原因")
                        return
                    }
                    order_before_tv.text.toString().isBlank() -> {
                        return
                    }
                }
                mPrintObj.remove("accHKChange")
                mPrintObj.remove("hkChangeReason")
                mPrintObj.put("accHKChange", order_number_ed.text.toString())// 货款变更金额
                mPrintObj.put("hkChangeReason", order_change_reason_tv.text.toString())// 货款变更原因
                mPresenter?.changeOrder(mPrintObj)
            }

        })
        loan_change_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                finish()
            }

        })
    }

    var mPrintObj = JSONObject()

    @SuppressLint("SetTextI18n")
    override fun getWaybillDetailS(obj: JSONObject) {
        basic_info_tv.text = "货款状态：xxxxxx${obj.optString("billno")}\n开单日期：${obj.optString("billDate")}\n发货网点：${obj.optString("webidCodeStr")}\n发  货 人：${obj.optString("shipper")}\n到货网点：${obj.optString("ewebidCodeStr")}\n收  货 人：${obj.optString("consignee")}"
        order_before_tv.text = obj.optString("accDaiShou")
        mPrintObj = obj
    }

    override fun getWaybillDetailNull() {
        TalkSureDialog(mContext, getScreenWidth(), "请检查您的运单号是否存在，然后稍后再试！").show()
    }

    override fun changeOrderS(result: String) {
        TalkSureDialog(mContext, getScreenWidth(), "变更成功，点击返回！") {
            onBackPressed()
        }.show()
    }

    override fun onBackPressed() {
        if (mKeyBoardUtil?.isShowings!!) {
            mKeyBoardUtil?.hideSoftKeyboard()
        } else
            super.onBackPressed()

    }
}