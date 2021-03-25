package com.mbcq.vehicleslibrary.activity.arrivalvehiclesscan


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.FragmentViewPagerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.baselibrary.view.TabBuilder
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.WebDbUtil
import com.mbcq.commonlibrary.WebsDbInterface
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterWithTimeDialog
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.fragment.arrivalshortfeederscan.ArrivalShortFeederScanFragment
import com.mbcq.vehicleslibrary.fragment.arrivaltrunkdeparturescan.ArrivalTrunkDepartureScanFragment
import kotlinx.android.synthetic.main.activity_arrival_vehicles_scan.*
import org.greenrobot.eventbus.EventBus

/**
 * @author: lzy
 * @time: 2020/10/29 13:52:00 到车扫描
 */

@Route(path = ARouterConstants.ArrivalVehiclesScanActivity)
class ArrivalVehiclesScanActivity : BaseMVPActivity<ArrivalVehiclesScanContract.View, ArrivalVehiclesScanPresenter>(), ArrivalVehiclesScanContract.View {


    override fun getLayoutId(): Int = R.layout.activity_arrival_vehicles_scan
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        top_index_title_tabLyout.addTab(top_index_title_tabLyout.newTab().setText("短驳到车"))
        top_index_title_tabLyout.addTab(top_index_title_tabLyout.newTab().setText("干线到车"))
        val fragments = ArrayList<Fragment>()
        fragments.add(ArrivalShortFeederScanFragment())
        fragments.add(ArrivalTrunkDepartureScanFragment())
        val mFragmentViewPagerAdapter = FragmentViewPagerAdapter(supportFragmentManager, fragments)
        arrival_vehicles_scan_viewpager.adapter = mFragmentViewPagerAdapter
        arrival_vehicles_scan_viewpager.offscreenPageLimit = top_index_title_tabLyout.tabCount
        //滑动绑定
        arrival_vehicles_scan_viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(top_index_title_tabLyout))
        top_index_title_tabLyout.addOnTabSelectedListener(object : TabBuilder() {
            override fun onSelected(tab: TabLayout.Tab) {
                arrival_vehicles_scan_viewpager.currentItem = tab.position
            }
        })
    }

    override fun onClick() {
        super.onClick()
        arrival_vehicles_scan_toolbar.setRightButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                WebDbUtil.getDbWebId(application, object : WebsDbInterface {
                    override fun isNull() {

                    }

                    override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                        FilterWithTimeDialog(getScreenWidth(), Gson().toJson(list), "webid", "webidCode", true, "到车扫描筛选", true, mClickInterface = object : OnClickInterface.OnClickInterface {
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
        arrival_vehicles_scan_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }
}