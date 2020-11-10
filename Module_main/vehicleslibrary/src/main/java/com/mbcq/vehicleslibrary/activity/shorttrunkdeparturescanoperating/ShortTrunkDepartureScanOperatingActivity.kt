package com.mbcq.vehicleslibrary.activity.shorttrunkdeparturescanoperating


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
    var mSoundPool: SoundPool? = null
    private var soundPoolMap: HashMap<Int, Int>? = null
    val SCAN_SOUND_ERROR_TAG = 1

    override fun getLayoutId(): Int = R.layout.activity_short_trunk_departure_scan_operating

    override fun initExtra() {
        super.initExtra()
        rxPermissions = RxPermissions(this)
        ARouter.getInstance().inject(this)
        initTts()
        initSoundPool()
    }

    fun initSoundPool() {
        mSoundPool = SoundPool(1, AudioManager.STREAM_ALARM, 0)
//        mSoundPool?.setOnLoadCompleteListener { soundPool, sampleId, status -> }
        soundPoolMap = HashMap<Int, Int>()
        mSoundPool?.let {
            soundPoolMap?.put(SCAN_SOUND_ERROR_TAG, it.load(this, com.mbcq.commonlibrary.R.raw.scan_error, 1))

        }
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

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
                ARouter.getInstance().build(ARouterConstants.RevokeShortTrunkDepartureScanOperatingActivity).withString("RevokeShortLoadingVehicles", mLastData).navigation()
            }

        })
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
                                    if (soundString != "未知地址") {
                                        mPresenter?.scanOrder(s1.substring(0, s1.length - 4), s1, PhoneDeviceMsgUtils.getDeviceOnlyTag(mContext), obj.optString("inoneVehicleFlag"), soundString)
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

    /***
     * 全部未扫描数量
     */
    var mTotalUnLoadingNum = 0

    /***
     * 全部未扫描重量
     */
    var mTotalUnLoadingWeight = 0.00

    /***
     * 全部未扫描重量
     */
    var mTotalLoadingWeight = 0.00

    /***
     * 全部未扫描体积
     */
    var mTotalUnLoadingVolume = 0.00

    /***
     * 全部扫描体积
     */
    var mTotalLoadingVolume = 0.00

    /**
     * 本车全部货物数量
     */
    var totalLoadingNum = 0

    /**
     *全部未扫描单子
     */
    var mTotalUnLoadingOrderNum = 0

    /**
     *全部扫描单子
     */
    var mTotalLoadingOrderNum = 0

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

    fun clearInfo() {
        /***
         * 全部未扫描数量
         */
        mTotalUnLoadingNum = 0

        /***
         * 全部未扫描重量
         */
        mTotalUnLoadingWeight = 0.00

        /***
         * 全部未扫描重量
         */
        mTotalLoadingWeight = 0.00

        /***
         * 全部未扫描体积
         */
        mTotalUnLoadingVolume = 0.00

        /***
         * 全部扫描体积
         */
        mTotalLoadingVolume = 0.00

        /**
         * 本车全部货物数量
         */
        totalLoadingNum = 0

        /**
         *全部未扫描单子
         */
        mTotalUnLoadingOrderNum = 0

        /**
         *全部扫描单子
         */
        mTotalLoadingOrderNum = 0
    }

    @SuppressLint("SetTextI18n")
    fun notifyMathChange() {
        unScan_info_tv.text = "未扫：${mTotalUnLoadingOrderNum}票 ${mTotalUnLoadingNum}件 ${mTotalUnLoadingWeight}kg  ${mTotalUnLoadingVolume}m³             扫描人:${UserInformationUtil.getUserName(mContext)}"
        scaned_info__tv.text = "已扫：${mTotalLoadingOrderNum}票 ${totalLoadingNum - mTotalUnLoadingNum}件 ${mTotalLoadingWeight}kg  ${mTotalLoadingVolume}m³             金额:xxxx"
        scan_progressBar.progress = (((totalLoadingNum - mTotalUnLoadingNum) * 100) / totalLoadingNum)
        scan_number_total_tv.text = "${totalLoadingNum - mTotalUnLoadingNum} / $totalLoadingNum"
    }
}