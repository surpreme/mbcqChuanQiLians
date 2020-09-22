package com.mbcq.vehicleslibrary.activity.alldeparturerecord.trunkdeparturehouse


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterDialog
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.bean.StockWaybillListBean
import kotlinx.android.synthetic.main.activity_add_trunk_departure_house.*
import org.json.JSONArray
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2018.08.25
 * 添加干线发车 运输方式
 */
@Route(path = ARouterConstants.TrunkDepartureHouseActivity)
class TrunkDepartureHouseActivity : BaseTrunkDepartureHouseActivity<TrunkDepartureHouseContract.View, TrunkDepartureHousePresenter>(), TrunkDepartureHouseContract.View {
    @Autowired(name = "TrunkDepartureHouse")
    @JvmField
    var mLastDataJson: String = ""

    override fun getLayoutId(): Int = R.layout.activity_add_trunk_departure_house

    @SuppressLint("SetTextI18n")
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        val mLastData = JSONObject(mLastDataJson)
        mDepartureLot = mLastData.optString("InoneVehicleFlag")
        departure_lot_tv.text = "发车批次: $mDepartureLot"
    }

    /**
     * 完成本车保存
     */
    fun completeCar() {
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
            val VehicleInterval = ""//发车区间
            mLastData.put("VehicleInterval", VehicleInterval)

            val YtWebidCode = ""//沿途网点编码
            mLastData.put("YtWebidCode", YtWebidCode)

            val YtWebidCodeStr = ""//沿途网点
            mLastData.put("YtWebidCodeStr", YtWebidCodeStr)

            mLastData.put("GxVehicleDetLst", jarray)
            mLastData.put("CommonStr", kk.toString())
            mPresenter?.saveInfo(mLastData)
        }

    }

    override fun onClick() {
        super.onClick()
        complete_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                completeCar()
            }

        })
        add_operating_interval_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                getDbWebId(object : WebDbInterface {
                    override fun isNull() {

                    }

                    override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                        geDeliveryPointLocal(list)
                    }

                })
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

    fun geDeliveryPointLocal(list: MutableList<WebAreaDbInfo>) {
        FilterDialog(getScreenWidth(), Gson().toJson(list), "webid", "选择沿途网点", true, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val checkBox = CheckBox(mContext)
                checkBox.text = list[position].webid
                operating_interval_ll.addView(checkBox)

            }

        }).show(supportFragmentManager, "BaseTrunkDepartureHouseActivitygeDeliveryPointLocalDialogFilterDialog")
    }

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getInventory(1)
    }

    override fun getInventoryS(list: List<StockWaybillListBean>) {
        short_feeder_house_tabLayout.getTabAt(0)?.text = "库存清单(${list.size})"
        mInventoryListAdapter?.appendData(list)
    }

    override fun saveInfoS(s: String) {
        TalkSureDialog(mContext, getScreenWidth(), "干线计划装车${mDepartureLot}完成，点击查看详情！") {
            onBackPressed()
            this.finish()
        }.show()
    }


}