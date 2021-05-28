package com.mbcq.vehicleslibrary.activity.alldeparturerecord.fixtrunkdeparturehouse


import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.tabs.TabLayout
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.bean.StockWaybillListBean
import com.mbcq.vehicleslibrary.fragment.shortfeederhouse.inventorylist.ShortFeederHouseInventoryListAdapter
import com.mbcq.vehicleslibrary.fragment.shortfeederhouse.loadinglist.ShortFeederHouseLoadingListAdapter
import kotlinx.android.synthetic.main.activity_fixed_trunk_departure_house.*
import java.lang.StringBuilder

/**
 * @author: lzy
 * @time: 2020-09-18 11:33
 * 修改 干线发车
 */
abstract class BaseFixedTrunkDepartureHouseActivity<V : BaseView, T : BasePresenterImpl<V>> : BaseMVPActivity<V, T>(), BaseView {

    var mInoneVehicleFlag = ""
    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)
    }

    var mXVolume = 0.0
    fun refreshTopInfo() {
        object : CountDownTimer(500, 500) {
            override fun onTick(millisUntilFinished: Long) {

            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                if (!isDestroyed) {
                    mLoadingListAdapter?.let {
                        var mWeight = 0.0
                        var mVolume = 0.0
                        var mQty = 0
                        var mPrice = 0.00
                        for (item in it.getAllData()) {
                            if (item.weight.toDoubleOrNull() != null)
                                mWeight += item.weight.toDouble()
                            if (item.volumn.toDoubleOrNull() != null)
                                mVolume += item.volumn.toDouble()
                            if (item.accSum.toDoubleOrNull() != null)
                                mPrice += item.accSum.toDouble()
                            if (item.totalQty.toIntOrNull() != null)
                                mQty += item.totalQty.toInt()

                        }
                        mXVolume=mVolume
                        over_total_info_tv.text = "已 装  车：${it.getAllData().size} 票 $mQty 件 ${haveTwoDouble(mWeight)} Kg ${haveTwoDouble(mVolume)} 方      ${haveTwoDouble(mPrice)}元"

                    }
                }
            }

        }.start()
    }


    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        fix_trunk_departure_house_tabLayout.addTab(fix_trunk_departure_house_tabLayout.newTab().setText("库存清单(0)"))
        fix_trunk_departure_house_tabLayout.addTab(fix_trunk_departure_house_tabLayout.newTab().setText("配载清单(0)"))
        fix_trunk_departure_house_tabLayout.addTab(fix_trunk_departure_house_tabLayout.newTab().setText("沿途网点"))

        initInventoryList()
        initLoadingList()
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

    protected open fun initLoadingList() {
        loadingList_recycler.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        mLoadingListAdapter = ShortFeederHouseLoadingListAdapter(mContext)
        loadingList_recycler.adapter = mLoadingListAdapter

        if (loadingList_recycler.itemDecorationCount == 0) {
            loadingList_recycler.addItemDecoration(object : BaseItemDecoration(mContext) {
                override fun configExtraSpace(position: Int, count: Int, rect: Rect) {
                    rect.top = ScreenSizeUtils.dp2px(mContext, 10f)
                }

                override fun doRule(position: Int, rect: Rect) {
                    rect.bottom = rect.top
                }
            })
        }

    }

    protected open fun initInventoryList() {
        inventoryList_recycler.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        mInventoryListAdapter = ShortFeederHouseInventoryListAdapter(mContext)
        inventoryList_recycler.adapter = mInventoryListAdapter

        if (inventoryList_recycler.itemDecorationCount == 0) {
            inventoryList_recycler.addItemDecoration(object : BaseItemDecoration(mContext) {
                override fun configExtraSpace(position: Int, count: Int, rect: Rect) {
                    rect.top = ScreenSizeUtils.dp2px(mContext, 10f)
                }

                override fun doRule(position: Int, rect: Rect) {
                    rect.bottom = rect.top
                }
            })
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

    fun getSelectInventoryList(): List<StockWaybillListBean>? {
        val mSelectedDatas = mutableListOf<StockWaybillListBean>()
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

    var mTypeIndex = 1
    override fun onClick() {
        super.onClick()
        all_selected_checked.setOnCheckedChangeListener { _, isChecked ->
            if (mTypeIndex == 1)
                mInventoryListAdapter?.checkedAll(isChecked)
            else if (mTypeIndex == 2)
                mLoadingListAdapter?.checkedAll(isChecked)

        }

        fix_trunk_departure_house_tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (all_selected_checked.isChecked) {
                    mTypeIndex = 3
                    all_selected_checked.isChecked = false
                }
                when {
                    tab.text.toString().contains("库存清单") -> {
                        inventoryList_recycler.visibility = View.VISIBLE
                        operating_interval_cl.visibility = View.GONE
                        loadingList_recycler.visibility = View.GONE
                        all_selected_checked.visibility = View.VISIBLE
                        operating_cardView.visibility = View.VISIBLE
                        operating_btn.text = "添加本车"
                        mTypeIndex = 1
                    }
                    tab.text.toString().contains("配载清单") -> {
                        inventoryList_recycler.visibility = View.GONE
                        operating_interval_cl.visibility = View.GONE
                        loadingList_recycler.visibility = View.VISIBLE
                        all_selected_checked.visibility = View.VISIBLE
                        operating_cardView.visibility = View.VISIBLE
                        operating_btn.text = "移出本车"
                        mTypeIndex = 2
                    }
                    tab.text.toString().contains("沿途网点") -> {
                        inventoryList_recycler.visibility = View.GONE
                        loadingList_recycler.visibility = View.GONE
                        operating_interval_cl.visibility = View.VISIBLE
                        all_selected_checked.visibility = View.GONE
                        operating_cardView.visibility = View.GONE
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        fixed_trunk_departure_house_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    fun addSomeThing() {
        val mSelectedDatas = mutableListOf<StockWaybillListBean>()
        val mUnSelectedDatas = mutableListOf<StockWaybillListBean>()
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

    fun refreshTopNumber() {
        fix_trunk_departure_house_tabLayout.getTabAt(0)?.text = "库存清单(${mInventoryListAdapter?.getAllData()?.size})"
        fix_trunk_departure_house_tabLayout.getTabAt(1)?.text = "配载清单(${mLoadingListAdapter?.getAllData()?.size})"
        refreshTopInfo()
    }

    fun removeSomeThing() {
        val mSelectedDatas = mutableListOf<StockWaybillListBean>()
        val mUnSelectedDatas = mutableListOf<StockWaybillListBean>()
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

    var mInventoryListAdapter: ShortFeederHouseInventoryListAdapter? = null
    var mLoadingListAdapter: ShortFeederHouseLoadingListAdapter? = null
}