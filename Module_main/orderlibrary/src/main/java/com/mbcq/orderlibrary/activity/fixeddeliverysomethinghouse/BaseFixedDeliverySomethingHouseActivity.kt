package com.mbcq.orderlibrary.activity.fixeddeliverysomethinghouse

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_fixed_delivery_something_house.*
import java.lang.StringBuilder

abstract class BaseFixedDeliverySomethingHouseActivity<V : BaseView, T : BasePresenterImpl<V>> : BaseMVPActivity<V, T>(), BaseView {
    var mTypeIndex = 1
    var mInventoryListAdapter: FixedDeliverySomethingHouseInventoryAdapter? = null
    var mLoadingListAdapter: FixedDeliverySomethingHouseLoadingAdapter? = null
    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)
    }

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
        val mSelectedDatas = mutableListOf<FixedDeliverySomethingHouseBean>()
        val mUnSelectedDatas = mutableListOf<FixedDeliverySomethingHouseBean>()
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

    fun getSelectInventoryOrderBean(): List<FixedDeliverySomethingHouseBean> {
        val mFixedDeliverySomethingHouseBean = mutableListOf<FixedDeliverySomethingHouseBean>()
        mInventoryListAdapter?.getAllData()?.let {
            for ((index, item) in (it.withIndex())) {
                if (item.isChecked) {
                    mFixedDeliverySomethingHouseBean.add(item)
                }
            }
        }
        return mFixedDeliverySomethingHouseBean
    }

    fun getSelectLoadingOrderBean(): List<FixedDeliverySomethingHouseBean> {
        val mFixedDeliverySomethingHouseBean = mutableListOf<FixedDeliverySomethingHouseBean>()
        mLoadingListAdapter?.getAllData()?.let {
            for ((index, item) in (it.withIndex())) {
                if (item.isChecked) {
                    mFixedDeliverySomethingHouseBean.add(item)
                }
            }
        }
        return mFixedDeliverySomethingHouseBean
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

    fun addSomeThing() {
        val mSelectedDatas = mutableListOf<FixedDeliverySomethingHouseBean>()
        val mUnSelectedDatas = mutableListOf<FixedDeliverySomethingHouseBean>()
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

    protected open fun initLoadingList() {
        loading_list_recycler.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        mLoadingListAdapter = FixedDeliverySomethingHouseLoadingAdapter(mContext)
        loading_list_recycler.adapter = mLoadingListAdapter
        /* mLoadingListAdapter?.mOnRemoveInterface = object :FixedDeliverySomethingHouseLoadingAdapter.OnRemoveInterface {
             override fun onClick(position: Int, item: FixedDeliverySomethingHouseBean) {
                 mLoadingListAdapter?.removeItem(position)
                 mInventoryListAdapter?.appendData(mutableListOf(item))
                 refreshTopNumber()
             }

         }*/
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

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun onClick() {
        super.onClick()
        fixed_delivery_something_house_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
        inventory_list_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                selectIndex(1)
            }

        })
        loading_list_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                selectIndex(2)
            }

        })
    }

    protected open fun initInventoryList() {
        inventory_list_recycler.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        mInventoryListAdapter = FixedDeliverySomethingHouseInventoryAdapter(mContext)
        inventory_list_recycler.adapter = mInventoryListAdapter
        /* mInventoryListAdapter?.mOnRemoveInterface = object : FixedDeliverySomethingHouseInventoryAdapter.OnRemoveInterface {
             override fun onClick(position: Int, item: FixedDeliverySomethingHouseBean) {
                 mInventoryListAdapter?.removeItem(position)
                 mLoadingListAdapter?.appendData(mutableListOf(item))
                 refreshTopNumber()
             }

         }*/

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
