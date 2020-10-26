package com.mbcq.commonlibrary.baseimagerecycler

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.mbcq.baselibrary.ui.BaseFragment
import com.mbcq.commonlibrary.R
import com.mbcq.commonlibrary.adapter.EmptyImageViewAdapter
import com.mbcq.commonlibrary.adapter.ImageViewBean
import kotlinx.android.synthetic.main.fragment_image_recycler.*

/**
 * @author liziyang
 * @time 2020-10-26
 * @information 图片浏览 和抖音类似
 */
class BaseImageRecyclerFragment(private val mImageViewList: List<ImageViewBean>) : BaseFragment() {
    override fun getLayoutResId(): Int = R.layout.fragment_image_recycler

    override fun initViews(view: View) {
        image_recycler_basic.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        image_recycler_basic.adapter = EmptyImageViewAdapter(mContext).also {
            it.appendData(mImageViewList)
        }
    }

}