package com.mbcq.orderlibrary.activity.acceptbilling


import android.annotation.SuppressLint
import android.content.Intent
import android.os.CountDownTimer
import android.view.Gravity
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.dialog.dialogfragment.LoadingTipsDialogFragment
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.baselibrary.util.system.TimeUtils
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.NumberPlateBean
import com.mbcq.commonlibrary.PrintBlueToothBean
import com.mbcq.commonlibrary.RadioGroupUtil
import com.mbcq.commonlibrary.adapter.BaseEditTextAdapterBean
import com.mbcq.commonlibrary.adapter.EditTextAdapter
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterDialog
import com.mbcq.commonlibrary.dialog.MoreCheckBoxPackageDialog
import com.mbcq.orderlibrary.R
import com.mbcq.orderlibrary.activity.acceptbilling.billingvolumecalculator.BillingVolumeCalculatorDialog
import com.mbcq.orderlibrary.activity.acceptbilling.billingweightcalculator.BillingWeightCalculatorDialog
import com.mbcq.orderlibrary.activity.addreceiver.AddReceiverBean
import com.mbcq.orderlibrary.activity.addshipper.AddShipperBean
import kotlinx.android.synthetic.main.activity_accept_billing.*
import org.json.JSONArray
import org.json.JSONObject


/**
 * @author: lzy
 * @time: 2020-12-08 08:59:12
 * 受理开单
 * @information 逻辑以及网络逻辑层 谨慎修改 随着项目叠加 分层压力
 */

@Route(path = ARouterConstants.AcceptBillingActivity)
class AcceptBillingActivity : BaseBlueToothAcceptBillingActivity<AcceptBillingContract.View, AcceptBillingPresenter>(), AcceptBillingContract.View {

    override fun getLayoutId(): Int = R.layout.activity_accept_billing

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getWaybillNumber()
        mPresenter?.getPaymentMode()
        mPresenter?.getTransportMode()
        mPresenter?.getCostInformation(UserInformationUtil.getWebIdCode(mContext))
        initPeople()
        bank_number_ed.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (bank_number_ed.text.toString().isNotEmpty()) {
                    mPresenter?.getCardNumberCondition(bank_number_ed.text.toString())
                }
            }
        }

    }

    override fun onClick() {
        super.onClick()
        save_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (isCanSaveAcctBilling()) {
                    saveAcctBilling()
                }
            }
        })
        /* receipt_requirements_name_tv.setOnClickListener(object : SingleClick() {
             override fun onSingleClick(v: View?) {
                 mPresenter?.getReceiptRequirement()
             }

         })*/
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

        choice_shipper_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                ARouter.getInstance().build(ARouterConstants.ChoiceShipperActivity).navigation(this@AcceptBillingActivity, RESULT_DATA_CODE)
            }

        })
        choice_receiver_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                ARouter.getInstance().build(ARouterConstants.ChoiceReceiverActivity).navigation(this@AcceptBillingActivity, RECEIVER_RESULT_DATA_CODE)
            }

        })
        destinationt_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                showDestination()
            }

        })
        destination_down_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                showDestination()
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
        arrive_outlet_down_iv.setOnClickListener(object : SingleClick() {
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
        pre_installed_car_number_name_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mPresenter?.getVehicles()
            }

        })
        salesman_name_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mPresenter?.getSalesman()
            }

        })

        location_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                getLocation()
            }

        })


        weight_name_calculation_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (mBillingWeightCalculatorDialog == null) {
                    mBillingWeightCalculatorDialog = BillingWeightCalculatorDialog(getScreenWidth(), object : BillingWeightCalculatorDialog.OnResultInterface {
                        override fun onResult(totalWeight: String) {
                            weight_name_ed.setText(totalWeight)
                        }

                    })
                }
                mBillingWeightCalculatorDialog?.show(supportFragmentManager, "BillingWeightCalculatorDialog")
            }

        })
        volume_name_calculation_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (mBillingVolumeCalculatorDialog == null) {
                    mBillingVolumeCalculatorDialog = BillingVolumeCalculatorDialog(getScreenWidth(), object : BillingVolumeCalculatorDialog.OnResultInterface {
                        override fun onResult(totalVolume: String) {
                            volume_name_ed.setText(totalVolume)
                        }

                    })
                }
                mBillingVolumeCalculatorDialog?.show(supportFragmentManager, "BillingVolumeCalculatorDialog")
            }

        })


    }

    fun showDestination() {
        if (endWebIdCode.isEmpty()) {
            showToast("请先选择到达网点")
            return
        }
        mPresenter?.getDestination(UserInformationUtil.getWebIdCode(mContext), endWebIdCode)
    }


    private fun saveAcctBilling() {
        val jsonObj = JSONObject()
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
        val OBillno = original_order_number_name_ed.text.toString()
        jsonObj.put("OBillno", OBillno)


        //开单日期
        val BillDate = TimeUtils.getCurrent()
        jsonObj.put("BillDate", BillDate)


        //运单状态编码
        val BillState = waybillNumberIndexTag
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
        val ShipperId = shipper_mShipperId_ed.text.toString()//发货客户编号
        jsonObj.put("ShipperId", ShipperId)

        val ShipperMb = shipper_phone_ed.text.toString() //发货人手机号
        jsonObj.put("ShipperMb", ShipperMb)

        val ShipperTel = shipper_mShipperTel_ed.text.toString() //发货人固定电话
        jsonObj.put("ShipperTel", ShipperTel)

        val Shipper = shipper_name_ed.text.toString() //发货人
        jsonObj.put("Shipper", Shipper)

        val ShipperCid = shipper_mShipperCid_ed.text.toString() //发货人身份证号
        jsonObj.put("ShipperCid", ShipperCid)

        val ShipperAddr = shipper_address_ed.text.toString() //发货人地址
        jsonObj.put("ShipperAddr", ShipperAddr)

        //**********************************************

        val IsUrgent = if (urgent_goods_check.isChecked) "1" else "0"//是否急货编码
        jsonObj.put("IsUrgent", IsUrgent)

        val IsUrgentStr = if (urgent_goods_check.isChecked) "急货" else ""  //是否急货 非急货
        jsonObj.put("IsUrgentStr", IsUrgentStr)

        val Transneed = mTransneed  //运输类型编码
        jsonObj.put("Transneed", Transneed)

        val TransneedStr = mTransneedStr //运输类型
        jsonObj.put("TransneedStr", TransneedStr)


        /**
         * 收货人信息
         */
        val ConsigneeMb = receiver_phone_ed.text.toString() //收货人手机号
        jsonObj.put("ConsigneeMb", ConsigneeMb)

        val ConsigneeTel = receiver_mConsigneeTel_ed.text.toString() //收货人固定电话
        jsonObj.put("ConsigneeTel", ConsigneeTel)

        val Consignee = receiver_name_ed.text.toString() //收货人
        jsonObj.put("Consignee", Consignee)

        val ConsigneeAddr = receiver_address_ed.text.toString() //收货人地址
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
        val BackQty = receipt_requirements_name_ed.text.toString()
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
        //业务员
        val SalesMan = salesman_name_tv.text.toString()
        jsonObj.put("SalesMan", SalesMan)
        //预装车号
        val PreVehicleNo = pre_installed_car_number_name_tv.text.toString()
        jsonObj.put("PreVehicleNo", PreVehicleNo)


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
            testObj.put("Volumn", volume_name_ed.text.toString())

            WayGoosLst.put(testObj)
            //总件数  TODO
            val TotalQty = numbers_name_ed.text.toString()
            jsonObj.put("TotalQty", TotalQty)
            //货号 运单号后五位+件数
            val GoodsNum = Billno.substring(Billno.length - 5) + "-" + TotalQty
            jsonObj.put("GoodsNum", GoodsNum)
        }
//******************************************************************货物展示汇总页面***********************************************************************

        if (mAddGoodsAcceptBillingAdapter.getAllData().isNotEmpty() && numbers_name_ed.text.toString().isBlank()) {
            val ggbb = mAddGoodsAcceptBillingAdapter.getAllData()[0]
//            var mXProduct=0.00
            var mXWeight = 0.00
            var mXVolumn = 0.00
            var mXQty = 0
            for (item in mAddGoodsAcceptBillingAdapter.getAllData()) {
                mXWeight += (item.weight).toDouble()
                mXVolumn += (item.volumn).toDouble()
                mXQty += (item.qty).toInt()
            }

            //货物名称
            val Product = ggbb.product
            jsonObj.put("Product", Product)

            //件数
            val Qty = mXQty
            jsonObj.put("Qty", Qty)


            //包装方式
            val Packages = ggbb.packages
            jsonObj.put("Packages", Packages)


            //重量
            val Weight = mXWeight
            jsonObj.put("Weight", Weight)

            //体积
            val Volumn = mXVolumn
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
            val Volumn = volume_name_ed.text.toString()
            jsonObj.put("Volumn", Volumn)
        }
        /**
         *
         */
//        WayGoosLst.add(test)
        jsonObj.put("WayGoosLst", WayGoosLst)
        //打印需要
        val priceObj = JSONObject()

        /**
         * 费用的所有添加
         */
        mEditTextAdapter?.getData()?.let {
            for (item in it) {
                jsonObj.put(item.tag, item.inputStr)
                priceObj.put(item.tag, item.inputStr)

            }
        }
        if (labelcheck.isChecked or waybillcheck.isChecked) {
            loadingTips = "正在打印中....."
        }

        mPresenter?.saveAcceptBilling(jsonObj, GsonUtils.toPrettyFormat(jsonObj.toString()), GsonUtils.toPrettyFormat(priceObj.toString()))

    }

    override fun getWaybillNumberS(result: String) {
        waybill_number_ed.setText(result)

    }

    override fun getDestinationS(result: String) {
        FilterDialog(getScreenWidth(), result, "mapDes", "选择目的地", true, isShowOutSide = true, showTipsTag = "FREQUENTLY_USED_DESTINATIONS", showBarTipsStr = "目的地", mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val mSelectData = Gson().fromJson<DestinationtBean>(mResult, DestinationtBean::class.java)
                destinationt_tv.text = mSelectData.mapDes
                destinationt = mSelectData.mapDes
            }

        }).show(supportFragmentManager, "getDestinationSFilterDialog")
    }

    override fun getCargoAppellationS(result: String) {
        FilterDialog(getScreenWidth(), result, "product", "货物名称", false, isShowOutSide = true, showTipsTag = "COMMON_GOODS_NAME", showBarTipsStr = "货物名称", mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val mSelectData = Gson().fromJson<CargoAppellationBean>(mResult, CargoAppellationBean::class.java)
                cargo_name_ed.setText(mSelectData.product)
            }

        }).show(supportFragmentManager, "getCargoAppellationSFilterDialog")
    }

    var mMoreCheckBoxPackageDialog: MoreCheckBoxPackageDialog? = null
    override fun getPackageS(result: String) {
        if (mMoreCheckBoxPackageDialog == null) {
            mMoreCheckBoxPackageDialog = MoreCheckBoxPackageDialog(getScreenWidth(), "请选择或者输入包装", result, "packages", "", object : OnClickInterface.OnClickInterface {
                override fun onResult(s1: String, s2: String) {
                    package_name_ed.setText(s1)

                }

            })
        }
        mMoreCheckBoxPackageDialog?.show(supportFragmentManager, "getPackagesFilterDialog")

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
                receipt_requirements_name_ed.setText(mSelectData.tdescribe)
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
        /* val mBasicAccTrans = BaseEditTextAdapterBean()
         mBasicAccTrans.title = "基本运费"
         mBasicAccTrans.tag = "accTrans"
         mKK.add(mBasicAccTrans)*/
        for (mIndex in mShowCostStr.indices) {
            val mBaseEditTextAdapterBean = BaseEditTextAdapterBean()
            mBaseEditTextAdapterBean.title = mShowCostStr[mIndex]
            when (mShowCostFilNam[mIndex]) {
                //工本费
                "accGb" -> {
                    mBaseEditTextAdapterBean.inputStr = data.optString("accGb")

                }
                //默认保价费
                "accSafe" -> {
                    mBaseEditTextAdapterBean.inputStr = data.optString("accSafe")

                }
            }
            //

            mBaseEditTextAdapterBean.tag = mShowCostFilNam[mIndex]
            mKK.add(mBaseEditTextAdapterBean)


        }
        val mBasicAccTransX = BaseEditTextAdapterBean()
        mBasicAccTransX.title = "代收货款"
        mBasicAccTransX.tag = "accDaiShou"
        mKK.add(mBasicAccTransX)
        val mBasicAccTrans = BaseEditTextAdapterBean()
        mBasicAccTrans.title = "回单服务费"
        mBasicAccTrans.tag = "accBackService"
        mKK.add(mBasicAccTrans)
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

    override fun saveAcceptBillingS(result: String, printJson: String, priceJson: String) {
        var showTipsStr = result
        if (labelcheck.isChecked or waybillcheck.isChecked) {
            showLoading()
            save_btn.isClickable = false
            try {
                val printAdapter = getZpBluetoothPrinter()
                val jsonObj = JSONObject(printJson)
                if (waybillcheck.isChecked) {
                    val priceObj = JSONObject(priceJson)
                    print_YH_TYD_NEW1(Gson().fromJson(printJson, PrintBlueToothBean::class.java), false, UserInformationUtil.getWebIdCodeStr(mContext), priceObj, printAdapter)
                }
                if (labelcheck.isChecked)
                    printMoreLabel(Gson().fromJson(printJson, PrintBlueToothBean::class.java), jsonObj.getInt("TotalQty"), printAdapter)
                closePrint(printAdapter)
            } catch (e: Exception) {
                LogUtils.e(e)
                showTipsStr += " 您未打开打印机或者其他原因，请到重新连接后进入补打印页面"
            }
            closeLoading()

        }
        TalkSureDialog(mContext, getScreenWidth(), showTipsStr) {
            /**
             * 重启activity
             * recreate()由于这个方法会走缓存 弃用
             */
            finish()
            ARouter.getInstance().build(ARouterConstants.AcceptBillingActivity).navigation()

        }.show()

    }

    override fun getShipperInfoS(result: String) {
        val titleList = mutableListOf<String>("opeMan", "contactMb", "address")
        val startList = mutableListOf<String>("姓名:", "电话:", "地址:")
        FilterDialog(getScreenWidth(), result, titleList, startList, "\n", "选择发货人", false, isShowOutSide = false, gravity = Gravity.CENTER_VERTICAL, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val mAddShipperBean = Gson().fromJson<AddShipperBean>(mResult, AddShipperBean::class.java)
                mAddShipperBean?.let {
                    shipper_address_ed.setText(it.address)
                    shipper_name_ed.setText(it.opeMan)
                    shipper_mShipperId_ed.setText(it.vipId)
                    shipper_phone_ed.setText(it.contactMb)
                    shipper_mShipperTel_ed.setText(it.contactTel)
                    shipper_mShipperCid_ed.setText(it.idCard)
                }

            }

        }).show(supportFragmentManager, "AddShipperActivityFilterDialog")

    }

    override fun getReceiverInfoS(result: String) {
        val titleList = mutableListOf<String>("opeMan", "contactMb", "address")
        val startList = mutableListOf<String>("姓名:", "电话:", "地址:")
        FilterDialog(getScreenWidth(), result, titleList, startList, "\n", "选择收货人", false, isShowOutSide = false, gravity = Gravity.CENTER_VERTICAL, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val mAddReceiverBean = Gson().fromJson<AddReceiverBean>(mResult, AddReceiverBean::class.java)
                mAddReceiverBean?.let {
                    receiver_address_ed.setText(it.address)
                    receiver_name_ed.setText(it.opeMan)
                    receiver_customer_code_tv.setText(it.vipId)
                    receiver_phone_ed.setText(it.contactMb)
                    receiver_mConsigneeTel_ed.setText(it.contactTel)
                    if (it.product.isNotBlank())
                        cargo_name_ed.setText(it.product)
                    if (it.packageX.isNotBlank())
                        package_name_ed.setText(it.packageX)
                }

            }

        }).show(supportFragmentManager, "AddReceiverActivityFilterDialog")
    }

    /**
     * 注 position 已经无法区别
     * 因为常用网点和所有网点是两个recyclerview
     */
    private fun showWebIdDialog(list: MutableList<WebAreaDbInfo>) {
        FilterDialog(getScreenWidth(), Gson().toJson(list), "webid", "选择到货网点", true, isShowOutSide = true, showTipsTag = "RECEIVING_OUTLETS", showBarTipsStr = "网点", mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val mWebAreaDbInfo = Gson().fromJson<WebAreaDbInfo>(mResult, WebAreaDbInfo::class.java)
                arrive_outlet_tv.text = mWebAreaDbInfo.webid
                destinationt_tv.text = ""
                destinationt = ""
                endWebIdCode = mWebAreaDbInfo.webidCode
                endWebIdCodeStr = mWebAreaDbInfo.webid
                eCompanyId = mWebAreaDbInfo.companyId
                object : CountDownTimer(500, 500) {
                    override fun onTick(millisUntilFinished: Long) {

                    }

                    override fun onFinish() {
                        if (!isDestroyed)
                            showDestination()
                    }

                }.start()

            }

        }).show(supportFragmentManager, "showWebIdDialogFilterDialog")
    }

    override fun refreshWaybillNumber() {
        mPresenter?.getWaybillNumber()
    }

    override fun getVehicleS(result: String) {
        FilterDialog(getScreenWidth(), result, "vehicleno", "选择车牌号", true, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            @SuppressLint("SetTextI18n")
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val mSelectData = Gson().fromJson<NumberPlateBean>(mResult, NumberPlateBean::class.java)
                pre_installed_car_number_name_tv.text = mSelectData.vehicleno
            }

        }).show(supportFragmentManager, "getVehicleSFilterDialog")
    }

    override fun getSalesmanS(result: String) {
        FilterDialog(getScreenWidth(), result, "salesmanName", "选择业务员", true, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            @SuppressLint("SetTextI18n")
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val mSelectData = Gson().fromJson<SalesmanAcceptBean>(mResult, SalesmanAcceptBean::class.java)
                salesman_name_tv.text = mSelectData.salesmanName
            }

        }).show(supportFragmentManager, "getSalesmanSFilterDialog")
    }

    fun initPeople() {
        shipper_phone_ed.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (shipper_phone_ed.text.toString().isNotEmpty() && shipper_circle_hide_ll.visibility == View.VISIBLE) {
                    mPresenter?.getShipperInfo(HttpParams("contactMb", shipper_phone_ed.text.toString()))
                }
            }
        }
        shipper_mShipperId_ed.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (shipper_mShipperId_ed.text.toString().isNotEmpty() && shipper_circle_hide_ll.visibility == View.VISIBLE) {
                    mPresenter?.getShipperInfo(HttpParams("vipId", shipper_mShipperId_ed.text.toString()))
                }
            }
        }
        /**
         *
         */
        receiver_phone_ed.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (receiver_phone_ed.text.toString().isNotEmpty() && receiver_circle_hide_ll.visibility == View.VISIBLE) {
                    mPresenter?.getReceiverInfo(HttpParams("contactMb", receiver_phone_ed.text.toString()))
                }
            }
        }
        receiver_mConsigneeTel_ed.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (receiver_mConsigneeTel_ed.text.toString().isNotEmpty() && receiver_circle_hide_ll.visibility == View.VISIBLE) {
                    mPresenter?.getReceiverInfo(HttpParams("contactTel", receiver_mConsigneeTel_ed.text.toString()))
                }
            }
        }
    }
}