package com.mbcq.vehicleslibrary.activity.departuretrunkdeparturescanoperating

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.billy.android.swipe.SmartSwipe
import com.billy.android.swipe.SmartSwipeWrapper
import com.billy.android.swipe.SwipeConsumer
import com.billy.android.swipe.consumer.TranslucentSlidingConsumer
import com.billy.android.swipe.listener.SimpleSwipeListener
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.shorttrunkdeparturescanoperating.ShortTrunkDepartureScanOperatingBean

class DepartureTrunkDepartureScanOperatingAdapter(context: Context) : BaseRecyclerAdapter<DepartureTrunkDepartureScanOperatingBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_arrival_trunk_short_scan_operating, parent, false))
    interface OnLookInformationInterface {
        fun lookInfo(v: View, position: Int, data: DepartureTrunkDepartureScanOperatingBean)
        fun lookAllInfo(v: View, position: Int, data: DepartureTrunkDepartureScanOperatingBean)
    }

    var mOnLookInformationInterface: OnLookInformationInterface? = null

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).billno_tv.text = mDatas[position].billno
        context?.let {
            holder.operating_progressbar.progressDrawable = ContextCompat.getDrawable(context, if (mDatas[position].waybillFcdQty == 0) R.drawable.progress_indeterminate_green_horizontal else R.drawable.progress_indeterminate_horizontal)
        }
        holder.look_information_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View) {
                mOnLookInformationInterface?.lookInfo(v, position, mDatas[position])
            }

        })

        holder.father_cl.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View) {
                val mScanSun = mDatas[position].unLoadQty
                if (mDatas[position].waybillFcdQty == 0) {
                    mClickInterface?.onItemClick(v, position, "")
                    return
                }
                val mEndScanSun = mScanSun + 1
                val endBillno = if (mEndScanSun.toString().length == 1) "000$mEndScanSun" else if (mEndScanSun.toString().length == 2) "00$mEndScanSun" else if (mEndScanSun.toString().length == 3) "0$mEndScanSun" else if (mEndScanSun.toString().length == 4) "$mEndScanSun" else ""
                mClickInterface?.onItemClick(v, position, mDatas[position].billno + endBillno)
            }
        })
        holder.isunplantag_tv.visibility = if (mDatas[position].isScanDet == "2") View.VISIBLE else View.GONE
        holder.receiver_tv.text = mDatas[position].consignee
        holder.address_tv.text = "${mDatas[position].webidCodeStrGx}---${mDatas[position].ewebidCodeStrGx}"
        holder.goods_name_tv.text = mDatas[position].product
        /**
        库存件数是扫一件少一件
        1。总件数=运单件数
        2、本车件数=   扫描件数+PC手动添加的件数
        3、@旧 剩余件数=库存件数-本车件数 @新 剩余件数=库存件数
        4、已扫件数=所有扫描的件数（大票货扫描件数+小票货扫描件数+PDA手动录入的件数）
        5、@旧 进度条=已扫件数件数/库存件数*100 @新 进度条=已扫描件数/（已扫件数件数+库存件数）*100
         */
        holder.goods_number_ifo_tv.text = "已扫:${mDatas[position].unLoadQty}     本车:${mDatas[position].unLoadQty}    剩余:${mDatas[position].waybillFcdQty}     总件数:${mDatas[position].totalQty}*${mDatas[position].weight}kg*${mDatas[position].volumn}m*"
        holder.operating_progressbar.progress = if (mDatas[position].unLoadQty == 0) 0 else if (mDatas[position].unLoadQty == (mDatas[position].unLoadQty + mDatas[position].waybillFcdQty)) 100 else ((mDatas[position].unLoadQty * 100) / (mDatas[position].unLoadQty + mDatas[position].waybillFcdQty))
        //侧滑删除
        SmartSwipe.wrap(holder.father_fl)
                .addConsumer(TranslucentSlidingConsumer())
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

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var operating_progressbar = itemView.findViewById<ProgressBar>(R.id.operating_progressbar)
        var billno_tv = itemView.findViewById<TextView>(R.id.billno_tv)
        var receiver_tv = itemView.findViewById<TextView>(R.id.receiver_tv)
        var address_tv = itemView.findViewById<TextView>(R.id.address_tv)
        var goods_name_tv = itemView.findViewById<TextView>(R.id.goods_name_tv)
        var goods_number_ifo_tv = itemView.findViewById<TextView>(R.id.goods_number_ifo_tv)
        var isunplantag_tv = itemView.findViewById<TextView>(R.id.isunplantag_tv)
        var look_information_tv = itemView.findViewById<TextView>(R.id.look_information_tv)
        var father_cl = itemView.findViewById<ConstraintLayout>(R.id.father_cl)
        var father_fl = itemView.findViewById<FrameLayout>(R.id.father_fl)

    }


}