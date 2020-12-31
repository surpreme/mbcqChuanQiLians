package com.mbcq.vehicleslibrary.fragment.arrivaltrunkdeparture


import android.annotation.SuppressLint
import android.view.View
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.interfaces.RxBus
import com.mbcq.baselibrary.ui.BaseListMVPFragment
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.allarrivalrecord.arrivalrecord.ArrivalRecordEvent
import com.mbcq.vehicleslibrary.fragment.trunkdeparture.TrunkDepartureBean
import kotlinx.android.synthetic.main.fragment_arrival_trunk_departure.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author: lzy
 * @time: 2020-09-21 10:40:36
 * 干线到车
 * TODO
 */

class ArrivalTrunkDepartureFragment : BaseListMVPFragment<ArrivalTrunkDepartureContract.View, ArrivalTrunkDeparturePresenter, TrunkDepartureBean>(), ArrivalTrunkDepartureContract.View {
    var mStartDateTag = ""
    var mEndDateTag = ""
    var mShippingOutletsTag = ""//发货网点
    var mType = 1

    override fun getRecyclerViewId(): Int = R.id.arrival_short_list_recycler

    @SuppressLint("SimpleDateFormat")
    override fun initExtra() {
        super.initExtra()
        mContext.let {
            val mDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            val mDate = Date(System.currentTimeMillis())
            val format = mDateFormat.format(mDate)
            mStartDateTag = "$format 00:00:00"
            mEndDateTag = "$format 23:59:59"
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
        }
    }

    @SuppressLint("CheckResult")
    override fun initDatas() {
        super.initDatas()
        mPresenter?.getUnLoading(mShippingOutletsTag, mStartDateTag, mEndDateTag)
        RxBus.build().toObservable(this, ArrivalRecordEvent::class.java).subscribe { msg ->
            if (msg.type == 1) {
                mShippingOutletsTag = msg.webCode
                mStartDateTag = msg.startTime
                mEndDateTag = msg.endTime
                refreshDataType(mType)
            }

        }
    }

    /**
     * @1在途
     * @2 已到车
     */
    protected fun refreshDataType(type: Int) {
        when (type) {

            1 -> {
                adapter.clearData()
                mPresenter?.getUnLoading(mShippingOutletsTag, mStartDateTag, mEndDateTag)
            }
            2 -> {
                adapter.clearData()
                mPresenter?.getLoading(mShippingOutletsTag, mStartDateTag, mEndDateTag)
            }
        }
    }

    override fun onClick() {
        super.onClick()
        type_gr.setOnCheckedChangeListener { _, checkedId ->
            run {
                when (checkedId) {
                    R.id.roading_rb -> {
                        mType = 1

                        refreshDataType(1)
                    }
                    R.id.roaded_rb -> {
                        mType = 2

                        refreshDataType(2)
                    }
                    else -> {

                    }

                }

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


    override fun getLayoutResId(): Int = R.layout.fragment_arrival_trunk_departure
    override fun getPageS(list: List<TrunkDepartureBean>) {
        adapter.appendData(list)

    }

    override fun confirmCarS(data: TrunkDepartureBean, position: Int) {
        adapter.notifyItemChangeds(position, data)

    }

    /* override fun confirmCarS(position: Int) {
 //        adapter.removeItem(position)
         refreshDataType(1)
     }*/

    /* override fun confirmCarS(data: TrunkDepartureBean, position: Int) {
         adapter.notifyItemChangeds(position, data)
     }*/

    override fun canCelCarS(data: TrunkDepartureBean, position: Int) {
        adapter.notifyItemChangeds(position, data)

    }

}