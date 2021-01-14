package com.mbcq.vehicleslibrary.activity.arrivaltrunkdeparturescanoperating


import android.Manifest
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.dialog.popup.XDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.BaseListMVPActivity
import com.mbcq.baselibrary.ui.BaseListMVPFragment
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.adapter.BaseTextAdapterBean
import com.mbcq.commonlibrary.dialog.BottomOptionsDialog
import com.mbcq.commonlibrary.scan.scanlogin.ScanDialogFragment
import com.mbcq.vehicleslibrary.R
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.activity_arrival_trunk_departure_scan_operating.*

/**
 * @author: lzy
 * @time: 2020-10-30 09:24:35 干线到车扫描操作页 干线扫描卸车
 */

@Route(path = ARouterConstants.ArrivalTrunkDepartureScanOperatingActivity)
class ArrivalTrunkDepartureScanOperatingActivity : BaseArrivalTrunkDepartureScanOperatingActivity<ArrivalTrunkDepartureScanOperatingContract.View, ArrivalTrunkDepartureScanOperatingPresenter, ArrivalTrunkDepartureScanOperatingBean>(), ArrivalTrunkDepartureScanOperatingContract.View {
    @Autowired(name = "ArrivalVehicles")
    @JvmField
    var mLastData: String = ""


    override fun getLayoutId(): Int = R.layout.activity_arrival_trunk_departure_scan_operating
    

    override fun onClick() {
        super.onClick()
        type_tv.apply {
            onSingleClicks {
                onFilterRecyclerData()
            }
        }
        scan_number_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                getCameraPermission()
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

    fun onFilterRecyclerData() {
        val list = mutableListOf<BaseTextAdapterBean>()
        /**
        按扫描先后
        按扫描率
        按计划外
        按计划
         */
        for (index in 0..1) {
            val mBaseTextAdapterBean = BaseTextAdapterBean()
            mBaseTextAdapterBean.title = if (index == 0) "默认" else "按扫描率"
            mBaseTextAdapterBean.tag = index.toString()
            list.add(mBaseTextAdapterBean)
        }
        XDialog.Builder(mContext)
                .setContentView(R.layout.dialog_bottom_options)
//                        .setWidth(type_tv.width)
                .setIsDarkWindow(false)
                .asCustom(BottomOptionsDialog(mContext, list).also {
                    it.mOnRecyclerClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
                        override fun onItemClick(v: View, position: Int, mResult: String) {
                            type_tv.text = if (mResult == "0") "默认" else  "按扫描率"
                            when (mResult) {
                                "0" -> {
                                }
                                "1" -> {

                                }
                            }
                        }

                    }
                })
                .showUp(type_tv)
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

    override fun onPDAScanResult(result: String) {
        TODO("Not yet implemented")
    }
}