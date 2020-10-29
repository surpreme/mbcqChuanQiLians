package com.mbcq.orderlibrary.activity.fixedacceptbilling


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.system.TimeUtils
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.*
import com.mbcq.commonlibrary.adapter.BaseEditTextAdapterBean
import com.mbcq.commonlibrary.adapter.EditTextAdapter
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterDialog
import com.mbcq.orderlibrary.R
import com.mbcq.orderlibrary.activity.acceptbilling.*
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.activity_accept_billing.*
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.*
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.account_bank_tv
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.account_names_tv
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.add_receiver_iv
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.add_receiver_tv
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.add_shipper_iv
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.add_shipper_tv
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.arrive_outlet_tv
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.bank_number_ed
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.bank_number_tv
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.cargo_name_down_iv
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.cargo_name_ed
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.cost_information_recycler
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.customer_mention_tv
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.destinationt_tv
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.get_delivery_home_tv
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.get_delivery_mention_tv
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.get_driver_direct_tv
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.home_delivery_tv
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.location_tv
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.numbers_name_ed
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.package_name_down_iv
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.package_name_ed
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.pay_way_title_rg
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.receipt_requirements_name_down_iv
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.receipt_requirements_name_tv
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.remarks_tv
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.save_btn
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.total_amount_tv
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.transport_method_rg
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.volume_name_tv
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.wait_notice_check
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.waybill_number_ed
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.weight_name_ed
import org.json.JSONArray
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-10-17 13:32:00 改单申请
 */

@Route(path = ARouterConstants.FixedAcceptBillingActivity)
class FixedAcceptBillingActivity : BaseFixedAcceptBillingActivity<FixedAcceptBillingActivityContract.View, FixedAcceptBillingActivityPresenter>(), FixedAcceptBillingActivityContract.View {

    override fun getLayoutId(): Int = R.layout.activity_fixed_accept_billing_activity

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getPaymentMode()
        mPresenter?.getTransportMode()

    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Constant.GPS_REQUEST_CODE -> {
                //做需要做的事情，比如再次检测是否打开GPS了 或者定位
                getLocation()
            }
            RESULT_DATA_CODE -> {
                (data?.getStringExtra("AddShipperResultData"))?.let {
                    val mDatas = JSONObject(it)
                    mShipperMb = mDatas.optString("phone")
                    mShipper = mDatas.optString("name")
                    mShipperAddr = mDatas.optString("address")
                    mShipperTel = mDatas.optString("shipperTel")
                    mShipperCid = mDatas.optString("shipperCid")
                    mShipperId = mDatas.optString("shipperId")
                    add_shipper_tv.text = "$mShipper $mShipperMb \n$mShipperAddr "


                }
            }
            RECEIVER_RESULT_DATA_CODE -> {
                (data?.getStringExtra("AddReceiveResultData"))?.let {
                    val mDatas = JSONObject(it)
                    mConsigneeMb = mDatas.optString("phone")//收货人手机号
                    mConsigneeTel = mDatas.optString("consigneeTel")//收货人固定电话
                    mConsignee = mDatas.optString("name")//收货人
                    mConsigneeAddr = mDatas.optString("address")//收货人地址
                    add_receiver_tv.text = "$mConsignee $mConsigneeMb \n$mConsigneeAddr "


                }
            }
        }
    }

    private fun saveAcctBilling() {
        val jsonObj = JSONObject()
        //id
        val Id = mSearchaId
        jsonObj.put("Id", Id)
        //到货公司编码
        val ECompanyId = eCompanyId
        jsonObj.put("ECompanyId", ECompanyId)

        //目的地
        val Destination = destinationt
        jsonObj.put("Destination", Destination)

        //订单号
        val OrderId = ""
        jsonObj.put("OrderId", OrderId)

        //发货网点
        val WebidCodeStr = UserInformationUtil.getWebIdCodeStr(mContext)
        jsonObj.put("WebidCodeStr", WebidCodeStr)

        //发货网点编码
        val WebidCode = UserInformationUtil.getWebIdCode(mContext)
        jsonObj.put("WebidCode", WebidCode)


        //运单号
        val Billno = waybill_number_ed.text.toString()
        jsonObj.put("Billno", Billno)


        //到货网点
        val EwebidCodeStr = endWebIdCodeStr
        jsonObj.put("EwebidCodeStr", EwebidCodeStr)

        //到货网点编码
        val EwebidCode = endWebIdCode
        jsonObj.put("EwebidCode", EwebidCode)

        //原单号
        val OBillno = ""
        jsonObj.put("OBillno", OBillno)


        //开单日期
        /*val BillDate = TimeUtils.getCurrent()
        jsonObj.put("BillDate", BillDate)*/


        //运单状态编码
        val BillState = ""
        jsonObj.put("BillState", BillState)


        //运单类型 billTypeStr
        val BillTypeStr = waybillNumberTag
        jsonObj.put("BillTypeStr", BillTypeStr)

        //付货方式编码
        val OkProcess = okProcessStrTagIndex
        jsonObj.put("OkProcess", OkProcess)

        //付货方式
        val OkProcessStr = okProcessStrTag
        jsonObj.put("OkProcessStr", OkProcessStr)

        //是否上门提货编码
        val IsTalkGoods = if (isTalkGoodsStrTag) "1" else "0"
        jsonObj.put("IsTalkGoods", IsTalkGoods)

        //是否上门提货
        val IsTalkGoodsStr = if (isTalkGoodsStrTag) "是" else "否"
        jsonObj.put("IsTalkGoodsStr", IsTalkGoodsStr)

        //会员卡号
        val VipId = bank_number_ed.text.toString()
        jsonObj.put("VipId", VipId)


        /**
         * 发货人信息
         * mShipperId
         * TODO
         */
        val ShipperId = ""//发货客户编号
        jsonObj.put("ShipperId", ShipperId)

        val ShipperMb = mShipperMb //发货人手机号
        jsonObj.put("ShipperMb", ShipperMb)

        val ShipperTel = mShipperTel //发货人固定电话
        jsonObj.put("ShipperTel", ShipperTel)

        val Shipper = mShipper //发货人
        jsonObj.put("Shipper", Shipper)

        val ShipperCid = mShipperCid //发货人身份证号
        jsonObj.put("ShipperCid", ShipperCid)

        val ShipperAddr = mShipperAddr //发货人地址
        jsonObj.put("ShipperAddr", ShipperAddr)

        val IsUrgent = "0" //是否急货编码
        jsonObj.put("IsUrgent", IsUrgent)

        val IsUrgentStr = "否" //是否急货
        jsonObj.put("IsUrgentStr", IsUrgentStr)

        val Transneed = mTransneed  //运输类型编码
        jsonObj.put("Transneed", Transneed)

        val TransneedStr = mTransneedStr //运输类型
        jsonObj.put("TransneedStr", TransneedStr)


        /**
         * 收货人信息
         */
        val ConsigneeMb = mConsigneeMb //收货人手机号
        jsonObj.put("ConsigneeMb", ConsigneeMb)

        val ConsigneeTel = mConsigneeTel //收货人固定电话
        jsonObj.put("ConsigneeTel", ConsigneeTel)

        val Consignee = mConsignee //收货人
        jsonObj.put("Consignee", Consignee)

        val ConsigneeAddr = mConsigneeAddr //收货人地址
        jsonObj.put("ConsigneeAddr", ConsigneeAddr)


        //合计金额
        val AccSum = total_amount_tv.text.toString()
        jsonObj.put("AccSum", AccSum)
        //付款方式编码
        val AccType = mAccType
        jsonObj.put("AccType", AccType)
        //付款方式
        val AccTypeStr = mAccTypeStr
        jsonObj.put("AccTypeStr", AccTypeStr)
        //回单要求
        val BackQty = receipt_requirements_name_tv.text.toString()
        jsonObj.put("BackQty", BackQty)
        //是否等通知放货
        val IsWaitNoticeStr = if (wait_notice_check.isChecked) "是" else "否"
        jsonObj.put("IsWaitNoticeStr", IsWaitNoticeStr)
        //是否等通知放货编码
        val IsWaitNotice = if (wait_notice_check.isChecked) "1" else "0"
        jsonObj.put("IsWaitNotice", IsWaitNotice)

        //银行卡号
        val BankCode = bank_number_tv.text.toString()
        jsonObj.put("BankCode", BankCode)

        //开户行
        val BankName = account_bank_tv.text.toString()
        jsonObj.put("BankName", BankName)
        //开户名
        val BankMan = account_names_tv.text.toString()
        jsonObj.put("BankMan", BankMan)

        //制单人
        val CreateMan = UserInformationUtil.getUserName(mContext)
        jsonObj.put("CreateMan", CreateMan)

        //备注
        val Remark = remarks_tv.text.toString()
        jsonObj.put("Remark", Remark)

        //设备端 3代表android
        val FromType = "3"
        jsonObj.put("FromType", FromType)
        /**
         *
         */
        //运单号对应的货物清单
        val WayGoosLst = JSONArray()
//******************************************************************货物明细 多条货物***********************************************************************
        /**
         * 点击添加货物
         */
        if (mAddGoodsAcceptBillingAdapter.getAllData().isNotEmpty()) {
            //总件数  TODO
            var TotalQty = 0
            for (item in mAddGoodsAcceptBillingAdapter.getAllData()) {
                val testObj = JSONObject()
                //货物名称
                testObj.put("Product", item.product)

                //件数
                testObj.put("Qty", item.qty)
                if (isInteger(item.qty)) {
                    TotalQty += (item.qty).toInt()
                }

                //包装方式
                testObj.put("Packages", item.packages)

                //重量
                testObj.put("Weight", item.weight)

                //体积
                testObj.put("Volumn", item.volumn)

                WayGoosLst.put(testObj)
            }
            jsonObj.put("TotalQty", TotalQty)
            //货号 运单号后五位+件数
            val GoodsNum = Billno.substring(Billno.length - 5) + "-" + TotalQty
            jsonObj.put("GoodsNum", GoodsNum)
        } else {
            /**
             * 不点击默认选择输入框
             */
            if (!isCanCargoInfoAdd()) {
                return
            }
            val testObj = JSONObject()
            //货物名称
            testObj.put("Product", cargo_name_ed.text.toString())

            //件数
            testObj.put("Qty", numbers_name_ed.text.toString())

            //包装方式
            testObj.put("Packages", package_name_ed.text.toString())

            //重量
            testObj.put("Weight", weight_name_ed.text.toString())

            //体积
            testObj.put("Volumn", volume_name_tv.text.toString())

            WayGoosLst.put(testObj)
            //总件数  TODO
            val TotalQty = numbers_name_ed.text.toString()
            jsonObj.put("TotalQty", TotalQty)
            //货号 运单号后五位+件数
            val GoodsNum = Billno.substring(Billno.length - 5) + "-" + TotalQty
            jsonObj.put("GoodsNum", GoodsNum)
        }
//******************************************************************货物展示页面（第一条） 不规范找后台***********************************************************************

        if (mAddGoodsAcceptBillingAdapter.getAllData().isNotEmpty() && numbers_name_ed.text.toString().isBlank()) {
            val ggbb = mAddGoodsAcceptBillingAdapter.getAllData()[0]

            //货物名称
            val Product = ggbb.product
            jsonObj.put("Product", Product)

            //件数
            val Qty = ggbb.qty
            jsonObj.put("Qty", Qty)


            //包装方式
            val Packages = ggbb.packages
            jsonObj.put("Packages", Packages)


            //重量
            val Weight = ggbb.weight
            jsonObj.put("Weight", Weight)

            //体积
            val Volumn = ggbb.volumn
            jsonObj.put("Volumn", Volumn)
        } else {
            //货物名称
            val Product = cargo_name_ed.text.toString()
            jsonObj.put("Product", Product)

            //件数
            val Qty = numbers_name_ed.text.toString()
            jsonObj.put("Qty", Qty)


            //包装方式
            val Packages = package_name_ed.text.toString()
            jsonObj.put("Packages", Packages)


            //重量
            val Weight = weight_name_ed.text.toString()
            jsonObj.put("Weight", Weight)

            //体积
            val Volumn = volume_name_tv.text.toString()
            jsonObj.put("Volumn", Volumn)
        }
        /**
         *
         */
//        WayGoosLst.add(test)
        jsonObj.put("WayGoosLst", WayGoosLst)


        /**
         * 费用的所有添加
         */
        mEditTextAdapter?.getData()?.let {
            for (item in it) {
                jsonObj.put(item.tag, item.inputStr)

            }
        }
        val farthObj = JSONObject()
        farthObj.put("Id", mSearchaId)
        farthObj.put("Billno", waybill_number_ed.text.toString())
        farthObj.put("updateRemark", modify_reason_ed.text.toString())
        farthObj.put("WaybillUpdated", jsonObj)
        mPresenter?.updateData(farthObj)


    }


    override fun onClick() {
        super.onClick()
        save_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (isCanSaveAcctBilling())
                    saveAcctBilling()
            }

        })
        add_shipper_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                ARouter.getInstance().build(ARouterConstants.AddShipperActivity).navigation(this@FixedAcceptBillingActivity, RESULT_DATA_CODE)

            }

        })
        add_receiver_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                ARouter.getInstance().build(ARouterConstants.AddReceiverActivity).navigation(this@FixedAcceptBillingActivity, RECEIVER_RESULT_DATA_CODE)

            }

        })
        location_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                getLocation()
            }

        })

        receipt_requirements_name_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mPresenter?.getReceiptRequirement()
            }

        })
        receipt_requirements_name_down_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mPresenter?.getReceiptRequirement()

            }

        })
        package_name_down_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mPresenter?.getPackage()

            }

        })
        cargo_name_down_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mPresenter?.getCargoAppellation()
            }
        })
        get_delivery_mention_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                initDeliveryMethod(1)
            }

        })
        get_delivery_home_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                initDeliveryMethod(2)
            }

        })
        get_driver_direct_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                initDeliveryMethod(3)
            }

        })
        customer_mention_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                initReceivingMethod(1)
            }

        })
        home_delivery_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                initReceivingMethod(2)
            }

        })
        destinationt_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (endWebIdCode.isEmpty()) {
                    showToast("请先选择到达网点")
                    return
                }
                mPresenter?.getDestination(UserInformationUtil.getWebIdCode(mContext), endWebIdCode)
            }

        })
        arrive_outlet_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                WebDbUtil.getDbWebId(application, object : WebsDbInterface {
                    override fun isNull() {
//                        mPresenter?.getWebAreaId()
                    }

                    override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                        showWebIdDialog(list)
                    }

                })
            }

        })
        waybill_number_search_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (waybill_number_ed.text.toString().isNotBlank()) {
                    if (mAddGoodsAcceptBillingAdapter.getAllData().isNotEmpty())
                        mAddGoodsAcceptBillingAdapter.clearData()
                    mPresenter?.getWillByMoreInfo(waybill_number_ed.text.toString())
                } else {
                    showToast("请输入运单号")
                }
            }

        })

    }

    override fun getReceiptRequirementS(result: String) {
        FilterDialog(getScreenWidth(), result, "tdescribe", "回单要求", false, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val mSelectData = Gson().fromJson<AcceptReceiptRequirementBean>(mResult, AcceptReceiptRequirementBean::class.java)
                receipt_requirements_name_tv.text = mSelectData.tdescribe
            }

        }).show(supportFragmentManager, "getReceiptRequirementSFilterDialog")
    }

    override fun updateDataS() {
        TalkSureDialog(mContext, getScreenWidth(), "修改运单已修改完毕 您可以去运单记录查看详情！") {
            onBackPressed()
        }.show()
    }

    override fun getPackageS(result: String) {
        FilterDialog(getScreenWidth(), result, "packages", "包装", false, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val mSelectData = Gson().fromJson<AcceptPackageBean>(mResult, AcceptPackageBean::class.java)
                package_name_ed.setText(mSelectData.packages)
            }

        }).show(supportFragmentManager, "getPackagesFilterDialog")

    }


    override fun getTransportModeS(result: String) {
        val mTransportModeArray = JSONArray(result)
        /**
         * 默认数据
         */
        if (!mTransportModeArray.isNull(0)) {
            mTransportModeArray.optJSONObject(0)?.let {
                mTransneed = it.optString("typecode")
                mTransneedStr = it.optString("tdescribe")
            }
        }
        /**
         * 添加数据到view
         */
        for (mIndex in 0 until mTransportModeArray.length()) {
            val obj = mTransportModeArray.optJSONObject(mIndex)
            obj?.let {
                transport_method_rg.addView(RadioGroupUtil.addSelectItem(mContext, it.optString("tdescribe"), mIndex))
            }
        }
        transport_method_rg.check(0)
        /**
         * 选中后的操作
         */
        transport_method_rg.setOnCheckedChangeListener { _, checkedId ->
            mTransneed = mTransportModeArray.getJSONObject(checkedId).optString("typecode")
            mTransneedStr = mTransportModeArray.getJSONObject(checkedId).optString("tdescribe")
        }
    }

    override fun getPaymentModeS(result: String) {
        val mPaymentModeArray = JSONArray(result)

        /**
         * 如果有提付默认选中 如果没有提付选中第一个
         */
        var mWithdrawIndex = 0
        /**
         * 添加数据到view
         */
        for (mIndex in 0 until mPaymentModeArray.length()) {
            val obj = mPaymentModeArray.optJSONObject(mIndex)
            obj?.let {
                if (obj.optString("tdescribe") == "提付") {
                    mWithdrawIndex = mIndex
                }
                pay_way_title_rg.addView(RadioGroupUtil.addSelectItem(mContext, it.optString("tdescribe"), mIndex))
            }
        }

        pay_way_title_rg.check(mWithdrawIndex)
        /**
         * 默认数据
         */
        if (!mPaymentModeArray.isNull(mWithdrawIndex)) {
            mPaymentModeArray.optJSONObject(mWithdrawIndex)?.let {
                mAccType = it.optString("typecode")
                mAccTypeStr = it.optString("tdescribe")
            }
        }
        /**
         * 选中后的操作
         */
        pay_way_title_rg.setOnCheckedChangeListener { _, checkedId ->
            mAccType = mPaymentModeArray.getJSONObject(checkedId).optString("typecode")
            mAccTypeStr = mPaymentModeArray.getJSONObject(checkedId).optString("tdescribe")
        }
    }


    private fun showWebIdDialog(list: MutableList<WebAreaDbInfo>) {
        FilterDialog(getScreenWidth(), Gson().toJson(list), "webid", "选择到货网点", true, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                arrive_outlet_tv.text = list[position].webid
                destinationt_tv.text = ""
                destinationt = ""
                endWebIdCode = list[position].webidCode
                endWebIdCodeStr = list[position].webid
                eCompanyId = list[position].companyId
            }

        }).show(supportFragmentManager, "showWebIdDialogFilterDialog")
    }


    @SuppressLint("SetTextI18n")
    override fun getWillByInfoS(data: JSONObject) {
        destinationt = data.optString("destination")//目的地
        destinationt_tv.text = destinationt
        endWebIdCodeStr = data.optString("ewebidCodeStr")//到货网点
        endWebIdCode = data.optString("ewebidCode")//到货网点 编码
        arrive_outlet_tv.text = endWebIdCodeStr
        isTalkGoodsStrTag = data.optString("isTalkGoods") == "1"
        okProcessStrTagIndex = data.optInt("okProcess")//付货方式编码
        okProcessStrTag = data.optString("okProcessStr")
        mTransneed = data.optString("transneed")//运输类型
        mTransneedStr = data.optString("transneedStr")//运输类型
        if (isInteger(data.optString("transneed")))
            transport_method_rg.check(data.optString("transneed").toInt())
        else
            transport_method_rg.check(0)
        bank_number_ed.setText(data.optString("vipId"))
        add_shipper_tv.text = "${data.optString("shipper")} ${data.optString("shipperMb")} \n ${data.optString("shipperAddr")}"//发货人信息
        add_receiver_tv.text = "${data.optString("consignee")} ${data.optString("consigneeMb")} \n ${data.optString("consigneeAddr")}"//收货人信息
        mShipper = data.optString("shipper")//发货人
        mShipperMb = data.optString("shipperMb")//发货人手机号
        mShipperTel = data.optString("shipperTel")//发货人电话
        mShipperAddr = data.optString("shipperAddr")//发货人地址

        mConsignee = data.optString("consignee")//收货人
        mConsigneeMb = data.optString("consigneeMb")//收货人手机号
        mConsigneeAddr = data.optString("consigneeAddr")//收货人地址


        waybillNumberTag = data.optString("billTypeStr")//订单号产生方式
        mSearchaId = data.optString("id")

        initReceivingMethod(if (isTalkGoodsStrTag) 2 else 1)
//        cargo_name_ed.setText(data.optString("product"))
//        numbers_name_ed.setText(data.optString("totalQty"))
//        package_name_ed.setText(data.optString("packages"))
//        weight_name_ed.setText(data.optString("weight"))
//        volume_name_tv.setText(data.optString("volumn"))
        receipt_requirements_name_tv.text = data.optString("backQty")
        account_names_tv.text = data.optString("bankMan")
        account_bank_tv.text = data.optString("bankName")
        bank_number_tv.text = data.optString("bankCode")
        remarks_tv.setText(data.optString("remark"))
        total_amount_tv.text = data.optString("accSum")
        wait_notice_check.isChecked = data.optInt("isWaitNotice", 0) == 1
        data.optJSONArray("WayGoosLst")?.let {
            val mAdapterList = mutableListOf<AddGoodsAcceptBillingBean>()
            for (index in 0 until it.length()) {
                val mAddGoodsAcceptBillingBean = AddGoodsAcceptBillingBean()
                mAddGoodsAcceptBillingBean.product = it.getJSONObject(index).optString("product")
                mAddGoodsAcceptBillingBean.qty = it.getJSONObject(index).optString("qty")
                mAddGoodsAcceptBillingBean.packages = it.getJSONObject(index).optString("packages")
                mAddGoodsAcceptBillingBean.weight = it.getJSONObject(index).optString("weight")
                mAddGoodsAcceptBillingBean.volumn = it.getJSONObject(index).optString("volumn")
                mAdapterList.add(mAddGoodsAcceptBillingBean)
            }
            if (mAdapterList.isNotEmpty())
                mAddGoodsAcceptBillingAdapter.appendData(mAdapterList)

        }
        /**
         * 防止多次查询运单
         */
        mEditTextAdapter?.getData()?.let {
            if (it.isNotEmpty()) {
                mEditTextAdapter?.clearDatas()
            }
        }
        mPresenter?.getCostInformation(UserInformationUtil.getWebIdCode(mContext), data)
    }

    override fun getWillByInfoNull() {
        mSearchaId = ""
        TalkSureDialog(mContext, getScreenWidth(), "未查询到运单信息，请检查后重新查询").show()
    }

    override fun getCostInformationS(result: String, fatherData: JSONObject) {
        val data = JSONObject(result)
        val mShowCostFilNam = data.optString("showCostFilNam").split(",")
        val mShowCostStr = data.optString("showCostStr").split(",")
        /**
         * 后台返回费用信息的判断
         */
        if (mShowCostFilNam.size != mShowCostStr.size) return

        /**
         * 添加数据到recyclerView
         */
        val mKK = mutableListOf<BaseEditTextAdapterBean>()
        val mBasicAccTrans = BaseEditTextAdapterBean()
        mBasicAccTrans.title = "基本运费"
        mBasicAccTrans.tag = "accTrans"
        mBasicAccTrans.inputStr = fatherData.optString("accTrans")

        mKK.add(mBasicAccTrans)
        for (mIndex in mShowCostStr.indices) {
            val mBaseEditTextAdapterBean = BaseEditTextAdapterBean()
            mBaseEditTextAdapterBean.title = mShowCostStr[mIndex]
            mBaseEditTextAdapterBean.tag = mShowCostFilNam[mIndex]
            mBaseEditTextAdapterBean.inputStr = fatherData.optString(mShowCostFilNam[mIndex])
            if (mBaseEditTextAdapterBean.title.isNotBlank())
                mKK.add(mBaseEditTextAdapterBean)

        }
        if (mEditTextAdapter == null)
            mEditTextAdapter = EditTextAdapter<BaseEditTextAdapterBean>(mContext)
        cost_information_recycler.layoutManager = GridLayoutManager(mContext, 2)
        cost_information_recycler.adapter = mEditTextAdapter
        mEditTextAdapter?.appendData(mKK)
        mEditTextAdapter?.mOnToTalInterface = object : EditTextAdapter.OnToTalInterface {
            override fun onItemFoused(v: View, position: Int, result: String, tag: String) {
                mEditTextAdapter?.getData()?.let {
                    var mToal = 0.00
                    //返款不计算
                    for (item in it) {
                        if (item.inputStr.isNotBlank()) {
                            if (item.tag != "accHuiKou") {
                                val mItemPrice = item.inputStr.toDouble()
                                mToal += mItemPrice
                            }

                        }
                    }
                    total_amount_tv.text = haveTwoDouble(mToal)


                }

            }

        }
    }

    override fun getDestinationS(result: String) {
        FilterDialog(getScreenWidth(), result, "mapDes", "选择目的地", true, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val mSelectData = Gson().fromJson<DestinationtBean>(mResult, DestinationtBean::class.java)
                destinationt_tv.text = mSelectData.mapDes
                destinationt = mSelectData.mapDes
            }

        }).show(supportFragmentManager, "getDestinationSFilterDialog")
    }

    override fun getCargoAppellationS(result: String) {
        FilterDialog(getScreenWidth(), result, "product", "货物名称", false, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val mSelectData = Gson().fromJson<CargoAppellationBean>(mResult, CargoAppellationBean::class.java)
                cargo_name_ed.setText(mSelectData.product)
            }

        }).show(supportFragmentManager, "getCargoAppellationSFilterDialog")
    }
}