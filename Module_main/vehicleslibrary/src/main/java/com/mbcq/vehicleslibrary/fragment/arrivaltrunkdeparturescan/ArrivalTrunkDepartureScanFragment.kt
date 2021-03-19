package com.mbcq.vehicleslibrary.fragment.arrivaltrunkdeparturescan


import android.annotation.SuppressLint
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.BaseListMVPActivity
import com.mbcq.baselibrary.ui.BaseListMVPFragment
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.FilterTimeUtils
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.allarrivalrecord.arrivalrecord.ArrivalRecordRefreshEvent
import com.mbcq.vehicleslibrary.activity.arrivalvehiclesscan.ArrivalVehiclesScanFilterRefreshEvent
import com.mbcq.vehicleslibrary.fragment.trunkdeparture.TrunkDepartureBean
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.activity_arrival_record.*
import kotlinx.android.synthetic.main.fragment_arrival_trunk_departure_scan.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-10-29 15:37:12 干线到车扫描
 */

class ArrivalTrunkDepartureScanFragment : BaseListMVPFragment<ArrivalTrunkDepartureScanContract.View, ArrivalTrunkDepartureScanPresenter, ArrivalTrunkDepartureScanBean>(), ArrivalTrunkDepartureScanContract.View {
    var mStartDateTag = ""
    var mEndDateTag = ""
    var mShippingOutletsTag = ""//发货网点

    override fun getLayoutResId(): Int = R.layout.fragment_arrival_trunk_departure_scan
    override fun getRecyclerViewId(): Int = R.id.arrival_trunk_departure_scan_list_recycler
    override fun setIsShowNetLoading(): Boolean {
        return false
    }

    override fun isOpenEventBus(): Boolean {
        return true
    }

    @SuppressLint("SetTextI18n")
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = false)
    fun onRefreshArrivalRecordDataEvent(event: ArrivalVehiclesScanFilterRefreshEvent) {
        if (event.type == 2) {
            mStartDateTag = event.startDate
            mEndDateTag = event.endDate
            mShippingOutletsTag = event.shippingOutletsTag
            initDatas()
        }
    }
    @SuppressLint("SimpleDateFormat")
    override fun initExtra() {
        super.initExtra()
        mStartDateTag = FilterTimeUtils.getStartTime(0)
        mEndDateTag = FilterTimeUtils.getEndTime()
        mShippingOutletsTag = UserInformationUtil.getWebIdCode(mContext)

    }
    override fun initDatas() {
        super.initDatas()
        mPresenter?.getLoading(mShippingOutletsTag, mStartDateTag, mEndDateTag)
    }

    override fun initViews(view: View) {
        super.initViews(view)
        search_arrival_trunk_departure_scan_smart.setEnableLoadMore(false)
        search_arrival_trunk_departure_scan_smart.setOnRefreshListener {
            adapter.clearData()
            initDatas()
            it.finishRefresh()
        }
    }

    override fun setAdapter(): BaseRecyclerAdapter<ArrivalTrunkDepartureScanBean> = ArrivalTrunkDepartureScanAdapter(mContext).also {
        it.mSureArrivalClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val obj = JSONObject(mResult)
                TalkSureCancelDialog(mContext, getScreenWidth(), "您确认要到车批次号为${obj.optString("inoneVehicleFlag")}的车辆吗") {
                    mPresenter?.sureArrivalCar(obj.optString("inoneVehicleFlag"))
                }.show()
            }

        }
        it.mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                ARouter.getInstance().build(ARouterConstants.ArrivalTrunkDepartureScanOperatingActivity).withString("ArrivalVehicles", mResult).navigation()
            }

        }
    }

    override fun getPageS(list: List<ArrivalTrunkDepartureScanBean>) {
        adapter.replaceData(list)
    }

    override fun sureArrivalCarS(result: String) {
        showToast("到车成功")
    }
}