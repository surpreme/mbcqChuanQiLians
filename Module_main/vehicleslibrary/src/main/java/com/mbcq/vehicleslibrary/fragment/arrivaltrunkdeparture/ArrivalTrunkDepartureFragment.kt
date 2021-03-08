package com.mbcq.vehicleslibrary.fragment.arrivaltrunkdeparture


import android.annotation.SuppressLint
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.interfaces.RxBus
import com.mbcq.baselibrary.ui.BaseSmartMVPFragment
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.FilterTimeUtils
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.allarrivalrecord.arrivalrecord.ArrivalRecordEvent
import com.mbcq.vehicleslibrary.activity.allarrivalrecord.arrivalrecord.ArrivalRecordRefreshEvent
import com.mbcq.vehicleslibrary.fragment.trunkdeparture.TrunkDepartureBean
import kotlinx.android.synthetic.main.fragment_arrival_trunk_departure.*
import org.greenrobot.eventbus.EventBus
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author: lzy
 * @time: 2020-09-21 10:40:36
 * 干线到车
 *
 */

class ArrivalTrunkDepartureFragment : BaseSmartMVPFragment<ArrivalTrunkDepartureContract.View, ArrivalTrunkDeparturePresenter, TrunkDepartureBean>(), ArrivalTrunkDepartureContract.View {
    var mStartDateTag = ""
    var mEndDateTag = ""
    var mShippingOutletsTag = ""//发货网点

    override fun getLayoutResId(): Int = R.layout.fragment_arrival_trunk_departure
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

    override fun setAdapter() = ArrivalTrunkDepartureAdapter(mContext).also {
        it.mOnArrivalConfirmInterface = object : ArrivalTrunkDepartureAdapter.OnArrivalConfirmInterface {
            override fun onResult(position: Int, data: TrunkDepartureBean) {
                TalkSureCancelDialog(mContext, getScreenWidth(), "您确认要到车${data.inoneVehicleFlag}吗？") {
                    mPresenter?.confirmCar(data, position)
                }.show()
            }

            override fun onCancel(position: Int, data: TrunkDepartureBean) {
                TalkSureCancelDialog(mContext, getScreenWidth(), "您确认要取消到车${data.inoneVehicleFlag}吗？") {
                    data.vehicleState = 1
                    data.vehicleStateStr = "发货"
                    mPresenter?.canCelCar(data, position)

                }.show()
            }

            override fun onUnloadingWarehousing(position: Int, data: TrunkDepartureBean) {
                ARouter.getInstance().build(ARouterConstants.TrunkDepartureUnloadingWarehousingActivity).withString("TrunkDepartureUnloadingWarehousing", Gson().toJson(data)).navigation()

            }

            override fun onItemClick(position: Int, data: TrunkDepartureBean) {
                data.isLookInfo = true
                ARouter.getInstance().build(ARouterConstants.TrunkDepartureUnloadingWarehousingActivity).withString("TrunkDepartureUnloadingWarehousing", Gson().toJson(data)).navigation()

            }
        }
    }

    @SuppressLint("CheckResult")
    override fun initDatas() {
        arrival_short_list_smart.setEnableLoadMore(false)
        super.initDatas()
        RxBus.build().toObservable(this, ArrivalRecordEvent::class.java).subscribe { msg ->
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
        mPresenter?.getArrivalCar(mCurrentPage,mShippingOutletsTag, mStartDateTag, mEndDateTag)

    }


    override fun onClick() {
        super.onClick()
        search_btn.apply {
            onSingleClicks {
                if (search_arrival_trunk_ed.text.toString().isNotBlank()) {
                    adapter.clearData()
                    if (checkStrIsNum(search_arrival_trunk_ed.text.toString()))
                        mPresenter?.searchScanInfo(search_arrival_trunk_ed.text.toString())
                    else
                        mPresenter?.searchInoneVehicleFlagTrunkDepature(search_arrival_trunk_ed.text.toString())

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


    override fun getPageS(list: List<TrunkDepartureBean>,totalNum:Int) {
        appendDatas(list)
        if (getCurrentPage() == 1) {
            if (totalNum==-1)return
            EventBus.getDefault().post(ArrivalRecordRefreshEvent(totalNum.toString(), 2))
        }

    }

    override fun confirmCarS(data: TrunkDepartureBean, position: Int) {
        adapter.notifyItemChangeds(position, data)

    }


    override fun canCelCarS(data: TrunkDepartureBean, position: Int) {
        adapter.notifyItemChangeds(position, data)

    }

}