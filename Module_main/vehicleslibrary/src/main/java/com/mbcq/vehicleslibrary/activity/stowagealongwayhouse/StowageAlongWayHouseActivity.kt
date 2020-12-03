package com.mbcq.vehicleslibrary.activity.stowagealongwayhouse


import android.annotation.SuppressLint
import android.view.View
import android.widget.CheckBox
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.alldeparturerecord.fixtrunkdeparturehouse.FixedTrunkDepartureHouseInfo
import com.mbcq.vehicleslibrary.bean.StockWaybillListBean
import com.mbcq.vehicleslibrary.fragment.shortfeederhouse.inventorylist.ShortFeederHouseInventoryListAdapter
import com.mbcq.vehicleslibrary.fragment.shortfeederhouse.loadinglist.ShortFeederHouseLoadingListAdapter
import kotlinx.android.synthetic.main.activity_stowage_along_way_house.*
import org.json.JSONObject
import java.lang.StringBuilder


/**
 * @author: lzy
 * @time: 2020-11-23 08:57:43 沿途配载操作详情页
 */

@Route(path = ARouterConstants.StowageAlongWayHouseActivity)
class StowageAlongWayHouseActivity : BaseStowageAlongWayHouseActivity<StowageAlongWayHouseContract.View, StowageAlongWayHousePresenter>(), StowageAlongWayHouseContract.View {
    @Autowired(name = "StowageAlongWayHouse")
    @JvmField
    var mFixDataJson: String = ""

    override fun getLayoutId(): Int = R.layout.activity_stowage_along_way_house

    @SuppressLint("SetTextI18n")
    override fun initDatas() {
        super.initDatas()
        val mLastData = JSONObject(mFixDataJson)
        mInoneVehicleFlag = mLastData.optString("inoneVehicleFlag")
        departure_lot_tv.text = "发车批次: $mInoneVehicleFlag"
        mPresenter?.getCarInfo(mLastData.optInt("Id"), mLastData.optString("inoneVehicleFlag"))
        mPresenter?.getInventory(1)

    }

    fun saveInfo() {
        val mSaveStowageAlongWayHouseBean = SaveStowageAlongWayHouseBean()
        //TODO 到货网点未完成
        mSaveStowageAlongWayHouseBean.ewebidCodeStr = ""
        mSaveStowageAlongWayHouseBean.inoneVehicleFlag = mInoneVehicleFlag
        mSaveStowageAlongWayHouseBean.gxVehicleDetLst = mLoadingListAdapter?.getAllData()
        val commonStrBuilder = StringBuilder()
        mLoadingListAdapter?.getAllData()?.let {
            for ((index, item) in it.withIndex()) {
                commonStrBuilder.append(item.billno)
                if (index != it.lastIndex) {
                    commonStrBuilder.append(",")
                }
            }
        }
        mSaveStowageAlongWayHouseBean.commonStr = commonStrBuilder.toString()
        mPresenter?.saveAlongInfo(Gson().toJson(mSaveStowageAlongWayHouseBean))
    }

    override fun onClick() {
        super.onClick()
        complete_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mLoadingListAdapter?.getAllData()?.let {
                    if (it.isEmpty()) {
                        showToast("请至少选择一票进行保存！")
                        return
                    }
                    saveInfo()
                }


            }

        })
        operating_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (operating_btn.text.toString() == "添加本车") {
                    getSelectInventoryList()?.let {
                        if (it.isEmpty()) {
                            showToast("请至少选择一票进行添加！")
                            return
                        }
                        addSomeThing()
                    }
                } else if (operating_btn.text.toString() == "移出本车") {
                    if (getSelectLoadingOrderItem() == 0) {
                        showToast("请至少选择一票进行移出！")
                        return
                    }
                    removeSomeThing()
                }
                refreshTopNumber()
            }

        })

    }

    override fun initInventoryList() {
        super.initInventoryList()
        mInventoryListAdapter?.mOnRemoveInterface = object : ShortFeederHouseInventoryListAdapter.OnRemoveInterface {
            override fun onClick(position: Int, item: StockWaybillListBean) {
                mInventoryListAdapter?.removeItem(position)
                mLoadingListAdapter?.appendData(mutableListOf(item))
                refreshTopNumber()
            }

        }
    }

    override fun initLoadingList() {
        super.initLoadingList()
        mLoadingListAdapter?.mOnRemoveInterface = object : ShortFeederHouseLoadingListAdapter.OnRemoveInterface {
            override fun onClick(position: Int, item: StockWaybillListBean) {
                mLoadingListAdapter?.removeItem(position)
                mInventoryListAdapter?.appendData(mutableListOf(item))
                refreshTopNumber()
            }

        }
    }

    override fun getCarInfo(result: FixedTrunkDepartureHouseInfo) {
        mLoadingListAdapter?.appendData(result.item)
        fix_trunk_departure_house_tabLayout.getTabAt(1)?.text = "配载清单(${result.item.size})"
        operating_interval_tv.text = result.vehicleInterval
        for (item in result.vehicleInterval.split("-")) {
            if (item != result.webidCodeStr) {
                if (item != result.ewebidCodeStr) {
                    val checkBox = CheckBox(mContext)
                    checkBox.text = item
                    operating_interval_ll.addView(checkBox)
                }
            }

        }
    }

    override fun getInventoryS(list: List<StockWaybillListBean>) {
        mInventoryListAdapter?.appendData(list)
        fix_trunk_departure_house_tabLayout.getTabAt(0)?.text = "库存清单(${list.size})"
    }

    override fun saveAlongInfoS(result: String) {
        TalkSureDialog(mContext, getScreenWidth(), result) {
            onBackPressed()
        }.show()
    }

}