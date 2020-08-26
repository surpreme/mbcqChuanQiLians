package com.mbcq.baselibrary.ui


import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.util.screen.StatusBarUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import java.text.DecimalFormat

/**
 * @Auther: liziyang
 * @datetime: 2020-01-18
 * @desc:
 */
abstract class BaseListFragment<T> : BaseFragment() {
    abstract fun getRecyclerViewId(): Int
    lateinit var recycler_view: RecyclerView
    abstract fun setAdapter(): BaseRecyclerAdapter<T>
    lateinit var adapter: BaseRecyclerAdapter<T>
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