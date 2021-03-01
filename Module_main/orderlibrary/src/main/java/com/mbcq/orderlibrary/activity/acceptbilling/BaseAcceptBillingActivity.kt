package com.mbcq.orderlibrary.activity.acceptbilling


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.location.LocationManager
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.db.SharePreferencesHelper
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.baselibrary.view.MoneyInputFilter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.*
import com.mbcq.orderlibrary.R
import com.mbcq.orderlibrary.activity.acceptbilling.billingvolumecalculator.BillingVolumeCalculatorDialog
import com.mbcq.orderlibrary.activity.acceptbilling.billingweightcalculator.BillingWeightCalculatorDialog
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.activity_accept_billing.*
import org.json.JSONObject
import java.lang.StringBuilder


/**
 * @author: lzy
 * @time: 2018.08.25
 * @information 受理开单 工具层 主要分担主城压力
 */

abstract class BaseAcceptBillingActivity<V : BaseView, T : BasePresenterImpl<V>> : BaseBlueToothAcceptBillingActivity<V, T>(), BaseView {

    /**
     * 到达网点
     */
    var endWebIdCode = ""
    var endWebIdCodeStr = ""

    /**
     * 到达公司的id
     * 这里从网点列表中获取
     */
    var eCompanyId = ""

    /**
     * 目的地
     */
    var destinationt = ""

    var mAccType = ""//付款方式编码

    var mAccTypeStr = ""//付款方式


    var mTransneed = ""//运输类型编码
    var mTransneedStr = ""//运输类型

    var mRequiredStr = ""//必填项
    var mAccNowIsCanHkStr = ""//返款限制的支付方式配置参数
    var mBackState = "" //回单状态编码
    var mBackStateListStr = "" //回单状态文字list字符串

    //结算重量 配置
    val mSettlementWeightConfiguration = 260

    //结算重量
    var mSettlementWeightValue = 0.00

    //轻重货 false 轻 true 重
    var mLightAndHeavyGoods = false
    /**
     * 发货人信息
     */
//    var mShipperId = ""//发货客户编号
//    var mShipperMb = ""//发货人手机号
//    var mShipperTel = ""//发货人固定电话
//    var mShipper = ""//发货人
//    var mShipperCid = ""//发货人身份证号
//    var mShipperAddr = ""//发货人地址

    /**
     * 收货人信息
     */
//    var mConsigneeMb = ""//收货人手机号
//    var mConsigneeTel = ""//收货人固定电话
//    var mConsignee = ""//收货人
//    var mConsigneeAddr = ""//收货人地址
    /**
     * 常用收货方式
     */
    val COMMONLY_USED_RECEIVING_METHODS = "COMMONLY_USED_RECEIVING_METHODS"

    /**
     * 常用付货方式
     */
    val COMMON_DELIVERY_METHODS = "COMMON_DELIVERY_METHODS"

    /**
     * 计算重量 弹窗 需要保存状态
     */
    var mBillingWeightCalculatorDialog: BillingWeightCalculatorDialog? = null

    /**
     * 计算体积
     */
    var mBillingVolumeCalculatorDialog: BillingVolumeCalculatorDialog? = null
    protected var mCommonlyInformationSharePreferencesHelper: SharePreferencesHelper? = null

    lateinit var rxPermissions: RxPermissions
    protected val RESULT_DATA_CODE = 5848
    protected val RECEIVER_RESULT_DATA_CODE = 4439
    protected val COMMON_USERS_REMARK = "COMMON_USERS_REMARK"

    /**
     * 运单号生成方式
     * 0机打1手写
     */

    protected var waybillNumberTag: String = "机打"

    protected var waybillNumberIndexTag: Int = 0

    //付货方式
    protected lateinit var okProcessStrTag: String
    var okProcessStrTagIndex = 1
    protected var isTalkGoodsStrTag: Boolean = false
    var mSoundPool: SoundPool? = null
    protected var soundPoolMap: HashMap<Int, Int>? = null
    val ACCEPT_SOUND_SUCCESS_TAG = 1
    override fun isShowErrorDialog() = true

    override fun initExtra() {
        super.initExtra()
        rxPermissions = RxPermissions(this)
        initSoundPool()
        if (mCommonlyInformationSharePreferencesHelper == null)
            mCommonlyInformationSharePreferencesHelper = SharePreferencesHelper(mContext, Constant.COMMON_CONFIGURATION_PREFERENCESMODE)
    }

    override fun onBackPressed() {
        TalkSureCancelDialog(mContext, getScreenWidth(), "您确定要退出受理开单页面吗？") {
            super.onBackPressed()
        }.show()
    }

    @SuppressLint("SetTextI18n")
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        initToolbar()
//        initTransportMethodLayout()
//        initPayWayLayout()
        waybillNumber(false)
//        initReceivingMethod(1)
//        initDeliveryMethod(1)
        initAddGoodsRecycler()
        initCommonlyInformationConfiguration()

        weight_name_ed.filters = arrayOf<InputFilter>(MoneyInputFilter())
        volume_name_ed.filters = arrayOf<InputFilter>(MoneyInputFilter())
        insured_amount_ed.filters = arrayOf<InputFilter>(MoneyInputFilter())
        quantity_price_ed.filters = arrayOf<InputFilter>(MoneyInputFilter())
        weight_price_ed.filters = arrayOf<InputFilter>(MoneyInputFilter())
        volume_price_ed.filters = arrayOf<InputFilter>(MoneyInputFilter())
        shipper_circle_hide_ll.visibility = View.GONE
        receiver_circle_hide_ll.visibility = View.GONE
        cargo_info_total_ll.visibility = View.GONE
        accept_billing_nested.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY > shipper_circle_hide_ll.top) {
                if (shipper_circle_hide_ll.visibility == View.VISIBLE) {
                    shipper_circle_hide_ll.visibility = View.GONE
                    if (shipper_name_ed.text.toString().isNotBlank() && shipper_phone_ed.text.toString().isNotBlank())
                        add_shipper_tv.text = "${shipper_name_ed.text} ${shipper_phone_ed.text} \n${shipper_address_ed.text} "
                }
            }
            if (scrollY > receiver_circle_hide_ll.top) {
                if (receiver_circle_hide_ll.visibility == View.VISIBLE) {
                    receiver_circle_hide_ll.visibility = View.GONE
                    if (add_receiver_tv.text.toString().isNotBlank() && receiver_phone_ed.text.toString().isNotBlank())
                        add_receiver_tv.text = "${receiver_name_ed.text} ${receiver_phone_ed.text} \n${receiver_address_ed.text} "

                }
            }
        })
        receipt_requirements_name_ed.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                //如果回单要求里面没有这个参数 就把回单backstate改成空字符串
                if (mBackStateListStr.isNotBlank()) {
                    if (receipt_requirements_name_ed.text.toString().isNotEmpty()) {
                        if (!mBackStateListStr.contains(receipt_requirements_name_ed.text.toString())) {
                            mBackState = ""
                        }
                    }
                }

            }

        })
        volume_name_ed.addTextChangedListener(mSettlementWeightValueTextWatcher)
        weight_name_ed.addTextChangedListener(mSettlementWeightValueTextWatcher)
    }
    /**
     * 结算重量和轻重货修改逻辑 要修改两个地方 这个是计算
     */
    val mSettlementWeightValueTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (volume_name_ed.text.toString().isNotEmpty() && weight_name_ed.text.toString().isNotEmpty()) {
                val mVolumeValue = volume_name_ed.text.toString().toDouble() * mSettlementWeightConfiguration
                val mWeightValue = weight_name_ed.text.toString().toDouble()
                mSettlementWeightValue = if (mVolumeValue > mWeightValue) mVolumeValue else mWeightValue
                mLightAndHeavyGoods = mWeightValue > mVolumeValue
            }

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

    fun planMoreGoods() {
        if (mAddGoodsAcceptBillingAdapter.getAllData().isNotEmpty()) {
            cargo_info_total_ll.visibility = View.VISIBLE
            val totalProduct = StringBuilder()
            var totalQty = 0
            val totalPackages = StringBuilder()
            var totalWeight = 0.00
            var totalVolume = 0.00
            for (item in mAddGoodsAcceptBillingAdapter.getAllData()) {
                totalProduct.append(item.product).append(",")
                totalQty += item.qty.toInt()
                totalPackages.append(item.packages).append(" ")
                totalWeight += item.weight.toDouble()
                totalVolume += item.volumn.toDouble()
            }
            product_total_tv.text = totalProduct.toString()
            qty_total_tv.text = totalQty.toString()
            packages_total_tv.text = totalPackages.toString()
            weight_total_tv.text = totalWeight.toString()
            volumn_total_tv.text = totalVolume.toString()
        } else {
            cargo_info_total_ll.visibility = View.GONE
        }
    }

    fun initSoundPool() {
        mSoundPool = SoundPool(1, AudioManager.STREAM_ALARM, 0)
//        mSoundPool?.setOnLoadCompleteListener { soundPool, sampleId, status -> }
        soundPoolMap = HashMap<Int, Int>()
        mSoundPool?.let {
            soundPoolMap?.put(ACCEPT_SOUND_SUCCESS_TAG, it.load(this, com.mbcq.commonlibrary.R.raw.scan_success, 1))

        }
    }

    fun getGPS() {
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

    private fun initCommonlyInformationConfiguration() {
        if (mCommonlyInformationSharePreferencesHelper?.contain(COMMONLY_USED_RECEIVING_METHODS)!!) {
            if (mCommonlyInformationSharePreferencesHelper?.getSharePreference(COMMONLY_USED_RECEIVING_METHODS, "客户自送") as String == "客户自送")
                initReceivingMethod(1)
            else
                initReceivingMethod(2)


        } else
            initReceivingMethod(1)
        if (mCommonlyInformationSharePreferencesHelper?.contain(COMMON_DELIVERY_METHODS)!!) {

            when (mCommonlyInformationSharePreferencesHelper?.getSharePreference(COMMON_DELIVERY_METHODS, "客户自提") as String) {
                "客户自提" -> initDeliveryMethod(1)
                "送货上门" -> initDeliveryMethod(2)
                "司机直送" -> initDeliveryMethod(3)
                else -> initReceivingMethod(1)
            }


        } else
            initDeliveryMethod(1)
    }


    /**
     * ARouter 度娘
     * {"name":"xxxx","phone":"15999999999","address":"1111"}
     */
    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RESULT_DATA_CODE -> {
                (data?.getStringExtra("AddShipperResultData"))?.let {
                    val mDatas = JSONObject(it)
                    shipper_phone_ed.setText(mDatas.optString("phone"))
                    shipper_name_ed.setText(mDatas.optString("name"))
                    shipper_address_ed.setText(mDatas.optString("address"))
                    shipper_mShipperTel_ed.setText(mDatas.optString("shipperTel"))
                    add_shipper_tv.text = "${shipper_name_ed.text} ${shipper_phone_ed.text} \n${shipper_address_ed.text} "
                }
            }
            RECEIVER_RESULT_DATA_CODE -> {
                (data?.getStringExtra("AddReceiveResultData"))?.let {
                    val mDatas = JSONObject(it)
                    receiver_phone_ed.setText(mDatas.optString("phone"))//收货人手机号
                    receiver_mConsigneeTel_ed.setText(mDatas.optString("consigneeTel"))//收货人固定电话
                    receiver_name_ed.setText(mDatas.optString("name"))//收货人
                    receiver_address_ed.setText(mDatas.optString("address"))//收货人地址
                    if (mDatas.optString("product").isNotBlank())
                        cargo_name_ed.setText(mDatas.optString("product"))
                    if (mDatas.optString("package").isNotBlank())
                        package_name_ed.setText(mDatas.optString("package"))
                    add_receiver_tv.text = "${receiver_name_ed.text} ${receiver_phone_ed.text} \n${receiver_address_ed.text} "

                }
            }
            Constant.GPS_REQUEST_CODE -> {
                //做需要做的事情，比如再次检测是否打开GPS了 或者定位
                getLocation()
            }
        }


    }


    private fun initToolbar() {
        accept_billing_toolbar.setBackButtonOnClickListener {
            onBackPressed()
        }
    }

    override fun onClick() {
        super.onClick()
        shipper_circle_tv.setOnClickListener(object : SingleClick(100L) {
            @SuppressLint("SetTextI18n")
            override fun onSingleClick(v: View?) {
                if (receiver_circle_hide_ll.visibility == View.VISIBLE)
                    receiver_circle_hide_ll.visibility = View.GONE
                if (shipper_circle_hide_ll.visibility == View.VISIBLE) {
                    add_shipper_tv.text = "${shipper_name_ed.text} ${shipper_phone_ed.text} \n${shipper_address_ed.text} "
                    if (add_shipper_tv.text.toString().isBlank()) {
                        add_shipper_tv.text = "添加发货人信息"
                    }
                }
                shipper_circle_hide_ll.visibility = if (shipper_circle_hide_ll.visibility == View.GONE) View.VISIBLE else View.GONE
            }

        })
        add_shipper_tv.setOnClickListener(object : SingleClick(100L) {
            @SuppressLint("SetTextI18n")
            override fun onSingleClick(v: View?) {
                if (receiver_circle_hide_ll.visibility == View.VISIBLE)
                    receiver_circle_hide_ll.visibility = View.GONE
                if (shipper_circle_hide_ll.visibility == View.VISIBLE) {
                    add_shipper_tv.text = "${shipper_name_ed.text} ${shipper_phone_ed.text} \n${shipper_address_ed.text} "
                    if (add_shipper_tv.text.toString().isBlank()) {
                        add_shipper_tv.text = "添加发货人信息"
                    }
                }
                shipper_circle_hide_ll.visibility = if (shipper_circle_hide_ll.visibility == View.GONE) View.VISIBLE else View.GONE
            }

        })
        receiver_circle_tv.setOnClickListener(object : SingleClick() {
            @SuppressLint("SetTextI18n")
            override fun onSingleClick(v: View?) {
                if (shipper_circle_hide_ll.visibility == View.VISIBLE) {
                    shipper_circle_hide_ll.visibility = View.GONE
                }
                if (receiver_circle_hide_ll.visibility == View.VISIBLE) {
                    add_receiver_tv.text = "${receiver_name_ed.text} ${receiver_phone_ed.text} \n${receiver_address_ed.text} "
                    if (add_receiver_tv.text.toString().isBlank()) {
                        add_receiver_tv.text = "添加收货人信息"
                    }
                }
                receiver_circle_hide_ll.visibility = if (receiver_circle_hide_ll.visibility == View.GONE) View.VISIBLE else View.GONE
            }

        })
        add_receiver_tv.setOnClickListener(object : SingleClick() {
            @SuppressLint("SetTextI18n")
            override fun onSingleClick(v: View?) {
                if (shipper_circle_hide_ll.visibility == View.VISIBLE) {
                    shipper_circle_hide_ll.visibility = View.GONE
                }
                if (receiver_circle_hide_ll.visibility == View.VISIBLE) {
                    add_receiver_tv.text = "${receiver_name_ed.text} ${receiver_phone_ed.text} \n${receiver_address_ed.text} "
                    if (add_receiver_tv.text.toString().isBlank()) {
                        add_receiver_tv.text = "添加收货人信息"
                    }
                }
                receiver_circle_hide_ll.visibility = if (receiver_circle_hide_ll.visibility == View.GONE) View.VISIBLE else View.GONE
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
        cargo_info_add_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (isCanCargoInfoAdd()) {
                    val mAddGoodsAcceptBillingBean = AddGoodsAcceptBillingBean()
                    mAddGoodsAcceptBillingBean.product = cargo_name_ed.text.toString()
                    mAddGoodsAcceptBillingBean.qty = numbers_name_ed.text.toString()
                    mAddGoodsAcceptBillingBean.packages = package_name_ed.text.toString()
                    mAddGoodsAcceptBillingBean.weight = weight_name_ed.text.toString()
                    mAddGoodsAcceptBillingBean.volumn = volume_name_ed.text.toString()
                    mAddGoodsAcceptBillingBean.safeMoney = insured_amount_ed.text.toString()
                    mAddGoodsAcceptBillingBean.qtyPrice = quantity_price_ed.text.toString()
                    mAddGoodsAcceptBillingBean.lightandheavy = if (mLightAndHeavyGoods) "重货" else "轻货"
                    mAddGoodsAcceptBillingBean.weightJs = mSettlementWeightValue.toString()
                    mAddGoodsAcceptBillingBean.setwPrice(weight_price_ed.text.toString())
                    mAddGoodsAcceptBillingBean.setvPrice(volume_price_ed.text.toString())
                    mAddGoodsAcceptBillingAdapter.appendData(mutableListOf(mAddGoodsAcceptBillingBean))
                    clearCargoInfoAdd()
                    planMoreGoods()
                }

            }

        })
    }

    lateinit var mAddGoodsAcceptBillingAdapter: AddGoodsAcceptBillingAdapter
    protected fun initAddGoodsRecycler() {
        cargo_info_recycler.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        mAddGoodsAcceptBillingAdapter = AddGoodsAcceptBillingAdapter(mContext).also {
            it.mOnRemoveItemInterface = object : AddGoodsAcceptBillingAdapter.OnRemoveItemInterface {
                override fun onResult(v: View, position: Int, data: String) {
                    planMoreGoods()
                }

            }
            cargo_info_recycler.adapter = it

        }
    }

    protected fun clearCargoInfoAdd() {
        cargo_name_ed.setText("")
        numbers_name_ed.setText("")
        package_name_ed.setText("")
        weight_name_ed.setText("")
        volume_name_ed.setText("")
        insured_amount_ed.setText("")
        quantity_price_ed.setText("")
        weight_price_ed.setText("")
        volume_price_ed.setText("")
        mSettlementWeightValue = 0.00
        mLightAndHeavyGoods = false
    }


    protected fun isCanCargoInfoAdd(): Boolean {
        if (mRequiredStr.contains("product"))
            if (cargo_name_ed.text.toString().isEmpty()) {
                showToast("请选择货物名称")
                showEditTextFocus(cargo_name_ed)
                return false
            }
        if (mRequiredStr.contains("qty"))
            if (numbers_name_ed.text.toString().isEmpty()) {
                showToast("请输入件数")
                showEditTextFocus(numbers_name_ed)
                return false
            }
        if (mRequiredStr.contains("packages"))
            if (package_name_ed.text.toString().isEmpty()) {
                showToast("请选择包装")
                showEditTextFocus(package_name_ed)
                return false
            }
        if (mRequiredStr.contains("weight"))
            if (weight_name_ed.text.toString().isEmpty()) {
                showToast("请输入重量")
                showEditTextFocus(weight_name_ed)
                return false
            }
        if (mRequiredStr.contains("volumn"))
            if (volume_name_ed.text.toString().isEmpty()) {
                showToast("请输入体积")
                showEditTextFocus(volume_name_ed)
                return false
            }
        // 保价金额 数量单价 重量单价 体积单价
        if (mRequiredStr.contains("safeMoney"))
            if (insured_amount_ed.text.toString().isEmpty()) {
                showToast("请输入保价金额")
                showEditTextFocus(insured_amount_ed)
                return false
            }
        if (mRequiredStr.contains("qtyPrice"))
            if (quantity_price_ed.text.toString().isEmpty()) {
                showToast("请输入数量单价")
                showEditTextFocus(quantity_price_ed)
                return false
            }
        if (mRequiredStr.contains("wPrice"))
            if (weight_price_ed.text.toString().isEmpty()) {
                showToast("请输入重量单价")
                showEditTextFocus(weight_price_ed)
                return false
            }
        if (mRequiredStr.contains("vPrice"))
            if (volume_price_ed.text.toString().isEmpty()) {
                showToast("请输入体积单价")
                showEditTextFocus(volume_price_ed)
                return false
            }
        return true

    }
    //订单号 运单号 到达网点 目的地 增值服务 卡号 原单号 发货人 发货人电话 发货人手机 发货人公司 发货人地址 发货人身份证号
    //收货人 收货人电话 收货人手机 收货人地址 收货人公司 货物名称 件数 包装方式 重量 体积 结算重量 轻重货 保价金额 数量单价 重量单价 体积单价

    //收货人 收货人电话 收货人手机 收货人地址 收货人公司
    // 货物名称 件数 包装方式 重量 体积
    // 结算重量 轻重货
    // 保价金额 数量单价 重量单价 体积单价

    protected fun isCanSaveAcctBilling(): Boolean {
        if (mRequiredStr.contains("orderId"))
            if (order_number_ed.text.toString().isEmpty()) {
                showToast("请输入订单号")
                showEditTextFocus(order_number_ed)
                return false
            }
        if (mRequiredStr.contains("billno"))
            if (waybill_number_ed.text.toString().isEmpty()) {
                showToast("请输入运单号")
                showEditTextFocus(waybill_number_ed)
                return false
            }
        if (mRequiredStr.contains("ewebidCode"))
            if (arrive_outlet_tv.text.toString().isEmpty()) {
                showToast("请选择到达网点")
                return false
            }
        if (mRequiredStr.contains("destination"))
            if (destinationt_tv.text.toString().isEmpty()) {
                showToast("请选择目的地")
                return false
            }
        if (mRequiredStr.contains("valueAddedService"))
            if (value_added_services_ed.text.toString().isEmpty()) {
                showToast("请输入增值服务")
                showEditTextFocus(value_added_services_ed)
                return false
            }
        if (mRequiredStr.contains("vipId"))
            if (bank_number_ed.text.toString().isEmpty()) {
                showToast("请输入卡号")
                showEditTextFocus(bank_number_ed)
                return false
            }
        if (mRequiredStr.contains("oBillno"))
            if (original_order_number_name_ed.text.toString().isEmpty()) {
                showToast("请输入原单号")
                showEditTextFocus(original_order_number_name_ed)
                return false
            }
        if (mRequiredStr.contains("shipper"))
            if (shipper_name_ed.text.toString().isEmpty()) {
                showToast("请输入发货人")
                showEditTextFocus(shipper_name_ed)
                return false
            }
        if (mRequiredStr.contains("shipperTel"))
            if (shipper_mShipperTel_ed.text.toString().isEmpty()) {
                showToast("请输入发货人电话")
                showEditTextFocus(shipper_mShipperTel_ed)
                return false
            }
        if (mRequiredStr.contains("shipperMb"))
            if (shipper_name_ed.text.toString().isEmpty()) {
                showToast("请输入发货人手机")
                showEditTextFocus(shipper_name_ed)
                return false
            }
        if (mRequiredStr.contains("shipperAddr"))
            if (shipper_address_ed.text.toString().isEmpty()) {
                showToast("请输入发货人地址")
                showEditTextFocus(shipper_address_ed)
                return false
            }
        if (mRequiredStr.contains("shipperCompany"))
            if (shipper_company_ed.text.toString().isEmpty()) {
                showToast("请输入发货人公司")
                showEditTextFocus(shipper_company_ed)
                return false
            }
        if (mRequiredStr.contains("shipperCid"))
            if (shipper_mShipperCid_ed.text.toString().isEmpty()) {
                showToast("请输入发货人证件号")
                showEditTextFocus(shipper_mShipperCid_ed)
                return false
            }
        if (mRequiredStr.contains("consignee"))
            if (receiver_name_ed.text.toString().isEmpty()) {
                showToast("请输入收货人")
                showEditTextFocus(receiver_name_ed)
                return false
            }
        if (mRequiredStr.contains("consigneeTel"))
            if (receiver_phone_ed.text.toString().isEmpty()) {
                showToast("请输入收货人电话")
                showEditTextFocus(receiver_phone_ed)
                return false
            }
        if (mRequiredStr.contains("consigneeMb"))
            if (receiver_phone_ed.text.toString().isEmpty()) {
                showToast("请输入收货人手机")
                showEditTextFocus(receiver_phone_ed)
                return false
            }
        if (mRequiredStr.contains("consigneeAddr"))
            if (receiver_address_ed.text.toString().isEmpty()) {
                showToast("请输入收货人地址")
                showEditTextFocus(receiver_address_ed)
                return false
            }
        if (mRequiredStr.contains("consigneeCompany"))
            if (receiver_company_ed.text.toString().isEmpty()) {
                showToast("请输入收货人公司")
                showEditTextFocus(receiver_company_ed)
                return false
            }
        return true
    }

    abstract fun refreshWaybillNumber()
    fun waybillNumber(isHandWriting: Boolean) {
        if (isHandWriting) {
            waybill_number_ed.isFocusableInTouchMode = true
            waybill_number_ed.isFocusable = true
            waybill_number_ed.requestFocus()
            waybillNumberTag = "手写"
            waybillNumberIndexTag = 1
            waybill_number_ed.setText("")
            waybill_number_handwriting_tv.setBackgroundResource(R.drawable.round_blue_lefttop_leftbottom)
            waybill_number_machine_printed_tv.setBackgroundResource(R.drawable.round_white_righttop_rightbottom)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                waybill_number_handwriting_tv.setTextColor(getColor(R.color.white))
                waybill_number_machine_printed_tv.setTextColor(getColor(R.color.black))
            } else {
                waybill_number_handwriting_tv.setTextColor(resources.getColor(R.color.white))
                waybill_number_machine_printed_tv.setTextColor(resources.getColor(R.color.black))

            }
        } else {
            waybill_number_ed.isFocusable = false
            waybill_number_ed.isFocusableInTouchMode = false

            hideKeyboard(waybill_number_ed)
            waybillNumberTag = "机打"
            waybillNumberIndexTag = 0
            refreshWaybillNumber()
            waybill_number_handwriting_tv.setBackgroundResource(R.drawable.round_white_lefttop_leftbottom)
            waybill_number_machine_printed_tv.setBackgroundResource(R.drawable.round_blue_righttop_rightbottom)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                waybill_number_handwriting_tv.setTextColor(getColor(R.color.black))
                waybill_number_machine_printed_tv.setTextColor(resources.getColor(R.color.white))

            } else {
                waybill_number_handwriting_tv.setTextColor(resources.getColor(R.color.black))
                waybill_number_machine_printed_tv.setTextColor(resources.getColor(R.color.white))

            }

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

    /**
     * 静态数据 因为需要动态 仅供参考
     * 运输方式
     * @1
     */
    private fun initTransportMethodLayout() {
        for (index in 0..3) {
            when (index) {
                0 -> {
                    transport_method_rg.addView(RadioGroupUtil.addSelectItem(mContext, "零担", index))
                }
                1 -> {
                    transport_method_rg.addView(RadioGroupUtil.addSelectItem(mContext, "马帮快线", index))
                }
                2 -> {
                    transport_method_rg.addView(RadioGroupUtil.addSelectItem(mContext, "整车", index))

                }
                3 -> {
                    transport_method_rg.addView(RadioGroupUtil.addSelectItem(mContext, "叮当小票", index))

                }

            }

            transport_method_rg.check(0)
        }


    }


    /**
     * 静态数据 因为需要动态 仅供参考
     * 付款方式 @2
     */
    private fun initPayWayLayout() {
        for (index in 0..5) {
            when (index) {
                0 -> {
                    pay_way_title_rg.addView(RadioGroupUtil.addSelectItem(mContext, "现付", index))
                }
                1 -> {
                    pay_way_title_rg.addView(RadioGroupUtil.addSelectItem(mContext, "提付", index))
                }
                2 -> {
                    pay_way_title_rg.addView(RadioGroupUtil.addSelectItem(mContext, "回单付", index))
                }
                3 -> {
                    pay_way_title_rg.addView(RadioGroupUtil.addSelectItem(mContext, "月结", index))
                }
                4 -> {
                    pay_way_title_rg.addView(RadioGroupUtil.addSelectItem(mContext, "货款扣", index))

                }
                5 -> {
                    pay_way_title_rg.addView(RadioGroupUtil.addSelectItem(mContext, "两笔付", index))

                }
            }


        }
        pay_way_title_rg.check(0)
    }

    protected fun showEditTextFocus(editText: EditText) {
        editText.isFocusable = true
        editText.isFocusableInTouchMode = true
        editText.requestFocus()
    }

    fun getLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
                    .subscribe { granted ->
                        if (granted) { // Always true pre-M
                            // I can control the camera now
                            getGPS()
//                            ARouter.getInstance().build(ARouterConstants.LocationActivity).navigation()
                        } else {
                            // Oups permission denied
                        }
                    }
        } else {
            getGPS()

//            ARouter.getInstance().build(ARouterConstants.LocationActivity).navigation()

        }
    }
}