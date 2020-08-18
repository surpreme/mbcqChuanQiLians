package com.mbcq.commonlibrary.scan.scanlogin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import cn.bingoogolapple.qrcode.core.BarcodeType
import cn.bingoogolapple.qrcode.core.QRCodeView
import cn.bingoogolapple.qrcode.zxing.ZXingView
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.dialog.dialogfragment.BaseDialogFragment
import com.mbcq.commonlibrary.R

class ScanDialogFragment : BaseDialogFragment, QRCodeView.Delegate {
    var mZXingView: ZXingView? = null
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

    }

    override fun setContentView(): Int = R.layout.dialog_scan_login

    override fun initView(view: View, savedInstanceState: Bundle?) {
        mZXingView = view.findViewById(R.id.mZxingview)
        mZXingView?.setDelegate(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mZXingView?.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        mZXingView?.startCamera()
        mZXingView?.startSpotAndShowRect()
        mZXingView?.startSpot()
        mZXingView?.setType(BarcodeType.ALL, null)
    }

    override fun onScanQRCodeSuccess(result: String?) {
        TalkSureDialog(mContext, 1200, result).show()
    }

    override fun onCameraAmbientBrightnessChanged(isDark: Boolean) {
        var tipText: String = mZXingView?.scanBoxView?.tipText!!
        val ambientBrightnessTip = "\n环境过暗，请打开闪光灯"
        if (isDark) {
            //在这里加入了根据传感器光线暗的时候自动打开闪光灯
            if (!tipText.contains(ambientBrightnessTip)) {
                mZXingView?.scanBoxView?.tipText = tipText + ambientBrightnessTip
//                zXingView.openFlashlight();
//                mZXingView.closeFlashlight();
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip))
                mZXingView?.scanBoxView?.tipText = tipText;
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