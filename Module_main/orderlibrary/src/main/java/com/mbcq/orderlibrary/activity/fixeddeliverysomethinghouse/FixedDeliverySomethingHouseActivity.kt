package com.mbcq.orderlibrary.activity.fixeddeliverysomethinghouse


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_fixed_delivery_something_house.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-10-09 11:00:00 修改送货上门
 * TODO
 */
@Route(path = ARouterConstants.FixedDeliverySomethingHouseActivity)
class FixedDeliverySomethingHouseActivity : BaseFixedDeliverySomethingHouseActivity<FixedDeliverySomethingHouseContract.View, FixedDeliverySomethingHousePresenter>(), FixedDeliverySomethingHouseContract.View {
    @Autowired(name = "FixedDeliverySomeThing")
    @JvmField
    var mLastDataJson: String = ""
    override fun getLayoutId(): Int = R.layout.activity_fixed_delivery_something_house
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        initLoadingList()
        initInventoryList()
    }

    override fun onClick() {
        super.onClick()
        operating_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                val mLastData = JSONObject(mLastDataJson)
                if (operating_btn.text.toString() == "添加本车") {
                    if (getSelectInventoryOrder().isEmpty()) {
                        showToast("请至少选择一票进行添加！")
                        return
                    }
                    val mAddOrderFixedTerminalAgentByCarBean = AddDeliverySomethingHouseFixBean()
                    mAddOrderFixedTerminalAgentByCarBean.waybillSendDetLst = getSelectInventoryOrderBean()
                    mAddOrderFixedTerminalAgentByCarBean.commonStr = getSelectInventoryOrder()
                    mAddOrderFixedTerminalAgentByCarBean.sendInOneFlag = mLastData.optString("sendInOneFlag")
                    mPresenter?.addOrder(Gson().toJson(mAddOrderFixedTerminalAgentByCarBean))

                } else if (operating_btn.text.toString() == "移出本车") {
                    if (getSelectLoadingOrderItem() == 0) {
                        showToast("请至少选择一票进行移出！")
                        return
                    }
                    val mRemoveOrderFixedTerminalAgentByCarBean = Gson().fromJson<RemoveOrderFixedTerminalAgentByCarBean>(mLastDataJson, RemoveOrderFixedTerminalAgentByCarBean::class.java)
                    mRemoveOrderFixedTerminalAgentByCarBean.waybillSendDetLst = getSelectLoadingOrderBean()
                    mRemoveOrderFixedTerminalAgentByCarBean.commonStr =getSelectLoadingOrder()
                    mRemoveOrderFixedTerminalAgentByCarBean.id =mLastData.optString("id")
                    mRemoveOrderFixedTerminalAgentByCarBean.sendInOneFlag =mLastData.optString("sendInOneFlag")
                    mPresenter?.removeOrder(getSelectLoadingOrder(), mLastData.optString("id"), mLastData.optString("sendInOneFlag"))
                }
            }

        })
        complete_vehicle_btn.setOnClickListener(object:SingleClick(){
            override fun onSingleClick(v: View?) {
                TalkSureDialog(mContext,getScreenWidth(),"${dispatch_number_tv.text}已修改完毕"){
                 onBackPressed()
                }.show()
            }

        })
        all_selected_checked.setOnCheckedChangeListener { _, isChecked ->
            if (mTypeIndex == 1)
                mInventoryListAdapter?.checkedAll(isChecked)
            else if (mTypeIndex == 2)
                mLoadingListAdapter?.checkedAll(isChecked)

        }
    }

    override fun initInventoryList() {
        super.initInventoryList()
        mInventoryListAdapter?.mOnRemoveInterface = object : FixedDeliverySomethingHouseInventoryAdapter.OnRemoveInterface {
            override fun onClick(position: Int, item: FixedDeliverySomethingHouseBean) {
                val mAddOrderFixedTerminalAgentByCarBean = Gson().fromJson<AddDeliverySomethingHouseFixBean>(mLastDataJson, AddDeliverySomethingHouseFixBean::class.java)
                mAddOrderFixedTerminalAgentByCarBean.waybillSendDetLst = mutableListOf(item)
                mAddOrderFixedTerminalAgentByCarBean.commonStr = item.billno
                val mLastData = JSONObject(mLastDataJson)
                mAddOrderFixedTerminalAgentByCarBean.sendInOneFlag =mLastData.optString("sendInOneFlag")
                mPresenter?.addOrderItem(Gson().toJson(mAddOrderFixedTerminalAgentByCarBean), position, item)

            }

        }
    }

    override fun initLoadingList() {
        super.initLoadingList()
        mLoadingListAdapter?.mOnRemoveInterface = object : FixedDeliverySomethingHouseLoadingAdapter.OnRemoveInterface {
            override fun onClick(position: Int, item: FixedDeliverySomethingHouseBean) {
                val mLastData = JSONObject(mLastDataJson)
                val mRemoveOrderFixedTerminalAgentByCarBean = RemoveOrderFixedTerminalAgentByCarBean()
                mRemoveOrderFixedTerminalAgentByCarBean.waybillSendDetLst = mutableListOf(item)
                mRemoveOrderFixedTerminalAgentByCarBean.commonStr =item.billno
                mRemoveOrderFixedTerminalAgentByCarBean.id =mLastData.optString("id")
                mRemoveOrderFixedTerminalAgentByCarBean.sendInOneFlag =mLastData.optString("sendInOneFlag")
                mPresenter?.removeOrderItem(Gson().toJson(mRemoveOrderFixedTerminalAgentByCarBean), "","", position, item)

            }

        }
    }

    @SuppressLint("SetTextI18n")
    override fun initDatas() {
        super.initDatas()
        val obj = JSONObject(mLastDataJson)
        dispatch_number_tv.text = "派车单号:${obj.optString("sendInOneFlag")}"
        mPresenter?.getInventory()
        mPresenter?.getLoadingInventory(obj.optString("id"), obj.optString("sendInOneFlag"))

    }

    @SuppressLint("SetTextI18n")
    override fun getInventoryS(list: List<FixedDeliverySomethingHouseBean>) {
        mInventoryListAdapter?.appendData(list)
        inventory_list_tv.text = "库存清单(${list.size})"
    }

    @SuppressLint("SetTextI18n")
    override fun getLoadingInventory(list: List<FixedDeliverySomethingHouseBean>) {
        mLoadingListAdapter?.appendData(list)
        loading_list_tv.text = "配载清单(${list.size})"
    }

    override fun addOrderItemS(position: Int, item: FixedDeliverySomethingHouseBean) {
        mInventoryListAdapter?.removeItem(position)
        mLoadingListAdapter?.appendData(mutableListOf(item))
        refreshTopNumber()
    }

    override fun removeOrderItemS(position: Int, item: FixedDeliverySomethingHouseBean) {
        mLoadingListAdapter?.removeItem(position)
        mInventoryListAdapter?.appendData(mutableListOf(item))
        refreshTopNumber()
    }

    override fun addOrderS() {
        addSomeThing()
        refreshTopNumber()

    }

    override fun removeOrderS() {
        removeSomeThing()
        refreshTopNumber()
    }
}