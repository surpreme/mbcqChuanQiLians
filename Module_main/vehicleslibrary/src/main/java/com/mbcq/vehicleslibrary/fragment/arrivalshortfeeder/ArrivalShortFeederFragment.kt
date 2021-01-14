package com.mbcq.vehicleslibrary.fragment.arrivalshortfeeder


import android.annotation.SuppressLint
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.interfaces.RxBus
import com.mbcq.baselibrary.ui.BaseListMVPFragment
import com.mbcq.baselibrary.ui.BaseSmartMVPFragment
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.FilterTimeUtils
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.allarrivalrecord.arrivalrecord.ArrivalRecordEvent
import com.mbcq.vehicleslibrary.fragment.shortfeeder.ShortFeederBean
import kotlinx.android.synthetic.main.fragment_arrival_short_feeder.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author: lzy
 * @time: 2020-09-21 09:06
 * 短驳到车
 *
 */

class ArrivalShortFeederFragment : BaseSmartMVPFragment<ArrivalShortFeederContract.View, ArrivalShortFeederPresenter, ShortFeederBean>(), ArrivalShortFeederContract.View {
    var mStartDateTag = ""
    var mEndDateTag = ""
    var mShippingOutletsTag = ""//发货网点

    override fun getLayoutResId() = R.layout.fragment_arrival_short_feeder
    override fun getRecyclerViewId(): Int = R.id.arrival_short_list_recycler
    override fun getSmartLayoutId(): Int = R.id.arrival_short_list_smart
    override fun getSmartEmptyId(): Int = R.id.arrival_short_list_smart_frame

    @SuppressLint("SimpleDateFormat")
    override fun initExtra() {
        super.initExtra()
        mContext.let {
            mStartDateTag = FilterTimeUtils.getStartTime(7)
            mEndDateTag = FilterTimeUtils.getEndTime()
            mShippingOutletsTag = UserInformationUtil.getWebIdCode(it)

        }

    }

    @SuppressLint("CheckResult")
    override fun initDatas() {
        arrival_short_list_smart.setEnableLoadMore(false)
        super.initDatas()
        RxBus.build().toObservable(this, ArrivalRecordEvent::class.java).subscribe { msg ->
            if (msg.type == 0) {
                mShippingOutletsTag = msg.webCode
                mStartDateTag = msg.startTime
                mEndDateTag = msg.endTime
                refresh()
            }

        }

    }

    override fun getPageDatas(mCurrentPage: Int) {
        super.getPageDatas(mCurrentPage)
        mPresenter?.getArrivalCarList(mShippingOutletsTag, mStartDateTag, mEndDateTag)
    }


    override fun onClick() {
        super.onClick()
        search_btn.apply {
            onSingleClicks {
                if (search_arrival_short_ed.text.toString().isNotBlank()) {
                    adapter.clearData()
                    if (checkStrIsNum(search_arrival_short_ed.text.toString()))
                        mPresenter?.searchScanInfo(search_arrival_short_ed.text.toString())
                    else
                        mPresenter?.searchInoneVehicleFlagShortFeeder(search_arrival_short_ed.text.toString())
                } else
                    refresh()
            }
        }
        cancel_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                for ((index, item) in (adapter.getAllData()).withIndex()) {
                    if (item.isChecked) {
                        mPresenter?.canCelCar(item, index)
                    }
                }
            }

        })
    }

    override fun setAdapter(): BaseRecyclerAdapter<ShortFeederBean> = ArrivalShortFeederAdapter(mContext).also {
        it.mOnArrivalConfirmInterface = object : ArrivalShortFeederAdapter.OnArrivalConfirmInterface {
            override fun onResult(position: Int, data: ShortFeederBean) {
                TalkSureCancelDialog(mContext, getScreenWidth(), "您确认要到车${data.inoneVehicleFlag}吗？") {
                    mPresenter?.confirmCar(data, position)
                }.show()
            }

            override fun onCancel(position: Int, data: ShortFeederBean) {
                TalkSureCancelDialog(mContext, getScreenWidth(), "您确认要取消到车${data.inoneVehicleFlag}吗？") {
                    mPresenter?.canCelCar(data, position)
                }.show()

            }

            override fun onUnloadingWarehousing(position: Int, data: ShortFeederBean) {
                ARouter.getInstance().build(ARouterConstants.ShortFeederUnloadingWarehousingActivity).withString("ShortFeederUnloadingWarehousing", Gson().toJson(data)).navigation()
            }

            override fun onItemClick(position: Int, data: ShortFeederBean) {
                data.isLookInfo = true
                ARouter.getInstance().build(ARouterConstants.ShortFeederUnloadingWarehousingActivity).withString("ShortFeederUnloadingWarehousing", Gson().toJson(data)).navigation()

            }

        }
    }

    override fun getPageS(list: List<ShortFeederBean>) {
        appendDatas(list)

    }

    override fun confirmCarS(data: ShortFeederBean, position: Int) {
        adapter.notifyItemChangeds(position, data)

    }


    override fun canCelCarS(data: ShortFeederBean, position: Int) {
        adapter.notifyItemChangeds(position, data)

    }


}