package com.mbcq.accountlibrary.fragment.setting

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.accountlibrary.R
import com.mbcq.accountlibrary.fragment.iconadapter.IconViewBean
import com.mbcq.accountlibrary.fragment.setting.bluetooth.BlueToothDeviceListBean
import com.mbcq.accountlibrary.fragment.setting.bluetooth.BlueToothDeviceListDialog
import com.mbcq.accountlibrary.fragment.setting.bluetooth.SelectBlueToothDeviceListBean
import com.mbcq.accountlibrary.fragment.settingadapter.SettingIconBean
import com.mbcq.accountlibrary.fragment.settingadapter.SettingViewRecyclerAdapter
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.interfaces.RxBus
import com.mbcq.baselibrary.ui.BaseFragment
import com.mbcq.baselibrary.ui.BaseListFragment
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.Constant
import kotlinx.android.synthetic.main.fragment_setting.*
import org.json.JSONObject

/**
 * 我的
 */
class SettingFragment : BaseListFragment<SettingIconBean>() {
    var bluetoothAdapter: BluetoothAdapter? = null

    override fun getLayoutResId(): Int = R.layout.fragment_setting

    override fun onClick() {
        super.onClick()
        setting_toolbar.setRightButtonOnClickListener {
            ARouter.getInstance().build(ARouterConstants.SettingActivity).navigation()

        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.BLUETOOTH_REQUEST_CODE) {
            bluetoothAdapter?.let {
                if (!it.isEnabled) {
                    TalkSureDialog(mContext, getScreenWidth(), "请打开蓝牙后再继续使用！！").show()
                } else {
                    enableBlueTooth(mResultIndex, mResultBlue)
                }
            }
        }
    }

    override fun addItemDecoration(): RecyclerView.ItemDecoration = object : BaseItemDecoration(mContext) {
        override fun configExtraSpace(position: Int, count: Int, rect: Rect) {
            if (position == 0 || position == 7 || position == 14 || position == 18)
                rect.bottom = ScreenSizeUtils.dp2px(mContext, 10f)
            else if (position == 17) {
                rect.bottom = ScreenSizeUtils.dp2px(mContext, 1f)

            }
        }

        override fun doRule(position: Int, rect: Rect) {
            rect.bottom = rect.top
        }
    }

    @SuppressLint("CheckResult")
    override fun initDatas() {
        super.initDatas()
        RxBus.build().toObservable(this, SelectBlueToothDeviceListBean::class.java).subscribe { msg ->


        }
        val list = arrayListOf<SettingIconBean>()
        for (index in 0..18) {
            val mSettingIconBean = SettingIconBean()
            when (index) {
                0 -> {
                    mSettingIconBean.tag = 2
                    val mSettingMoreList = mutableListOf<SettingIconBean.ItemBean>()
                    for (mIIIDEx in 0..2) {
                        val mNNNN = SettingIconBean.ItemBean()
                        when (mIIIDEx) {
                            0 -> {
                                mNNNN.showTxt = "门店自寄"
                            }
                            1 -> {
                                mNNNN.showTxt = "偏好设置"
                            }
                            2 -> {
                                mNNNN.showTxt = "门店自寄"
                            }
                        }
                        mSettingMoreList.add(mNNNN)
                    }
                    mSettingIconBean.iconItemBean = mSettingMoreList
                }
                1 -> {
                    mSettingIconBean.tag = 1
                }
                2 -> {
                    mSettingIconBean.tag = 3
                    mSettingIconBean.title = "打印模式"
                }
                3 -> {
                    mSettingIconBean.tag = 3
                    mSettingIconBean.title = "运单打印机"
                    mSettingIconBean.contentText = UserInformationUtil.getWayBillBlueToothPrinterName(mContext)
                }
                4 -> {
                    mSettingIconBean.tag = 3
                    mSettingIconBean.title = "标签打印机"
                    mSettingIconBean.contentText = UserInformationUtil.getWayBillBlueToothPrinterName(mContext)

                }
                5 -> {
                    mSettingIconBean.tag = 3
                    mSettingIconBean.title = "指环王设置"

                }
                6 -> {
                    mSettingIconBean.tag = 3
                    mSettingIconBean.title = "网间托运单"

                }
                7 -> {
                    mSettingIconBean.tag = 3
                    mSettingIconBean.title = "网内托运单"

                }
                /**
                 *
                 */
                8 -> {
                    mSettingIconBean.tag = 3
                    mSettingIconBean.title = "常用收货网点"

                }
                9 -> {
                    mSettingIconBean.tag = 3
                    mSettingIconBean.title = "常用目的地"

                }
                10 -> {
                    mSettingIconBean.tag = 3
                    mSettingIconBean.title = "常用收货方式"

                }
                11 -> {
                    mSettingIconBean.tag = 3
                    mSettingIconBean.title = "常用付货方式"

                }
                12 -> {
                    mSettingIconBean.tag = 3
                    mSettingIconBean.title = "常用货物名称"

                }
                13 -> {
                    mSettingIconBean.tag = 3
                    mSettingIconBean.title = "常用包装方式"

                }
                14 -> {
                    mSettingIconBean.tag = 3
                    mSettingIconBean.title = "常用开单备注"

                }
                /**
                 *
                 */
                15 -> {
                    mSettingIconBean.tag = 3
                    mSettingIconBean.title = "扫描备注"

                }
                16 -> {
                    mSettingIconBean.tag = 3
                    mSettingIconBean.title = "语音播报开关"

                }
                17 -> {
                    mSettingIconBean.tag = 3
                    mSettingIconBean.title = "语音播报人"

                }
                18 -> {
                    mSettingIconBean.tag = 4
                }
            }
            list.add(mSettingIconBean)

        }
        adapter.appendData(list)

    }

    fun getCommitedBlueToothDeviceList(mPosition: Int, result: String) {
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
        BlueToothDeviceListDialog(getScreenWidth(), object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                //{"title":"运单打印机","tag:"3"}
                val obj = JSONObject(result)
                val blueObj = JSONObject(mResult)
                obj.put("contentText", blueObj.optString("deviceName"))
                if (obj.optString("title") == "运单打印机") {
                    mContext.let {
                        UserInformationUtil.setWayBillBlueToothPrinter(it, blueObj.optString("deviceHardwareAddress"))
                        UserInformationUtil.setWayBillBlueToothPrinterName(it, blueObj.optString("deviceName"))
                    }
                }
                showToast(GsonUtils.toPrettyFormat(obj))
                val mSettingIconBean = Gson().fromJson<SettingIconBean>(GsonUtils.toPrettyFormat(obj), SettingIconBean::class.java)
                adapter.notifyItemChangeds(mPosition, mSettingIconBean)
            }

        }, mDatas, mPosition).show(childFragmentManager, "getCommitedBlueToothDeviceLists")
    }

    //启动蓝牙
    private fun enableBlueTooth(position: Int, result: String) {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            mContext.let {
                TalkSureDialog(it, getScreenWidth(), "设备不支持蓝牙功能").show()

            }
            return

        }
        bluetoothAdapter?.let {
            if (!it.isEnabled) {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, Constant.BLUETOOTH_REQUEST_CODE)
            } else {
                getCommitedBlueToothDeviceList(position, result)
            }
        }

    }

    var mResultBlue = ""
    var mResultIndex = 0
    override fun getRecyclerViewId(): Int = R.id.setting_recycler_view

    override fun setAdapter(): BaseRecyclerAdapter<SettingIconBean> = SettingViewRecyclerAdapter(mContext).also {
        it.mOnClickInterface = object : SettingViewRecyclerAdapter.OnClickInterface {
            override fun onExitLogIn(v: View) {
                activity?.finish()
                ARouter.getInstance().build(ARouterConstants.LogInActivity).navigation()

            }

            override fun onCommonly(v: View, position: Int, result: String) {
                val mSettingIconBean = Gson().fromJson<SettingIconBean>(result, SettingIconBean::class.java)
                mResultBlue = result
                mResultIndex = position
                //{"title":"运单打印机","tag:"3"}
//                showToast(result)
                when (mSettingIconBean.title) {
                    "运单打印机" -> {
                        enableBlueTooth(position, result)
                    }
                    "标签打印机" -> {
                        enableBlueTooth(position, result)
                    }
                }
            }

        }
    }

}