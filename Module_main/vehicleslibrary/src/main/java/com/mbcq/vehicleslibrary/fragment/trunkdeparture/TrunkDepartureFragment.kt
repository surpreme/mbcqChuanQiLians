package com.mbcq.vehicleslibrary.fragment.trunkdeparture


import android.annotation.SuppressLint
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.interfaces.RxBus
import com.mbcq.baselibrary.ui.BaseSmartMVPFragment
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.activity.departurerecord.DepartureRecordEvent
import com.mbcq.vehicleslibrary.R
import kotlinx.android.synthetic.main.fragment_trunk_departure.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author: lzy
 * @time: 2020-09-12 15:44:58
 * 干线发车记录
 */

class TrunkDepartureFragment : BaseSmartMVPFragment<TrunkDepartureContract.View, TrunkDeparturePresenter, TrunkDepartureBean>(), TrunkDepartureContract.View {
    var mStartDateTag = ""
    var mEndDateTag = ""
    var mShippingOutletsTag = ""//发货网点
    override fun getSmartLayoutId(): Int = R.id.trunk_departure_smart

    override fun getSmartEmptyId(): Int = R.id.trunk_departure_smart_frame

    override fun getRecyclerViewId(): Int = R.id.trunk_departure_recycler

    override fun setAdapter(): BaseRecyclerAdapter<TrunkDepartureBean> = TrunkDepartureAdapter(mContext)

    override fun getLayoutResId(): Int = R.layout.fragment_trunk_departure
    override fun getTrunkDepartureS(list: List<TrunkDepartureBean>) {
        appendDatas(list)
    }

    @SuppressLint("SimpleDateFormat")
    override fun initExtra() {
        super.initExtra()
        mContext?.let {
            val mDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            val mDate = Date(System.currentTimeMillis())
            val format = mDateFormat.format(mDate)
            mStartDateTag = "$format 00:00:00"
            mEndDateTag = "$format 23:59:59"
            mShippingOutletsTag = UserInformationUtil.getWebIdCode(it) + ","

        }

    }

    override fun onClick() {
        super.onClick()
        add_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                ARouter.getInstance().build(ARouterConstants.AddTrunkDepartureActivity).navigation()
            }

        })

    }

    @SuppressLint("CheckResult")
    override fun initDatas() {
        super.initDatas()
        RxBus.build().toObservable(this, DepartureRecordEvent::class.java).subscribe { msg ->
            if (msg.type == 1) {
                mShippingOutletsTag = msg.webCode
                mStartDateTag = msg.startTime
                mEndDateTag = msg.endTime
                refresh()
            }

        }
    }

    override fun getPageDatas(mCurrentPage: Int) {
        super.getPageDatas(mCurrentPage)
        mPresenter?.getTrunkDeparture(mCurrentPage, mShippingOutletsTag, mStartDateTag, mEndDateTag)

    }
}