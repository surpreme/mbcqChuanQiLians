package com.mbcq.vehicleslibrary.activity.fixshortfeederhouse


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.interfaces.RxBus
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.departurerecord.DepartureRecordRefreshEvent
import com.mbcq.vehicleslibrary.bean.StockWaybillListBean
import kotlinx.android.synthetic.main.activity_fix_short_feeder_house.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-18 14:23:00
 */
@Route(path = ARouterConstants.FixShortFeederHouseActivity)
class FixShortFeederHouseActivity : BaseFixShortFeederHouseActivity<FixShortFeederHouseContract.View, FixShortFeederHousePresenter>(), FixShortFeederHouseContract.View {
    @Autowired(name = "FixedShortFeederHouse")
    @JvmField
    var mFixDataJson: String = ""
    var mInoneVehicleFlag = ""

    override fun getLayoutId(): Int = R.layout.activity_fix_short_feeder_house

    @SuppressLint("SetTextI18n")
    override fun initDatas() {
        super.initDatas()
        val mLastData = JSONObject(mFixDataJson)
        mInoneVehicleFlag = mLastData.optString("InoneVehicleFlag")
        departure_lot_tv.text = "发车批次: $mInoneVehicleFlag"
        mPresenter?.getInventory(1)
        mPresenter?.getCarInfo(mLastData.optInt("Id"), mLastData.optString("InoneVehicleFlag"))
    }

    override fun onClick() {
        super.onClick()
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

        complete_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (complete_btn.text == "取消完成本车") {
                    TalkSureCancelDialog(mContext, getScreenWidth(), "您确定要取消完成${departure_lot_tv.text}吗？") {
                        val modifyData = JSONObject(mFixDataJson)
                        mPresenter?.modify(modifyData)
                    }.show()
                }
            }

        })
        all_selected_checked.setOnCheckedChangeListener { _, isChecked ->
            if (mTypeIndex == 1)
                mInventoryListAdapter?.checkedAll(isChecked)
            else if (mTypeIndex == 2)
                mLoadingListAdapter?.checkedAll(isChecked)

        }

    }

    override fun modifyS() {
        TalkSureDialog(mContext, getScreenWidth(), "成功取消完成本车") {
            RxBus.build().postSticky(DepartureRecordRefreshEvent::class.java)
            onBackPressed()
            this.finish()
        }.show()
    }

    //Stack trace:
//java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
    override fun getCarInfo(result: FixShortFeederHouseCarInfo) {
        mLoadingListAdapter?.appendData(result.item)
        short_feeder_house_tabLayout.getTabAt(1)?.text = "配载清单(${result.item.size})"
        if (result.vehicleState == "1") {
            complete_btn.text = "取消完成本车"
            operating_btn.setBackgroundColor(Color.GRAY)
            operating_btn.isClickable = false
            all_selected_checked.isEnabled = false
            mInventoryListAdapter?.mOnRemoveInterface = null
            mLoadingListAdapter?.mOnRemoveInterface = null
        }

    }

    override fun getInventoryS(list: List<StockWaybillListBean>) {
        short_feeder_house_tabLayout.getTabAt(0)?.text = "库存清单(${list.size})"
        mInventoryListAdapter?.appendData(list)
    }
}