package com.mbcq.vehicleslibrary.activity.trunkdeparturehouse


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.bean.StockWaybillListBean
import kotlinx.android.synthetic.main.activity_add_trunk_departure_house.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2018.08.25
 * 添加干线发车 运输方式 update
 */
@Route(path = ARouterConstants.TrunkDepartureHouseActivity)
class TrunkDepartureHouseActivity : BaseTrunkDepartureHouseActivity<TrunkDepartureHouseContract.View, TrunkDepartureHousePresenter>(), TrunkDepartureHouseContract.View {
    @Autowired(name = "TrunkDepartureHouse")
    @JvmField
    var mLastDataJson: String = ""
    var mDepartureLot = ""

    override fun getLayoutId(): Int = R.layout.activity_add_trunk_departure_house

    @SuppressLint("SetTextI18n")
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        val mLastData = JSONObject(mLastDataJson)
        mDepartureLot = mLastData.optString("InoneVehicleFlag")
        departure_lot_tv.text = "发车批次: $mDepartureLot"
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

        all_selected_checked.setOnCheckedChangeListener { _, isChecked ->
            if (mTypeIndex == 1)
                mInventoryListAdapter?.checkedAll(isChecked)
            else if (mTypeIndex == 2)
                mLoadingListAdapter?.checkedAll(isChecked)

        }
    }

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getInventory(1)
    }

    override fun getInventoryS(list: List<StockWaybillListBean>) {
        short_feeder_house_tabLayout.getTabAt(0)?.text = "库存清单(${list.size})"
        mInventoryListAdapter?.appendData(list)
    }


}