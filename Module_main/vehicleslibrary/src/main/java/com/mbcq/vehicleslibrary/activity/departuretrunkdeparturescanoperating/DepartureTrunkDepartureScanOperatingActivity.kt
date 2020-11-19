package com.mbcq.vehicleslibrary.activity.departuretrunkdeparturescanoperating


import android.Manifest
import android.annotation.SuppressLint
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.iflytek.cloud.ErrorCode
import com.iflytek.cloud.SpeechConstant
import com.iflytek.cloud.SpeechSynthesizer
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.BaseListMVPActivity
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.baselibrary.util.system.PhoneDeviceMsgUtils
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.scan.scanlogin.ScanDialogFragment
import com.mbcq.vehicleslibrary.R
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.activity_departure_trunk_departure_scan_operating.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-04 14:30 :21 干线扫描发车
 */

@Route(path = ARouterConstants.DepartureTrunkDepartureScanOperatingActivity)
class DepartureTrunkDepartureScanOperatingActivity : BaseDepartureTrunkDepartureScanOperatingActivity<DepartureTrunkDepartureScanOperatingContract.View, DepartureTrunkDepartureScanOperatingPresenter, DepartureTrunkDepartureScanOperatingBean>(), DepartureTrunkDepartureScanOperatingContract.View {
    @Autowired(name = "LoadingVehicles")
    @JvmField
    var mLastData: String = ""


    override fun getLayoutId(): Int = R.layout.activity_departure_trunk_departure_scan_operating


    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        clearInfo()
        val obj = JSONObject(mLastData)
        mPresenter?.getCarInfo(obj.optString("inoneVehicleFlag"))
        unloading_batch_tv.text = "卸车批次:${obj.optString("inoneVehicleFlag")}"
    }

    override fun onClick() {
        super.onClick()
        departure_vehicles_scan_operating_toolbar.setRightTitleOnClickListener(object :SingleClick(){
            override fun onSingleClick(v: View?) {
                ARouter.getInstance().build(ARouterConstants.RevokeDepartureTrunkDepartureScanOperatingActivity).withString("RevokeDepartureLoadingVehicles", mLastData).navigation()

            }

        })
        commit_btn.setOnClickListener(object :SingleClick(){
            override fun onSingleClick(v: View?) {
                if (mTotalUnLoadingNum != 0) {
                    showToast("请扫描完毕再发车")
                    return
                }
                val modifyData = JSONObject(mLastData)
                mPresenter?.saveScanPost(modifyData.optInt("id"), modifyData.optString("inoneVehicleFlag"))
            }

        })
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
                        ScanDialogFragment(getScreenWidth(), null, object : OnClickInterface.OnClickInterface {
                            override fun onResult(s1: String, s2: String) {
                                if (s1.length > 5) {
                                    val obj = JSONObject(mLastData)
                                    var soundString = "未知地址"
                                    for (item in adapter.getAllData()) {
                                        if (item.billno == s1.substring(0, s1.length - 4)) {
                                            soundString = item.ewebidCodeStr
                                        }
                                    }
                                    if (soundString != "未知地址") {
                                        mPresenter?.scanOrder(s1.substring(0, s1.length - 4), s1, PhoneDeviceMsgUtils.getDeviceOnlyTag(mContext), obj.optString("inoneVehicleFlag"), soundString, (((totalLoadingNum - (mTotalUnLoadingNum - 1)) * 100) / totalLoadingNum).toString())
                                    } else {
                                        soundPoolMap?.get(SCAN_SOUND_ERROR_TAG)?.let { mSoundPool?.play(it, 1f, 1f, 0, 0, 1f) }

                                        /*  val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM) //系统自带警告声
                                        val rt = RingtoneManager.getRingtone(applicationContext, uri)
                                        rt.play()*/

                                    }
                                }
                            }

                        }).show(supportFragmentManager, "ScanDialogFragment")
                    } else {
                        // Oups permission denied
                        TalkSureDialog(mContext, getScreenWidth(), "权限未赋予！照相机无法启动！请联系在线客服或手动进入系统设置授予摄像头权限！").show()

                    }
                }

    }

    override fun getRecyclerViewId(): Int = R.id.departure_vehicles_scan_operating_recycler
    override fun setAdapter(): BaseRecyclerAdapter<DepartureTrunkDepartureScanOperatingBean> = DepartureTrunkDepartureScanOperatingAdapter(mContext)
    override fun getCarInfoS(list: List<DepartureTrunkDepartureScanOperatingBean>) {
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


    override fun scanOrderS(billno: String, soundStr: String) {
        for ((index, item) in adapter.getAllData().withIndex()) {
            if (item.billno == billno) {
                val ii = item
                ii.unLoadQty = item.unLoadQty + 1
                adapter.notifyItemChangeds(index, ii)
                 if (ii.unLoadQty == ii.totalQty) {
                     mTotalUnLoadingOrderNum -= 1//全部未扫描单子
                     mTotalLoadingOrderNum += 1//全部扫描单子
                 }
                 mTotalUnLoadingNum -= 1//全部未扫描数量
                 mTotalUnLoadingVolume = (mTotalUnLoadingVolume - ((ii.volumn) / ii.totalQty))//全部未扫描体积
                 mTotalLoadingVolume = (mTotalLoadingVolume + ((ii.volumn) / ii.totalQty))//全部扫描体积
                 mTotalUnLoadingWeight = (mTotalUnLoadingWeight - ((ii.weight) / ii.totalQty))//全部未扫描重量
                 mTotalLoadingWeight = (mTotalLoadingWeight + ((ii.weight) / ii.totalQty))//全部扫描重量
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

}