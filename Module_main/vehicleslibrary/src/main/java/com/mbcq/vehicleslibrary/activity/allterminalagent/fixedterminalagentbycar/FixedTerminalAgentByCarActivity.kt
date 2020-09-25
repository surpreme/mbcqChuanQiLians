package com.mbcq.vehicleslibrary.activity.allterminalagent.fixedterminalagentbycar


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.allterminalagent.TerminalAgentByCarHouseBean
import com.mbcq.vehicleslibrary.activity.allterminalagent.terminalagentbycarhouse.TerminalAgentByCarHouseInventoryAdapter
import com.mbcq.vehicleslibrary.activity.allterminalagent.terminalagentbycarhouse.TerminalAgentByCarHouseLoadingAdapter
import kotlinx.android.synthetic.main.activity_fixed_terminal_agent_by_car.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-25  11:30:15
 * 终端代理 修改
 */
@Route(path = ARouterConstants.FixedTerminalAgentByCarActivity)
class FixedTerminalAgentByCarActivity : BaseFixedTerminalAgentByCarActivity<FixedTerminalAgentByCarContract.View, FixedTerminalAgentByCarPresenter>(), FixedTerminalAgentByCarContract.View {
    @Autowired(name = "FixedTerminalAgentByCarJson")
    @JvmField
    var mLastDataJson: String = ""

    override fun getLayoutId(): Int = R.layout.activity_fixed_terminal_agent_by_car
    override fun initDatas() {
        super.initDatas()
        mPresenter?.getInventory()
        mPresenter?.getLoadingData(mDepartureLot)

    }

    @SuppressLint("SetTextI18n")
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        val mLastData = JSONObject(mLastDataJson)
        mDepartureLot = mLastData.optString("agentBillno")
        dispatch_number_tv.text = "派车单号: $mDepartureLot"
    }

    override fun onClick() {
        super.onClick()
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
                    val mAddOrderFixedLocalGentShortFeederHouseBean = Gson().fromJson<AddOrderFixedTerminalAgentByCarBean>(mLastDataJson, AddOrderFixedTerminalAgentByCarBean::class.java)
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
    override fun initLoadingList() {
        super.initLoadingList()
        mLoadingListAdapter?.mOnRemoveInterface = object : TerminalAgentByCarHouseLoadingAdapter.OnRemoveInterface {
            override fun onClick(position: Int, item: TerminalAgentByCarHouseBean) {
                val mLastData = JSONObject(mLastDataJson)
                mLastData.put("commonStr", item.billno)
                mPresenter?.removeOrderItem(mLastData, position, item)

            }

        }
    }

    override fun initInventoryList() {
        super.initInventoryList()
        mInventoryListAdapter?.mOnRemoveInterface = object : TerminalAgentByCarHouseInventoryAdapter.OnRemoveInterface {
            override fun onClick(position: Int, item: TerminalAgentByCarHouseBean) {
                val mAddOrderFixedTerminalAgentByCarBean = Gson().fromJson<AddOrderFixedTerminalAgentByCarBean>(mLastDataJson, AddOrderFixedTerminalAgentByCarBean::class.java)
                mAddOrderFixedTerminalAgentByCarBean.waybillAgentDetLst = mutableListOf(item)
                mAddOrderFixedTerminalAgentByCarBean.commonStr = getSelectInventoryOrder()
                mPresenter?.addOrderItem(Gson().toJson(mAddOrderFixedTerminalAgentByCarBean), position, item)

            }

        }
    }
    @SuppressLint("SetTextI18n")
    override fun getInventoryS(list: List<TerminalAgentByCarHouseBean>) {
        mInventoryListAdapter?.appendData(list)
        inventory_list_tv.text = "库存清单(${list.size})"

    }

    @SuppressLint("SetTextI18n")
    override fun getLoadingDataS(list: List<TerminalAgentByCarHouseBean>) {
        mLoadingListAdapter?.appendData(list)
        loading_list_tv.text = "配载清单(${list.size})"

    }

    override fun removeOrderS() {
        removeSomeThing()
        showTopTotal()

    }

    override fun removeOrderItemS(position: Int, item: TerminalAgentByCarHouseBean) {
        mLoadingListAdapter?.removeItem(position)
        mInventoryListAdapter?.appendData(mutableListOf(item))
        showTopTotal()

    }


    override fun addOrderS() {
        addSomeThing()
        showTopTotal()
    }

    override fun addOrderItemS(position: Int, item: TerminalAgentByCarHouseBean) {
        mInventoryListAdapter?.removeItem(position)
        mLoadingListAdapter?.appendData(mutableListOf(item))
        showTopTotal()

    }



}