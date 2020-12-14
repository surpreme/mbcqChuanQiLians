package com.mbcq.vehicleslibrary.fragment.trunkdeparture


import android.annotation.SuppressLint
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.interfaces.RxBus
import com.mbcq.baselibrary.ui.BaseSmartMVPFragment
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.activity.alldeparturerecord.departurerecord.DepartureRecordEvent
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.alldeparturerecord.departurerecord.TrunkDepartureIsRefreshEvent
import kotlinx.android.synthetic.main.fragment_trunk_departure.*
import org.json.JSONObject
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

    override fun setAdapter(): BaseRecyclerAdapter<TrunkDepartureBean> = TrunkDepartureAdapter(mContext).also {
        it.mOnTrunkDepartureClickInterface=object :TrunkDepartureAdapter.OnTrunkDepartureClickInterface{
            override fun onModify(v: View, position: Int, itemData: TrunkDepartureBean) {
                val job = JSONObject()
                job.put("InoneVehicleFlag", itemData.inoneVehicleFlag)
                job.put("Id", itemData.id)
                ARouter.getInstance().build(ARouterConstants.FixedTrunkDepartureHouseActivity).withString("FixedTrunkDepartureHouse", GsonUtils.toPrettyFormat(job.toString())).navigation()

            }

            override fun onPint(v: View, position: Int, itemData: TrunkDepartureBean) {

            }

        }
    }

    override fun getLayoutResId(): Int = R.layout.fragment_trunk_departure

    @SuppressLint("SetTextI18n")
    override fun getTrunkDepartureS(list: List<TrunkDepartureBean>, totalData: TrunkDepartureTotalBean) {
        appendDatas(list)
        if (getCurrentPage() == 1)
            all_info_bottom_tv.text = "合计：${list.size}票，${totalData.weight}kg，${totalData.volumn}m³，运费${totalData.accSum}"

    }

    override fun invalidOrderS() {
        refresh()
    }

    @SuppressLint("SimpleDateFormat")
    override fun initExtra() {
        super.initExtra()
        mContext.let {
            val mDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            val mDate = Date(System.currentTimeMillis())
            val format = mDateFormat.format(mDate)
            mStartDateTag = "$format 00:00:00"
            mEndDateTag = "$format 23:59:59"
            mShippingOutletsTag = UserInformationUtil.getWebIdCode(it) + ","

        }

    }

    override fun onClick() {
        super.onClick()//
        modify_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mContext.let {
                    var mItemdata: TrunkDepartureBean? = null
                    for (item in adapter.getAllData()) {
                        if (item.isChecked) {
                            mItemdata = item
                        }
                    }
                    if (mItemdata != null) {
                        val job = JSONObject()
                        job.put("InoneVehicleFlag", mItemdata.inoneVehicleFlag)
                        job.put("Id", mItemdata.id)
                        ARouter.getInstance().build(ARouterConstants.FixedTrunkDepartureHouseActivity).withString("FixedTrunkDepartureHouse", GsonUtils.toPrettyFormat(job.toString())).navigation()
                    } else {
                        showToast("请至少选择一辆车次进行操作修改")
                    }

                }
            }

        })
        invalid_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mContext.let {
                    var data: TrunkDepartureBean? = null
                    for (item in adapter.getAllData()) {
                        if (item.isChecked) {
                            data = item
                        }
                    }
                    if (data != null) {
                        TalkSureCancelDialog(it, getScreenWidth(), "您确定要作废车次${data.inoneVehicleFlag}吗?作废后不可恢复！") {
                            mPresenter?.invalidOrder(data.inoneVehicleFlag, data.id)

                        }.show()
                    } else {
                        showToast("请至少选择一辆车次进行操作作废")
                    }

                }

            }
        })
        add_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                ARouter.getInstance().build(ARouterConstants.AddTrunkDepartureActivity).navigation()
            }

        })

    }

    @SuppressLint("CheckResult")
    override fun initDatas() {
        super.initDatas()
        RxBus.build().toObservable(this, TrunkDepartureIsRefreshEvent::class.java).subscribe {
            refresh()
        }
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