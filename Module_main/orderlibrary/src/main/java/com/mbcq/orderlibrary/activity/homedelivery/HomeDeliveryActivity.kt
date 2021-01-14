package com.mbcq.orderlibrary.activity.homedelivery


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.ui.BaseSmartMVPActivity
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.FilterTimeUtils
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_home_delivery.*

/**
 * @author: lzy
 * @time: 2020-01-14 15:44:03 上门提货
 */

@Route(path = ARouterConstants.HomeDeliveryActivity)
class HomeDeliveryActivity : BaseSmartMVPActivity<HomeDeliveryContract.View, HomeDeliveryPresenter, HomeDeliveryBean>(), HomeDeliveryContract.View {
    var mStartDateTag = ""
    var mEndDateTag = ""
    var mShippingOutletsTag = ""

    override fun getLayoutId(): Int = R.layout.activity_home_delivery

    @SuppressLint("SimpleDateFormat")
    override fun initExtra() {
        super.initExtra()
        mStartDateTag = FilterTimeUtils.getStartTime(7)
        mEndDateTag = FilterTimeUtils.getEndTime()
        mShippingOutletsTag = UserInformationUtil.getWebIdCode(mContext)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun getPageDatas(mCurrentPage: Int) {
        super.getPageDatas(mCurrentPage)
        mPresenter?.getPage(mCurrentPage, mShippingOutletsTag, mStartDateTag, mEndDateTag)

    }

    override fun onClick() {
        super.onClick()
        add_btn.apply {
            onSingleClicks {
                ARouter.getInstance().build(ARouterConstants.AddHomeDeliveryActivity).navigation()
            }
        }
        home_delivery_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    override fun getSmartLayoutId(): Int = R.id.home_delivery_recording_smart
    override fun getSmartEmptyId(): Int = R.id.home_delivery_recording_smart_frame
    override fun getRecyclerViewId(): Int = R.id.home_delivery_recording_recycler

    override fun setAdapter(): BaseRecyclerAdapter<HomeDeliveryBean> = HomeDeliveryAdapter(mContext)
    override fun getPageS(list: List<HomeDeliveryBean>) {
        appendDatas(list)

    }
}