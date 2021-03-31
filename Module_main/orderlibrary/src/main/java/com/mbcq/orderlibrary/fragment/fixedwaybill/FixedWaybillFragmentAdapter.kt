package com.mbcq.orderlibrary.fragment.fixedwaybill

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.orderlibrary.R
import com.mbcq.orderlibrary.fragment.waybillinformation.WaybillInformationFixedTableAdapter
import com.mbcq.orderlibrary.fragment.waybillinformation.WaybillInformationFixedTableBean


class FixedWaybillFragmentAdapter(context: Context) : BaseRecyclerAdapter<FixedWaybillListBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_accept_billing_fragment_record, parent, false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).fixed_waybill_info_tv.text = "修改时间：${mDatas[position].opeDate}\n修  改 人：${mDatas[position].opeMan}\n修改原因：${mDatas[position].updateRemark}"
        holder.fix_waybill_state_tv.text = mDatas[position].state
        holder.device_from_info_tv.text = "操作端：" + mDatas[position].fromTypeStr
        context?.let {
            holder.fixed_waybill_table_recycler.adapter = WaybillInformationFixedTableAdapter(context).also {
                val titlesStr = mDatas[position].updateItem.split("－")
                val beforeStr = mDatas[position].updateBeforeContent.split("－")
                val afterStr = mDatas[position].updateAfterContent.split("－")
                if (titlesStr.lastIndex == beforeStr.lastIndex && beforeStr.lastIndex == afterStr.lastIndex) {
                    val recyList = mutableListOf<WaybillInformationFixedTableBean>()
                    val mTitleBean = WaybillInformationFixedTableBean()
                    mTitleBean.contentStr = "修改内容"
                    mTitleBean.beforeStr = "旧信息"
                    mTitleBean.endStr = "新信息"
                    mTitleBean.isTitles = true
                    recyList.add(mTitleBean)
                    for (index in titlesStr.indices) {
                        if (titlesStr[index].isBlank() && beforeStr[index].isBlank() && afterStr[index].isBlank())
                            continue
                        val mWaybillInformationFixedTableBean = WaybillInformationFixedTableBean()
                        mWaybillInformationFixedTableBean.contentStr = titlesStr[index]
                        mWaybillInformationFixedTableBean.beforeStr = beforeStr[index]
                        mWaybillInformationFixedTableBean.endStr = afterStr[index]
                        mWaybillInformationFixedTableBean.isTitles = false
                        recyList.add(mWaybillInformationFixedTableBean)
                    }
                    it.appendData(recyList)
                }
            }
            holder.fixed_waybill_table_recycler.layoutManager = LinearLayoutManager(context)
            holder.fixed_waybill_table_recycler.addItemDecoration(object : BaseItemDecoration(context) {
                override fun configExtraSpace(position: Int, count: Int, rect: Rect) {
                    rect.top = ScreenSizeUtils.dp2px(mContext, 1f)
                }

                override fun doRule(position: Int, rect: Rect) {
                    rect.bottom = rect.top
                }

            })
        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var fixed_waybill_info_tv = itemView.findViewById<TextView>(R.id.fixed_waybill_info_tv)
        var fix_waybill_state_tv = itemView.findViewById<TextView>(R.id.fix_waybill_state_tv)
        var fixed_waybill_table_recycler = itemView.findViewById<RecyclerView>(R.id.fixed_waybill_table_recycler)
        var device_from_info_tv = itemView.findViewById<TextView>(R.id.device_from_info_tv)
    }
}