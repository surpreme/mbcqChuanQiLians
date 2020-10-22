package com.mbcq.accountlibrary.fragment.setting.bluetooth

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.mbcq.accountlibrary.R
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.interfaces.RxBus
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick

class BlueToothDeviceListAdapter(context: Context?, mPosition: Int) : BaseRecyclerAdapter<BlueToothDeviceListBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_bluetooth_device_list, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).bluetooth_name_tv.text = mDatas[position].deviceName
        holder.bluetooth_content_tv.text = mDatas[position].deviceHardwareAddress
        holder.itemView.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View) {
                val mSelectBlueToothDeviceListBean = SelectBlueToothDeviceListBean()
                mSelectBlueToothDeviceListBean.setmPosition(position)
                mSelectBlueToothDeviceListBean.deviceName = mDatas[position].deviceName
                mSelectBlueToothDeviceListBean.deviceHardwareAddress = mDatas[position].deviceHardwareAddress
//                RxBus.build().post(mSelectBlueToothDeviceListBean)
                mClickInterface?.onItemClick(v,position,Gson().toJson(mDatas[position]))
            }
        })
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var bluetooth_name_tv = itemView.findViewById<TextView>(R.id.bluetooth_name_tv)
        var bluetooth_content_tv = itemView.findViewById<TextView>(R.id.bluetooth_content_tv)
    }
}