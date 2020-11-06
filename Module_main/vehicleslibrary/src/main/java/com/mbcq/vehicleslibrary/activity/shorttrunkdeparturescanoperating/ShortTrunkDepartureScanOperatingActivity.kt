package com.mbcq.vehicleslibrary.activity.shorttrunkdeparturescanoperating


import android.Manifest
import android.annotation.SuppressLint
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
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.baselibrary.util.system.PhoneDeviceMsgUtils
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
    var mTts: SpeechSynthesizer? = null


    override fun getLayoutId(): Int = R.layout.activity_short_trunk_departure_scan_operating

    override fun initExtra() {
        super.initExtra()
        rxPermissions = RxPermissions(this)
        ARouter.getInstance().inject(this)
        initTts()
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
                                    mTts?.startSpeaking(soundString, null)
                                    mPresenter?.scanOrder(s1.substring(0, s1.length - 4), s1, PhoneDeviceMsgUtils.getDeviceOnlyTag(mContext), obj.optString("inoneVehicleFlag"), soundString)
                                }
                            }

                        }).show(supportFragmentManager, "ScanDialogFragment")
                    } else {
                        // Oups permission denied
                        TalkSureDialog(mContext, getScreenWidth(), "权限未赋予！照相机无法启动！请联系在线客服或手动进入系统设置授予摄像头权限！").show()

                    }
                }

    }

    private fun initTts() {
        if (mTts == null) {
            mTts = SpeechSynthesizer.createSynthesizer(mContext) { p0 ->
                if (p0 != ErrorCode.SUCCESS) {
                    LogUtils.i("TTS", "语音初始化失败,错误码：$p0")
                    TalkSureCancelDialog(mContext, getScreenWidth(), "语音初始化失败,请稍后重试，如需继续使用请点取消，点击确定即可退出！") {
                        onBackPressed()
                    }.show()
                }
            }
            //设置发音人
            mTts?.setParameter(SpeechConstant.VOICE_NAME, "x2_xiaoxue")
            //设置语速,值范围：[0, 100],默认值：50
            mTts?.setParameter(SpeechConstant.SPEED, "49")
            //设置音量
            mTts?.setParameter(SpeechConstant.VOLUME, "tts_volume")
            //设置语调
            mTts?.setParameter(SpeechConstant.PITCH, "tts_pitch")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mTts?.stopSpeaking()
        // 退出时释放连接
        mTts?.destroy()
    }

    override fun getRecyclerViewId(): Int = R.id.short_vehicles_scan_operating_recycler

    override fun setAdapter(): BaseRecyclerAdapter<ShortTrunkDepartureScanOperatingBean> = ShortTrunkDepartureScanOperatingAdapter(mContext).also {

    }

    override fun isShowErrorDialog(): Boolean {
        return true
    }

    override fun getCarInfoS(list: List<ShortTrunkDepartureScanOperatingBean>) {
        adapter.appendData(list)
    }

    override fun scanOrderS(billno: String, soundStr: String) {
        for ((index, item) in adapter.getAllData().withIndex()) {
            if (item.billno == billno) {
                val ii = item
                ii.unLoadQty = ii.unLoadQty--
                adapter.notifyItemChangeds(index, ii)
            }
        }
    }
}