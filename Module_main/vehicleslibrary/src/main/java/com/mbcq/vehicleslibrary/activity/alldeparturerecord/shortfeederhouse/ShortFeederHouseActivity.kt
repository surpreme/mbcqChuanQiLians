package com.mbcq.vehicleslibrary.activity.alldeparturerecord.shortfeederhouse


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.alldeparturerecord.departurerecord.DepartureRecordAddSuccessEvent
import com.mbcq.vehicleslibrary.bean.StockWaybillListBean
import kotlinx.android.synthetic.main.activity_short_feeder_house.*
import org.greenrobot.eventbus.EventBus
import org.json.JSONArray
import org.json.JSONObject


/**
 * @author: lzy
 * @time: 2020-09-15 10:12:09
 * 短驳计划装车
 */
@Route(path = ARouterConstants.ShortFeederHouseActivity)
class ShortFeederHouseActivity : BasesShortFeederHouseActivity<ShortFeederHouseContract.View, ShortFeederHousePresenter>(), ShortFeederHouseContract.View {
    @Autowired(name = "ShortFeederHouse")
    @JvmField
    var mLastDataJson: String = ""
    var mDepartureLot = ""
    var mMaximumVehicleWeight = 0.0


    override fun getLayoutId(): Int = R.layout.activity_short_feeder_house

    override fun initDatas() {
        super.initDatas()
        JSONObject(mLastDataJson).also {
            mPresenter?.getInventory(1,it.optString("ewebidCode"), it.optString("ewebidCodeStr"))

        }
    }


    @SuppressLint("SetTextI18n")
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        val mLastData = JSONObject(mLastDataJson)
        mDepartureLot = mLastData.optString("InoneVehicleFlag")
        departure_lot_tv.text = "发车批次: $mDepartureLot"
        mMaximumVehicleWeight = mLastData.optDouble("MaximumVehicleWeight", 0.0)
    }

    /**
     * 完成本车保存
     */
    fun completeCar() {
        mLoadingListAdapter?.getAllData()?.let {
            if (it.isEmpty()) {
                TalkSureDialog(mContext, getScreenWidth(), "请配载您要发的运单").show()
                return

            }
            val mLastData = JSONObject(mLastDataJson)
            val jarray = JSONArray()
            val kk = StringBuilder()

            for ((index, item) in it.withIndex()) {
                val obj = JSONObject(Gson().toJson(item))
                obj.put("qty", item.developmentsQty)
                val xV = ((item.volumn.toDouble()) / (item.totalQty.toInt()))
                obj.put("sfVolumn", haveTwoDouble(xV * item.developmentsQty))
                val xW = ((item.weight.toDouble()) / (item.totalQty.toInt()))
                obj.put("sfWeight", haveTwoDouble(xW * item.developmentsQty))
                kk.append(item.billno)
                if (index != it.size - 1)
                    kk.append(",")
                jarray.put(obj)
            }
            mLastData.put("DbVehicleDetLst", jarray)
            mLastData.put("CommonStr", kk.toString())
            mLastData.put("supWeight", mMaximumVehicleWeight / 1000)//承重
            mPresenter?.saveInfo(mLastData)
        }

    }

    override fun removeSomeThing() {
        super.removeSomeThing()
        refreshTopInfo()
    }

    override fun addSomeThing() {
        super.addSomeThing()
        refreshTopInfo()
    }

    override fun onClick() {
        super.onClick()
        complete_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                TalkSureCancelDialog(mContext, getScreenWidth(), "${if (mMaximumVehicleWeight < mXWeight) "本车已超载，" else ""}您确定要完成配载吗？") {
                    completeCar()
                }.show()
            }

        })
        operating_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (operating_btn.text.toString() == "添加本车") {
                    addSomeThing()

                } else if (operating_btn.text.toString() == "移出本车") {
                    removeSomeThing()
                }
                short_feeder_house_tabLayout.getTabAt(0)?.text = "库存清单(${mInventoryListAdapter?.getAllData()?.size})"
                short_feeder_house_tabLayout.getTabAt(1)?.text = "配载清单(${mLoadingListAdapter?.getAllData()?.size})"
            }

        })

        all_selected_checked.setOnCheckedChangeListener { _, isChecked ->
            if (mTypeIndex == 1)
                mInventoryListAdapter?.checkedAll(isChecked)
            else if (mTypeIndex == 2)
                mLoadingListAdapter?.checkedAll(isChecked)

        }

    }

    override fun overLocalCarS(s: String) {
        TalkSureDialog(mContext, getScreenWidth(), "短驳计划装车${mDepartureLot}已完成，点击查看详情！") {
            EventBus.getDefault().postSticky(DepartureRecordAddSuccessEvent(1))
            onBackPressed()
        }.show()
    }

    override fun saveInfoS(result: String) {
        if (JSONObject(mLastDataJson).optInt("isScan") == 1) {
            TalkSureDialog(mContext, getScreenWidth(), "短驳计划装车${mDepartureLot}已完成，您需要到扫描页面进行扫描！") {
                EventBus.getDefault().postSticky(DepartureRecordAddSuccessEvent(1))
                onBackPressed()
            }.show()
        } else {
            TalkSureCancelDialog(mContext, getScreenWidth(), "短驳计划装车${mDepartureLot}配载成功，您确定要完成本车并发车吗?") {
                mPresenter?.overLocalCar(mDepartureLot)
            }.show()
        }




    }

    override fun getInventoryS(list: List<StockWaybillListBean>) {
        short_feeder_house_tabLayout.getTabAt(0)?.text = "库存清单(${list.size})"
        mInventoryListAdapter?.appendData(list)
    }
}