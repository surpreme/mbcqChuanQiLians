package com.mbcq.vehicleslibrary.activity.fixhomedeliveryconfiguration


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.util.system.TimeUtils
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.NumberPlateBean
import com.mbcq.commonlibrary.dialog.FilterDialog
import com.mbcq.commonlibrary.dialog.TimeDialogUtil
import com.mbcq.vehicleslibrary.R
import kotlinx.android.synthetic.main.activity_fix_home_delivery_configuration.*
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * @author: lzy
 * @time: 2021-03-27 17:53:06 修改上门提货配置
 */

@Route(path = ARouterConstants.FixHomeDeliveryConfigurationActivity)
class FixHomeDeliveryConfigurationActivity : BaseMVPActivity<FixHomeDeliveryConfigurationContract.View, FixHomeDeliveryConfigurationPresenter>(), FixHomeDeliveryConfigurationContract.View {
    @Autowired(name = "FixHomeDeliveryConfiguration")
    @JvmField
    var mLastData: String = ""
    override fun getLayoutId(): Int = R.layout.activity_fix_home_delivery_configuration
    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        departure_date_tv.text = TimeUtils.getCurrentYYMMDD()
        showConfiguration()
    }

    private fun showConfiguration() {
        val obj = JSONObject(mLastData)
        remark_ed.setText(obj.optString("remark"))
        departure_date_tv.text = obj.optString("pickUpDate")
        number_plate_tv.text = obj.optString("vehicleNo")
        driver_name_ed.setText(obj.optString("chauffer"))
        contact_number_ed.setText(obj.optString("chaufferTel"))
        departure_lot_tv.text = obj.optString("inOneVehicleFlag")
    }

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getBatch()
    }

    /**
    {"pickUpDate":"2021-3-26","inOneVehicleFlag":"TH1003-20210326-003","vehicleNo":"浙G12370","Chauffer":"张凯","supWeight":"0","VehicleType":"集装箱","ChaufferTel":"16276665366","id":"1411","CommonStr":"10030005497","PickUpdetLst":[{"id":1411,"companyId":2001,"eCompanyId":0,"orderId":"","billno":"10030005497","oBillno":"111","billDate":"2021-03-24T09:58:28","billState":1,"billStateStr":"已入库","billType":0,"billTypeStr":"机打","goodsNum":"05497-111","okProcess":1,"okProcessStr":"自提","isUrgent":0,"isUrgentStr":"","isTalkGoods":1,"isTalkGoodsStr":"上门提货","webidCode":1003,"webidCodeStr":"汕头","ewebidCode":1001,"ewebidCodeStr":"义乌后湖","destination":"义乌后湖","transneed":1,"transneedStr":"零担","vipId":"11","shipperId":"","shipperMb":"15999999999","shipperTel":"123456","shipper":"测试","shipperCid":"","shipperAddr":"发货人地址","consigneeMb":"15999999999","consigneeTel":"654321","consignee":"测试1","consigneeAddr":"收货人地址","product":"物价啊啊啊,,","totalQty":111,"qty":111,"packages":"纸箱,,","weight":11,"volumn":11,"weightJs":2750,"safeMoney":11,"accDaiShou":0,"accHKChange":0,"hkChangeReason":"","sxf":0,"wPrice":1,"vPrice":1,"qtyPrice":1,"accNow":0,"accArrived":111,"accBack":0,"accMonth":0,"accHuoKuanKou":0,"accTrans":111,"accFetch":0,"accPackage":0,"accSend":0,"accGb":5,"accSafe":0,"accRyf":0,"accHuiKou":0,"accSms":0,"accZz":0,"accZx":0,"accCb":0,"accSl":0,"accAz":0,"accFj":0,"accWz":0,"accJc":0,"accSum":116,"accType":2,"accTypeStr":"提付","backQty":"","backState":0,"isWaitNotice":0,"isWaitNoticeStr":"0","bankCode":"","bankName":"","bankMan":"","bankNumber":"","createMan":"","salesMan":"测试","opeMan":"wzj","remark":"111","fromType":0,"isTransferCount":0,"preVehicleNo":"1","valueAddedService":"111","bilThState":0,"bilThStateStr":"","accBackService":0,"Lightandheavy":"轻货","shipperCompany":"发货人公司","consigneeCompany":"收货人公司","consigneeId":null,"shipperId1":""}]}
     */
    fun saveInfo() {
        if (departure_date_tv.text.toString().isBlank()) {
            showToast("请检查您的提货日期！")
            return
        }
        if (number_plate_tv.text.toString().isBlank()) {
            showToast("请选择车牌号！")
            return
        }
        if (driver_name_ed.text.toString().isBlank()) {
            showToast("请输入司机姓名！")
            return
        }
        if (contact_number_ed.text.toString().isBlank()) {
            showToast("请输入司机电话！")
            return
        }
        val obj = JSONObject()
        obj.put("pickUpDate", departure_date_tv.text.toString())
        obj.put("inOneVehicleFlag", departure_lot_tv.text.toString())
        obj.put("vehicleNo", number_plate_tv.text.toString())
        obj.put("Chauffer", driver_name_ed.text.toString())
        obj.put("ChaufferTel", contact_number_ed.text.toString())
        obj.put("remark", remark_ed.text.toString())
        obj.put("supWeight", mSupweight)
        obj.put("VehicleType", mVehicleType)
//        ARouter.getInstance().build(ARouterConstants.HomeDeliveryHouseActivity).withString("HomeDeliveryHouse", GsonUtils.toPrettyFormat(obj)).navigation()
//        finish()
    }

    @SuppressLint("SimpleDateFormat")
    override fun onClick() {
        super.onClick()
        departure_date_tv.apply {
            onSingleClicks {
                TimeDialogUtil.getChoiceTimer(mContext, OnTimeSelectListener { date, _ ->
                    val mDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                    val format: String = mDateFormat.format(date)
                    departure_date_tv.text = format

                }, "选择上门提货时间", isStartCurrentTime = true, isEndCurrentTime = false, isYear = true, isHM = false, isDialog = false).show(departure_date_tv)
            }
        }
        save_btn.apply {
            onSingleClicks {
                saveInfo()
            }
        }
        number_plate_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mPresenter?.getVehicles()

            }

        })
        fix_home_delivery_configuration_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    override fun getBatchS(result: String) {
        if (result.isBlank()) {
            TalkSureDialog(mContext, getScreenWidth(), "获取发车批次失败，请稍后再试") {
                onBackPressed()
            }.show()
            return
        }
        departure_lot_tv.text = result
    }

    var mSupweight = ""
    var mVehicleType = ""
    override fun getVehicleS(result: String) {
        FilterDialog(getScreenWidth(), result, "vehicleno", "选择车牌号", true, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            @SuppressLint("SetTextI18n")
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val mSelectData = Gson().fromJson<NumberPlateBean>(mResult, NumberPlateBean::class.java)
                number_plate_tv.text = mSelectData.vehicleno
                driver_name_ed.setText(mSelectData.chauffer)
                contact_number_ed.setText(mSelectData.chauffermb)
                mSupweight = mSelectData.supweight.toString()
                mVehicleType = mSelectData.vehicleshape.toString()

            }

        }).show(supportFragmentManager, "AddHomeDeliveryVehicleSFilterDialog")
    }
}