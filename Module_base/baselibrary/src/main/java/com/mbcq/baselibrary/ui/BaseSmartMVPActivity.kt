package com.mbcq.baselibrary.ui


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
abstract class BaseSmartMVPActivity<V : BaseView, T : BasePresenterImpl<V>, X> : BaseListMVPActivity<V, T, X>(), BaseView {
    abstract fun getSmartLayoutId(): Int
    abstract fun getSmartEmptyId(): Int

    open fun getEnableLoadMore(): Boolean = true//是否分页 加载更多
    open fun getIsOnCreateGetData(): Boolean = true //在onCreate 或者 onResume 加载分页数据

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
        mSmartRefreshLayout.setEnableLoadMore(getEnableLoadMore())
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
        if (getIsOnCreateGetData())
            getPageDatas(mCurrentPage)

    }
    override fun onResume() {
        super.onResume()
        if (!getIsOnCreateGetData()) {
            refresh()
        }
    }
    var mNoDataView: View? = null

    @SuppressLint("UseCompatLoadingForDrawables")
    fun showNoData() {
        /* if (mNoDataIv == null)
             mNoDataIv = ImageView(mContext)
         mNoDataIv?.scaleType = ImageView.ScaleType.CENTER_INSIDE
         mNoDataIv?.setImageDrawable(getDrawable(R.drawable.base_smart_empty))*/
        val inflater = LayoutInflater.from(mContext)
        if (mNoDataView == null)
            mNoDataView = inflater.inflate(R.layout.nodata_ll, null)
//        val linearParams = ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.MATCH_PARENT)
        val lp: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        mSmartFrameLayout.addView(mNoDataView, lp)
//        mSmartFrameLayout.addView(mNoDataIv, linearParams)
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
        if (getEnableLoadMore())
            isHaveMore = list.isNotEmpty() && isMore

    }

}