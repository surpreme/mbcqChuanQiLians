package com.mbcq.orderlibrary.activity.controlmanagement


import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
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
import com.mbcq.commonlibrary.WebDbUtil
import com.mbcq.commonlibrary.WebsDbInterface
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterWithTimeDialog
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_control_management.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author: lzy
 * @time: 2020-10-16 13:10:06 控货管理
 */

@Route(path = ARouterConstants.ControlManagementActivity)
class ControlManagementActivity : BaseSmartMVPActivity<ControlManagementContract.View, ControlManagementPresenter, ControlManagementBean>(), ControlManagementContract.View {
    private var isCanRefreshTotalTitle = true
    private var mStartDateTag = ""
    private var mEndDateTag = ""
    private var mShippingOutletsTag = ""//发货网点

    override fun getLayoutId(): Int = R.layout.activity_control_management

    @SuppressLint("SimpleDateFormat")
    override fun initExtra() {
        super.initExtra()
        val mDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val mDate = Date(System.currentTimeMillis())
        val format = mDateFormat.format(mDate)
        mStartDateTag = "${TimeUtils.getLastdayStr(7)} 23:59:59"
        mEndDateTag = "$format 00:00:00"

        mShippingOutletsTag = UserInformationUtil.getWebIdCode(mContext)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun getPageDatas(mCurrentPage: Int) {
        super.getPageDatas(mCurrentPage)
        mPresenter?.getPageData(mCurrentPage, mShippingOutletsTag, mStartDateTag, mEndDateTag)

    }

    override fun onClick() {
        super.onClick()
        operating_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                var checkedNumber = ""
                for (item in adapter.getAllData()) {
                    if (item.checked) {
                        checkedNumber = item.billno
                        break
                    }

                }
                ARouter.getInstance().build(ARouterConstants.DeliveryAdjustmentActivity).withString("DeliveryAdjustment",checkedNumber).navigation()

            }

        })
        control_management_toolbar.setRightButtonOnClickListener(object:SingleClick(2000){
            override fun onSingleClick(v: View?) {
                WebDbUtil.getDbWebId(application, object : WebsDbInterface {
                    override fun isNull() {

                    }

                    override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                        FilterWithTimeDialog(getScreenWidth(), Gson().toJson(list), "webid", "webidCode", true, "签收记录筛选", true, mClickInterface = object : OnClickInterface.OnClickInterface {
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

                        }).show(supportFragmentManager, "ControlManagementFilterWithTimeDialog")
                    }

                })
            }

        })
        control_management_toolbar.setBackButtonOnClickListener(object : SingleClick() {
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

    override fun setAdapter(): BaseRecyclerAdapter<ControlManagementBean> = ControlManagementAdapter(mContext)
    override fun getSmartLayoutId(): Int = R.id.control_management_smart
    override fun getSmartEmptyId(): Int = R.id.control_management_smart_frame
    override fun getRecyclerViewId(): Int = R.id.control_management_recycler
    var mTotalS = 0
    override fun getPageDataS(list: List<ControlManagementBean>, totalS: String) {
        if (isCanRefreshTotalTitle) {
            control_management_toolbar.setCenterTitleText("控货管理($totalS)")
            if (isInteger(totalS))
                mTotalS = totalS.toInt()
            isCanRefreshTotalTitle = false
        }
        appendDatas(list)
    }
}