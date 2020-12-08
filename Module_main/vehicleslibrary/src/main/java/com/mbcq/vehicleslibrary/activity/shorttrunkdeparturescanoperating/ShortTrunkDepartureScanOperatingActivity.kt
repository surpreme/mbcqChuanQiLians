package com.mbcq.vehicleslibrary.activity.shorttrunkdeparturescanoperating


import android.Manifest
import android.annotation.SuppressLint
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.system.PhoneDeviceMsgUtils
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.scan.scanlogin.ScanDialogFragment
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.departuretrunkdeparturescanoperating.revoke.RevokeDepartureTrunkDepartureScanDataBean
import com.mbcq.vehicleslibrary.activity.shorttrunkdeparturescanoperating.revoke.RevokeShortTrunkDepartureScanDataBean
import com.mbcq.vehicleslibrary.fragment.ScanNumDialog
import kotlinx.android.synthetic.main.activity_short_trunk_departure_scan_operating.*
import org.json.JSONObject
import java.lang.StringBuilder


/**
 * @author: lzy
 * @time: 2020-11-04 16:05:23 短驳发车
 */

@Route(path = ARouterConstants.ShortTrunkDepartureScanOperatingActivity)
class ShortTrunkDepartureScanOperatingActivity : BaseShortTrunkDepartureScanOperatingActivity<ShortTrunkDepartureScanOperatingContract.View, ShortTrunkDepartureScanOperatingPresenter, ShortTrunkDepartureScanOperatingBean>(), ShortTrunkDepartureScanOperatingContract.View {
    @Autowired(name = "ShortLoadingVehicles")
    @JvmField
    var mLastData: String = ""

    override fun getLayoutId(): Int = R.layout.activity_short_trunk_departure_scan_operating

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        clearInfo()
        val obj = JSONObject(mLastData)
        mPresenter?.getCarInfo(obj.optString("inoneVehicleFlag"))
        unloading_batch_tv.text = "卸车批次:${obj.optString("inoneVehicleFlag")}"
        unScan_info_tv.text = "未扫：xx票 xxx件 xxxxkg  xxm³             扫描人:${UserInformationUtil.getUserName(mContext)}"
    }

    override fun onClick() {
        super.onClick()
        save_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (mTotalUnLoadingNum != 0) {
                    showToast("请扫描完毕再发车")
                    return
                }
                val modifyData = JSONObject(mLastData)
                mPresenter?.saveScanPost(modifyData.optInt("id"), modifyData.optString("inoneVehicleFlag"))
            }

        })
        short_vehicles_scan_operating_toolbar.setRightTitleOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                val mRevokeShortTrunkDepartureScanDataBean = RevokeShortTrunkDepartureScanDataBean()
                mRevokeShortTrunkDepartureScanDataBean.inoneVehicleFlag = JSONObject(mLastData).optString("inoneVehicleFlag")
                mRevokeShortTrunkDepartureScanDataBean.mTotalUnLoadingOrderNum = mTotalUnLoadingNum
                mRevokeShortTrunkDepartureScanDataBean.mTotalLoadingOrderNum = totalLoadingNum
                ARouter.getInstance().build(ARouterConstants.RevokeShortTrunkDepartureScanOperatingActivity).withSerializable("RevokeShortLoadingVehicles", mRevokeShortTrunkDepartureScanDataBean).navigation()
            }

        })
        scan_number_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                getCameraPermission()
            }

        })

    }

    fun scanSuccess(s1: String) {
        if (s1.length > 5) {
            val obj = JSONObject(mLastData)
            var soundString = "未知地址"
            for (item in adapter.getAllData()) {
                if (item.billno == s1.substring(0, s1.length - 4)) {
                    soundString = item.ewebidCodeStr
                    if (item.totalQty > 20) {
                        ScanNumDialog(item.totalQty - item.unLoadQty,1,object : OnClickInterface.OnClickInterface {
                            override fun onResult(x1: String, x2: String) {
                                if (isInteger(x1)) {
                                    val mScanSun = item.totalQty - item.unLoadQty
                                    if (x1.toInt() > mScanSun) {
                                        showToast("您输入的数量已经超过货物剩余的数量")
                                        return
                                    }
                                    val scanBuilder = StringBuilder()
                                    for (index in ((mScanSun - x1.toInt()) + 1)..mScanSun) {
                                        val endBillno = if (index.toString().length == 1) "000$index" else if (index.toString().length == 2) "00$index" else if (index.toString().length == 3) "0$index" else if (index.toString().length == 4) "$index" else ""
                                        scanBuilder.append(s1.substring(0, s1.length - 4) + endBillno)
                                        if (index != mScanSun)
                                            scanBuilder.append(",")
                                    }
                                    val mOutPintO = (scanBuilder.toString().split(",").lastIndex + 1)
                                    mPresenter?.scanOrder(s1.substring(0, s1.length - 4), scanBuilder.toString(), PhoneDeviceMsgUtils.getDeviceOnlyTag(mContext), obj.optString("inoneVehicleFlag"), soundString, (((totalLoadingNum - (mTotalUnLoadingNum - mOutPintO)) * 100) / totalLoadingNum).toString())

                                }
                            }

                        }).show(supportFragmentManager, "ScanDialogFragment")
                    } else
                        mPresenter?.scanOrder(s1.substring(0, s1.length - 4), s1, PhoneDeviceMsgUtils.getDeviceOnlyTag(mContext), obj.optString("inoneVehicleFlag"), soundString, (((totalLoadingNum - (mTotalUnLoadingNum - 1)) * 100) / totalLoadingNum).toString())

                }
            }
            if (soundString == "未知地址") {
                soundPoolMap?.get(SCAN_SOUND_ERROR_TAG)?.let { mSoundPool?.play(it, 1f, 1f, 0, 0, 1f) }

            }
        }
    }

    fun getCameraPermission() {
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe { granted ->
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                        ScanDialogFragment(getScreenWidth(), null, object : OnClickInterface.OnClickInterface {
                            override fun onResult(s1: String, s2: String) {
                                scanSuccess(s1)
                            }

                        }).show(supportFragmentManager, "ScanDialogFragment")
                    } else {
                        // Oups permission denied
                        TalkSureDialog(mContext, getScreenWidth(), "权限未赋予！照相机无法启动！请联系在线客服或手动进入系统设置授予摄像头权限！").show()

                    }
                }

    }


    override fun getRecyclerViewId(): Int = R.id.short_vehicles_scan_operating_recycler

    override fun setAdapter(): BaseRecyclerAdapter<ShortTrunkDepartureScanOperatingBean> = ShortTrunkDepartureScanOperatingAdapter(mContext)


    /**
     * unLoadQty 已扫数量
     */
    @SuppressLint("SetTextI18n")
    override fun getCarInfoS(list: List<ShortTrunkDepartureScanOperatingBean>) {
        if (!adapter.getAllData().isNullOrEmpty()) {
            adapter.clearData()
        }
        adapter.appendData(list)
        for (item in list) {

            totalLoadingNum = (totalLoadingNum + item.totalQty)//本车全部货物数量+
            mTotalUnLoadingNum = (mTotalUnLoadingNum + (item.totalQty - item.unLoadQty))//全部未扫描数量
            mTotalUnLoadingVolume = (mTotalUnLoadingVolume + (item.volumn / item.totalQty) * (item.totalQty - item.unLoadQty))//全部未扫描体积
            mTotalLoadingVolume = (mTotalLoadingVolume + (item.volumn / item.totalQty) * item.unLoadQty)//全部扫描体积
            mTotalUnLoadingWeight = (mTotalUnLoadingWeight + (item.weight / item.totalQty) * (item.totalQty - item.unLoadQty))//全部未扫描重量
            mTotalLoadingWeight = (mTotalLoadingWeight + (item.weight / item.totalQty) * item.unLoadQty)//全部扫描重量
            if (item.unLoadQty != item.totalQty) {
                mTotalUnLoadingOrderNum = (mTotalUnLoadingOrderNum + 1)//全部未扫描单子
            } else {
                mTotalLoadingOrderNum = (mTotalLoadingOrderNum + 1)//全部扫描单子

            }
        }
        notifyMathChange()
    }

    override fun scanOrderS(billno: String, soundStr: String, mMoreScanBillno: String) {
        for ((index, item) in adapter.getAllData().withIndex()) {
            if (item.billno == billno) {
                val ii = item
                val mScanO: Int = if (mMoreScanBillno.split(",").isNullOrEmpty()) 1 else mMoreScanBillno.split(",").lastIndex + 1
                ii.unLoadQty = item.unLoadQty + mScanO
                adapter.notifyItemChangeds(index, ii)
                if (ii.unLoadQty == ii.totalQty) {
                    mTotalUnLoadingOrderNum -= 1//全部未扫描单子
                    mTotalLoadingOrderNum += 1//全部扫描单子
                }
                mTotalUnLoadingNum -= mScanO//全部未扫描数量
                mTotalUnLoadingVolume = (mTotalUnLoadingVolume - mScanO * ((ii.volumn) / ii.totalQty))//全部未扫描体积
                mTotalLoadingVolume = (mTotalLoadingVolume + mScanO * ((ii.volumn) / ii.totalQty))//全部扫描体积
                mTotalUnLoadingWeight = (mTotalUnLoadingWeight - mScanO * ((ii.weight) / ii.totalQty))//全部未扫描重量
                mTotalLoadingWeight = (mTotalLoadingWeight + mScanO * ((ii.weight) / ii.totalQty))//全部扫描重量
                notifyMathChange()
            }
        }
        mTts?.startSpeaking(soundStr, null)

    }

    override fun saveScanPostS(result: String) {
        val obj = JSONObject(mLastData)
        TalkSureDialog(mContext, getScreenWidth(), "车次为${obj.optString("inoneVehicleFlag")}的车辆已经扫描发车，点击此处返回！") {
            onBackPressed()
        }.show()
    }

    override fun onPDAScanResult(result: String) {
        scanSuccess(result)
    }


}