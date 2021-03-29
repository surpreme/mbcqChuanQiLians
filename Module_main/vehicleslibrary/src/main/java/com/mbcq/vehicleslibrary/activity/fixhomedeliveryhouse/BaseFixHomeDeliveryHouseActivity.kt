package com.mbcq.vehicleslibrary.activity.fixhomedeliveryhouse


import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.os.CountDownTimer
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
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.homedeliveryhouse.HomeDeliveryHouseBean
import com.mbcq.vehicleslibrary.activity.homedeliveryhouse.HomeDeliveryHouseInventoryAdapter
import com.mbcq.vehicleslibrary.activity.homedeliveryhouse.HomeDeliveryHouseLoadingAdapter
import kotlinx.android.synthetic.main.activity_fix_home_delivery_house.*


/**
 * @author: lzy
 * @time: 2021-01-14 17:59:02 上门提货
 *
 */
abstract class BaseFixHomeDeliveryHouseActivity<V : BaseView, T : BasePresenterImpl<V>> : BaseMVPActivity<V, T>(), BaseView {

    var mDepartureLot = ""

    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)

    }

    @SuppressLint("SetTextI18n")
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        initLoadingList()
        initInventoryList()


    }

    var mTotalQty = 0
    var mToTalWeight = 0.0
    var mToTalVolume = 0.0
    fun refreshTopInfo() {
        object : CountDownTimer(500, 500) {
            override fun onTick(millisUntilFinished: Long) {

            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                if (!isDestroyed) {
                    mLoadingListAdapter?.let {
                        mToTalWeight = 0.0
                        mToTalVolume = 0.0
                        mTotalQty = 0
                        var mPrice = 0.00
                        for (item in it.getAllData()) {
                            if (item.weight.toDoubleOrNull() != null)
                                mToTalWeight += item.weight.toDouble()
                            if (item.volumn.toDoubleOrNull() != null)
                                mToTalVolume += item.volumn.toDouble()
                            if (item.accSum.toDoubleOrNull() != null)
                                mPrice += item.accSum.toDouble()
                            if (item.totalQty != null) {
                                if (item.totalQty.toIntOrNull() != null)
                                    mTotalQty += item.totalQty.toInt()
                            } else
                                mTotalQty += item.qty.toInt()

                        }
                        over_total_info_tv.text = "已 装  车：${it.getAllData().size} 票 $mTotalQty 件 ${haveTwoDouble(mToTalWeight)} Kg ${haveTwoDouble(mToTalVolume)} 方      ${haveTwoDouble(mPrice)}元"

                    }
                }
            }

        }.start()
    }

    fun removeSomeThing() {
        val mSelectedDatas = mutableListOf<HomeDeliveryHouseBean>()
        val mUnSelectedDatas = mutableListOf<HomeDeliveryHouseBean>()
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
        val mSelectedDatas = mutableListOf<HomeDeliveryHouseBean>()
        val mUnSelectedDatas = mutableListOf<HomeDeliveryHouseBean>()
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

    var mInventoryListAdapter: HomeDeliveryHouseInventoryAdapter? = null
    var mLoadingListAdapter: HomeDeliveryHouseLoadingAdapter? = null
    open fun initLoadingList() {
        loading_list_recycler.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        mLoadingListAdapter = HomeDeliveryHouseLoadingAdapter(mContext)
        loading_list_recycler.adapter = mLoadingListAdapter
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

    var mTypeIndex = 1

    open fun initInventoryList() {
        inventory_list_recycler.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        mInventoryListAdapter = HomeDeliveryHouseInventoryAdapter(mContext)
        inventory_list_recycler.adapter = mInventoryListAdapter
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

    @SuppressLint("SetTextI18n")
    fun showTopTotal() {
        inventory_list_tv.text = "库存清单(${mInventoryListAdapter?.getAllData()?.size})"
        loading_list_tv.text = "配载清单(${mLoadingListAdapter?.getAllData()?.size})"
        refreshTopInfo()

    }

    override fun onClick() {
        super.onClick()
        fix_home_delivery_house_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }
    fun getSelectInventoryOrder(): String {
        val mSelectWaybillNumber = java.lang.StringBuilder()
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

    fun getSelectInventoryList(): List<HomeDeliveryHouseBean>? {
        val mSelectedDatas = mutableListOf<HomeDeliveryHouseBean>()
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

    fun getSelectLoadingList(): List<HomeDeliveryHouseBean>? {
        val mSelectedDatas = mutableListOf<HomeDeliveryHouseBean>()
        mLoadingListAdapter?.getAllData()?.let {
            for ((index, item) in (it.withIndex())) {
                if (item.isChecked) {
                    mSelectedDatas.add(item)
                }
            }
            return mSelectedDatas

        }
        return null

    }


}