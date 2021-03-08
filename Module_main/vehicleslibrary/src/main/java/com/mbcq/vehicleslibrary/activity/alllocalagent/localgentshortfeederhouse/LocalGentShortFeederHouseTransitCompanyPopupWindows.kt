package com.mbcq.vehicleslibrary.activity.alllocalagent.localgentshortfeederhouse

import android.content.Context
import android.view.Gravity
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.dialog.popup.BasePopupWindows
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.commonlibrary.adapter.BaseTextAdapterBean
import com.mbcq.commonlibrary.adapter.TextViewAdapter
import com.mbcq.vehicleslibrary.R

class LocalGentShortFeederHouseTransitCompanyPopupWindows(context: Context, var listData: List<BaseTextAdapterBean>, var mOnClickInterface: OnClickInterface.OnClickInterface? = null) : BasePopupWindows(context) {
    override fun initView(view: View) {
        val popup_local_gent_transit_company_recycler = view.findViewById<RecyclerView>(R.id.popup_local_gent_transit_company_recycler)
        popup_local_gent_transit_company_recycler.layoutManager = LinearLayoutManager(context)
        val mTextViewAdapter = TextViewAdapter<BaseTextAdapterBean>(context, Gravity.CENTER_VERTICAL)
        mTextViewAdapter.setIsShowOutSide(false)
        mTextViewAdapter.mClick = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                mOnClickInterface?.onResult(mResult,"")
                dismiss()
            }

        }
        popup_local_gent_transit_company_recycler.adapter = mTextViewAdapter
        popup_local_gent_transit_company_recycler.addItemDecoration(object : BaseItemDecoration(context) {})
        mTextViewAdapter.appendData(listData)


    }

}