package com.mbcq.orderlibrary.activity.deliverysomethinghouse

import android.annotation.SuppressLint
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_delivery_something_house.*
import java.lang.StringBuilder

abstract class BaseDeliverySomethingHouseActivity<V : BaseView, T : BasePresenterImpl<V>> : BaseMVPActivity<V, T>(), BaseView {
    var mTypeIndex = 1
    var mInventoryListAdapter: DeliverySomethingHouseInventoryAdapter? = null
    var mLoadingListAdapter: DeliverySomethingHouseLoadingAdapter? = null

    protected fun selectIndex(type: Int) {
        mTypeIndex = type
        when (type) {
            1 -> {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    inventory_list_tv.setTextColor(getColor(R.color.base_blue))
                    inventory_list_line.background = ContextCompat.getDrawable(mContext, R.color.base_blue)
                    loading_list_tv.setTextColor(getColor(R.color.black))
                    loading_list_line.background = ContextCompat.getDrawable(mContext, R.color.white)
                } else {
                    inventory_list_tv.setTextColor(resources.getColor(R.color.base_blue))
                    inventory_list_line.background = ContextCompat.getDrawable(mContext, R.color.base_blue)
                    loading_list_tv.setTextColor(resources.getColor(R.color.black))
                    loading_list_line.background = ContextCompat.getDrawable(mContext, R.color.white)
                }
                inventory_list_recycler.visibility = View.VISIBLE
                loading_list_recycler.visibility = View.GONE
                operating_btn.text = "添加本车"
            }
            2 -> {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    loading_list_tv.setTextColor(getColor(R.color.base_blue))
                    loading_list_line.background = ContextCompat.getDrawable(mContext, R.color.base_blue)
                    inventory_list_tv.setTextColor(getColor(R.color.black))
                    inventory_list_line.background = ContextCompat.getDrawable(mContext, R.color.white)
                } else {
                    loading_list_tv.setTextColor(resources.getColor(R.color.base_blue))
                    loading_list_line.background = ContextCompat.getDrawable(mContext, R.color.base_blue)
                    inventory_list_tv.setTextColor(resources.getColor(R.color.black))
                    inventory_list_line.background = ContextCompat.getDrawable(mContext, R.color.white)
                }
                loading_list_recycler.visibility = View.VISIBLE
                inventory_list_recycler.visibility = View.GONE
                operating_btn.text = "移出本车"

            }
        }
    }

    fun removeSomeThing() {
        val mSelectedDatas = mutableListOf<DeliverySomethingHouseBean>()
        val mUnSelectedDatas = mutableListOf<DeliverySomethingHouseBean>()
        mLoadingListAdapter?.getAllData()?.let {
            for ((index, item) in (it.withIndex())) {
                if (item.isChecked) {
                    item.isChecked = false
                    mSelectedDatas.add(item)
                } else {
                    mUnSelectedDatas.add(item)
                }
            }
            mInventoryListAdapter?.appendData(mSelectedDatas)
            mLoadingListAdapter?.clearData()
            mLoadingListAdapter?.appendData(mUnSelectedDatas)
            if (all_selected_checked.isChecked) {
                all_selected_checked.isChecked = false
            }
        }
    }

    fun addSomeThing() {
        val mSelectedDatas = mutableListOf<DeliverySomethingHouseBean>()
        val mUnSelectedDatas = mutableListOf<DeliverySomethingHouseBean>()
        mInventoryListAdapter?.getAllData()?.let {
            for ((index, item) in (it.withIndex())) {
                if (item.isChecked) {
                    item.isChecked = false
                    mSelectedDatas.add(item)
                } else {
                    mUnSelectedDatas.add(item)
                }
            }
            mLoadingListAdapter?.appendData(mSelectedDatas)
            mInventoryListAdapter?.clearData()
            mInventoryListAdapter?.appendData(mUnSelectedDatas)
            if (all_selected_checked.isChecked) {
                all_selected_checked.isChecked = false
            }

        }

    }

    fun getSelectLoadingOrder(): String {
        val mSelectWaybillNumber = StringBuilder()
        mLoadingListAdapter?.getAllData()?.let {
            for ((index, item) in (it.withIndex())) {
                if (item.isChecked) {
                    mSelectWaybillNumber.append(item.billno)
                    if (index != it.size - 1)
                        mSelectWaybillNumber.append(",")
                }
            }
        }
        return mSelectWaybillNumber.toString()
    }

    fun getSelectInventoryOrder(): String {
        val mSelectWaybillNumber = StringBuilder()
        mInventoryListAdapter?.getAllData()?.let {
            for ((index, item) in (it.withIndex())) {
                if (item.isChecked) {
                    mSelectWaybillNumber.append(item.billno)
                    if (index != it.size - 1)
                        mSelectWaybillNumber.append(",")
                }
            }
        }
        return mSelectWaybillNumber.toString()
    }

    fun getSelectLoadingOrderItem(): Int {
        var mResultIndex = 0
        mLoadingListAdapter?.getAllData()?.let {
            for ((_, item) in (it.withIndex())) {
                if (item.isChecked) {
                    mResultIndex++
                }
            }
            return mResultIndex
        }
        return 0
    }

    fun getSelectInventoryList(): List<DeliverySomethingHouseBean>? {
        val mSelectedDatas = mutableListOf<DeliverySomethingHouseBean>()
        mInventoryListAdapter?.getAllData()?.let {
            for ((index, item) in (it.withIndex())) {
                if (item.isChecked) {
                    mSelectedDatas.add(item)
                }
            }
            return mSelectedDatas

        }
        return null

    }

    //点击显示送货配置弹窗
    abstract fun onRecyclerShowDialog(position: Int, item: DeliverySomethingHouseBean)
    protected open fun initLoadingList() {
        loading_list_recycler.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        mLoadingListAdapter = DeliverySomethingHouseLoadingAdapter(mContext)
        loading_list_recycler.adapter = mLoadingListAdapter
        mLoadingListAdapter?.mOnUseInterface = object : DeliverySomethingHouseLoadingAdapter.OnUseInterface {
            override fun onDialog(position: Int, item: DeliverySomethingHouseBean) {
                onRecyclerShowDialog(position, item)
            }

            override fun onClick(position: Int, item: DeliverySomethingHouseBean) {
                mLoadingListAdapter?.removeItem(position)
                mInventoryListAdapter?.appendData(mutableListOf(item))
                refreshTopNumber()
            }

        }
        if (loading_list_recycler.itemDecorationCount == 0) {
            loading_list_recycler.addItemDecoration(object : BaseItemDecoration(mContext) {
                override fun configExtraSpace(position: Int, count: Int, rect: Rect) {
                    rect.top = ScreenSizeUtils.dp2px(mContext, 10f)
                }

                override fun doRule(position: Int, rect: Rect) {
                    rect.bottom = rect.top
                }
            })
        }

    }

    @SuppressLint("SetTextI18n")
    fun refreshTopNumber() {
        inventory_list_tv.text = "库存清单(${mInventoryListAdapter?.getAllData()?.size})"
        loading_list_tv.text = "配载清单(${mLoadingListAdapter?.getAllData()?.size})"
    }

    protected open fun initInventoryList() {
        inventory_list_recycler.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        mInventoryListAdapter = DeliverySomethingHouseInventoryAdapter(mContext)
        inventory_list_recycler.adapter = mInventoryListAdapter
        mInventoryListAdapter?.mOnRemoveInterface = object : DeliverySomethingHouseInventoryAdapter.OnRemoveInterface {
            override fun onClick(position: Int, item: DeliverySomethingHouseBean) {
                mInventoryListAdapter?.removeItem(position)
                mLoadingListAdapter?.appendData(mutableListOf(item))
                refreshTopNumber()
            }

        }

        if (inventory_list_recycler.itemDecorationCount == 0) {
            inventory_list_recycler.addItemDecoration(object : BaseItemDecoration(mContext) {
                override fun configExtraSpace(position: Int, count: Int, rect: Rect) {
                    rect.top = ScreenSizeUtils.dp2px(mContext, 10f)
                }

                override fun doRule(position: Int, rect: Rect) {
                    rect.bottom = rect.top
                }
            })
        }

    }
}
