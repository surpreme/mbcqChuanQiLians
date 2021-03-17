package com.mbcq.vehicleslibrary.activity.shortfeederunloadingwarehousing


import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.ui.BaseListMVPActivity
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.LocalEntity
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.fragment.ArrivalVehiclesEvent
import com.mbcq.vehicleslibrary.fragment.shortfeeder.ShortFeederBean
import kotlinx.android.synthetic.main.activity_short_feeder_unloading_warehousing.*
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject
import java.lang.StringBuilder

/**
 * @author: lzy
 * @time: 2020-12-31 16:28:43 短驳卸车入库
 * @ShortFeederUnloadingWarehousingReceiptAdapter 回车清单
 * @ShortFeederUnloadingWarehousingAdapter 本车清单 默认
 */

@Route(path = ARouterConstants.ShortFeederUnloadingWarehousingActivity)
class ShortFeederUnloadingWarehousingActivity : BaseListMVPActivity<ShortFeederUnloadingWarehousingContract.View, ShortFeederUnloadingWarehousingPresenter, ShortFeederUnloadingWarehousingBean>(), ShortFeederUnloadingWarehousingContract.View {
    @Autowired(name = "ShortFeederUnloadingWarehousing")
    @JvmField
    var mShortFeederUnloadingWarehousing: String = ""
    val mTabEntities: ArrayList<CustomTabEntity> = ArrayList()
    var mShortFeederUnloadingWarehousingReceiptAdapter: ShortFeederUnloadingWarehousingReceiptAdapter? = null

    override fun getLayoutId(): Int = R.layout.activity_short_feeder_unloading_warehousing

    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)
    }

    @SuppressLint("SetTextI18n")
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        initToptab()
        val mObj=JSONObject(mShortFeederUnloadingWarehousing)
        departure_tv.text = "发车批次：${mObj.optString("inoneVehicleFlag")}"
        web_info_tv.text = "运行区间：${mObj.optString("webidCodeStr")}-${mObj.optString("ewebidCodeStr")}"
        over_total_info_tv.text = "已 装  车：${mObj.optString("ps")}票 x件 ${mObj.optString("weight")}Kg ${mObj.optString("volumn")}方     ${mObj.optString("yf")}元"
        mShortFeederUnloadingWarehousingReceiptAdapter = ShortFeederUnloadingWarehousingReceiptAdapter(mContext).also {
            receipt_list_recycler.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
            receipt_list_recycler.adapter = it
        }
        when (mObj.optInt("vehicleState", 0)) {
            1 -> {//发货
                bottom_ll.visibility = View.VISIBLE

            }
            2 -> {//到货
                bottom_ll.visibility = View.VISIBLE

            }
            else -> {
                bottom_ll.visibility = View.GONE

            }
        }
    }

    private fun initToptab() {
        mTabEntities.add(LocalEntity("本车清单"))
        mTabEntities.add(LocalEntity("回单清单"))
        short_feeder_unloading_warehousing_tabLayout.setTabData(mTabEntities)
        short_feeder_unloading_warehousing_tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                if (position == 0) {
                    this_car_list_recycler.visibility = View.VISIBLE
                    receipt_list_recycler.visibility = View.GONE
                    complete_vehicle_btn.text = "卸车入库"
                    all_selected_checked.visibility = View.VISIBLE
                    printed_selected_checked.visibility = View.GONE
                } else if (position == 1) {
                    this_car_list_recycler.visibility = View.GONE
                    receipt_list_recycler.visibility = View.VISIBLE
                    complete_vehicle_btn.text = "打印"
                    all_selected_checked.visibility = View.GONE
                    printed_selected_checked.visibility = View.VISIBLE
                }

            }

            override fun onTabReselect(position: Int) {

            }

        })
    }

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getVehicleInfo(JSONObject(mShortFeederUnloadingWarehousing).optString("inoneVehicleFlag"))
        mPresenter?.getVehicleReceiptInfo(JSONObject(mShortFeederUnloadingWarehousing).optString("inoneVehicleFlag"))
    }

    override fun onClick() {
        super.onClick()
        complete_vehicle_btn.apply {
            onSingleClicks {
                if (complete_vehicle_btn.text == "卸车入库") {
                    var isSelected = false
                    for (item in adapter.getAllData()) {
                        if (item.isChecked) {
                            isSelected = true
                            break
                        }
                    }
                    if (!isSelected) {
                        showToast("请至少选择一件运单进行卸车入库")
                        return@onSingleClicks
                    }
                    //************************************************start
                    val mVehicleObj = JSONObject(mShortFeederUnloadingWarehousing)
                    if (mVehicleObj.optInt("vehicleState", 0) == 1) {
                        TalkSureCancelDialog(mContext, getScreenWidth(), "您还未到车，您确认要到车${mVehicleObj.optString("inoneVehicleFlag")}吗？") {
                            mPresenter?.confirmCar(Gson().fromJson(mShortFeederUnloadingWarehousing, ShortFeederBean::class.java), 0)
                        }.show()
                        return@onSingleClicks
                    }
                    //************************************************end
                    val billnoSsbuilder = StringBuilder()
                    for (item in adapter.getAllData()) {
                        if (item.isChecked) {
                            billnoSsbuilder.append(item.billno).append(",")
                        }
                    }
                    TalkSureCancelDialog(mContext, getScreenWidth(), "您确定要卸车入库运单号为：${billnoSsbuilder.toString()}的运单吗？") {
                        mPresenter?.UnloadingWarehousing(billnoSsbuilder.toString().substring(0, billnoSsbuilder.toString().length - 1), mVehicleObj.optString("inoneVehicleFlag"))

                    }.show()

                }
            }
        }
        short_feeder_unloading_warehousing_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    override fun getRecyclerViewId(): Int = R.id.this_car_list_recycler

    override fun setAdapter(): BaseRecyclerAdapter<ShortFeederUnloadingWarehousingBean> = ShortFeederUnloadingWarehousingAdapter(mContext).also {
        all_selected_checked.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.text.contains("全选")) {
                it.checkedAll(isChecked)
            }
        }
    }

    override fun getVehicleInfoS(list: List<ShortFeederUnloadingWarehousingBean>) {
        adapter.appendData(list)
    }

    override fun getVehicleReceiptInfoS(list: List<ShortFeederUnloadingWarehousingBean>) {
        mShortFeederUnloadingWarehousingReceiptAdapter?.appendData(list)
    }

    override fun confirmCarS(data: ShortFeederBean, position: Int) {
        val mVehicleObj = JSONObject(mShortFeederUnloadingWarehousing)
        if (mVehicleObj.has("vehicleState")) {
            mVehicleObj.remove("vehicleState")
            mVehicleObj.put("vehicleState", 2)
        }
        if (mVehicleObj.has("vehicleStateStr")) {
            mVehicleObj.remove("vehicleStateStr")
            mVehicleObj.put("vehicleStateStr", "到货")
        }
        mShortFeederUnloadingWarehousing = GsonUtils.toPrettyFormat(mVehicleObj)
        EventBus.getDefault().postSticky(ArrivalVehiclesEvent(1, mShortFeederUnloadingWarehousing))

        var isSelected = false
        for (item in adapter.getAllData()) {
            if (item.isChecked) {
                isSelected = true
                break
            }
        }
        if (!isSelected) {
            showToast("请至少选择一件运单进行卸车入库")
            return
        }
        val billnoSsbuilder = StringBuilder()
        for (item in adapter.getAllData()) {
            if (item.isChecked) {
                billnoSsbuilder.append(item.billno).append(",")
            }
        }
        TalkSureCancelDialog(mContext, getScreenWidth(), "您确定要卸车入库运单号为：${billnoSsbuilder.toString()}的运单吗？") {
            mPresenter?.UnloadingWarehousing(billnoSsbuilder.toString().substring(0, billnoSsbuilder.toString().length - 1), JSONObject(mShortFeederUnloadingWarehousing).optString("inoneVehicleFlag"))

        }.show()

    }

    override fun UnloadingWarehousingS(result: String) {
        val mVehicleObj = JSONObject(mShortFeederUnloadingWarehousing)
        if (mVehicleObj.has("vehicleState")) {
            mVehicleObj.remove("vehicleState")
            mVehicleObj.put("vehicleState", 3)
        }
        if (mVehicleObj.has("vehicleStateStr")) {
            mVehicleObj.remove("vehicleStateStr")
            mVehicleObj.put("vehicleStateStr", "到货处理结束")
        }
        mShortFeederUnloadingWarehousing = GsonUtils.toPrettyFormat(mVehicleObj)
        EventBus.getDefault().postSticky(ArrivalVehiclesEvent(1, mShortFeederUnloadingWarehousing))
        object : CountDownTimer(200, 200) {
            override fun onFinish() {
                if (!isDestroyed) {
                    TalkSureDialog(mContext, getScreenWidth(), "$result 卸车入库成功,点击返回！") {
                        onBackPressed()
                    }.show()
                }
            }

            override fun onTick(millisUntilFinished: Long) {

            }

        }.start()

    }
}