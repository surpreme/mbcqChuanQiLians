package com.mbcq.orderlibrary.activity.acceptbilling


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.InputFilter
import android.text.TextPaint
import android.text.TextUtils
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
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.greendao.DaoSession
import com.mbcq.commonlibrary.greendao.WebAreaDbInfoDao
import com.mbcq.orderlibrary.R
import com.mbcq.orderlibrary.activity.acceptbilling.billingvolumecalculator.BillingVolumeCalculatorDialog
import com.mbcq.orderlibrary.activity.acceptbilling.billingweightcalculator.BillingWeightCalculatorDialog
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.activity_accept_billing.*
import org.json.JSONObject


/**
 * @author: lzy
 * @time: 2018.08.25
 * @information 受理开单 工具层 主要分担主城压力
 */

abstract class BaseAcceptBillingActivity<V : BaseView, T : BasePresenterImpl<V>> : CommonPrintMVPActivity<V, T>(), BaseView {

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
    override fun isShowErrorDialog() = true

    override fun initExtra() {
        super.initExtra()
        rxPermissions = RxPermissions(this)
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
        shipper_circle_hide_ll.visibility = View.GONE
        receiver_circle_hide_ll.visibility = View.GONE
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


    interface WebDbInterface {
        fun isNull()
        fun isSuccess(list: MutableList<WebAreaDbInfo>)

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

    /**
     * 得到greenDao数据库中的网点
     * 可视化 stetho 度娘
     */
    protected fun getDbWebId(mWebDbInterface: WebDbInterface) {
        val daoSession: DaoSession = (application as CommonApplication).daoSession
        val userInfoDao: WebAreaDbInfoDao = daoSession.webAreaDbInfoDao
        val dbDatas = userInfoDao.queryBuilder().list()
        if (dbDatas.isNullOrEmpty()) {
            mWebDbInterface.isNull()
        } else {
            mWebDbInterface.isSuccess(dbDatas)
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
                    mAddGoodsAcceptBillingAdapter.appendData(mutableListOf(mAddGoodsAcceptBillingBean))
                    clearCargoInfoAdd()
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
                    it.removeItem(position)
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
    }

    protected fun isCanCargoInfoAdd(): Boolean {
        if (cargo_name_ed.text.toString().isEmpty()) {
            showToast("请选择货物名称")
            showEditTextFocus(cargo_name_ed)
            return false
        }
        if (numbers_name_ed.text.toString().isEmpty()) {
            showToast("请输入件数")
            showEditTextFocus(numbers_name_ed)
            return false
        }
        if (package_name_ed.text.toString().isEmpty()) {
            showToast("请选择包装")
            showEditTextFocus(package_name_ed)
            return false
        }
        if (weight_name_ed.text.toString().isEmpty()) {
            showToast("请输入重量")
            showEditTextFocus(weight_name_ed)
            return false
        }
        if (volume_name_ed.text.toString().isEmpty()) {
            showToast("请输入体积")
            showEditTextFocus(volume_name_ed)
            return false
        }
        return true

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
//        if (mShipperMb.isEmpty()) {
        if (shipper_name_ed.text.toString().isBlank()) {
            showToast("请选择发货人")
            return false
        }
//        if (mConsigneeMb.isEmpty()) {
        if (receiver_name_ed.text.toString().isBlank()) {
            showToast("请选择收货人")
            return false
        }
        /*  if (cargo_name_ed.text.toString().isEmpty()) {
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
          if (volume_name_ed.text.toString().isEmpty()) {
              showToast("请输入体积")
              return false
          }*/
        if (receipt_requirements_name_ed.text.toString().isEmpty()) {
            showToast("请选择回单要求")
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