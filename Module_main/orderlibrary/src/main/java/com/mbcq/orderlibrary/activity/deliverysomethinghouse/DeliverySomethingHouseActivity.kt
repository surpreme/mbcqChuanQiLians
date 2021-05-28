package com.mbcq.orderlibrary.activity.deliverysomethinghouse


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_delivery_something_house.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.StringBuilder

/**
 * @author: lzy
 * @time:2020-9-29 13:18:09 送货操作页
 */
@Route(path = ARouterConstants.DeliverySomethingHouseActivity)
class DeliverySomethingHouseActivity : BaseDeliverySomethingHouseActivity<DeliverySomethingHouseContract.View, DeliverySomethingHousePresenter>(), DeliverySomethingHouseContract.View {
    override fun getLayoutId(): Int = R.layout.activity_delivery_something_house


    @SuppressLint("SetTextI18n")
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)

        initLoadingList()
        initInventoryList()
    }

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getDeparture()
        mPresenter?.getInventory()
    }


    /**
     * 完成本车保存
     */
    fun completeCar() {
        mLoadingListAdapter?.getAllData()?.let {
            if (it.isEmpty())
                return
            val mLastData = JSONObject()
            val jarray = JSONArray()
            val kk = StringBuilder()

            for ((index, item) in it.withIndex()) {
                val obj = JSONObject(Gson().toJson(item))
                obj.put("senWebCod", UserInformationUtil.getWebIdCode(mContext))
                obj.put("senWebCodStr", UserInformationUtil.getWebIdCodeStr(mContext))
                kk.append(item.billno)
                if (index != it.size - 1)
                    kk.append(",")
                jarray.put(obj)
            }
            mLastData.put("WaybillSendDetLst", jarray)
            mLastData.put("CommonStr", kk.toString())
            mPresenter?.saveInfo(mLastData)
        }

    }


    override fun onClick() {
        super.onClick()
        complete_vehicle_btn.setOnLongClickListener {
            ARouter.getInstance().build(ARouterConstants.DeliverySomethingMapHouseActivity).navigation()
            true
        }
        complete_vehicle_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                completeCar()
            }

        })
        all_selected_checked.setOnCheckedChangeListener { _, isChecked ->
            if (mTypeIndex == 1)
                mInventoryListAdapter?.checkedAll(isChecked)
            else if (mTypeIndex == 2)
                mLoadingListAdapter?.checkedAll(isChecked)

        }
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
        delivery_something_house_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })

    }

    @SuppressLint("SetTextI18n")
    override fun getInventoryS(list: List<DeliverySomethingHouseBean>) {
        mInventoryListAdapter?.appendData(list)
        inventory_list_tv.text = "库存清单(${list.size})"

    }

    override fun saveInfoS(result: String) {
        TalkSureDialog(mContext, getScreenWidth(), "送货上门${dispatch_number_tv.text}完成，点击查看详情！") {
            onBackPressed()
            this.finish()
        }.show()
    }

    @SuppressLint("SetTextI18n")
    override fun getDepartureS(s: String) {
        dispatch_number_tv.text = "派车单号：$s"

    }

    override fun onRecyclerShowDialog(position: Int, item: DeliverySomethingHouseBean) {
        DeliverySomeThingPriceDialog(getScreenWidth(), Gson().toJson(item), object : OnClickInterface.OnClickInterface {
            override fun onResult(s1: String, s2: String) {
                val obj = JSONObject(s1)
                item.accForklift = obj.optString("accForklift")//叉车费
                item.accHanding = obj.optString("accHanding")//装卸费
                item.accsend = obj.optString("accsend")//实际送货费
                item.sendqty = obj.optString("sendqty")//实际件数
                mLoadingListAdapter?.notifyItemChangeds(position, item)
//                refreshTopInfo()
            }

        }).show(supportFragmentManager, "DeliverySomeThingPriceDialog")
    }
}