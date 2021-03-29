package com.mbcq.vehicleslibrary.activity.fixhomedeliveryhouse


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.homedelivery.HomeDeliveryBean
import com.mbcq.vehicleslibrary.activity.homedeliveryhouse.HomeDeliveryHouseBean
import com.mbcq.vehicleslibrary.activity.homedeliveryhouse.HomeDeliveryHouseInventoryAdapter
import com.mbcq.vehicleslibrary.activity.homedeliveryhouse.HomeDeliveryHouseLoadingAdapter
import kotlinx.android.synthetic.main.activity_fix_home_delivery_house.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2021-03-27 09:22:17 修改上门提货
 */

@Route(path = ARouterConstants.FixHomeDeliveryHouseActivity)
class FixHomeDeliveryHouseActivity : BaseFixHomeDeliveryHouseActivity<FixHomeDeliveryHouseContract.View, FixHomeDeliveryHousePresenter>(), FixHomeDeliveryHouseContract.View {
    @Autowired(name = "FixHomeDeliveryHouse")
    @JvmField
    var mLastData: String = ""

    override fun getLayoutId(): Int = R.layout.activity_fix_home_delivery_house

    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)
    }

    @SuppressLint("SetTextI18n")
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        dispatch_number_tv.text = "提货批次：${JSONObject(mLastData).optString("inOneVehicleFlag")}"
    }

    override fun initLoadingList() {
        super.initLoadingList()
        mLoadingListAdapter?.mOnRemoveInterface = object : HomeDeliveryHouseLoadingAdapter.OnRemoveInterface {
            override fun onClick(position: Int, item: HomeDeliveryHouseBean) {
                val mHomeDeliveryBean = Gson().fromJson<HomeDeliveryBean>(mLastData, HomeDeliveryBean::class.java)
                mHomeDeliveryBean.pickUpdetLst = mutableListOf(item)
                mHomeDeliveryBean.commonStr = item.billno
                mPresenter?.removeOrderItem(Gson().toJson(mHomeDeliveryBean), position, item)

            }

        }
    }

    override fun initInventoryList() {
        super.initInventoryList()
        mInventoryListAdapter?.mOnRemoveInterface = object : HomeDeliveryHouseInventoryAdapter.OnRemoveInterface {
            override fun onClick(position: Int, item: HomeDeliveryHouseBean) {
                val mHomeDeliveryBean = Gson().fromJson<HomeDeliveryBean>(mLastData, HomeDeliveryBean::class.java)
                mHomeDeliveryBean.pickUpdetLst = mutableListOf(item)
                mHomeDeliveryBean.commonStr = item.billno
                mPresenter?.addOrderItem(Gson().toJson(mHomeDeliveryBean), position, item)

            }

        }
    }

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getInventory()
        mPresenter?.getLoading(JSONObject(mLastData).optString("id"))
    }


    override fun onClick() {
        super.onClick()
        all_selected_checked.setOnCheckedChangeListener { _, isChecked ->
            if (mTypeIndex == 1)
                mInventoryListAdapter?.checkedAll(isChecked)
            else if (mTypeIndex == 2)
                mLoadingListAdapter?.checkedAll(isChecked)

        }
        operating_btn.apply {
            onSingleClicks {
                if (operating_btn.text.toString() == "添加本车") {
                    val mHomeDeliveryBean = Gson().fromJson<HomeDeliveryBean>(mLastData, HomeDeliveryBean::class.java)
                    mHomeDeliveryBean.pickUpdetLst = getSelectInventoryList()
                    mHomeDeliveryBean.commonStr = getSelectInventoryOrder()
                    mPresenter?.addOrder(Gson().toJson(mHomeDeliveryBean))


                } else if (operating_btn.text.toString() == "移出本车") {
                    val mHomeDeliveryBean = Gson().fromJson<HomeDeliveryBean>(mLastData, HomeDeliveryBean::class.java)
                    mHomeDeliveryBean.pickUpdetLst = getSelectLoadingList()
                    mHomeDeliveryBean.commonStr =  getSelectLoadingOrder()
                    mPresenter?.removeOrder(Gson().toJson(mHomeDeliveryBean))

                }
            }
        }
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

    @SuppressLint("SetTextI18n")
    override fun getInventoryS(list: List<HomeDeliveryHouseBean>) {
        mInventoryListAdapter?.appendData(list)
        inventory_list_tv.text = "库存清单(${list.size})"
    }

    @SuppressLint("SetTextI18n")
    override fun getLoadingS(list: List<HomeDeliveryHouseBean>) {
        mLoadingListAdapter?.appendData(list)
        loading_list_tv.text = "配载清单(${list.size})"
        refreshTopInfo()
    }

    override fun removeOrderItemS(position: Int, item: HomeDeliveryHouseBean) {
        mLoadingListAdapter?.removeItem(position)
        mInventoryListAdapter?.appendData(mutableListOf(item))
        showTopTotal()

    }

    override fun addOrderItemS(position: Int, item: HomeDeliveryHouseBean) {
        mInventoryListAdapter?.removeItem(position)
        mLoadingListAdapter?.appendData(mutableListOf(item))
        showTopTotal()

    }

    override fun removeOrderS() {
        removeSomeThing()
        showTopTotal()

    }

    override fun addOrderS() {
        addSomeThing()
        showTopTotal()
    }
}