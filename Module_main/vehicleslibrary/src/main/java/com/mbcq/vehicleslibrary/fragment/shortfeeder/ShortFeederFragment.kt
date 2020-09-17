package com.mbcq.vehicleslibrary.fragment.shortfeeder


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
import com.mbcq.vehicleslibrary.activity.departurerecord.DepartureRecordRefreshEvent
import kotlinx.android.synthetic.main.fragment_short_feeder.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


/**
 * @author: lzy
 * @time: 2020-09-12 13:08：55
 * 短驳发车记录
 */

class ShortFeederFragment : BaseSmartMVPFragment<ShortFeederContract.View, ShortFeederPresenter, ShortFeederBean>(), ShortFeederContract.View {
    var mStartDateTag = ""
    var mEndDateTag = ""
    var mShippingOutletsTag = ""//发货网点
    override fun getLayoutResId(): Int = R.layout.fragment_short_feeder
    override fun getSmartLayoutId(): Int = R.id.short_feeder_smart
    override fun getSmartEmptyId(): Int = R.id.short_feeder_smart_frame
    override fun getRecyclerViewId(): Int = R.id.short_feeder_recycler
    override fun setAdapter(): BaseRecyclerAdapter<ShortFeederBean> = ShortFeederAdapter(mContext)
    override fun getPageDatas(mCurrentPage: Int) {
        super.getPageDatas(mCurrentPage)
        mPresenter?.getShortFeeder(mCurrentPage, mShippingOutletsTag, mStartDateTag, mEndDateTag)

    }

    override fun onClick() {
        super.onClick()
        add_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                ARouter.getInstance().build(ARouterConstants.AddShortFeederActivity).navigation()
            }

        })
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

    @SuppressLint("CheckResult")
    override fun initDatas() {
        super.initDatas()
        RxBus.build().toObservable(this, DepartureRecordRefreshEvent::class.java).subscribe {
            refresh()
        }
        RxBus.build().toObservable(this, DepartureRecordEvent::class.java).subscribe { msg ->
            if (msg.type == 0) {
                mShippingOutletsTag = msg.webCode
                mStartDateTag = msg.startTime
                mEndDateTag = msg.endTime
                refresh()
            }

        }
    }

    override fun getShortFeederS(list: List<ShortFeederBean>) {
        appendDatas(list)
    }
}