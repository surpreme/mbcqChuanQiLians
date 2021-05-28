package com.mbcq.orderlibrary.activity.receipt.receiptconsignment


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.BaseSmartMVPActivity
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
import kotlinx.android.synthetic.main.activity_receipt_consignment.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.StringBuilder


/**
 * @author: lzy
 * @time: 2020-11-10 18:11:43 回单寄出
 */

@Route(path = ARouterConstants.ReceiptConsignmentActivity)
class ReceiptConsignmentActivity : BaseSmartMVPActivity<ReceiptConsignmentContract.View, ReceiptConsignmentPresenter, ReceiptConsignmentBean>(), ReceiptConsignmentContract.View {
    var mStartDateTag = ""
    var mEndDateTag = ""
    var mShippingOutletsTag = ""

    override fun getLayoutId(): Int = R.layout.activity_receipt_consignment

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
        receipt_consignment_btn.setOnClickListener(object : SingleClick() {
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
                ReceiptConsignmentCompleteDialog(getScreenWidth(), object : ReceiptConsignmentCompleteDialog.OnResultInterface {
                    override fun onResult(mSendOutDate: String, mSendOutCompanyId: String, mSendOutWbidCode: String, mSendOutWbidCodeStr: String, mGiveOutCondition: String) {
                        val mDataList = mutableListOf<ReceiptConsignmentBean>()

                        for (item in mmmData) {
                            if (item.isChecked) {
                                item.sendOutDate = mSendOutDate//寄出时间
//                            item.sendOutCompanyId = mSendOutCompanyId//寄出公司id
                                item.receiveWebidCode = mSendOutWbidCode.toInt()//寄到网点编码
                                item.receiveWebidCodeStr = mSendOutWbidCodeStr//寄到网点编码
                                item.giveOutCondition = mGiveOutCondition//寄出备注
                                mDataList.add(item)
                            }
//                              jsonObj.put("SendOutDate", mSendOutDate)//寄出时间
//                              jsonObj.put("SendOutCompanyId", mSendOutCompanyId)//寄出公司id
//                              jsonObj.put("SendOutWbidCode", mSendOutWbidCode)//寄出网点编码
//                              jsonObj.put("SendOutWbidCodeStr", mSendOutWbidCodeStr)//寄出网点
//                              jsonObj.put("GiveOutCondition", mGiveOutCondition)//寄出备注
//                              jarry.put(jsonObj)
                        }

                        mPresenter?.complete(Gson().toJson(mDataList))
                    }

                }).show(supportFragmentManager, "ReceiptConsignmentCompleteDialog")
            }

        })
        receipt_consignment_toolbar.setRightButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                WebDbUtil.getDbWebId(application, object : WebsDbInterface {
                    override fun isNull() {

                    }

                    override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                        FilterWithTimeDialog(getScreenWidth(), Gson().toJson(list), "webid", "webidCode", true, "回单签收筛选", true, mClickInterface = object : OnClickInterface.OnClickInterface {
                            override fun onResult(s1: String, s2: String) {
                                mShippingOutletsTag = s1
                                val timeList = s2.split("@")
                                if (timeList.isNotEmpty() && timeList.size == 2) {
                                    mStartDateTag = timeList[0]
                                    mEndDateTag = timeList[1]
                                }
                                refresh()
                            }

                        }).show(supportFragmentManager, "ReceiptSignActivityFilterWithTimeDialog")
                    }

                })

            }

        })
        receipt_consignment_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }


    override fun getSmartLayoutId(): Int = R.id.receipt_consignment_smart
    override fun getSmartEmptyId(): Int = R.id.receipt_consignment_smart_frame
    override fun getRecyclerViewId(): Int = R.id.receipt_consignment_recycler
    override fun setAdapter(): BaseRecyclerAdapter<ReceiptConsignmentBean> = ReceiptConsignmentAdapter(mContext).also {
        receipt_consignment_checkbox.setOnCheckedChangeListener { _, isChecked ->
            it.checkedAll(isChecked)
        }
        it.mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                ARouter.getInstance().build(ARouterConstants.ReceiptInformationActivity).withString("billno", mResult).navigation()
            }

        }
    }

    override fun getPageS(list: List<ReceiptConsignmentBean>) {
        appendDatas(list)
    }

    override fun completeS(result: String) {
        refresh()
    }
}