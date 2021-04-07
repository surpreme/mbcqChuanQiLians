package com.mbcq.orderlibrary.activity.acceptbillingrecording


import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.dialog.popup.XDialog
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.BaseSmartMVPActivity
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.util.system.TimeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.FilterTimeUtils
import com.mbcq.commonlibrary.WebDbUtil
import com.mbcq.commonlibrary.WebsDbInterface
import com.mbcq.commonlibrary.adapter.BaseTextAdapterBean
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.BottomOptionsDialog
import com.mbcq.commonlibrary.dialog.FilterWithTimeDialog
import com.mbcq.commonlibrary.scan.scanlogin.ScanDialogFragment
import com.mbcq.orderlibrary.R
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.activity_accept_billing_recording.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author: lzy
 * @time: 2020-10-17 09:58:12  改单申请 记录
 * */

@Route(path = ARouterConstants.AcceptBillingRecordingActivity)
class AcceptBillingRecordingActivity : BaseSmartMVPActivity<AcceptBillingRecordingContract.View, AcceptBillingRecordingPresenter, AcceptBillingRecordingBean>(), AcceptBillingRecordingContract.View {
    lateinit var rxPermissions: RxPermissions

    var mStartDateTag = ""
    var mEndDateTag = ""
    var mShippingOutletsTag = ""

    override fun getLayoutId(): Int = R.layout.activity_accept_billing_recording
    override fun getSmartLayoutId(): Int = R.id.accept_billing_recording_smart
    override fun getSmartEmptyId(): Int = R.id.accept_billing_recording_smart_frame
    override fun getRecyclerViewId(): Int = R.id.accept_billing_recording_recycler

    @SuppressLint("SimpleDateFormat")
    override fun initExtra() {
        super.initExtra()
        mStartDateTag = FilterTimeUtils.getStartTime(7)
        mEndDateTag = FilterTimeUtils.getEndTime()
        mShippingOutletsTag = UserInformationUtil.getWebIdCode(mContext)
        rxPermissions = RxPermissions(this)

    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun getPageDatas(mCurrentPage: Int) {
        super.getPageDatas(mCurrentPage)
        mPresenter?.getPage(mCurrentPage, mShippingOutletsTag, mStartDateTag, mEndDateTag)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REFRESH_DATA_CODE || requestCode == ACCEPT_BILLING_FIXED_REVIEW_REFRESH_TAG) {
            refresh()
        }
    }

    override fun onClick() {
        super.onClick()
        scan_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                getCameraPermission()
            }

        })
        type_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                val list = mutableListOf<BaseTextAdapterBean>()
                for (index in 0..2) {
                    val mBaseTextAdapterBean = BaseTextAdapterBean()
                    mBaseTextAdapterBean.title = if (index == 0) "全部" else if (index == 1) "待运营审核" else "待财务审核"
                    mBaseTextAdapterBean.tag = index.toString()
                    list.add(mBaseTextAdapterBean)
                }
                XDialog.Builder(mContext)
                        .setContentView(R.layout.dialog_bottom_options)
//                        .setWidth(type_tv.width)
                        .setIsDarkWindow(false)
                        .asCustom(BottomOptionsDialog(mContext, list).also {
                            it.mOnRecyclerClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
                                override fun onItemClick(v: View, position: Int, mResult: String) {
                                    type_tv.text = if (mResult == "0") "全部" else if (mResult == "1") "待运营审核" else "待财务审核"
                                }

                            }
                        })
                        .showUp(type_tv)

            }

        })
        change_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                ARouter.getInstance().build(ARouterConstants.FixedAcceptBillingActivity).navigation(this@AcceptBillingRecordingActivity, REFRESH_DATA_CODE)
            }

        })
        accept_billing_recording_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
        accept_billing_recording_toolbar.setRightButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                WebDbUtil.getDbWebId(application, object : WebsDbInterface {
                    override fun isNull() {

                    }

                    override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                        FilterWithTimeDialog(getScreenWidth(), Gson().toJson(list), "webid", "webidCode", true, "到货库存记录筛选", true, mClickInterface = object : OnClickInterface.OnClickInterface {
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

                        }).show(supportFragmentManager, "mArrivalInventoryActivityFilterWithTimeDialog")
                    }

                })
            }

        })
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

    override fun addItemDecoration(): RecyclerView.ItemDecoration = object : BaseItemDecoration(mContext) {
        override fun configExtraSpace(position: Int, count: Int, rect: Rect) {
            rect.top = ScreenSizeUtils.dp2px(mContext, 10f)
        }

        override fun doRule(position: Int, rect: Rect) {
            rect.bottom = rect.top
        }

    }

    companion object {
        /**
         * 刷新 审核
         */
        const val ACCEPT_BILLING_FIXED_REVIEW_REFRESH_TAG = 4511

        /**
         * 刷新 修改
         */
        const val REFRESH_DATA_CODE = 1730
    }

    override fun setAdapter(): BaseRecyclerAdapter<AcceptBillingRecordingBean> = AcceptBillingRecordingAdapter(mContext).also {
        it.mOnFixedAcceptBillingRecordingInterface = object : AcceptBillingRecordingAdapter.OnFixedAcceptBillingRecordingInterface {
            override fun onReject(v: View, position: Int, data: AcceptBillingRecordingBean) {
                TalkSureCancelDialog(mContext, getScreenWidth(), "您确定要驳回申请网点为${data.opeWebidCodeStr}，申请人为${data.opeMan}，运单号为${data.billno}的记录吗？") {
                    mPresenter?.rejectOrder(data.billno, data.id.toString(), position)
                }.show()

            }

            override fun onReview(v: View, position: Int, data: AcceptBillingRecordingBean) {
                if (data.yyCheckMan.isBlank()) {
                    data.fixType = 1
                    ARouter.getInstance().build(ARouterConstants.AcceptBillingFixedReviewActivity).withString("AcceptBillingFixedReview", Gson().toJson(data)).navigation(this@AcceptBillingRecordingActivity, ACCEPT_BILLING_FIXED_REVIEW_REFRESH_TAG)
                } else if (data.cwCheckMan.isBlank() && data.isUpdMon == 1) {
                    data.fixType = 2
                    ARouter.getInstance().build(ARouterConstants.AcceptBillingFixedReviewActivity).withString("AcceptBillingFixedReview", Gson().toJson(data)).navigation(this@AcceptBillingRecordingActivity, ACCEPT_BILLING_FIXED_REVIEW_REFRESH_TAG)
                }
            }

            override fun onOperationReview(v: View, position: Int, data: AcceptBillingRecordingBean) {
                data.fixType = 1
                ARouter.getInstance().build(ARouterConstants.AcceptBillingFixedReviewActivity).withString("AcceptBillingFixedReview", Gson().toJson(data)).navigation(this@AcceptBillingRecordingActivity, ACCEPT_BILLING_FIXED_REVIEW_REFRESH_TAG)
            }

            override fun onFinancialAudit(v: View, position: Int, data: AcceptBillingRecordingBean) {
                data.fixType = 2
                ARouter.getInstance().build(ARouterConstants.AcceptBillingFixedReviewActivity).withString("AcceptBillingFixedReview", Gson().toJson(data)).navigation(this@AcceptBillingRecordingActivity, ACCEPT_BILLING_FIXED_REVIEW_REFRESH_TAG)
            }

            override fun onLookMoreInfo(v: View, position: Int, data: AcceptBillingRecordingBean) {
                data.fixType = 3
                ARouter.getInstance().build(ARouterConstants.AcceptBillingFixedReviewActivity).withString("AcceptBillingFixedReview", Gson().toJson(data)).navigation()
            }

        }
    }

    override fun getPageS(list: List<AcceptBillingRecordingBean>) {
        appendDatas(list)
    }

    override fun rejectOrderS(result: String, position: Int) {
        adapter.removeItem(position)

    }
}