package com.mbcq.vehicleslibrary.activity.arrivaltrunkdeparturescanoperating


import android.Manifest
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.ui.BaseListMVPActivity
import com.mbcq.baselibrary.ui.BaseListMVPFragment
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.scan.scanlogin.ScanDialogFragment
import com.mbcq.vehicleslibrary.R
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.activity_arrival_trunk_departure_scan_operating.*

/**
 * @author: lzy
 * @time: 2020-10-30 09:24:35 干线到车扫描操作页
 */

@Route(path = ARouterConstants.ArrivalTrunkDepartureScanOperatingActivity)
class ArrivalTrunkDepartureScanOperatingActivity : BaseListMVPActivity<ArrivalTrunkDepartureScanOperatingContract.View, ArrivalTrunkDepartureScanOperatingPresenter, ArrivalTrunkDepartureScanOperatingBean>(), ArrivalTrunkDepartureScanOperatingContract.View {
    @Autowired(name = "ArrivalVehicles")
    @JvmField
    var mLastData: String = ""
    lateinit var rxPermissions: RxPermissions


    override fun getLayoutId(): Int = R.layout.activity_arrival_trunk_departure_scan_operating
    override fun initExtra() {
        super.initExtra()
        rxPermissions = RxPermissions(this)

    }
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun onClick() {
        super.onClick()
        scan_number_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                getCameraPermission()
            }

        })
        arrival_vehicles_scan_operating_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }
    fun getCameraPermission() {
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe { granted ->
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                        ScanDialogFragment(getScreenWidth()).show(supportFragmentManager, "ScanDialogFragment")
                    } else {
                        // Oups permission denied
                        TalkSureDialog(mContext, getScreenWidth(), "权限未赋予！照相机无法启动！请联系在线客服或手动进入系统设置授予摄像头权限！").show()

                    }
                }

    }
    override fun getRecyclerViewId(): Int = R.id.arrival_vehicles_scan_operating_recycler

    override fun setAdapter(): BaseRecyclerAdapter<ArrivalTrunkDepartureScanOperatingBean> = ArrivalTrunkDepartureScanOperatingAdapter(mContext).also {
        val data = mutableListOf<ArrivalTrunkDepartureScanOperatingBean>()
        data.add(ArrivalTrunkDepartureScanOperatingBean())
        data.add(ArrivalTrunkDepartureScanOperatingBean())
        data.add(ArrivalTrunkDepartureScanOperatingBean())
        data.add(ArrivalTrunkDepartureScanOperatingBean())
        it.appendData(data)
    }
}