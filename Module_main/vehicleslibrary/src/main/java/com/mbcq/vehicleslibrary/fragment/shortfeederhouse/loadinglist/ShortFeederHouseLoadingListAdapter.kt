package com.mbcq.vehicleslibrary.fragment.shortfeederhouse.loadinglist

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.bean.StockWaybillListBean

/**
 * 界面共用太多 这里当isDevelopments 处理拆票 在appenData的时候添加进去
 */
class ShortFeederHouseLoadingListAdapter(context: Context?) : BaseRecyclerAdapter<StockWaybillListBean>(context = context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_short_feeder_house_loading, parent, false))
    fun checkedAll(isC: Boolean) {
        for ((index, item) in mDatas.withIndex()) {
            item.isChecked = isC
            notifyItemChanged(index)
        }
    }

    var mOnRemoveInterface: OnRemoveInterface? = null

    interface OnRemoveInterface {
        fun onClick(position: Int, item: StockWaybillListBean)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).waybill_number_tv.text = mDatas[position].billno
        holder.waybill_number_tv.text = mDatas[position].billno
        holder.cargo_No_tv.text = mDatas[position].goodsNum
        holder.waybill_time_tv.text = mDatas[position].billDate
        holder.shipper_outlets_tv.text = mDatas[position].webidCodeStr
        holder.receiver_outlets_tv.text = mDatas[position].ewebidCodeStr
        holder.shipper_tv.text = mDatas[position].shipper
        holder.receiver_tv.text = mDatas[position].consignee

        holder.developments_qty_tv.text = when {
            //新增车辆 使用这个 这个是缓存自己写入的件数
            mDatas[position].isDevelopments -> "实发件数：${mDatas[position].developmentsQty}"
            //修改车辆 干线展示用
            mDatas[position].qtyGx.isNotBlank() -> "实发件数：${mDatas[position].qtyGx}"
            //修改车辆 短驳展示用
            else -> "实发件数：${mDatas[position].qtyDb}"
        }
        holder.itemView.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View) {
                if (mDatas[position].isDevelopments)
                    mClickInterface?.onItemClick(v, position, Gson().toJson(mDatas[position]))
            }

        })
        holder.waybill_move_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mOnRemoveInterface?.onClick(position, mDatas[position])
            }

        })
        context?.let {
            holder.record_checkbox_iv.setImageDrawable(ContextCompat.getDrawable(it, if (mDatas[position].isChecked) R.drawable.ic_checked_icon else R.drawable.ic_unchecked_icon))

        }
        holder.record_checkbox_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mDatas[position].isChecked = !mDatas[position].isChecked
                notifyItemChanged(position)
            }

        })
        holder.information_tv.text = "${mDatas[position].product} ${mDatas[position].totalQty}件 ${mDatas[position].volumn}m³ ${mDatas[position].packages} ${mDatas[position].weight}Kg ${mDatas[position].accTypeStr}${mDatas[position].accSum}  "
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var waybill_number_tv: TextView = itemView.findViewById(R.id.waybill_number_tv)
        var record_checkbox_iv: ImageView = itemView.findViewById(R.id.record_checkbox_iv)
        var information_tv: TextView = itemView.findViewById(R.id.information_tv)
        var waybill_move_iv: ImageView = itemView.findViewById(R.id.waybill_move_iv)
        var cargo_No_tv: TextView = itemView.findViewById(R.id.cargo_No_tv)
        var waybill_time_tv: TextView = itemView.findViewById(R.id.waybill_time_tv)
        var shipper_outlets_tv: TextView = itemView.findViewById(R.id.shipper_outlets_tv)
        var receiver_outlets_tv: TextView = itemView.findViewById(R.id.receiver_outlets_tv)
        var shipper_tv: TextView = itemView.findViewById(R.id.shipper_tv)
        var receiver_tv: TextView = itemView.findViewById(R.id.receiver_tv)
        var developments_qty_tv: TextView = itemView.findViewById(R.id.developments_qty_tv)
    }
}