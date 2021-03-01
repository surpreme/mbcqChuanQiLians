package com.mbcq.vehicleslibrary.activity.alllocalagent.localgentshortfeederhouse


import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.alllocalagent.event.LocalGentShortFeederHouseEvent
import kotlinx.android.synthetic.main.activity_local_gent_short_feeder_house.*
import org.greenrobot.eventbus.EventBus
import org.json.JSONArray
import org.json.JSONObject


/**
 * @author: lzy
 * @time: 2020-09-22 17:13:27
 * 中转出库
 */
@Route(path = ARouterConstants.LocalGentShortFeederHouseActivity)
class LocalGentShortFeederHouseActivity : BaseLocalGentShortFeederHouseActivity<LocalGentShortFeederHouseContract.View, LocalGentShortFeederHousePresenter>(), LocalGentShortFeederHouseContract.View {
    @Autowired(name = "LocalGentShortFeederHouse")
    @JvmField
    var mLastDataJson: String = ""


    override fun getLayoutId(): Int = R.layout.activity_local_gent_short_feeder_house

    @SuppressLint("SetTextI18n")
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        val mLastData = JSONObject(mLastDataJson)
        mDepartureLot = mLastData.optString("AgentBillno")
        dispatch_number_tv.text = "派车单号: $mDepartureLot"

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
            mLastData.put("WaybillAgentDetLst", jarray)
            mLastData.put("CommonStr", kk.toString())
            mPresenter?.completeVehicle(mLastData)
        }
    }

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getInventory()
    }





    override fun onClick() {
        super.onClick()
        complete_vehicle_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                TalkSureCancelDialog(mContext, getScreenWidth(), "您确定要完成${dispatch_number_tv.text}吗") {
                    saveV()
                }.show()
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

    @SuppressLint("SetTextI18n")
    override fun getInventoryS(list: List<LocalGentShortFeederHouseBean>) {
        mInventoryListAdapter?.appendData(list)
        inventory_list_tv.text = "库存清单(${list.size})"

    }

    override fun completeVehicleS() {
        TalkSureDialog(mContext, getScreenWidth(), "${dispatch_number_tv.text}已完成出库") {
            EventBus.getDefault().postSticky(LocalGentShortFeederHouseEvent(JSONObject(mLastDataJson).optInt("mTypeS")))
            onBackPressed()
        }.show()
    }

}