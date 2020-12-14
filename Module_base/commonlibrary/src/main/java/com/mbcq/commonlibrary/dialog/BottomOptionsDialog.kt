package com.mbcq.commonlibrary.dialog

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.dialog.popup.BasePopupWindows
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.commonlibrary.R
import com.mbcq.commonlibrary.adapter.BaseTextAdapterBean
import com.mbcq.commonlibrary.adapter.TextViewAdapter

class BottomOptionsDialog(context: Context, list: List<BaseTextAdapterBean>) : BasePopupWindows(context) {
    lateinit var bottom_options_recycler: RecyclerView
    var mDatas: List<BaseTextAdapterBean> = list
    var mOnRecyclerClickInterface: OnClickInterface.OnRecyclerClickInterface? = null

    override fun initView(view: View) {
        bottom_options_recycler = view.findViewById(R.id.bottom_options_recycler)
        val mTextViewAdapter = TextViewAdapter<BaseTextAdapterBean>(context)
        bottom_options_recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        bottom_options_recycler.adapter = mTextViewAdapter
        bottom_options_recycler.addItemDecoration(object : BaseItemDecoration(context) {
            override fun configExtraSpace(position: Int, count: Int, rect: Rect) {
                rect.top = ScreenSizeUtils.dp2px(mContext, 0.3f)
            }

            override fun doRule(position: Int, rect: Rect) {
                rect.bottom = rect.top
            }
        })
        mTextViewAdapter.setIsShowOutSide(false)
        mTextViewAdapter.setTextPadding(10)
        mTextViewAdapter.mClick = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                mOnRecyclerClickInterface?.onItemClick(v, position, mResult)
                dismiss()
            }

        }
        mTextViewAdapter.appendData(mDatas)

    }

}