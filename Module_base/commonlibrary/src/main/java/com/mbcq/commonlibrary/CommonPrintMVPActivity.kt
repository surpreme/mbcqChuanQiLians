package com.mbcq.commonlibrary

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.log.LogUtils
import zpSDK.zpSDK.zpBluetoothPrinter

/**
 * @Auther: liziyang
 * @datetime: 2020-10-23
 * @desc:
 */
abstract class CommonPrintMVPActivity<V : BaseView, T : BasePresenterImpl<V>> : BaseMVPActivity<V, T>(), BaseView {
    protected var bluetoothAdapter: BluetoothAdapter? = null
    protected fun isCanPrint(): Boolean {
        if (!enableBlueTooth())
            return false
        if (UserInformationUtil.getWayBillBlueToothPrinter(mContext).isBlank()) {
            TalkSureDialog(mContext, getScreenWidth(), "您未配置蓝牙打印机 ").show()
            return false
        }

        return true
    }

    override fun initExtra() {
        super.initExtra()
        mBlueToothConnectInterface = setBlueToothConnectInterface()
    }

    protected fun getZpBluetoothPrinter(): zpBluetoothPrinter {
        val zpSDK = zpBluetoothPrinter(this)
        if (!zpSDK.connect(UserInformationUtil.getWayBillBlueToothPrinter(mContext))) {
            TalkSureDialog(mContext, getScreenWidth(), "未连接到打印机 请您稍后再试 要不然换一个您再试试").show()
        }
        return zpSDK
    }

    protected fun showPrintEx(state: Int) {
        when (state) {
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
    }

    abstract fun setBlueToothConnectInterface(): BlueToothConnectInterface
    private var mBlueToothConnectInterface: BlueToothConnectInterface? = null

    interface BlueToothConnectInterface {
        fun isUnUsed(reason: String)
        fun overring()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.BLUETOOTH_REQUEST_CODE) {
            //请求蓝牙
            //0拒绝 -1同意
            if (resultCode == 0) {
                TalkSureDialog(mContext, getScreenWidth(), "请允许打开蓝牙，否则无法打印").show()
                mBlueToothConnectInterface?.isUnUsed("请允许打开蓝牙，否则无法打印")
            } else {
//                enableBlueTooth()

            }
        }
    }

    //启动蓝牙
    protected fun enableBlueTooth(): Boolean {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            TalkSureDialog(mContext, getScreenWidth(), "设备不支持蓝牙功能").show()
            return false

        }
        bluetoothAdapter?.let {
            return if (!it.isEnabled) {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, Constant.BLUETOOTH_REQUEST_CODE)
                false
            } else {
                true
            }
        }
        return false
    }
}