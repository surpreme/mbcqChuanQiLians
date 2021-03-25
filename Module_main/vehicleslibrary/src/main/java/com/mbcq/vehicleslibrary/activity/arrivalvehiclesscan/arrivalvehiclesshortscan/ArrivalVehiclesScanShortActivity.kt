package com.mbcq.vehicleslibrary.activity.arrivalvehiclesscan.arrivalvehiclesshortscan


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.WebDbUtil
import com.mbcq.commonlibrary.WebsDbInterface
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterWithTimeDialog
import com.mbcq.commonlibrary.scan.pda.CommonScanPDAMVPActivity
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.arrivalvehiclesscan.ArrivalVehiclesScanFilterRefreshEvent
import kotlinx.android.synthetic.main.activity_arrival_vehicles_scan_short.*
import org.greenrobot.eventbus.EventBus

/**
 * @author: lzy
 * @time: 2021-03-20 09:33:16 短驳到车扫描记录
 */

@Route(path = ARouterConstants.ArrivalVehiclesScanShortActivity)
class ArrivalVehiclesScanShortActivity : CommonScanPDAMVPActivity<ArrivalVehiclesScanShortContract.View, ArrivalVehiclesScanShortPresenter>(), ArrivalVehiclesScanShortContract.View {
    override fun getLayoutId(): Int = R.layout.activity_arrival_vehicles_scan_short
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun onClick() {
        super.onClick()
        arrival_vehicles_short_scan_toolbar
                .setRightButtonOnClickListener(object : SingleClick() {
                    override fun onSingleClick(v: View?) {
                        WebDbUtil.getDbWebId(application, object : WebsDbInterface {
                            override fun isNull() {

                            }

                            override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                                FilterWithTimeDialog(getScreenWidth(), Gson().toJson(list), "webid", "webidCode", true, "短驳到车扫描筛选", true, mClickInterface = object : OnClickInterface.OnClickInterface {
                                    /**
                                     * s1 网点
                                     * s2  start@end
                                     */

                                    override fun onResult(s1: String, s2: String) {
                                        val timeList = s2.split("@")
                                        if (timeList.isNotEmpty() && timeList.size == 2) {
                                            val mArrivalVehiclesScanFilterRefreshEvent = ArrivalVehiclesScanFilterRefreshEvent(1, "", timeList[0], timeList[1], s1)
                                            EventBus.getDefault().post(mArrivalVehiclesScanFilterRefreshEvent)
                                        }
                                    }

                                }).show(supportFragmentManager, "ArrivalVehiclesScanFilterWithTimeDialog")
                            }

                        })
                    }

                })
        arrival_vehicles_short_scan_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    override fun onPDAScanResult(result: String) {
        if (checkStrIsNum(result))
            mPresenter?.getShortCarNumber(result)

    }

    override fun getShortCarNumberS(result: ArrivalVehiclesScanShortSearchWithBillnoBean) {


    }
}