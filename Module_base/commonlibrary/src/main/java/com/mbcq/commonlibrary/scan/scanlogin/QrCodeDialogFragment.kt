package com.mbcq.commonlibrary.scan.scanlogin

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder
import com.mbcq.baselibrary.dialog.dialogfragment.BaseDialogFragment
import com.mbcq.commonlibrary.R


/**
 *生成二维码弹窗 带logo
 */
class QrCodeDialogFragment : BaseDialogFragment {
    lateinit var qrCode_iv: ImageView
    override fun setContentView(): Int = R.layout.dialog_qrcode
    var mScreenWidths: Int
    override fun setDialogWidth(): Int {
        return mScreenWidths / 4 * 3
    }

    override fun setDialogHeight(): Int {
        return mScreenWidths
    }
    override fun setCanceledOnTouchOutside(): Boolean {
        return true
    }

    override fun setCancelable(): Boolean {
        return true
    }
    override fun initView(view: View, savedInstanceState: Bundle?) {
        qrCode_iv = view.findViewById(R.id.qrCode_iv)
        createEnglishQRCodeWithLogo("lzylovelylzylovelylzylovelylzylovelylzylovelylzylovelylzylovelylzylovely")
    }
    constructor(mScreenWidth: Int) : super() {
        mScreenWidths = mScreenWidth
    }
    @SuppressLint("StaticFieldLeak")
    private fun createEnglishQRCodeWithLogo(qrText: String) {
        object : AsyncTask<Void?, Void?, Bitmap?>() {


            override fun onPostExecute(bitmap: Bitmap?) {
                if (bitmap != null) {
                    qrCode_iv.setImageBitmap(bitmap)
                }
            }

            override fun doInBackground(vararg params: Void?): Bitmap? {
                val logoBitmap = BitmapFactory.decodeResource(mContext.resources, R.drawable.app_logo)
                return QRCodeEncoder.syncEncodeQRCode(qrText, BGAQRCodeUtil.dp2px(mContext, 150f), Color.BLACK, Color.WHITE,
                        logoBitmap)
            }
        }.execute()
    }

}