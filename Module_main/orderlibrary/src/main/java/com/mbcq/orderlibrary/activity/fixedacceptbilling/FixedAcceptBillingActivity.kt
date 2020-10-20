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
import com.mbcq.orderlibrary.activity.acceptbilling.AcceptPackageBean
import com.mbcq.orderlibrary.activity.acceptbilling.AcceptReceiptRequirementBean
import com.mbcq.orderlibrary.activity.acceptbilling.CargoAppellationBean
import com.mbcq.orderlibrary.activity.acceptbilling.DestinationtBean
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.*
import org.json.JSONArray
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-10-17 13:32:00 改单申请
 */

@Route(path = ARouterConstants.FixedAcceptBillingActivity)
class FixedAcceptBillingActivity : BaseMVPActivity<FixedAcceptBillingActivityContract.View, FixedAcceptBillingActivityPresenter>(), FixedAcceptBillingActivityContract.View {
    var mTransneed = ""//运输类型编码
    var mTransneedStr = ""//运输类型

    var mAccType = ""//付款方式编码
    var mAccTypeStr = ""//付款方式

    /**
     * 到达网点
     */
    var endWebIdCode = ""
    var endWebIdCodeStr = ""

    /**
     * 目的地
     */
    var destinationt = ""
    var isTalkGoodsStrTag: Boolean = false

    //付货方式
    var okProcessStrTag: String = ""
    var okProcessStrTagIndex = 1

    /**
     * 到达公司的id
     * 这里从网点列表中获取
     */
    var eCompanyId = ""

    /**
     * 订单号产生方式
     */
    var waybillNumberTag = ""

    /**
     * 发货人信息
     */
    var mShipperId = ""//发货客户编号
    var mShipperMb = ""//发货人手机号
    var mShipperTel = ""//发货人固定电话
    var mShipper = ""//发货人
    var mShipperCid = ""//发货人身份证号
    var mShipperAddr = ""//发货人地址

    /**
     * 收货人信息
     */
    var mConsigneeMb = ""//收货人手机号
    var mConsigneeTel = ""//收货人固定电话
    var mConsignee = ""//收货人
    var mConsigneeAddr = ""//收货人地址
    var mSearchaId = ""

    lateinit var rxPermissions: RxPermissions
    protected val RESULT_DATA_CODE = 4937
    protected val RECEIVER_RESULT_DATA_CODE = 2369

    override fun getLayoutId(): Int = R.layout.activity_fixed_accept_billing_activity
    override fun initExtra() {
        super.initExtra()
        rxPermissions = RxPermissions(this)

    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

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
        val jsonObj = JsonObject()
        //id
        val Id = mSearchaId
        jsonObj.addProperty("Id", Id)
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


        /*//开单日期
        val BillDate = TimeUtils.getCurrent()
        jsonObj.addProperty("BillDate", BillDate)*/


        //运单状态编码
        val BillState = ""
        jsonObj.addProperty("BillState", BillState)


        //运单类型 billTypeStr
        val BillTypeStr = waybillNumberTag
        jsonObj.addProperty("BillTypeStr", BillTypeStr)

        //付货方式编码
        val OkProcess = okProcessStrTagIndex
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
         * mShipperId
         * TODO
         */
        val ShipperId = ""//发货客户编号
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

        //总件数  TODO
        val TotalQty = numbers_name_ed.text.toString()
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
         *
         */
        //运单号对应的货物清单
        val WayGoosLst = JsonArray()
        val testObj = JsonObject()
        //货物名称
        testObj.addProperty("Product", Product)

        //件数
        testObj.addProperty("Qty", Qty)

        //包装方式
        testObj.addProperty("Packages", Packages)

        //重量
        testObj.addProperty("Weight", Weight)

        //体积
        testObj.addProperty("Volumn", Volumn)


        WayGoosLst.add(testObj)
        /**
         * 修改原因
         */
        jsonObj.addProperty("updateRemark",modify_reason_ed.text.toString())
        /**
         *
         */
//        WayGoosLst.add(test)
        jsonObj.add("WayGoosLst", WayGoosLst)

        /**
         * 费用的所有添加
         */
        mEditTextAdapter?.getData()?.let {
            for (item in it) {
                jsonObj.addProperty(item.tag, item.inputStr)

            }
        }
        val farthObj =JsonObject()
        farthObj.addProperty("Id",mSearchaId)
        farthObj.addProperty("Billno",waybill_number_ed.text.toString())
        farthObj.addProperty("updateRemark",modify_reason_ed.text.toString())
        farthObj.add("WaybillUpdated",jsonObj)
        mPresenter?.updateData(farthObj)


    }

    protected fun isCanSaveAcctBilling(): Boolean {
        if (endWebIdCode.isEmpty()) {
            showToast("请选择到达网点")
            return false
        }
        if (destinationt.isEmpty()) {
            showToast("请选择目的地")
            return false
        }
        if (mShipperMb.isEmpty()) {
            showToast("请选择发货人")
            return false
        }
        if (mConsigneeMb.isEmpty()) {
            showToast("请选择收货人")
            return false
        }
        if (cargo_name_ed.text.toString().isEmpty()) {
            showToast("请选择货物名称")
            return false
        }
        if (package_name_ed.text.toString().isEmpty()) {
            showToast("请选择包装")
            return false
        }
        if (weight_name_ed.text.toString().isEmpty()) {
            showToast("请输入重量")
            return false
        }
        if (volume_name_tv.text.toString().isEmpty()) {
            showToast("请输入体积")
            return false
        }
        if (receipt_requirements_name_tv.text.toString().isEmpty()) {
            showToast("请选择回单要求")
            return false
        }
        if (modify_reason_ed.text.toString().isEmpty()) {
            showToast("请输入修改原因")
            return false
        }
        return true
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
                    mPresenter?.getWillByInfo(waybill_number_ed.text.toString())
                } else {
                    showToast("请输入运单号")
                }
            }

        })
        fixed_accept_billing_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
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

    override fun getPackageS(result: String) {
        FilterDialog(getScreenWidth(), result, "packages", "包装", false, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val mSelectData = Gson().fromJson<AcceptPackageBean>(mResult, AcceptPackageBean::class.java)
                package_name_ed.setText(mSelectData.packages)
            }

        }).show(supportFragmentManager, "getPackagesFilterDialog")

    }

    fun takeGPS() {
        if (checkGPSIsOpen()) {
            ARouter.getInstance().build(ARouterConstants.LocationActivity).navigation()
        } else {
            TalkSureCancelDialog(mContext, getScreenWidth(), "需要打开系统定位开关 用于提供精确的定位及导航服务") {
                //跳转GPS设置界面
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivityForResult(intent, Constant.GPS_REQUEST_CODE)
            }.show()

        }
    }

    fun getLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
                    .subscribe { granted ->
                        if (granted) { // Always true pre-M
                            // I can control the camera now
                            takeGPS()
                        } else {
                            // Oups permission denied
                            showToast("您不给我权限就别想打开了 我也无能为力")
                        }
                    }
        } else {
            takeGPS()
        }
    }

    /**
     * 检测GPS是否打开
     *
     * @return
     */
    private fun checkGPSIsOpen(): Boolean {
        val isOpen: Boolean
        val locationManager: LocationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        isOpen = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        return isOpen
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

    /**
     * 收货方式
     * @1 客户自提
     * @2 上门提货
     */
    fun initReceivingMethod(type: Int) {
        when (type) {
            1 -> {
                customer_mention_tv.setBackgroundResource(R.drawable.round_blue)
                customer_mention_tv.setTextColor(Color.WHITE)

                home_delivery_tv.setBackgroundResource(R.drawable.hollow_out_gray)
                home_delivery_tv.setTextColor(Color.BLACK)
                isTalkGoodsStrTag = false
            }
            2 -> {
                home_delivery_tv.setBackgroundResource(R.drawable.round_blue)
                home_delivery_tv.setTextColor(Color.WHITE)

                customer_mention_tv.setBackgroundResource(R.drawable.hollow_out_gray)
                customer_mention_tv.setTextColor(Color.BLACK)
                isTalkGoodsStrTag = true

            }
        }
    }

    /**
     * 付货方式
     *
     */
    fun initDeliveryMethod(type: Int) {
        okProcessStrTagIndex = type
        when (type) {
            1 -> {
                get_delivery_mention_tv.setBackgroundResource(R.drawable.round_blue)
                get_delivery_mention_tv.setTextColor(Color.WHITE)

                get_delivery_home_tv.setBackgroundResource(R.drawable.hollow_out_gray)
                get_delivery_home_tv.setTextColor(Color.BLACK)

                get_driver_direct_tv.setBackgroundResource(R.drawable.hollow_out_gray)
                get_driver_direct_tv.setTextColor(Color.BLACK)
                okProcessStrTag = get_delivery_mention_tv.text.toString()

            }
            2 -> {
                get_delivery_home_tv.setBackgroundResource(R.drawable.round_blue)
                get_delivery_home_tv.setTextColor(Color.WHITE)

                get_delivery_mention_tv.setBackgroundResource(R.drawable.hollow_out_gray)
                get_delivery_mention_tv.setTextColor(Color.BLACK)

                get_driver_direct_tv.setBackgroundResource(R.drawable.hollow_out_gray)
                get_driver_direct_tv.setTextColor(Color.BLACK)
                okProcessStrTag = get_delivery_home_tv.text.toString()

            }
            3 -> {
                get_driver_direct_tv.setBackgroundResource(R.drawable.round_blue)
                get_driver_direct_tv.setTextColor(Color.WHITE)

                get_delivery_mention_tv.setBackgroundResource(R.drawable.hollow_out_gray)
                get_delivery_mention_tv.setTextColor(Color.BLACK)

                get_delivery_home_tv.setBackgroundResource(R.drawable.hollow_out_gray)
                get_delivery_home_tv.setTextColor(Color.BLACK)
                okProcessStrTag = get_driver_direct_tv.text.toString()

            }
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

    var mEditTextAdapter: EditTextAdapter<BaseEditTextAdapterBean>? = null


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
        cargo_name_ed.setText(data.optString("product"))
        numbers_name_ed.setText(data.optString("totalQty"))
        package_name_ed.setText(data.optString("packages"))
        weight_name_ed.setText(data.optString("weight"))
        volume_name_tv.setText(data.optString("volumn"))
        receipt_requirements_name_tv.text = data.optString("backQty")
        account_names_tv.text = data.optString("bankMan")
        account_bank_tv.text = data.optString("bankName")
        bank_number_tv.text = data.optString("bankCode")
        remarks_tv.setText(data.optString("remark"))
        total_amount_tv.text = data.optString("accSum")
        wait_notice_check.isChecked = data.optInt("isWaitNotice", 0) == 1
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