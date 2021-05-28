package com.mbcq.vehicleslibrary.activity.alldeparturerecord.fixshortfeederhouse


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
import kotlinx.android.synthetic.main.activity_fix_short_feeder_house.*
import java.lang.StringBuilder


/**
 * @author: lzy
 * @time: 2020-09-15 10:12:09
 * 短驳计划装车
 */
abstract class BaseFixShortFeederHouseActivity<V : BaseView, T : BasePresenterImpl<V>> : BaseMVPActivity<V, T>(), BaseView {

    var mTypeIndex = 1


    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)

    }

    override fun onClick() {
        super.onClick()
        fix_short_feeder_house_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
        short_feeder_house_tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (all_selected_checked.isChecked) {
                    mTypeIndex = 3
                    all_selected_checked.isChecked = false
                }
                if (tab.text.toString().contains("库存清单")) {
                    inventoryList_recycler.visibility = View.VISIBLE
                    loadingList_recycler.visibility = View.GONE
                    operating_btn.text = "添加本车"
                    mTypeIndex = 1
                } else if (tab.text.toString().contains("配载清单")) {
                    inventoryList_recycler.visibility = View.GONE
                    loadingList_recycler.visibility = View.VISIBLE
                    operating_btn.text = "移出本车"
                    mTypeIndex = 2
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
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

    fun refreshTopNumber() {
        short_feeder_house_tabLayout.getTabAt(0)?.text = "库存清单(${mInventoryListAdapter?.getAllData()?.size})"
        short_feeder_house_tabLayout.getTabAt(1)?.text = "配载清单(${mLoadingListAdapter?.getAllData()?.size})"
        refreshTopInfo()
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

    protected open fun addSomeThing() {
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

    @SuppressLint("SetTextI18n")
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        short_feeder_house_tabLayout.addTab(short_feeder_house_tabLayout.newTab().setText("库存清单(0)"))
        short_feeder_house_tabLayout.addTab(short_feeder_house_tabLayout.newTab().setText("配载清单(0)"))
        initInventoryList()
        initLoadingList()
    }

    var mInventoryListAdapter: ShortFeederHouseInventoryListAdapter? = null
    var mLoadingListAdapter: ShortFeederHouseLoadingListAdapter? = null
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


}