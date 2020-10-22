package com.mbcq.accountlibrary.fragment.setting.bluetooth

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.mbcq.accountlibrary.R
import com.mbcq.accountlibrary.fragment.settingadapter.SettingViewRecyclerAdapter
import com.mbcq.baselibrary.dialog.dialogfragment.BaseDialogFragment
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.view.SingleClick
import kotlinx.android.synthetic.main.dialog_bluetooth_list.*

class BlueToothDeviceListDialog(private val mScreenWidth: Int, private val mClick: OnClickInterface.OnRecyclerClickInterface, datas: List<BlueToothDeviceListBean>,val mPosition: Int) : BaseDialogFragment() {

    private val mDatas: List<BlueToothDeviceListBean> = datas
    override fun setDialogWidth(): Int {
        return mScreenWidth / 4 * 3
    }


    override fun initView(view: View, savedInstanceState: Bundle?) {
        bluetooth_device_recycler_view.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        val mBlueToothDeviceListAdapter = BlueToothDeviceListAdapter(mContext,mPosition)
        mBlueToothDeviceListAdapter.mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                mClick.onItemClick(v, position, mResult)
                dismiss()
            }

        }

        bluetooth_device_recycler_view.adapter = mBlueToothDeviceListAdapter
        mBlueToothDeviceListAdapter.appendData(mDatas)
        //************
        close_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                dismiss()
            }

        })
    }

    override fun setContentView(): Int = R.layout.dialog_bluetooth_list

}