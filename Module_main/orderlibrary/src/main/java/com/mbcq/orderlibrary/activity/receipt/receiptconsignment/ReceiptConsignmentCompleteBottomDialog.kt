package com.mbcq.orderlibrary.activity.receipt.receiptconsignment

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.dialog.popup.BasePopupWindows
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.commonlibrary.adapter.BaseTextAdapterBean
import com.mbcq.commonlibrary.adapter.TextViewAdapter
import com.mbcq.orderlibrary.R

class ReceiptConsignmentCompleteBottomDialog(context: Context, val list: List<BaseTextAdapterBean>, var mCclick: OnClickInterface.OnRecyclerClickInterface? = null) : BasePopupWindows(context) {
    override fun initView(view: View) {
        val receipt_consignment_complete_bottom_recycler = view.findViewById<RecyclerView>(R.id.receipt_consignment_complete_bottom_recycler)
        receipt_consignment_complete_bottom_recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        receipt_consignment_complete_bottom_recycler.adapter = TextViewAdapter<BaseTextAdapterBean>(context).also {
            it.appendData(list)
            it.setIsShowOutSide(false)
            it.mClick = object : OnClickInterface.OnRecyclerClickInterface {
                override fun onItemClick(v: View, position: Int, mResult: String) {
                    mCclick?.onItemClick(v, position, mResult)
                    dismiss()
                }

            }
        }
        if (receipt_consignment_complete_bottom_recycler.itemDecorationCount == 0) {
            receipt_consignment_complete_bottom_recycler.addItemDecoration(object : BaseItemDecoration(context) {
                override fun configExtraSpace(position: Int, count: Int, rect: Rect) {
                    rect.top = ScreenSizeUtils.dp2px(context, 2f)

                }

                override fun doRule(position: Int, rect: Rect) {
                    rect.bottom = rect.top
                }
            })
        }

    }

}