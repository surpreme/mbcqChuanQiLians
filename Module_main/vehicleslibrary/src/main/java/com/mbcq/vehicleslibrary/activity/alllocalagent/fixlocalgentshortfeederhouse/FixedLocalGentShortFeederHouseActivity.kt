package com.mbcq.vehicleslibrary.activity.alllocalagent.fixlocalgentshortfeederhouse


import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.alllocalagent.localgentshortfeederhouse.LocalGentShortFeederHouseBean
import com.mbcq.vehicleslibrary.activity.alllocalagent.localgentshortfeederhouse.LocalGentShortFeederHouseInventoryAdapter
import com.mbcq.vehicleslibrary.activity.alllocalagent.localgentshortfeederhouse.LocalGentShortFeederHouseLoadingAdapter
import com.mbcq.vehicleslibrary.bean.StockWaybillListBean
import kotlinx.android.synthetic.main.activity_fixed_local_gent_short_feeder_house.*
import org.json.JSONArray
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-23 11:05:00
 */
@Route(path = ARouterConstants.FixedLocalGentShortFeederHouseActivity)
class FixedLocalGentShortFeederHouseActivity : BaseFixedLocalGentShortFeederHouseActivity<FixedLocalGentShortFeederHouseContract.View, FixedLocalGentShortFeederHousePresenter>(), FixedLocalGentShortFeederHouseContract.View {

    @Autowired(name = "FixedLocalGentShortFeederJson")
    @JvmField
    var mLastDataJson: String = ""

    override fun getLayoutId(): Int = R.layout.activity_fixed_local_gent_short_feeder_house

    @SuppressLint("SetTextI18n")
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        val mLastData = JSONObject(mLastDataJson)
        mDepartureLot = mLastData.optString("agentBillno")
        dispatch_number_tv.text = "派车单号: $mDepartureLot"

    }

    fun saveV() {
        mLoadingListAdapter?.getAllData()?.let {
            if (it.isEmpty())
                return
            val mLastData = JSONObject(mLastDataJson)
            val jarray = JSONArray()
            val kk = StringBuilder()

            for ((index, item) in it.withIndex()) {
                val obj = JSONObject()
                obj.put("billno", item.billno)
                kk.append(item.billno)
                if (index != it.size - 1)
                    kk.append(",")
                jarray.put(obj)
            }
            mLastData.put("WaybillAgentDetLst", jarray)
            mLastData.put("CommonStr", kk.toString())
            mPresenter?.completeVehicle(mLastData)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initDatas() {
        super.initDatas()

        mPresenter?.getInventory()
        mPresenter?.getLoadingData(mDepartureLot)
    }

    override fun initLoadingList() {
        super.initLoadingList()
        mLoadingListAdapter?.mOnRemoveInterface = object : LocalGentShortFeederHouseLoadingAdapter.OnRemoveInterface {
            override fun onClick(position: Int, item: LocalGentShortFeederHouseBean) {
                val mLastData = JSONObject(mLastDataJson)
                mLastData.put("commonStr", item.billno)
                mPresenter?.removeOrderItem(mLastData, position, item)

            }

        }
    }

    override fun initInventoryList() {
        super.initInventoryList()
        mInventoryListAdapter?.mOnRemoveInterface = object : LocalGentShortFeederHouseInventoryAdapter.OnRemoveInterface {
            override fun onClick(position: Int, item: LocalGentShortFeederHouseBean) {
                val mAddOrderFixedLocalGentShortFeederHouseBean = Gson().fromJson<AddOrderFixedLocalGentShortFeederHouseBean>(mLastDataJson, AddOrderFixedLocalGentShortFeederHouseBean::class.java)
                mAddOrderFixedLocalGentShortFeederHouseBean.waybillAgentDetLst = mutableListOf(item)
                mAddOrderFixedLocalGentShortFeederHouseBean.commonStr = getSelectInventoryOrder()
                mPresenter?.addOrderItem(Gson().toJson(mAddOrderFixedLocalGentShortFeederHouseBean), position, item)

            }

        }
    }




    override fun onClick() {
        super.onClick()
        complete_vehicle_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                TalkSureCancelDialog(mContext, getScreenWidth(), "您确定要完成${dispatch_number_tv.text}吗") {
                    saveV()
                }.show()
            }

        })
        all_selected_checked.setOnCheckedChangeListener { _, isChecked ->
            if (mTypeIndex == 1)
                mInventoryListAdapter?.checkedAll(isChecked)
            else if (mTypeIndex == 2)
                mLoadingListAdapter?.checkedAll(isChecked)

        }
        operating_btn.setOnClickListener(object : SingleClick() {
            @SuppressLint("SetTextI18n")
            override fun onSingleClick(v: View?) {
                if (operating_btn.text.toString() == "添加本车") {
                    val mAddOrderFixedLocalGentShortFeederHouseBean = Gson().fromJson<AddOrderFixedLocalGentShortFeederHouseBean>(mLastDataJson, AddOrderFixedLocalGentShortFeederHouseBean::class.java)
                    mAddOrderFixedLocalGentShortFeederHouseBean.waybillAgentDetLst = getSelectInventoryList()
                    mAddOrderFixedLocalGentShortFeederHouseBean.commonStr = getSelectInventoryOrder()
                    mPresenter?.addOrder(Gson().toJson(mAddOrderFixedLocalGentShortFeederHouseBean))


                } else if (operating_btn.text.toString() == "移出本车") {
                    val mLastData = JSONObject(mLastDataJson)
                    mLastData.put("commonStr", getSelectLoadingOrder())
                    mPresenter?.removeOrder(mLastData)

                }

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



    @SuppressLint("SetTextI18n")
    override fun getInventoryS(list: List<LocalGentShortFeederHouseBean>) {
        mInventoryListAdapter?.appendData(list)
        inventory_list_tv.text = "库存清单(${list.size})"

    }

    @SuppressLint("SetTextI18n")
    override fun getLoadingDataS(list: List<LocalGentShortFeederHouseBean>) {
        mLoadingListAdapter?.appendData(list)
        loading_list_tv.text = "配载清单(${list.size})"

    }

    override fun completeVehicleS() {
        TalkSureDialog(mContext, getScreenWidth(), "${dispatch_number_tv.text}已完成出库") {
            onBackPressed()
        }.show()
    }

    override fun removeOrderS() {
        removeSomeThing()
        showTopTotal()

    }

    override fun removeOrderItemS(position: Int, item: LocalGentShortFeederHouseBean) {
        mLoadingListAdapter?.removeItem(position)
        mInventoryListAdapter?.appendData(mutableListOf(item))
        showTopTotal()

    }


    override fun addOrderS() {
        addSomeThing()
        showTopTotal()
    }

    override fun addOrderItemS(position: Int, item: LocalGentShortFeederHouseBean) {
        mInventoryListAdapter?.removeItem(position)
        mLoadingListAdapter?.appendData(mutableListOf(item))
        showTopTotal()

    }


}