package com.mbcq.vehicleslibrary.activity.shorttrunkdeparturescanoperating


import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.ui.BaseListMVPActivity
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.scan.scanlogin.ScanDialogFragment
import com.mbcq.vehicleslibrary.R
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.activity_short_trunk_departure_scan_operating.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-04 16:05:23 短驳干线发车
 */

@Route(path = ARouterConstants.ShortTrunkDepartureScanOperatingActivity)
class ShortTrunkDepartureScanOperatingActivity : BaseListMVPActivity<ShortTrunkDepartureScanOperatingContract.View, ShortTrunkDepartureScanOperatingPresenter, ShortTrunkDepartureScanOperatingBean>(), ShortTrunkDepartureScanOperatingContract.View {
    @Autowired(name = "ShortLoadingVehicles")
    @JvmField
    var mLastData: String = ""
    lateinit var rxPermissions: RxPermissions


    override fun getLayoutId(): Int = R.layout.activity_short_trunk_departure_scan_operating

    override fun initExtra() {
        super.initExtra()
        rxPermissions = RxPermissions(this)
        ARouter.getInstance().inject(this)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    @SuppressLint("SetTextI18n")
    override fun initDatas() {
        super.initDatas()
        val obj = JSONObject(mLastData)
        mPresenter?.getCarInfo(obj.optString("inoneVehicleFlag"))
        unloading_batch_tv.text = "卸车批次:${obj.optString("inoneVehicleFlag")}"
        unScan_info_tv.text = "未扫：xx票 xxx件 xxxxkg  xxm³             扫描人:${UserInformationUtil.getUserName(mContext)}"
    }

    override fun onClick() {
        super.onClick()
        scan_number_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                getCameraPermission()
            }

        })
        short_vehicles_scan_operating_toolbar.setBackButtonOnClickListener(object : SingleClick() {
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

    override fun getRecyclerViewId(): Int = R.id.short_vehicles_scan_operating_recycler

    override fun setAdapter(): BaseRecyclerAdapter<ShortTrunkDepartureScanOperatingBean> = ShortTrunkDepartureScanOperatingAdapter(mContext).also {

    }

    override fun getCarInfoS(list: List<ShortTrunkDepartureScanOperatingBean>) {
        adapter.appendData(list)
    }
}