package com.mbcq.baselibrary.ui


import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import com.mbcq.baselibrary.R
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.scwang.smartrefresh.header.DeliveryHeader
import com.scwang.smartrefresh.header.TaurusHeader
import com.scwang.smartrefresh.header.WaterDropHeader
import com.scwang.smartrefresh.header.WaveSwipeHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.header.ClassicsHeader

/**
 * @Auther: liziyang
 * @datetime: 2020-01-18
 * @desc:
 * 参考WaybillRecordActivity使用方法
 */
abstract class BaseSmartMVPActivity<V : BaseView, T : BasePresenterImpl<V>, X> : BaseListMVPActivity<V, T, X>(), BaseView {
    abstract fun getSmartLayoutId(): Int
    abstract fun getSmartEmptyId(): Int
    open fun getPageDatas(mCurrentPage: Int) {}
    private var isHaveMore: Boolean = true
    var isMore: Boolean = true
    private var mCurrentPage: Int = 1
    lateinit var mSmartRefreshLayout: SmartRefreshLayout
    lateinit var mSmartFrameLayout: ViewGroup

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        mSmartRefreshLayout = this.findViewById(getSmartLayoutId())
        mSmartRefreshLayout.setRefreshHeader(ClassicsHeader(mContext))
        mSmartFrameLayout = this.findViewById(getSmartEmptyId())
        showNoData()
        mSmartRefreshLayout.setOnRefreshListener {
            refresh()
            it.finishRefresh()
        }
        mSmartRefreshLayout.setOnLoadMoreListener {
            if (isHaveMore) {
                mCurrentPage++
                getPageDatas(mCurrentPage)
                it.finishLoadMore()
            } else {
                it.finishLoadMoreWithNoMoreData()
            }

        }
    }

    override fun initDatas() {
        getPageDatas(mCurrentPage)

    }

    var mNoDataIv: ImageView? = null
    fun showNoData() {
        if (mNoDataIv == null)
            mNoDataIv = ImageView(mContext)
        mNoDataIv?.scaleType = ImageView.ScaleType.CENTER_INSIDE
        mNoDataIv?.setImageDrawable(getDrawable(R.drawable.base_smart_empty))
        val linearParams = ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.MATCH_PARENT)
        mSmartFrameLayout.addView(mNoDataIv, linearParams)
    }

    fun stopNoData() {
        if (mNoDataIv != null)
            mSmartFrameLayout.removeView(mNoDataIv)
    }

    open fun refresh() {
        adapter.clearData()
        mCurrentPage = 1
        isHaveMore = true
        isMore = true
        mSmartRefreshLayout.setNoMoreData(false)
        getPageDatas(mCurrentPage)
    }

    fun appendDatas(list: List<X>) {
        if (list.isNotEmpty() && mCurrentPage == 1)
            stopNoData()

        if (isHaveMore)
            adapter.appendData(list)
        isHaveMore = list.isNotEmpty() && isMore

    }

}