package com.mbcq.vehicleslibrary.activity.arrivalshortscanoperating

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.billy.android.swipe.SmartSwipe
import com.billy.android.swipe.SmartSwipeWrapper
import com.billy.android.swipe.SwipeConsumer
import com.billy.android.swipe.consumer.TranslucentSlidingConsumer
import com.billy.android.swipe.listener.SimpleSwipeListener
import com.google.gson.Gson
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.vehicleslibrary.R

class ArrivalShortScanOperatingAdapter(context: Context) : BaseRecyclerAdapter<ArrivalShortScanOperatingBean>(context) {
    interface OnLookInformationInterface {
        fun lookInfo(v: View, position: Int, data: ArrivalShortScanOperatingBean)
        fun lookAllInfo(v: View, position: Int, data: ArrivalShortScanOperatingBean)
    }

    var mOnLookInformationInterface: OnLookInformationInterface? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_arrival_trunk_departure_scan_operating, parent, false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).billno_tv.text = mDatas[position].billno
        holder.receiver_tv.text = mDatas[position].consignee
        holder.address_tv.text = "${mDatas[position].webidCodeStr}-${mDatas[position].ewebidCodeStr}"
        holder.goods_name_tv.text = mDatas[position].product
        holder.goods_number_ifo_tv.text = "已扫:${mDatas[position].loadQty}     本车:${mDatas[position].qty}    剩余:${(mDatas[position].qty) - (mDatas[position].loadQty)}     总件数:${mDatas[position].totalQty}"
        holder.father_fl.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View) {
                if ((mDatas[position].qty) - (mDatas[position].loadQty) == 0) return
                mClickInterface?.onItemClick(v, position, Gson().toJson(mDatas[position]))
            }

        })
        //侧滑删除
        holder.mSwipeConsumer?.let {
            it.addConsumer(TranslucentSlidingConsumer())
                    .enableRight() //启用左右两侧侧滑
                    .addListener(object : SimpleSwipeListener() {
                        override fun onSwipeOpened(wrapper: SmartSwipeWrapper?, consumer: SwipeConsumer, direction: Int) {
                            super.onSwipeOpened(wrapper, consumer, direction)
                            if (direction == SwipeConsumer.DIRECTION_RIGHT) {
                                mOnLookInformationInterface?.lookInfo(holder.father_fl, position, mDatas[position])
                            } else if (direction == SwipeConsumer.DIRECTION_LEFT) {
//                            mOnLookInformationInterface?.lookAllInfo(holder.father_fl, position, mDatas[position])

                            }
                        }
                    })
        }

        /**
         * @qty 本车数量
         * @loadQty 扫描数量
         * @totalQty 总件数
         */
        context?.let {
            holder.operating_progressbar.progressDrawable = ContextCompat.getDrawable(context, if ((mDatas[position].qty - mDatas[position].loadQty) == 0) R.drawable.progress_indeterminate_green_horizontal else R.drawable.progress_indeterminate_horizontal)
            holder.operating_progressbar.progress =
                    if (mDatas[position].loadQty == 0)
                        0
                    else if (mDatas[position].loadQty == mDatas[position].qty)
                        100
                    else
                        ((mDatas[position].loadQty * 100) / (mDatas[position].qty))

        }

    }

    class ItemViewHolder : RecyclerView.ViewHolder {
        var billno_tv = itemView.findViewById<TextView>(R.id.billno_tv)
        var receiver_tv = itemView.findViewById<TextView>(R.id.receiver_tv)
        var address_tv = itemView.findViewById<TextView>(R.id.address_tv)
        var goods_name_tv = itemView.findViewById<TextView>(R.id.goods_name_tv)
        var goods_number_ifo_tv = itemView.findViewById<TextView>(R.id.goods_number_ifo_tv)
        var operating_progressbar = itemView.findViewById<ProgressBar>(R.id.operating_progressbar)
        var father_fl = itemView.findViewById<FrameLayout>(R.id.father_fl)

        //侧滑删除
        var mSwipeConsumer: SmartSwipeWrapper? = null

        //                .addConsumer(TranslucentSlidingConsumer())
//                .enableRight() //启用左右两侧侧滑
        constructor(itemView: View) : super(itemView) {
            mSwipeConsumer = SmartSwipe.wrap(father_fl)

        }


    }


}