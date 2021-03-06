package com.mbcq.vehicleslibrary.fragment.trunkdeparture


import android.annotation.SuppressLint
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.interfaces.RxBus
import com.mbcq.baselibrary.ui.BaseSmartMVPFragment
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.FilterTimeUtils
import com.mbcq.vehicleslibrary.activity.alldeparturerecord.departurerecord.DepartureRecordEvent
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.alldeparturerecord.departurerecord.DepartureRecordAddSuccessEvent
import kotlinx.android.synthetic.main.fragment_trunk_departure.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-12 15:44:58
 * 干线发车记录
 */

class TrunkDepartureFragment : BaseSmartMVPFragment<TrunkDepartureContract.View, TrunkDeparturePresenter, TrunkDepartureBean>(), TrunkDepartureContract.View {
    var mStartDateTag = ""
    var mEndDateTag = ""
    var mShippingOutletsTag = ""//发货网点

    override fun getLayoutResId(): Int = R.layout.fragment_trunk_departure

    override fun getSmartLayoutId(): Int = R.id.trunk_departure_smart

    override fun getSmartEmptyId(): Int = R.id.trunk_departure_smart_frame

    override fun getRecyclerViewId(): Int = R.id.trunk_departure_recycler

    override fun isOpenEventBus(): Boolean = true
    override fun setIsShowNetLoading(): Boolean = false


    override fun setAdapter(): BaseRecyclerAdapter<TrunkDepartureBean> = TrunkDepartureAdapter(mContext).also {
        it.mOnTrunkDepartureClickInterface = object : TrunkDepartureAdapter.OnTrunkDepartureClickInterface {
            override fun onModify(v: View, position: Int, itemData: TrunkDepartureBean) {
                val job = JSONObject()
                job.put("InoneVehicleFlag", itemData.inoneVehicleFlag)
                job.put("Id", itemData.id)
                job.put("VehicleNo", itemData.vehicleNo)
                job.put("ewebidCode", itemData.ewebidCode)
                job.put("ewebidCodeStr", itemData.ewebidCodeStr)
                ARouter.getInstance().build(ARouterConstants.FixedTrunkDepartureHouseActivity).withString("FixedTrunkDepartureHouse", GsonUtils.toPrettyFormat(job.toString())).navigation()

            }

            override fun onPint(v: View, position: Int, itemData: TrunkDepartureBean) {

            }

            override fun onItemClick(v: View, position: Int, itemData: TrunkDepartureBean) {
                val job = JSONObject()
                job.put("InoneVehicleFlag", itemData.inoneVehicleFlag)
                job.put("Id", itemData.id)
                job.put("VehicleNo", itemData.vehicleNo)
                job.put("VehicleState", itemData.vehicleState)
                job.put("ewebidCode", itemData.ewebidCode)
                job.put("ewebidCodeStr", itemData.ewebidCodeStr)
                ARouter.getInstance().build(ARouterConstants.FixedTrunkDepartureHouseActivity).withString("FixedTrunkDepartureHouse", GsonUtils.toPrettyFormat(job.toString())).navigation()
            }

        }
    }


    @SuppressLint("SetTextI18n")
    override fun getTrunkDepartureS(list: List<TrunkDepartureBean>, totalData: TrunkDepartureTotalBean) {
        appendDatas(list)
        if (getCurrentPage() == 1)
            all_info_bottom_tv.text = "合计：${list.size}票，${totalData.weight}kg，${totalData.volumn}m³，运费${totalData.accSum}"

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onRefreshTrunkNewDataEvent(event: DepartureRecordAddSuccessEvent) {
        if (event.refreshType == 2)
            refresh()
    }

    override fun invalidOrderS() {
        refresh()
    }

    override fun initViews(view: View) {
        super.initViews(view)
        search_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (billno_ed.text.toString().isNotBlank()) {
                    adapter.clearData()
                    if (checkStrIsNum(billno_ed.text.toString()))
                        mPresenter?.searchScanInfo(billno_ed.text.toString())
                    else
                        mPresenter?.searchInoneVehicleFlagTrunkDeparture(billno_ed.text.toString())

                } else
                    refresh()
            }

        })
    }

    @SuppressLint("SimpleDateFormat")
    override fun initExtra() {
        super.initExtra()
        mContext.let {
            mStartDateTag = FilterTimeUtils.getStartTime(7)
            mEndDateTag = FilterTimeUtils.getEndTime()
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
                            break
                        }
                    }
                    if (mItemdata?.vehicleState != 0) {
                        showToast("只有发车计划中才可以修改")
                        return@let
                    }
                    run {
                        val job = JSONObject()
                        job.put("InoneVehicleFlag", mItemdata.inoneVehicleFlag)
                        job.put("Id", mItemdata.id)
                        ARouter.getInstance().build(ARouterConstants.FixedDepartureTrunkConfigurationActivity).withString("FixedDepartureTrunkConfiguration", GsonUtils.toPrettyFormat(job.toString())).navigation()
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
        /* RxBus.build().toObservable(this, TrunkDepartureIsRefreshEvent::class.java).subscribe {
             refresh()
         }*/
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