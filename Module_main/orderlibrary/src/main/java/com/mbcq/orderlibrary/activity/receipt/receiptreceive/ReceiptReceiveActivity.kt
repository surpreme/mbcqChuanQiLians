package com.mbcq.orderlibrary.activity.receipt.receiptreceive


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.BaseListMVPActivity
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
import com.mbcq.orderlibrary.activity.receipt.receiptconsignment.ReceiptConsignmentBean
import kotlinx.android.synthetic.main.activity_receipt_receive.*
import java.lang.StringBuilder

/**
 * @author: lzy
 * @time: 2020-11-11 08:58:16 回单接收
 */

@Route(path = ARouterConstants.ReceiptReceiveActivity)
class ReceiptReceiveActivity : BaseSmartMVPActivity<ReceiptReceiveContract.View, ReceiptReceivePresenter, ReceiptReceiveBean>(), ReceiptReceiveContract.View {
    var mStartDateTag = ""
    var mEndDateTag = ""
    var mShippingOutletsTag = ""

    override fun getLayoutId(): Int = R.layout.activity_receipt_receive

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
        mSmartRefreshLayout.setEnableLoadMore(false)

    }

    override fun getPageDatas(mCurrentPage: Int) {
        super.getPageDatas(mCurrentPage)
        mPresenter?.getPage(mCurrentPage, mShippingOutletsTag, mStartDateTag, mEndDateTag)

    }

    override fun onClick() {
        super.onClick()
        receipt_receive_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View) {
                hideKeyboard(v)
                val selectStr = StringBuilder()
                val mmmData = adapter.getAllData()
                for ((index, item) in mmmData.withIndex()) {
                    if (item.isChecked) {
                        selectStr.append(item.billno)
                    }
                    if (index != mmmData.size) {
                        selectStr.append(",")
                    }

                }
                if (selectStr.toString().replace(",", "").isBlank()) {
                    showToast("请至少选择一个运单进行操作")
                    return
                }
                ReceiptReceiveCompleteDialog(getScreenWidth(), object : ReceiptReceiveCompleteDialog.OnResultInterface {
                    override fun onResult(mReceiveDate: String, mState: String) {

                        val mDataList = mutableListOf<ReceiptReceiveBean>()

                        for (item in mmmData) {
                            if (item.isChecked) {
                                //TODO
                                item.receiveDate = mReceiveDate//接收时间
                                mDataList.add(item)
                            }
                        }

                        mPresenter?.complete(Gson().toJson(mDataList))
                    }

                }).show(supportFragmentManager, "ReceiptReceiveCompleteDialog")
            }

        })

        receipt_receive_toolbar.setRightButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                WebDbUtil.getDbWebId(application, object : WebsDbInterface {
                    override fun isNull() {
                    }

                    override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                        FilterWithTimeDialog(getScreenWidth(), Gson().toJson(list), "webid", "webidCode", true, "回单接收筛选", true, mClickInterface = object : OnClickInterface.OnClickInterface {
                            override fun onResult(s1: String, s2: String) {
                                mShippingOutletsTag = s1
                                val timeList = s2.split("@")
                                if (timeList.isNotEmpty() && timeList.size == 2) {
                                    mStartDateTag = timeList[0]
                                    mEndDateTag = timeList[1]
                                }
                                refresh()
                            }

                        }).show(supportFragmentManager, "ReceiptReceiveActivityFilterWithTimeDialog")
                    }
                })

            }

        })
        receipt_receive_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    override fun getSmartLayoutId(): Int = R.id.receipt_receive_smart
    override fun getSmartEmptyId(): Int = R.id.receipt_receive_smart_frame
    override fun getRecyclerViewId(): Int = R.id.receipt_receive_recycler
    override fun setAdapter(): BaseRecyclerAdapter<ReceiptReceiveBean> = ReceiptReceiveAdapter(mContext).also {
        receipt_receive_checkbox.setOnCheckedChangeListener { _, isChecked ->
            it.checkedAll(isChecked)
        }
        it.mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                ARouter.getInstance().build(ARouterConstants.ReceiptInformationActivity).withString("", mResult).navigation()
            }

        }
    }

    override fun getPageS(list: List<ReceiptReceiveBean>) {
        appendDatas(list)
    }

    override fun completeS(result: String) {
        TalkSureDialog(mContext, getScreenWidth(), "回单接收成功，点击返回！") {
            onBackPressed()
        }.show()
    }
}