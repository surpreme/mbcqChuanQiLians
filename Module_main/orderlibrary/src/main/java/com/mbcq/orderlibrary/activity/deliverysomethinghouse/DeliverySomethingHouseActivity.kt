package com.mbcq.orderlibrary.activity.deliverysomethinghouse


import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_delivery_something_house.*
import org.json.JSONArray
import org.json.JSONObject

/**
 * @author: lzy
 * @time:2020-9-29 13:18:09 送货操作页
 */
@Route(path = ARouterConstants.DeliverySomethingHouseActivity)
class DeliverySomethingHouseActivity : BaseDeliverySomethingHouseActivity<DeliverySomethingHouseContract.View, DeliverySomethingHousePresenter>(), DeliverySomethingHouseContract.View {
    @Autowired(name = "AddDeliverySomeThing")
    @JvmField
    var mLastDataJson: String = ""
    override fun getLayoutId(): Int = R.layout.activity_delivery_something_house


    @SuppressLint("SetTextI18n")
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        val obj = JSONObject(mLastDataJson)
        dispatch_number_tv.text = "派车单号：${obj.optString("SendInOneFlag")}"
        initLoadingList()
        initInventoryList()
    }

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getInventory()
    }

    override fun initInventoryList() {
        super.initInventoryList()
        mInventoryListAdapter?.mOnRemoveInterface = object : DeliverySomethingHouseInventoryAdapter.OnRemoveInterface {
            override fun onClick(position: Int, item: DeliverySomethingHouseBean) {
                val mDeliverySomethingHouseFixBean = Gson().fromJson<DeliverySomethingHouseFixBean>(mLastDataJson, DeliverySomethingHouseFixBean::class.java)

                mDeliverySomethingHouseFixBean.waybillSendDetLst = mutableListOf(item)
                /* val mLastData = JSONObject(mLastDataJson)
                 val listData = JSONArray()
                 val itemObj = JSONObject()
                 itemObj.put("billno", item.billno)
                 itemObj.put("billno", item.billno)
                 listData.put(itemObj)
                 mLastData.put("WaybillSendDetLst", listData)*/
                mPresenter?.addOrderItem(Gson().toJson(mDeliverySomethingHouseFixBean), position, item)
            }
        }
    }

    override fun initLoadingList() {
        super.initLoadingList()
        mLoadingListAdapter?.mOnRemoveInterface = object : DeliverySomethingHouseLoadingAdapter.OnRemoveInterface {
            override fun onClick(position: Int, item: DeliverySomethingHouseBean) {
                val mLastData = JSONObject(mLastDataJson)
//                mPresenter?.removeOrderItem(item.billno, mLastData.optString("Id"), mLastData.optString("InoneVehicleFlag"), position, item)
            }

        }
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
        delivery_something_house_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })

    }

    override fun getInventoryS(list: List<DeliverySomethingHouseBean>) {
        mInventoryListAdapter?.appendData(list)
    }
}