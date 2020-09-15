package com.mbcq.orderlibrary.activity.acceptbilling


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.system.TimeUtils
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.RadioGroupUtil
import com.mbcq.commonlibrary.adapter.BaseEditTextAdapterBean
import com.mbcq.commonlibrary.adapter.EditTextAdapter
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterDialog
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_accept_billing.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.StringBuilder


/**
 * @author: lzy
 * @time: 2018.08.25login
 * 受理开单
 */

@Route(path = ARouterConstants.AcceptBillingActivity)
class AcceptBillingActivity : BaseAcceptBillingActivity<AcceptBillingContract.View, AcceptBillingPresenter>(), AcceptBillingContract.View {

    override fun getLayoutId(): Int = R.layout.activity_accept_billing

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getWaybillNumber()
        mPresenter?.getPaymentMode()
        mPresenter?.getTransportMode()
        mPresenter?.getCostInformation(UserInformationUtil.getWebIdCode(mContext))

        bank_number_ed.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (bank_number_ed.text.toString().isNotEmpty()) {
                    mPresenter?.getCardNumberCondition(bank_number_ed.text.toString())
                }
            }
        }
    }


    /**
     * ARouter 度娘
     * {"name":"xxxx","phone":"15999999999","address":"1111"}
     */
    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_DATA_CODE) {
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
        } else if (requestCode == RECEIVER_RESULT_DATA_CODE) {
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

    override fun onClick() {
        super.onClick()
        save_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (isCanSaveAcctBilling())
                    saveAcctBilling()
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
        receipt_requirements_name_tv.setOnClickListener(object : SingleClick() {
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
        add_shipper_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                ARouter.getInstance().build(ARouterConstants.AddShipperActivity).navigation(this@AcceptBillingActivity, RESULT_DATA_CODE)

            }

        })
        add_receiver_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                ARouter.getInstance().build(ARouterConstants.AddReceiverActivity).navigation(this@AcceptBillingActivity, RECEIVER_RESULT_DATA_CODE)

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
                getDbWebId(object : WebDbInterface {
                    override fun isNull() {
                        mPresenter?.getWebAreaId()
                    }

                    override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                        showWebIdDialog(list)
                    }

                })
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
        location_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                getLocation()
            }

        })

        waybill_number_handwriting_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                waybillNumber(true)
            }

        })
        waybill_number_machine_printed_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                waybillNumber(false)
            }

        })

        cancel_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }


    private fun saveAcctBilling() {
        val jsonObj = JsonObject()
        //到货公司编码
        val ECompanyId = eCompanyId
        jsonObj.addProperty("ECompanyId", ECompanyId)

        //目的地
        val Destination = destinationt
        jsonObj.addProperty("Destination", Destination)

        //订单号
        val OrderId = ""
        jsonObj.addProperty("OrderId", OrderId)

        //发货网点
        val WebidCodeStr = UserInformationUtil.getWebIdCodeStr(mContext)
        jsonObj.addProperty("WebidCodeStr", WebidCodeStr)

        //发货网点编码
        val WebidCode = UserInformationUtil.getWebIdCode(mContext)
        jsonObj.addProperty("WebidCode", WebidCode)


        //运单号
        val Billno = waybill_number_ed.text.toString()
        jsonObj.addProperty("Billno", Billno)


        //到货网点
        val EwebidCodeStr = endWebIdCodeStr
        jsonObj.addProperty("EwebidCodeStr", EwebidCodeStr)

        //到货网点编码
        val EwebidCode = endWebIdCode
        jsonObj.addProperty("EwebidCode", EwebidCode)

        //原单号
        val OBillno = ""
        jsonObj.addProperty("OBillno", OBillno)


        //开单日期
        val BillDate = TimeUtils.getCurrent()
        jsonObj.addProperty("BillDate", BillDate)


        //运单状态编码
        val BillState = ""
        jsonObj.addProperty("BillState", BillState)


        //运单类型 billTypeStr
        val BillTypeStr = waybillNumberTag
        jsonObj.addProperty("BillTypeStr", BillTypeStr)

        //付货方式编码
        val OkProcess = ""
        jsonObj.addProperty("OkProcess", OkProcess)

        //付货方式
        val OkProcessStr = okProcessStrTag
        jsonObj.addProperty("OkProcessStr", OkProcessStr)

        //是否上门提货编码
        val IsTalkGoods = if (isTalkGoodsStrTag) "1" else "0"
        jsonObj.addProperty("IsTalkGoods", IsTalkGoods)

        //是否上门提货
        val IsTalkGoodsStr = if (isTalkGoodsStrTag) "是" else "否"
        jsonObj.addProperty("IsTalkGoodsStr", IsTalkGoodsStr)

        //会员卡号
        val VipId = bank_number_ed.text.toString()
        jsonObj.addProperty("VipId", VipId)


        /**
         * 发货人信息
         */
        val ShipperId = mShipperId//发货客户编号
        jsonObj.addProperty("ShipperId", ShipperId)

        val ShipperMb = mShipperMb //发货人手机号
        jsonObj.addProperty("ShipperMb", ShipperMb)

        val ShipperTel = mShipperTel //发货人固定电话
        jsonObj.addProperty("ShipperTel", ShipperTel)

        val Shipper = mShipper //发货人
        jsonObj.addProperty("Shipper", Shipper)

        val ShipperCid = mShipperCid //发货人身份证号
        jsonObj.addProperty("ShipperCid", ShipperCid)

        val ShipperAddr = mShipperAddr //发货人地址
        jsonObj.addProperty("ShipperAddr", ShipperAddr)

        val IsUrgent = "0" //是否急货编码
        jsonObj.addProperty("IsUrgent", IsUrgent)

        val IsUrgentStr = "否" //是否急货
        jsonObj.addProperty("IsUrgentStr", IsUrgentStr)

        val Transneed = mTransneed  //运输类型编码
        jsonObj.addProperty("Transneed", Transneed)

        val TransneedStr = mTransneedStr //运输类型
        jsonObj.addProperty("TransneedStr", TransneedStr)


        /**
         * 收货人信息
         */
        val ConsigneeMb = mConsigneeMb //收货人手机号
        jsonObj.addProperty("ConsigneeMb", ConsigneeMb)

        val ConsigneeTel = mConsigneeTel //收货人固定电话
        jsonObj.addProperty("ConsigneeTel", ConsigneeTel)

        val Consignee = mConsignee //收货人
        jsonObj.addProperty("Consignee", Consignee)

        val ConsigneeAddr = mConsigneeAddr //收货人地址
        jsonObj.addProperty("ConsigneeAddr", ConsigneeAddr)


        //货物名称
        val Product = cargo_name_ed.text.toString()
        jsonObj.addProperty("Product", Product)

        //总件数
        val TotalQty = ""
        jsonObj.addProperty("TotalQty", TotalQty)


        //件数
        val Qty = numbers_name_ed.text.toString()
        jsonObj.addProperty("Qty", Qty)

        //货号 运单号后五位+件数
        val GoodsNum = Billno.substring(Billno.length - 5) + "-" + Qty
        jsonObj.addProperty("GoodsNum", GoodsNum)

        //包装方式
        val Packages = package_name_ed.text.toString()
        jsonObj.addProperty("Packages", Packages)


        //重量
        val Weight = weight_name_ed.text.toString()
        jsonObj.addProperty("Weight", Weight)

        //体积
        val Volumn = volume_name_tv.text.toString()
        jsonObj.addProperty("Volumn", Volumn)

        //合计金额
        val AccSum = total_amount_tv.text.toString()
        jsonObj.addProperty("AccSum", AccSum)
        //付款方式编码
        val AccType = mAccType
        jsonObj.addProperty("AccType", AccType)
        //付款方式
        val AccTypeStr = mAccTypeStr
        jsonObj.addProperty("AccTypeStr", AccTypeStr)
        //回单要求
        val BackQty = receipt_requirements_name_tv.text.toString()
        jsonObj.addProperty("BackQty", BackQty)
        //是否等通知放货
        val IsWaitNoticeStr = if (wait_notice_check.isChecked) "是" else "否"
        jsonObj.addProperty("IsWaitNoticeStr", IsWaitNoticeStr)
        //是否等通知放货编码
        val IsWaitNotice = if (wait_notice_check.isChecked) "1" else "0"
        jsonObj.addProperty("IsWaitNotice", IsWaitNotice)

        //银行卡号
        val BankCode = bank_number_tv.text.toString()
        jsonObj.addProperty("BankCode", BankCode)

        //开户行
        val BankName = account_bank_tv.text.toString()
        jsonObj.addProperty("BankName", BankName)
        //开户名
        val BankMan = account_names_tv.text.toString()
        jsonObj.addProperty("BankMan", BankMan)

        //制单人
        val CreateMan = UserInformationUtil.getUserName(mContext)
        jsonObj.addProperty("CreateMan", CreateMan)

        //备注
        val Remark = remarks_tv.text.toString()
        jsonObj.addProperty("Remark", Remark)

        //设备端 3代表android
        val FromType = "3"
        jsonObj.addProperty("FromType", FromType)
        /**
         * 费用的所有添加
         */
        mEditTextAdapter?.getData()?.let {
            for (item in it) {
                jsonObj.addProperty(item.tag, item.inputStr)

            }
        }
        mPresenter?.saveAcceptBilling(jsonObj)


    }

    override fun getWaybillNumberS(result: String) {
        waybill_number_ed.setText(result)

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

    override fun getPackageS(result: String) {
        FilterDialog(getScreenWidth(), result, "packages", "包装", false, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val mSelectData = Gson().fromJson<AcceptPackageBean>(mResult, AcceptPackageBean::class.java)
                package_name_ed.setText(mSelectData.packages)
            }

        }).show(supportFragmentManager, "getPackagesFilterDialog")

    }

    override fun getCardNumberConditionS(result: String) {
        val mAcceptMemberInfoBean = Gson().fromJson<AcceptMemberInfoBean>(result, AcceptMemberInfoBean::class.java)
        if (mAcceptMemberInfoBean != null) {
            account_names_tv.text = mAcceptMemberInfoBean.shipper
            mPresenter?.getBankCardInfo(mAcceptMemberInfoBean.vipBankId)
        }
    }

    /**
     * 清空输入会员卡号的信息
     */
    override fun getCardNumberConditionNull() {
        account_names_tv.text = ""
        account_bank_tv.text = ""
        bank_number_tv.text = ""
    }

    override fun getBankCardInfoS(result: String) {
        val dataslist = Gson().fromJson<List<AcceptBankInfoBean>>(result, object : TypeToken<List<AcceptBankInfoBean>>() {}.type)
        if (dataslist.isNullOrEmpty()) return
        account_bank_tv.text = dataslist[0].bankName
        bank_number_tv.text = dataslist[0].bankNumber

    }

    override fun getReceiptRequirementS(result: String) {
        FilterDialog(getScreenWidth(), result, "tdescribe", "回单要求", false, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val mSelectData = Gson().fromJson<AcceptReceiptRequirementBean>(mResult, AcceptReceiptRequirementBean::class.java)
                receipt_requirements_name_tv.text = mSelectData.tdescribe
            }

        }).show(supportFragmentManager, "getReceiptRequirementSFilterDialog")
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

    var mEditTextAdapter: EditTextAdapter<BaseEditTextAdapterBean>? = null
    override fun getCostInformationS(result: String) {
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
        for (mIndex in mShowCostStr.indices) {
            val mBaseEditTextAdapterBean = BaseEditTextAdapterBean()
            mBaseEditTextAdapterBean.title = mShowCostStr[mIndex]
            mBaseEditTextAdapterBean.tag = mShowCostFilNam[mIndex]
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

    override fun saveAcceptBillingS(result: String) {
        TalkSureDialog(mContext, getScreenWidth(), result) {
            onBackPressed()
        }.show()

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

}