package com.mbcq.vehicleslibrary.activity.fixedshortfeederconfiguration


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.util.system.TimeUtils
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.Constant
import com.mbcq.commonlibrary.NumberPlateBean
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterDialog
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.fixedscanshortfeederconfiguration.FixedScanShortFeederConfigurationBean
import kotlinx.android.synthetic.main.activity_fixed_short_feeder_configuration.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-12-21 14:42:34 修改短驳配置信息
 */

@Route(path = ARouterConstants.FixedShortFeederConfigurationActivity)
class FixedShortFeederConfigurationActivity : BaseFixedShortFeederConfigurationActivity<FixedShortFeederConfigurationContract.View, FixedShortFeederConfigurationPresenter>(), FixedShortFeederConfigurationContract.View {
    /**
     * 发车批次号
     */
    @Autowired(name = "FixedShortFeederConfiguration")
    @JvmField
    var mLastDataNo: String = ""

    override fun getLayoutId(): Int = R.layout.activity_fixed_short_feeder_configuration
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getCarInfo(JSONObject(mLastDataNo).optString("InoneVehicleFlag"))
    }

    fun saveCarInfoTrunk() {
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
        obj.put("id", mFixedId)// 修改的id
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
        obj.put("fromType", Constant.ANDROID)//
        obj.put("fromtypeStr", Constant.ANDROID_STR)
        obj.put("commonStr", "0")
        mPresenter?.saveInfo(GsonUtils.toPrettyFormat(obj))

    }

    override fun onClick() {
        super.onClick()
        commit_btn.apply {
            onSingleClicks {
                saveCarInfoTrunk()
            }
        }
        cash_freight_ll.apply {
            onSingleClicks {
                showHideCashFreight()
            }
        }
        freight_onarrival_ll.apply {
            onSingleClicks {
                showHideFreightOnArrival()
            }
        }
        number_plate_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mPresenter?.getVehicles()
            }

        })
        oil_card_first_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                getDbWebId(object : WebDbInterface {
                    override fun isNull() {
                    }

                    override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                        geDeliveryPointLocal(list, 1)
                    }

                })

            }

        })
        oil_card_second_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                getDbWebId(object : WebDbInterface {
                    override fun isNull() {
                    }

                    override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                        geDeliveryPointLocal(list, 2)
                    }

                })
            }

        })
        oil_card_third_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                getDbWebId(object : WebDbInterface {
                    override fun isNull() {
                    }

                    override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                        geDeliveryPointLocal(list, 3)
                    }

                })
            }

        })
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

    override fun getCarInfoS(data: FixedScanShortFeederConfigurationBean) {
        data.id?.let {
            mFixedId = data.id.toInt()
        }
        data.inoneVehicleFlag?.let {
            contract_No_tv.text = data.inoneVehicleFlag
        }
        number_plate_tv.text = getBeanString(data.vehicleNo)
        destination_tv.text = getBeanString(data.ewebidCodeStr)
        oil_card_first_tv.text =  getBeanString(data.ewebidCodeStr1)
        oil_card_second_tv.text = getBeanString( data.ewebidCodeStr2)
        oil_card_third_tv.text = getBeanString( data.ewebidCodeStr3)
        total_freight_tv.text = getBeanString(data.accTansSum)// 运费合计
        mWebCodeId =  getBeanString(data.ewebidCode)
        mFirstEwebidCode = getBeanString(data.ewebidCode1)
        mSencondEwebidCode = getBeanString(data.ewebidCode2)
        mThridEwebidCode = getBeanString(data.ewebidCode3)
        mToPayTotalPrice = getBeanString(data.accArrSum).toDouble()// 到付合计
        driver_name_ed.setText(getBeanString(data.chauffer))
        contact_number_ed.setText(getBeanString(data.chaufferMb))
        oil_card_first_ed.setText(getBeanString(data.accArrived1))
        oil_card_second_ed.setText(getBeanString(data.accArrived2))
        oil_card_third_ed.setText(getBeanString(data.accArrived3))
        cash_freight_ed.setText(getBeanString(data.accNow))// 现付
        return_freight_ed.setText(getBeanString(data.accBack))// 回付
        cash_card_ed.setText(getBeanString(data.accYk))// 油费
        oil_card_number_ed.setText(getBeanString(data.ykCard))// 油卡
        loading_fee_ed.setText(getBeanString(data.accZx))// 装卸费

        /**
         * 普运  马帮快线 补发数据
         */
        mode_transport_rg.check(if (getBeanString(data.transneedStr )== "普运") 0 else if (getBeanString(data.transneedStr )== "马帮快线") 1 else 2)
        mTransneedStr = getBeanString(data.transneedStr)
        mPresenter?.geSelectVehicles(getBeanString(data.vehicleNo), getBeanString(data.chaufferMb))

    }

    @SuppressLint("SetTextI18n")
    override fun geSelectVehicleS(result: String, vehicleNo: String, chaufferMb: String) {
        val mAllData = Gson().fromJson<List<NumberPlateBean>>(result, object : TypeToken<List<NumberPlateBean>>() {}.type)
        for (item in mAllData) {
            if (item.vehicleno == vehicleNo && item.chauffermb == chaufferMb) {
                vehicle_type_tv.text = if (item.vehicletype == 1) "大车" else if (item.vehicletype == 2) "小车" else "未知车型"
                on_board_weight_tv.text = "${item.supweight}吨"
            }

        }
    }

    override fun saveInfoS(result: String) {
        TalkSureDialog(mContext, getScreenWidth(), "发车批次为${JSONObject(mLastDataNo).optString("InoneVehicleFlag")} 已经重新配置成功，点击返回！") {
            onBackPressed()
        }.show()

    }
}