package com.mbcq.orderlibrary.activity.receipt.receiptgeneralledger


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.BaseSmartMVPActivity
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.system.TimeUtils
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.WebDbUtil
import com.mbcq.commonlibrary.WebsDbInterface
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterWithTimeDialog
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_receipt_general_ledger.*

/**
 * @author: lzy
 * @time: 2020-11-12 09:23:05 回单总账
 */

@Route(path = ARouterConstants.ReceiptGeneralLedgerActivity)
class ReceiptGeneralLedgerActivity : BaseSmartMVPActivity<ReceiptGeneralLedgerContract.View, ReceiptGeneralLedgerPresenter, ReceiptGeneralLedgerBean>(), ReceiptGeneralLedgerContract.View {
    var mStartDateTag = ""
    var mEndDateTag = ""
    var mShippingOutletsTag = ""

    override fun getLayoutId(): Int = R.layout.activity_receipt_general_ledger

    @SuppressLint("SimpleDateFormat")
    override fun initExtra() {
        super.initExtra()
        mStartDateTag = "${TimeUtils.getLastdayStr(7)} 00:00:00"
        mEndDateTag = "${TimeUtils.getCurrentYYMMDD()} 23:59:59"
        mShippingOutletsTag = UserInformationUtil.getWebIdCode(mContext)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun getPageDatas(mCurrentPage: Int) {
        super.getPageDatas(mCurrentPage)
        mPresenter?.getPage(mCurrentPage)

    }

    override fun onClick() {
        super.onClick()
        receipt_general_ledger_toolbar.setRightButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                WebDbUtil.getDbWebId(application, object : WebsDbInterface {
                    override fun isNull() {

                    }

                    override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                        FilterWithTimeDialog(getScreenWidth(), Gson().toJson(list), "webid", "webidCode", true, "回单总账筛选", true, mClickInterface = object : OnClickInterface.OnClickInterface {
                            override fun onResult(s1: String, s2: String) {
                                mShippingOutletsTag = s1
                                val timeList = s2.split("@")
                                if (timeList.isNotEmpty() && timeList.size == 2) {
                                    mStartDateTag = timeList[0]
                                    mEndDateTag = timeList[1]
                                }
                                refresh()
                            }

                        }).show(supportFragmentManager, "ReceiptGeneralLedgerActivityFilterWithTimeDialog")
                    }

                })
            }

        })
        receipt_general_ledger_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    override fun getSmartLayoutId(): Int = R.id.receipt_general_ledger_smart
    override fun getSmartEmptyId(): Int = R.id.receipt_general_ledger_smart_frame
    override fun getRecyclerViewId(): Int = R.id.receipt_general_ledger_recycler
    override fun setAdapter(): BaseRecyclerAdapter<ReceiptGeneralLedgerBean> = ReceiptGeneralLedgerAdapter(mContext).also {
        it.mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                ARouter.getInstance().build(ARouterConstants.ReceiptInformationActivity).withString("billno", mResult).navigation()
            }

        }
    }

    override fun getPageS(list: List<ReceiptGeneralLedgerBean>) {
        appendDatas(list)
    }
}