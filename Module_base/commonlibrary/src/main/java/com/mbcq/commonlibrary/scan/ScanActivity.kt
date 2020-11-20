package com.mbcq.commonlibrary.scan

import android.os.Bundle
import android.os.Vibrator
import android.view.View
import cn.bingoogolapple.qrcode.core.BarcodeType
import cn.bingoogolapple.qrcode.core.QRCodeView
import cn.bingoogolapple.qrcode.zxing.ZXingView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.ui.BaseActivity
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.R
import kotlinx.android.synthetic.main.activity_scan.*

/**
 * @author liziyang
 * @info 扫描的activity
 */
@Route(path = ARouterConstants.ScanActivity)
class ScanActivity : BaseActivity(), QRCodeView.Delegate {
    var mZXingView: ZXingView? = null

    override fun getLayoutId(): Int = R.layout.activity_scan
    override fun onClick() {
        super.onClick()
        scan_toolbar.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(1)
        scan_toolbar.setPadding(0, ScreenSizeUtils.getStatusBarHeight(mContext), 0, 0)
        mZXingView = findViewById(R.id.mZxingview)
        mZXingView?.setDelegate(this@ScanActivity)
    }

    override fun onScanQRCodeSuccess(result: String?) {
        openVibrator()
        mZXingView?.stopSpot()
        ARouter.getInstance().build(ARouterConstants.WebPageLogInActivity).navigation()
        this.finish()
    }

    override fun onCameraAmbientBrightnessChanged(isDark: Boolean) {
        var tipText: String = mZXingView?.scanBoxView?.tipText!!
        val ambientBrightnessTip = "\n环境过暗，请打开闪光灯"
        if (isDark) {
            //在这里加入了根据传感器光线暗的时候自动打开闪光灯
            if (!tipText.contains(ambientBrightnessTip)) {
                mZXingView?.scanBoxView?.tipText = tipText + ambientBrightnessTip
                mZXingView?.openFlashlight()
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip))
                mZXingView?.scanBoxView?.tipText = tipText
            }
        }
    }

    override fun onScanQRCodeOpenCameraError() {
        TalkSureDialog(mContext, 1200, "打开系统摄像头失败\n请打开权限重试") {
        }.show()
    }

    override fun onResume() {
        super.onResume()
        mZXingView?.startCamera()
        mZXingView?.startSpotAndShowRect()
        mZXingView?.startSpot()
        mZXingView?.setType(BarcodeType.ALL, null)
    }

    override fun onStop() {
        super.onStop()
        mZXingView?.closeFlashlight()

    }

    override fun onDestroy() {
        super.onDestroy()
        mZXingView?.onDestroy()

    }

    /**
     * 打开系统震动
     */
    fun openVibrator() {
        val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator?
        vibrator?.vibrate(200)
    }

}