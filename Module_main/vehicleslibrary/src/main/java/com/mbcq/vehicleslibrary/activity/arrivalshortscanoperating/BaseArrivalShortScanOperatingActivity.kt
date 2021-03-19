package com.mbcq.vehicleslibrary.activity.arrivalshortscanoperating

import android.graphics.Color
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.view.Gravity
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.iflytek.cloud.ErrorCode
import com.iflytek.cloud.SpeechConstant
import com.iflytek.cloud.SpeechSynthesizer
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.baselibrary.view.CustomizeToastUtil
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.scan.pda.CommonScanPDAMVPListActivity
import com.mbcq.vehicleslibrary.R
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.activity_arrival_short_scan_operating.*

abstract class BaseArrivalShortScanOperatingActivity<V : BaseView, T : BasePresenterImpl<V>, X> : CommonScanPDAMVPListActivity<V, T, X>(), BaseView {
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

    lateinit var rxPermissions: RxPermissions
    var mTts: SpeechSynthesizer? = null
    var mSoundPool: SoundPool? = null
    protected var soundPoolMap: HashMap<Int, Int>? = null
    val SCAN_SOUND_ERROR_TAG = 1


    override fun initExtra() {
        super.initExtra()
        rxPermissions = RxPermissions(this)
        ARouter.getInstance().inject(this)
        initTts()
        initSoundPool()
    }
    override fun onBeforeCreate() {
        super.onBeforeCreate()
        window.setFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun onClick() {
        super.onClick()
        arrival_vehicles_scan_operating_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }
    override fun showError(msg: String) {
        LogUtils.e(msg)
        CustomizeToastUtil().Short(mContext, msg).setGravity(Gravity.CENTER).setToastBackground(Color.WHITE, R.drawable.toast_radius).show()
    }

    fun initSoundPool() {
        mSoundPool = SoundPool(1, AudioManager.STREAM_ALARM, 0)
//        mSoundPool?.setOnLoadCompleteListener { soundPool, sampleId, status -> }
        soundPoolMap = HashMap<Int, Int>()
        mSoundPool?.let {
            soundPoolMap?.put(SCAN_SOUND_ERROR_TAG, it.load(this, com.mbcq.commonlibrary.R.raw.scan_error, 1))

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
            mTts?.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan")
            //设置语速,值范围：[0, 100],默认值：50
            mTts?.setParameter(SpeechConstant.SPEED, "49")
            //设置音量
            mTts?.setParameter(SpeechConstant.VOLUME, "tts_volume")
            //设置语调
            mTts?.setParameter(SpeechConstant.PITCH, "tts_pitch")
        }
    }
    fun errorStep(errorReason: String) {
        soundPoolMap?.get(SCAN_SOUND_ERROR_TAG)?.let { mSoundPool?.play(it, 1f, 1f, 0, 0, 1f) }
        CustomizeToastUtil().Short(mContext, errorReason).setGravity(Gravity.CENTER).setToastBackground(Color.WHITE, R.drawable.toast_radius).show()

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



    override fun onDestroy() {
        super.onDestroy()
        mTts?.stopSpeaking()
        // 退出时释放连接
        mTts?.destroy()
    }

}