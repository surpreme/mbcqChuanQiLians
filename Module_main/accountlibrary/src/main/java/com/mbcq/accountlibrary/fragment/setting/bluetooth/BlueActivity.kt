package com.mbcq.accountlibrary.fragment.setting.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.accountlibrary.R
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.ui.BaseActivity
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.Constant

//@Route(path = ARouterConstants.BlueActivity)
class BlueActivity : BaseActivity() {
    var bluetoothAdapter: BluetoothAdapter? = null
    override fun getLayoutId(): Int = R.layout.textview

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        enableBlueTooth()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.BLUETOOTH_REQUEST_CODE) {
            enableBlueTooth()
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
    private fun enableBlueTooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            TalkSureDialog(mContext, getScreenWidth(), "设备不支持蓝牙功能") {
                onBackPressed()
            }.show()
            return

        }
        bluetoothAdapter?.let {
            if (!it.isEnabled) {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, Constant.BLUETOOTH_REQUEST_CODE)
            } else {
                getCommitedBlueToothDeviceList()
            }
        }

    }

}