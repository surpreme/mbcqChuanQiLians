package com.mbcq.orderlibrary.activity.receipt.receiptsign


import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.BaseSmartMVPActivity
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.util.system.TimeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.WebDbUtil
import com.mbcq.commonlibrary.WebsDbInterface
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterWithTimeDialog
import com.mbcq.commonlibrary.dialog.TimeDialogUtil
import com.mbcq.commonlibrary.scan.scanlogin.ScanDialogFragment
import com.mbcq.orderlibrary.R
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.activity_receipt_sign.*
import java.lang.StringBuilder
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author: lzy
 * @time: 2020-11-07 09:06:21 回单签收
 */

@Route(path = ARouterConstants.ReceiptSignActivity)
class ReceiptSignActivity : BaseSmartMVPActivity<ReceiptSignContract.View, ReceiptSignPresenter, ReceiptSignBean>(), ReceiptSignContract.View {
    var mStartDateTag = ""
    var mEndDateTag = ""
    var mShippingOutletsTag = ""
    lateinit var rxPermissions: RxPermissions

    override fun getLayoutId(): Int = R.layout.activity_receipt_sign


    @SuppressLint("SimpleDateFormat")
    override fun initExtra() {
        super.initExtra()
        rxPermissions = RxPermissions(this)
        val mDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val mDate = Date(System.currentTimeMillis())
        val format = mDateFormat.format(mDate)
        mStartDateTag = "${TimeUtils.getLastdayStr(7)} 00:00:00"
        mEndDateTag = "$format 23:59:59"
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
        receipt_sign_btn.setOnClickListener(object : SingleClick() {
            @SuppressLint("SimpleDateFormat")
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
                TimeDialogUtil.getChoiceTimer(mContext, OnTimeSelectListener { date, _ ->
                    val mDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                    val format: String = mDateFormat.format(date)
//                    annual_review_date_tv.text = format
                    val selectListData = mutableListOf<ReceiptSignBean>()
                    for (item in mmmData) {
                        if (item.isChecked) {
                            item.fetchdate = format
                            selectListData.add(item)
                        }
                    }
                    mPresenter?.complete(Gson().toJson(selectListData))

                }, "选择回单签收时间", isStartCurrentTime = false, isEndCurrentTime = false, isYear = true, isHM = false, isDialog = false).show(receipt_sign_btn)
            }

        })

        scan_order_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                getCameraPermission()
            }

        })
        receipt_sign_toolbar.setRightButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                WebDbUtil.getDbWebId(application, object : WebsDbInterface {
                    override fun isNull() {

                    }

                    override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                        FilterWithTimeDialog(getScreenWidth(), Gson().toJson(list), "webid", "webidCode", true, "回单签收筛选", true, mClickInterface = object : OnClickInterface.OnClickInterface {
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
                                refresh()
                            }

                        }).show(supportFragmentManager, "ReceiptSignActivityFilterWithTimeDialog")
                    }

                })

            }

        })


        receipt_sign_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    override fun addItemDecoration(): RecyclerView.ItemDecoration = object : BaseItemDecoration(mContext) {
        override fun configExtraSpace(position: Int, count: Int, rect: Rect) {
            rect.top = ScreenSizeUtils.dp2px(mContext, 10f)
        }

        override fun doRule(position: Int, rect: Rect) {
            rect.bottom = rect.top
        }

    }

    fun getCameraPermission() {
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe { granted ->
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                        ScanDialogFragment(getScreenWidth(), null, object : OnClickInterface.OnClickInterface {
                            override fun onResult(s1: String, s2: String) {

                            }

                        }).show(supportFragmentManager, "ScanDialogFragment")
                    } else {
                        // Oups permission denied
                        TalkSureDialog(mContext, getScreenWidth(), "权限未赋予！照相机无法启动！请联系在线客服或手动进入系统设置授予摄像头权限！").show()

                    }
                }

    }

    override fun getSmartLayoutId(): Int = R.id.receipt_sign_smart
    override fun getSmartEmptyId(): Int = R.id.receipt_sign_smart_frame
    override fun getRecyclerViewId(): Int = R.id.receipt_sign_recycler
    override fun setAdapter(): BaseRecyclerAdapter<ReceiptSignBean> = ReceiptSignAdapter(mContext).also {
        receipt_sign_checkbox.setOnCheckedChangeListener { _, isChecked ->
            it.checkedAll(isChecked)
        }
        it.mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                ARouter.getInstance().build(ARouterConstants.ReceiptInformationActivity).withString("", mResult).navigation()
            }

        }
    }

    override fun getPageS(list: List<ReceiptSignBean>) {
        appendDatas(list)
    }

    override fun completeS(result: String) {
        TalkSureDialog(mContext, getScreenWidth(), "签收成功，点击返回!") {
            onBackPressed()
        }.show()
    }
}