package com.mbcq.commonlibrary.dialog

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.dialog.dialogfragment.BaseDialogFragment
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.commonlibrary.R
import com.mbcq.commonlibrary.adapter.BaseAdapterBean
import com.mbcq.commonlibrary.adapter.TextViewAdapter

class FilterDialog(var mScreenWidth: Int, var mDatas: List<BaseAdapterBean>, var tips: String, var isGridLayoutManager: Boolean, var mClickInterface: OnClickInterface.OnRecyclerClickInterface) : BaseDialogFragment() {
    lateinit var filter_recycler_view: RecyclerView
    lateinit var top_title_tv: TextView
    lateinit var close_btn: Button
    override fun setDialogWidth(): Int {
        return mScreenWidth / 4 * 3
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        filter_recycler_view = view.findViewById(R.id.filter_recycler_view)
        close_btn = view.findViewById(R.id.close_btn)
        top_title_tv = view.findViewById(R.id.top_title_tv)
        top_title_tv.text = tips
        close_btn.setOnClickListener {
            dismiss()
        }
        if (isGridLayoutManager)
        filter_recycler_view.layoutManager = GridLayoutManager(mContext, 3)
        else
            filter_recycler_view.layoutManager =LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)

        val mTextViewAdapter = TextViewAdapter<BaseAdapterBean>(mContext)
        mTextViewAdapter.mClick = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, result: String) {
                mClickInterface.onItemClick(v, position, result)
                dismiss()
            }

        }
        filter_recycler_view.adapter = mTextViewAdapter
        mTextViewAdapter.appendData(mDatas)

    }

    override fun setContentView(): Int = R.layout.dialog_filter_recyclerview

}