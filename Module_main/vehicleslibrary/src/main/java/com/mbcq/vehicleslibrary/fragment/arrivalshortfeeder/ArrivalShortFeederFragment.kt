package com.mbcq.vehicleslibrary.fragment.arrivalshortfeeder


import android.annotation.SuppressLint
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.interfaces.RxBus
import com.mbcq.baselibrary.ui.BaseListMVPFragment
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
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
 * 短驳到车 TODO
 */

class ArrivalShortFeederFragment : BaseListMVPFragment<ArrivalShortFeederContract.View, ArrivalShortFeederPresenter, ShortFeederBean>(), ArrivalShortFeederContract.View {
    var mStartDateTag = ""
    var mEndDateTag = ""
    var mShippingOutletsTag = ""//发货网点
    var mType = 1

    override fun getLayoutResId() = R.layout.fragment_arrival_short_feeder


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

    @SuppressLint("CheckResult")
    override fun initDatas() {
        super.initDatas()
        mPresenter?.getUnLoading(mShippingOutletsTag, mStartDateTag, mEndDateTag)
        RxBus.build().toObservable(this, ArrivalRecordEvent::class.java).subscribe { msg ->
            if (msg.type == 0) {
                mShippingOutletsTag = msg.webCode
                mStartDateTag = msg.startTime
                mEndDateTag = msg.endTime
                refreshDataType(mType)
            }

        }

    }

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

        }
    }

    override fun getPageS(list: List<ShortFeederBean>) {
        adapter.appendData(list)

    }

    override fun confirmCarS(data: ShortFeederBean, position: Int) {
        adapter.notifyItemChangeds(position, data)

    }

    /* override fun confirmCarS(position: Int) {
         adapter.removeItem(position)

     }*/


    override fun canCelCarS(data: ShortFeederBean, position: Int) {
        adapter.notifyItemChangeds(position, data)

    }


}