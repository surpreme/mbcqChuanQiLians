package com.mbcq.vehicleslibrary.activity.allterminalagent.terminalagentbycarhouse


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.allterminalagent.TerminalAgentByCarHouseBean
import kotlinx.android.synthetic.main.activity_terminal_agent_by_car_house.*
import org.json.JSONArray
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-25 13:33:00
 */
@Route(path = ARouterConstants.TerminalAgentByCarHouseActivity)
class TerminalAgentByCarHouseActivity : BaseTerminalAgentByCarHouseActivity<TerminalAgentByCarHouseContract.View, TerminalAgentByCarHousePresenter>(), TerminalAgentByCarHouseContract.View {
    @Autowired(name = "TerminalAgentByCarHouse")
    @JvmField
    var mLastDataJson: String = ""

    override fun getLayoutId(): Int = R.layout.activity_terminal_agent_by_car_house

    @SuppressLint("SetTextI18n")
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        val mLastData = JSONObject(mLastDataJson)
        mDepartureLot = mLastData.optString("AgentBillno")
        dispatch_number_tv.text = "派车单号: $mDepartureLot"

    }

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getInventory()
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
            mLastData.put("waybillAgentDetLst", jarray)
            mLastData.put("CommonStr", kk.toString())
            mPresenter?.completeVehicle(mLastData)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun getInventoryS(list: List<TerminalAgentByCarHouseBean>) {
        mInventoryListAdapter?.appendData(list)
        inventory_list_tv.text = "库存清单(${list.size})"

    }

    override fun completeVehicleS() {
        TalkSureDialog(mContext, getScreenWidth(), "${dispatch_number_tv.text}已完成出库") {
            onBackPressed()
        }.show()
    }

    override fun onClick() {
        super.onClick()
        complete_vehicle_btn.setOnClickListener(object : SingleClick(2000) {
            override fun onSingleClick(v: View?) {
                saveV()
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
                    addSomeThing()

                } else if (operating_btn.text.toString() == "移出本车") {
                    removeSomeThing()
                }
                showTopTotal()
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

}