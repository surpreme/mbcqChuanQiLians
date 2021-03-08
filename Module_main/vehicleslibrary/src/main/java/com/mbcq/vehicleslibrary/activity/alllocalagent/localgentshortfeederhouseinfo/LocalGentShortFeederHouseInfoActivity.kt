package com.mbcq.vehicleslibrary.activity.alllocalagent.localgentshortfeederhouseinfo


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.alllocalagent.localgentshortfeederhouse.LocalGentShortFeederHouseBean
import kotlinx.android.synthetic.main.activity_local_gent_short_feeder_house_info.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2021-03-02 15:23:03 本地代理 详情页
 */

@Route(path = ARouterConstants.LocalGentShortFeederHouseInfoActivity)
class LocalGentShortFeederHouseInfoActivity : BaseLocalGentShortFeederHouseInfoActivity<LocalGentShortFeederHouseInfoContract.View, LocalGentShortFeederHouseInfoPresenter>(), LocalGentShortFeederHouseInfoContract.View {
    @Autowired(name = "LocalGentShortFeederInfoJson")
    @JvmField
    var mLastDataJson: String = ""

    override fun getLayoutId(): Int = R.layout.activity_local_gent_short_feeder_house_info
    @SuppressLint("SetTextI18n")
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        val mLastData = JSONObject(mLastDataJson)
        mDepartureLot = mLastData.optString("agentBillno")
        dispatch_number_tv.text = "派车单号: $mDepartureLot"
    }
    @SuppressLint("SetTextI18n")
    override fun initDatas() {
        super.initDatas()
        mPresenter?.getInventory()
        mPresenter?.getLoadingData(mDepartureLot)
    }

    override fun onClick() {
        super.onClick()
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
        refreshTopInfo()
    }
}