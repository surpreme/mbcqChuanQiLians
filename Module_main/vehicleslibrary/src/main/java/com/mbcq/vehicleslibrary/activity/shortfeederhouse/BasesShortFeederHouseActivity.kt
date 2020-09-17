package com.mbcq.vehicleslibrary.activity.shortfeederhouse


import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
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
import com.mbcq.vehicleslibrary.fragment.shortfeederhouse.bean.ShortFeederHouseListBean
import com.mbcq.vehicleslibrary.fragment.shortfeederhouse.inventorylist.ShortFeederHouseInventoryListAdapter
import com.mbcq.vehicleslibrary.fragment.shortfeederhouse.loadinglist.ShortFeederHouseLoadingListAdapter
import kotlinx.android.synthetic.main.activity_short_feeder_house.*

/**
 * @author: lzy
 * @time: 2020-09-15 10:12:09
 * 短驳计划装车
 */
abstract class BasesShortFeederHouseActivity<V : BaseView, T : BasePresenterImpl<V>> : BaseMVPActivity<V, T>(), BaseView {

    var mTypeIndex = 1


    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)

    }

    override fun onClick() {
        super.onClick()
        short_feeder_house_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
        short_feeder_house_tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
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

    fun removeSomeThing() {
        val mSelectedDatas = mutableListOf<ShortFeederHouseListBean>()
        val mUnSelectedDatas = mutableListOf<ShortFeederHouseListBean>()
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
        val mSelectedDatas = mutableListOf<ShortFeederHouseListBean>()
        val mUnSelectedDatas = mutableListOf<ShortFeederHouseListBean>()
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
    private fun initLoadingList() {
        loadingList_recycler.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        mLoadingListAdapter = ShortFeederHouseLoadingListAdapter(mContext)
        loadingList_recycler.adapter = mLoadingListAdapter
        mLoadingListAdapter?.mOnRemoveInterface = object : ShortFeederHouseLoadingListAdapter.OnRemoveInterface {
            override fun onClick(position: Int, item: ShortFeederHouseListBean) {
                mLoadingListAdapter?.removeItem(position)
                mInventoryListAdapter?.appendData(mutableListOf(item))
                short_feeder_house_tabLayout.getTabAt(0)?.text = "库存清单(${mInventoryListAdapter?.getAllData()?.size})"
                short_feeder_house_tabLayout.getTabAt(1)?.text = "配载清单(${mLoadingListAdapter?.getAllData()?.size})"
            }

        }
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

    private fun initInventoryList() {
        inventoryList_recycler.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        mInventoryListAdapter = ShortFeederHouseInventoryListAdapter(mContext)
        inventoryList_recycler.adapter = mInventoryListAdapter
        mInventoryListAdapter?.mOnRemoveInterface = object : ShortFeederHouseInventoryListAdapter.OnRemoveInterface {
            override fun onClick(position: Int, item: ShortFeederHouseListBean) {
                mInventoryListAdapter?.removeItem(position)
                mLoadingListAdapter?.appendData(mutableListOf(item))
                short_feeder_house_tabLayout.getTabAt(0)?.text = "库存清单(${mInventoryListAdapter?.getAllData()?.size})"
                short_feeder_house_tabLayout.getTabAt(1)?.text = "配载清单(${mLoadingListAdapter?.getAllData()?.size})"

            }

        }
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

    var mAllDatas = mutableListOf<ShortFeederHouseListBean>()


}