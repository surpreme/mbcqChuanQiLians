package com.mbcq.accountlibrary.fragment.setting.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.accountlibrary.R
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.ui.BaseActivity
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.Constant
import zpSDK.zpSDK.zpBluetoothPrinter
import java.io.InputStream

/**
 * @information 蓝牙  以及打印机示例
 * @author lzy
 * @time 2020-10-23
 */
@Route(path = ARouterConstants.BlueActivity)
class BlueActivity : BaseActivity() {
    //    var mLoadingDialogFragment: LoadingDialogFragment? = null
    lateinit var text: TextView
    var bluetoothAdapter: BluetoothAdapter? = null
    override fun getLayoutId(): Int = R.layout.textview

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        enableBlueTooth()
        text = findViewById(R.id.text)
        text.text = "打印"
        text.setPadding(0, 200, 0, 0)
    }

    @SuppressLint("ResourceType")
    override fun onClick() {
        super.onClick()
        text.setOnClickListener(object : SingleClick(4000) {
            override fun onSingleClick(v: View?) {
                if (!enableBlueTooth())
                    return
                val zpSDK = zpBluetoothPrinter(this@BlueActivity)
                if (!zpSDK.connect(UserInformationUtil.getWayBillBlueToothPrinter(mContext))) {
                    TalkSureDialog(mContext, getScreenWidth(), "未连接到打印机 请您稍后再试 要不然换一个您再试试").show()
                    return
                }
                val res: Resources = resources
                val `is`: InputStream = res.openRawResource(R.drawable.app_logo)
                val bmpDraw = BitmapDrawable(`is`)
                val bmp: Bitmap = bmpDraw.bitmap


                zpSDK.pageSetup(568, 568)
                zpSDK.drawBarCode(8, 540, "12345678901234567", 128, true, 3, 60)
                zpSDK.drawGraphic(90, 48, 0, 0, bmp)
                zpSDK.drawQrCode(350, 48, "111111111", 0, 3, 0)
                zpSDK.drawText(90, 48 + 100, "400-8800-", 3, 0, 0, false, false)
                zpSDK.drawText(100, 48 + 100 + 56, "株洲      张贺", 4, 0, 0, false, false)
                zpSDK.drawText(250, 48 + 100 + 56 + 56, "经由  株洲", 2, 0, 0, false, false)

                zpSDK.drawText(100, 48 + 100 + 56 + 56 + 80, "2015110101079-01-01   广州", 3, 0, 0, false, false)
                zpSDK.drawText(100, 48 + 100 + 56 + 56 + 80 + 80, "2015-11-01  23:00    卡班", 3, 0, 0, false, false)

                zpSDK.drawBarCode(124, 48 + 100 + 56 + 56 + 80 + 80 + 80, "12345678901234567", 128, false, 3, 60)
                zpSDK.print(0, 0)

                zpSDK.printerStatus()
                when (zpSDK.GetStatus()) {
                    -1 -> {
                        TalkSureDialog(mContext, getScreenWidth(), "获取状态异常").show()

                    }
                    0 -> {
//                    TalkSureDialog(mContext, getScreenWidth(), "打印机正常").show()
                        LogUtils.d("打印机正常")
//                    mLoadingDialogFragment?.dismiss()

                    }
                    1 -> {
                        TalkSureDialog(mContext, getScreenWidth(), "请检查是否缺纸，如若缺纸请尽快添加打印纸").show()

                    }
                    2 -> {
                        TalkSureDialog(mContext, getScreenWidth(), "请检查打印机顶部的盖子是否关闭，请确认关闭后重试").show()

                    }
                }
                zpSDK.disconnect()
            }

            override fun onFastClick(v: View?) {
                super.onFastClick(v)
                showToast("请高抬您的小手手 等一会再点 ")
            }

        })

    }

    //0拒绝 -1同意
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.BLUETOOTH_REQUEST_CODE) {
            if (resultCode==0){
                TalkSureDialog(mContext,getScreenWidth(),"你个智障爱用不用，老子不伺候你了").show()
            }else{
//                enableBlueTooth()

            }
        }

    }

    fun getCommitedBlueToothDeviceList() {
        val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
        val mDatas = mutableListOf<BlueToothDeviceListBean>()
        pairedDevices?.forEach { device ->
            val deviceName = device.name
            val deviceHardwareAddress = device.address // MAC address
            val mBlueToothDeviceListBean = BlueToothDeviceListBean()
            mBlueToothDeviceListBean.deviceName = deviceName
            mBlueToothDeviceListBean.deviceHardwareAddress = deviceHardwareAddress
            mDatas.add(mBlueToothDeviceListBean)
        }
//        BlueToothDeviceListDialog(getScreenWidth(),mDatas).show(supportFragmentManager,"getCommitedBlueToothDeviceList")
    }

    //启动蓝牙
    private fun enableBlueTooth(): Boolean {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            TalkSureDialog(mContext, getScreenWidth(), "设备不支持蓝牙功能") {
                onBackPressed()
            }.show()
            return false

        }
        bluetoothAdapter?.let {
            return if (!it.isEnabled) {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, Constant.BLUETOOTH_REQUEST_CODE)
                false
            } else {
                true
                //                getCommitedBlueToothDeviceList()
            }
        }
        return false
    }

}