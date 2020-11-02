package com.mbcq.commonlibrary.scan.scanlogin

import android.content.Context.VIBRATOR_SERVICE
import android.os.Bundle
import android.os.Vibrator
import android.view.View
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.DialogFragment
import cn.bingoogolapple.qrcode.core.BarcodeType
import cn.bingoogolapple.qrcode.core.QRCodeView
import cn.bingoogolapple.qrcode.zxing.ZXingView
import com.iflytek.cloud.ErrorCode
import com.iflytek.cloud.InitListener
import com.iflytek.cloud.SpeechConstant
import com.iflytek.cloud.SpeechSynthesizer
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.dialog.dialogfragment.BaseDialogFragment
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.commonlibrary.R


/**
 * 扫码弹窗
 *  可参考 已实现
 *
 * */
class ScanDialogFragment : BaseDialogFragment, QRCodeView.Delegate {
    var mZXingView: ZXingView? = null
    var mTts: SpeechSynthesizer? = null
    var mScreenWidths: Int
    override fun setDialogWidth(): Int {
        return mScreenWidths / 4 * 3
    }

    override fun setDialogHeight(): Int {
        return mScreenWidths
    }

    constructor(mScreenWidth: Int) : super() {
        mScreenWidths = mScreenWidth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar)
        initTts()
    }

    override fun setContentView(): Int = R.layout.dialog_scan_login

    override fun initView(view: View, savedInstanceState: Bundle?) {
        mZXingView = view.findViewById(R.id.mZxingview)
        mZXingView?.setDelegate(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mZXingView?.onDestroy()
        mTts?.stopSpeaking()
        // 退出时释放连接
        mTts?.destroy()


    }

    override fun onResume() {
        super.onResume()
        mZXingView?.startCamera()
        mZXingView?.startSpotAndShowRect()
        mZXingView?.startSpot()
        mZXingView?.setType(BarcodeType.ALL, null)
    }

    /**
     * 打开系统震动
     */
    fun openVibrator() {
        val vibrator = activity?.getSystemService(VIBRATOR_SERVICE) as Vibrator?
        vibrator?.vibrate(200)
        mTts?.startSpeaking("小乐 我爱你 扑盖 老子要杀了你 么么哒", null)
    }

    override fun onScanQRCodeSuccess(result: String?) {
        openVibrator()
        mZXingView?.stopSpot()

        TalkSureDialog(mContext, 1200, result) {
            mZXingView?.startSpot()

        }.show()
    }

    private fun initTts() {
        if (mTts == null) {
            mTts = SpeechSynthesizer.createSynthesizer(activity) { p0 ->
                if (p0 != ErrorCode.SUCCESS) {
                    LogUtils.i("TTS", "语音初始化失败,错误码：$p0")
                }
            }
            //设置发音人
            mTts?.setParameter(SpeechConstant.VOICE_NAME, "x_xiaomei")
            //设置语速,值范围：[0, 100],默认值：50
            mTts?.setParameter(SpeechConstant.SPEED, "49")
            //设置音量
            mTts?.setParameter(SpeechConstant.VOLUME, "tts_volume")
            //设置语调
            mTts?.setParameter(SpeechConstant.PITCH, "tts_pitch")
        }
    }

    override fun onCameraAmbientBrightnessChanged(isDark: Boolean) {
        var tipText: String = mZXingView?.scanBoxView?.tipText!!
        val ambientBrightnessTip = "\n环境过暗，请打开闪光灯"
        if (isDark) {
            //在这里加入了根据传感器光线暗的时候自动打开闪光灯
            if (!tipText.contains(ambientBrightnessTip)) {
                mZXingView?.scanBoxView?.tipText = tipText + ambientBrightnessTip
//                zXingView.openFlashlight()
//                mZXingView.closeFlashlight()
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip))
                mZXingView?.scanBoxView?.tipText = tipText
            }
        }

    }


    override fun setCanceledOnTouchOutside(): Boolean = true
    override fun setCancelable(): Boolean = true
    override fun setIsShowBackDark(): Boolean = true

    override fun onScanQRCodeOpenCameraError() {
        TalkSureDialog(mContext, 1200, "打开系统摄像头失败\n请打开权限重试").show()

    }


}