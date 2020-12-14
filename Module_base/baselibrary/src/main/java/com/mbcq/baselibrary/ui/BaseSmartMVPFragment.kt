package com.mbcq.baselibrary.ui


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.mbcq.baselibrary.R
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.header.ClassicsHeader

/**
 * @Auther: liziyang
 * @datetime: 2020-01-18
 * @desc:
 * 参考WaybillRecordActivity使用方法
 */
abstract class BaseSmartMVPFragment<V : BaseView, T : BasePresenterImpl<V>, X> : BaseListMVPFragment<V, T, X>(), BaseView {
    abstract fun getSmartLayoutId(): Int
    abstract fun getSmartEmptyId(): Int
    fun getEnableLoadMore(): Boolean = true//是否分页 加载更多
    open fun getPageDatas(mCurrentPage: Int) {}
    private var isHaveMore: Boolean = true
    var isMore: Boolean = true
    private var mCurrentPage: Int = 1
    fun getCurrentPage():Int=mCurrentPage
    lateinit var mSmartRefreshLayout: SmartRefreshLayout
    lateinit var mSmartFrameLayout: ViewGroup
    override fun initViews(view: View) {
        super.initViews(view)
        mSmartRefreshLayout = view.findViewById(getSmartLayoutId())
        mSmartRefreshLayout.setRefreshHeader(ClassicsHeader(mContext))
        mSmartFrameLayout = view.findViewById(getSmartEmptyId())
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

    var mNoDataView: View? = null

    fun showNoData() {
        val inflater = LayoutInflater.from(mContext)
        if (mNoDataView == null)
            mNoDataView = inflater.inflate(R.layout.nodata_ll, null)
        val lp: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        mSmartFrameLayout.addView(mNoDataView, lp)
    }

    fun stopNoData() {
        if (mNoDataView != null)
            mSmartFrameLayout.removeView(mNoDataView)
    }

    open fun refresh() {
        if (mSmartFrameLayout.childCount <= 1)
            showNoData()
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