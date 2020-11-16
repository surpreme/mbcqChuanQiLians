package com.mbcq.vehicleslibrary.activity.addscanshortfeeder


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Config
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.system.PhoneDeviceMsgUtils
import com.mbcq.baselibrary.util.system.TimeUtils
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.Constant
import com.mbcq.commonlibrary.NumberPlateBean
import com.mbcq.commonlibrary.dialog.FilterDialog
import com.mbcq.vehicleslibrary.R
import kotlinx.android.synthetic.main.activity_add_scan_short_feeder.*
import kotlinx.android.synthetic.main.activity_add_scan_short_feeder.cash_card_ed
import kotlinx.android.synthetic.main.activity_add_scan_short_feeder.cash_freight_ed
import kotlinx.android.synthetic.main.activity_add_scan_short_feeder.cash_freight_ll
import kotlinx.android.synthetic.main.activity_add_scan_short_feeder.contact_number_ed
import kotlinx.android.synthetic.main.activity_add_scan_short_feeder.contract_No_tv
import kotlinx.android.synthetic.main.activity_add_scan_short_feeder.destination_tv
import kotlinx.android.synthetic.main.activity_add_scan_short_feeder.driver_name_ed
import kotlinx.android.synthetic.main.activity_add_scan_short_feeder.freight_onarrival_ll
import kotlinx.android.synthetic.main.activity_add_scan_short_feeder.loading_fee_ed
import kotlinx.android.synthetic.main.activity_add_scan_short_feeder.next_step_btn
import kotlinx.android.synthetic.main.activity_add_scan_short_feeder.number_plate_tv
import kotlinx.android.synthetic.main.activity_add_scan_short_feeder.oil_card_first_ed
import kotlinx.android.synthetic.main.activity_add_scan_short_feeder.oil_card_first_tv
import kotlinx.android.synthetic.main.activity_add_scan_short_feeder.oil_card_number_ed
import kotlinx.android.synthetic.main.activity_add_scan_short_feeder.oil_card_second_ed
import kotlinx.android.synthetic.main.activity_add_scan_short_feeder.oil_card_second_tv
import kotlinx.android.synthetic.main.activity_add_scan_short_feeder.oil_card_third_ed
import kotlinx.android.synthetic.main.activity_add_scan_short_feeder.oil_card_third_tv
import kotlinx.android.synthetic.main.activity_add_scan_short_feeder.on_board_weight_tv
import kotlinx.android.synthetic.main.activity_add_scan_short_feeder.return_freight_ed
import kotlinx.android.synthetic.main.activity_add_scan_short_feeder.total_freight_tv
import kotlinx.android.synthetic.main.activity_add_scan_short_feeder.vehicle_type_tv
import kotlinx.android.synthetic.main.activity_add_short_feeder.*
import org.json.JSONArray
import org.json.JSONObject


/**
 * @author: lzy
 * @time: 2020-11-12 17:42:45 无计划短驳装车
 */

@Route(path = ARouterConstants.AddScanShortFeederActivity)
class AddScanShortFeederActivity : BaseAddScanShortFeederActivity<AddScanShortFeederContract.View, AddScanShortFeederPresenter>(), AddScanShortFeederContract.View {
    override fun getLayoutId(): Int = R.layout.activity_add_scan_short_feeder

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
        obj.put("inoneVehicleFlag", contract_No_tv.text.toString())
        obj.put("contractNo", contract_No_tv.text.toString())
        obj.put("ecompanyId", mECompanyId)// 到车公司编码
        obj.put("ewebidCode", mWebCodeId)// 到车网点编码
        obj.put("transneed", mTransneed)// 运输类型编码
        obj.put("transneedStr", mTransneedStr)// 运输类型
        obj.put("accNow", cash_freight_ed.text.toString())// 现付
        obj.put("accBack", return_freight_ed.text.toString())// 回付
        obj.put("accYk", cash_card_ed.text.toString())// 油费
        obj.put("ykCard", oil_card_number_ed.text.toString())// 油卡
        obj.put("ewebidCode1", mFirstEwebidCode)// 到付网点1
        obj.put("ewebidCodeStr1", oil_card_first_tv.text.toString())// 到付网点1
        obj.put("accArrived1", oil_card_first_ed.text.toString())// 到付金额
        obj.put("ewebidCode2", mSencondEwebidCode)// 到付网点2
        obj.put("ewebidCodeStr2", oil_card_second_tv.text.toString())// 到付网点2
        obj.put("accArrived2", oil_card_second_ed.text.toString())// 到付金额2
        obj.put("ewebidCode3", mThridEwebidCode)// 到付网点3
        obj.put("ewebidCodeStr3", oil_card_third_tv.text.toString())// 到付网点3
        obj.put("accArrived3", oil_card_third_ed.text.toString())// 到付金额3
        obj.put("accZx", loading_fee_ed.text.toString())// 装卸费
        obj.put("accJh", 0)// 接货费
        obj.put("accTansSum", total_freight_tv.text.toString())// 运费合计
        obj.put("accArrSum", mToPayTotalPrice)// 到付合计
        obj.put("accOther", 0)// 其它费用
        obj.put("vehicleInterval", UserInformationUtil.getWebIdCodeStr(mContext) + "-" + destination_tv.text.toString())// 发车区间  A-B
        obj.put("remark", "")// 备注
        obj.put("isScan", "2")//  0默认 1有计扫描发车 2无计划扫描
        obj.put("sendDate", TimeUtils.getCurrTime2())// 发车日期
        obj.put("vehicleNo", number_plate_tv.text.toString())// 车牌号
        obj.put("chauffer", driver_name_ed.text.toString())// 司机
        obj.put("chaufferMb", contact_number_ed.text.toString())// 司机手机号码
        obj.put("chaufferMb", contact_number_ed.text.toString())// 司机手机号码
        obj.put("ewebidCodeStr", destination_tv.text.toString())// 到车网点
        obj.put("sendOpeMan", UserInformationUtil.getUserName(mContext))// 发车操作人
        obj.put("webidCode", UserInformationUtil.getWebIdCode(mContext))// 发车网点编码
        obj.put("webidCodeStr", UserInformationUtil.getWebIdCodeStr(mContext))// 发车网点
        obj.put("scanWebidType", mScanType)// 发车网点
        obj.put("fromType", Constant.ANDROID)//
//        val json = GsonUtils.toPrettyFormat(obj.toString())
        //TODO
        val testJay = JSONArray()
        val testObj = JSONObject()
        testObj.put("billno", "0")
        testJay.put(testObj)
        obj.put("dbVehicleDetLst", testJay)
        obj.put("commonStr", "0")
        mPresenter?.saveInfo(obj)
    }

    override fun onClick() {
        super.onClick()
        number_plate_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mPresenter?.getVehicles()
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
        next_step_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                saveCarInfo()

                /*   if (!PhoneDeviceMsgUtils.isApkInDebug(mContext)) {
                       ARouter.getInstance().build(ARouterConstants.RevokeShortTrunkDepartureUnPlanScanOperatingActivity).navigation()

                   } else {

                   }
   */
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
            }

        }).show(supportFragmentManager, "getVehicleSFilterDialog")
    }

    override fun saveInfoS(result: String) {
        ARouter.getInstance().build(ARouterConstants.RevokeShortTrunkDepartureUnPlanScanOperatingActivity).withString("ShortLoadingVehicles", result).navigation()

    }

    override fun modifyS(result: String) {

    }

}