package com.mbcq.orderlibrary.activity.acceptbilling


import android.Manifest
import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.text.InputFilter
import android.text.TextPaint
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.baselibrary.view.MoneyInputFilter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.*
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.greendao.DaoSession
import com.mbcq.commonlibrary.greendao.WebAreaDbInfoDao
import com.mbcq.orderlibrary.R
import com.mbcq.orderlibrary.activity.acceptbilling.billingweightcalculator.BillingWeightCalculatorDialog
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.activity_accept_billing.*
import zpSDK.zpSDK.zpBluetoothPrinter
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*


/**
 * @author: lzy
 * @time: 2018.08.25
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

    /**
     * 计算重量 弹窗 需要保存状态
     */
    var mBillingWeightCalculatorDialog: BillingWeightCalculatorDialog? = null

    lateinit var rxPermissions: RxPermissions
    protected val RESULT_DATA_CODE = 5848
    protected val RECEIVER_RESULT_DATA_CODE = 4439

    //运单号生成方式
    protected lateinit var waybillNumberTag: String

    //付货方式
    protected lateinit var okProcessStrTag: String
    var okProcessStrTagIndex = 1
    protected var isTalkGoodsStrTag: Boolean = false
    override fun isShowErrorDialog() = true

    override fun initExtra() {
        super.initExtra()
        rxPermissions = RxPermissions(this)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        initToolbar()
//        initTransportMethodLayout()
//        initPayWayLayout()
        waybillNumber(false)
        initReceivingMethod(1)
        initDeliveryMethod(1)
        initAddGoodsRecycler()
        weight_name_ed.filters = arrayOf<InputFilter>(MoneyInputFilter())
        volume_name_tv.filters = arrayOf<InputFilter>(MoneyInputFilter())
    }


    interface WebDbInterface {
        fun isNull()
        fun isSuccess(list: MutableList<WebAreaDbInfo>)

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
        cargo_info_add_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (isCanCargoInfoAdd()) {
                    val mAddGoodsAcceptBillingBean = AddGoodsAcceptBillingBean()
                    mAddGoodsAcceptBillingBean.product = cargo_name_ed.text.toString()
                    mAddGoodsAcceptBillingBean.qty = numbers_name_ed.text.toString()
                    mAddGoodsAcceptBillingBean.packages = package_name_ed.text.toString()
                    mAddGoodsAcceptBillingBean.weight = weight_name_ed.text.toString()
                    mAddGoodsAcceptBillingBean.volumn = volume_name_tv.text.toString()
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
        volume_name_tv.setText("")
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
        if (volume_name_tv.text.toString().isEmpty()) {
            showToast("请输入体积")
            showEditTextFocus(volume_name_tv)
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
        if (mShipperMb.isEmpty()) {
            showToast("请选择发货人")
            return false
        }
        if (mConsigneeMb.isEmpty()) {
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
          if (volume_name_tv.text.toString().isEmpty()) {
              showToast("请输入体积")
              return false
          }*/
        if (receipt_requirements_name_tv.text.toString().isEmpty()) {
            showToast("请选择回单要求")
            return false
        }
        return true
    }

    fun waybillNumber(isHandWriting: Boolean) {
        if (isHandWriting) {
            waybill_number_ed.isFocusableInTouchMode = true
            waybill_number_ed.isFocusable = true
            waybill_number_ed.requestFocus()
            waybillNumberTag = "手写"
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
                            ARouter.getInstance().build(ARouterConstants.LocationActivity).navigation()
                        } else {
                            // Oups permission denied
                        }
                    }
        } else {
            ARouter.getInstance().build(ARouterConstants.LocationActivity).navigation()

        }
    }
}