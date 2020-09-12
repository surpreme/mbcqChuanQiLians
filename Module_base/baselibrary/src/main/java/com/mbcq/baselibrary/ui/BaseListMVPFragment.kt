package com.mbcq.baselibrary.ui


import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.BaseMVPFragment
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.BaseRecyclerAdapter

/**
 * @Auther: liziyang
 * @datetime: 2020-01-18
 * @desc:
 */
abstract class BaseListMVPFragment<V : BaseView, T : BasePresenterImpl<V>,X> : BaseMVPFragment<V,T>(),BaseView {
    abstract fun getRecyclerViewId(): Int
    lateinit var recycler_view: RecyclerView
    abstract fun setAdapter(): BaseRecyclerAdapter<X>
    lateinit var adapter: BaseRecyclerAdapter<X>
    open fun addItemDecoration(): RecyclerView.ItemDecoration= object : BaseItemDecoration(mContext) {
        override fun configExtraSpace(position: Int, count: Int, rect: Rect) {

        }

        override fun doRule(position: Int, rect: Rect) {
            rect.bottom = rect.top
        }
    }

    open fun setLayoutManager(): LinearLayoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
    override fun initViews(view: View) {
        recycler_view = view.findViewById(getRecyclerViewId())
        recycler_view.layoutManager = setLayoutManager()
        adapter=setAdapter()
        recycler_view.adapter = adapter
        if (recycler_view.itemDecorationCount == 0) {
            recycler_view.addItemDecoration(addItemDecoration())
        }
    }


}