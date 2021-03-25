package com.mbcq.vehicleslibrary.activity.alldeparturerecord.addshortfeeder


import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.system.TimeUtils
import com.mbcq.baselibrary.view.MoneyInputFilter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.NumberPlateBean
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterDialog
import com.mbcq.vehicleslibrary.R
import kotlinx.android.synthetic.main.activity_add_short_feeder.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-14 13:42:27
 * 添加短驳发车 运输方式 update
 */
@Route(path = ARouterConstants.AddShortFeederActivity)
class AddShortFeederActivity : BaseAddShortFeederActivity<AddShortFeederContract.View, AddShortFeederPresenter>(), AddShortFeederContract.View {
    override fun getLayoutId(): Int = R.layout.activity_add_short_feeder
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        oil_card_first_ed.filters = arrayOf<InputFilter>(MoneyInputFilter())
        oil_card_second_ed.filters = arrayOf<InputFilter>(MoneyInputFilter())
        oil_card_third_ed.filters = arrayOf<InputFilter>(MoneyInputFilter())
    }

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getDepartureBatchNumber()
    }

    fun saveCarInfo() {
        if (number_plate_tv.text.toString().isEmpty()) {
            showToast("请选择车牌号")
            return
        }
        if (driver_name_ed.text.toString().isEmpty()) {
            showToast("请输入司机姓名")
            return
        }
        if (contact_number_ed.text.toString().isEmpty()) {
            showToast("请输入司机联系电话")
            return
        }
        if (destination_tv.text.toString().isEmpty()) {
            showToast("请选择到货网点")
            return
        }
        val obj = JSONObject()
        obj.put("InoneVehicleFlag", contract_No_tv.text.toString())//**
        obj.put("ContractNo", contract_No_tv.text.toString())//
        obj.put("EcompanyId", mECompanyId)// 到车公司编码
        obj.put("ewebidCode", mWebCodeId)// 到车网点编码**
        obj.put("Transneed", mTransneed)// 运输类型编码**
        obj.put("TransneedStr", mTransneedStr)// 运输类型**
        obj.put("AccNow", cash_freight_ed.text.toString())// 现付**
        obj.put("AccBack", return_freight_ed.text.toString())// 回付**
        obj.put("AccYk", cash_card_ed.text.toString())// 油费
        obj.put("YkCard", oil_card_number_ed.text.toString())// 油卡
        obj.put("EwebidCode1", mFirstEwebidCode)// 到付网点1
        obj.put("EwebidCodeStr1", oil_card_first_tv.text.toString())// 到付网点1**
        obj.put("AccArrived1", oil_card_first_ed.text.toString())// 到付金额**
        obj.put("EwebidCode2", mSencondEwebidCode)// 到付网点2
        obj.put("EwebidCodeStr2", oil_card_second_tv.text.toString())// 到付网点2**
        obj.put("AccArrived2", oil_card_second_ed.text.toString())// 到付金额2**
        obj.put("EwebidCode3", mThridEwebidCode)// 到付网点3
        obj.put("EwebidCodeStr3", oil_card_third_tv.text.toString())// 到付网点3**
        obj.put("AccArrived3", oil_card_third_ed.text.toString())// 到付金额3**
        obj.put("AccZx", loading_fee_ed.text.toString())// 装卸费
        obj.put("AccJh", 0)// 接货费
        obj.put("AccTansSum", total_freight_tv.text.toString())// 运费合计**
        obj.put("isScan", if (choice_scan_checkBox.isChecked) "1" else "0")// 是否扫描发车
        obj.put("AccArrSum", mToPayTotalPrice)// 到付合计
        obj.put("AccOther", 0)// 其它费用
        obj.put("VehicleInterval", UserInformationUtil.getWebIdCodeStr(mContext) + "-" + destination_tv.text.toString())// 发车区间  A-B
        obj.put("Remark", "")// 备注

        obj.put("SendDate", TimeUtils.getCurrTime2())// 发车日期**
        obj.put("VehicleNo", number_plate_tv.text.toString())// 车牌号**
        obj.put("Chauffer", driver_name_ed.text.toString())// 司机**
        obj.put("ChaufferMb", contact_number_ed.text.toString())// 司机手机号码**
        obj.put("ChaufferMb", contact_number_ed.text.toString())// 司机手机号码
        obj.put("ewebidCodeStr", destination_tv.text.toString())// 到车网点**
        if (on_board_weight_tv.text.toString().replace("吨", "").toDoubleOrNull() != null)
            obj.put("MaximumVehicleWeight", on_board_weight_tv.text.toString().replace("吨", "").toDouble() * 1000)// 车载最大重量
        obj.put("SendOpeMan", UserInformationUtil.getUserName(mContext))// 发车操作人
        obj.put("WebidCode", UserInformationUtil.getWebIdCode(mContext))// 发车网点编码**
        obj.put("WebidCodeStr", UserInformationUtil.getWebIdCodeStr(mContext))// 发车网点**
        obj.put("vehicleShape", mVehicleShape)//车辆用途种类**
        val json = GsonUtils.toPrettyFormat(obj.toString())
        ARouter.getInstance().build(ARouterConstants.ShortFeederHouseActivity).withString("ShortFeederHouse", json).navigation(this,43)
        this.finish()
    }

    override fun onClick() {
        super.onClick()
        next_step_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                saveCarInfo()
            }

        })

        cash_freight_ll.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                showHideCashFreight()
            }

        })
        freight_onarrival_ll.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                showHideFreightOnArrival()

            }

        })
        number_plate_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mPresenter?.getVehicles()
            }

        })

    }


    override fun getDepartureBatchNumberS(inOneVehicleFlag: String) {
        if (inOneVehicleFlag.isBlank()) {
            TalkSureDialog(mContext, getScreenWidth(), "获取合同编号失败 点击确认退出") {
                onBackPressed()
            }.show()
            return
        }
        contract_No_tv.text = inOneVehicleFlag
    }

    var mVehicleShape = ""
    override fun getVehicleS(result: String) {
        FilterDialog(getScreenWidth(), result, "vehicleno", "选择车牌号", true, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            @SuppressLint("SetTextI18n")
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val mSelectData = Gson().fromJson<NumberPlateBean>(mResult, NumberPlateBean::class.java)
                number_plate_tv.text = mSelectData.vehicleno
                driver_name_ed.setText(mSelectData.chauffer)
                contact_number_ed.setText(mSelectData.chauffermb)
                vehicle_type_tv.text = if (mSelectData.vehicletype == 1) "大车" else if (mSelectData.vehicletype == 2) "小车" else "未知车型"
                on_board_weight_tv.text = "${mSelectData.supweight}吨"
                mVehicleShape=mSelectData.vehicleshape
            }

        }).show(supportFragmentManager, "getVehicleSFilterDialog")
    }


}