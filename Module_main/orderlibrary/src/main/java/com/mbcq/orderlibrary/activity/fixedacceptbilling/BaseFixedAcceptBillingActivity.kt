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
import android.text.InputFilter
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.system.TimeUtils
import com.mbcq.baselibrary.view.MoneyInputFilter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.*
import com.mbcq.commonlibrary.adapter.BaseEditTextAdapterBean
import com.mbcq.commonlibrary.adapter.EditTextAdapter
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterDialog
import com.mbcq.orderlibrary.R
import com.mbcq.orderlibrary.activity.acceptbilling.*
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.activity_fixed_accept_billing_activity.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-10-17 13:32:00 改单申请
 */

abstract class BaseFixedAcceptBillingActivity<V : BaseView, T : BasePresenterImpl<V>> : BaseMVPActivity<V, T>(), BaseView {
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
    /*  var mShipperId = ""//发货客户编号
      var mShipperMb = ""//发货人手机号
      var mShipperTel = ""//发货人固定电话
      var mShipper = ""//发货人
      var mShipperCid = ""//发货人身份证号
      var mShipperAddr = ""//发货人地址

      */
    /**
     * 收货人信息
     *//*
    var mConsigneeMb = ""//收货人手机号
    var mConsigneeTel = ""//收货人固定电话
    var mConsignee = ""//收货人
    var mConsigneeAddr = ""//收货人地址*/

    var mSearchaId = ""

    lateinit var rxPermissions: RxPermissions
    protected val RESULT_DATA_CODE = 4937
    protected val RECEIVER_RESULT_DATA_CODE = 2369

    /**
     * 刷新
     */
    protected val REFRESH_DATA_CODE = 1730

    override fun initExtra() {
        super.initExtra()
        rxPermissions = RxPermissions(this)

    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        initAddGoodsRecycler()
        shipper_circle_hide_ll.visibility = View.GONE
        receiver_circle_hide_ll.visibility = View.GONE
        weight_name_ed.filters = arrayOf<InputFilter>(MoneyInputFilter())
        volume_name_ed.filters = arrayOf<InputFilter>(MoneyInputFilter())
        fixed_accept_billing_nested.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY > shipper_circle_hide_ll.top) {
                if (shipper_circle_hide_ll.visibility == View.VISIBLE) {
                    shipper_circle_hide_ll.visibility = View.GONE
//                    fixed_accept_billing_nested.smoothScrollTo(0, shipper_circle_tv.bottom)
                }
            }
            if (scrollY > receiver_circle_hide_ll.top) {
                if (receiver_circle_hide_ll.visibility == View.VISIBLE) {
                    receiver_circle_hide_ll.visibility = View.GONE
//                    fixed_accept_billing_nested.smoothScrollTo(0, receiver_circle_tv.bottom)

                }
            }
        })
    }


    protected fun isCanCargoInfoAdd(): Boolean {
        if (cargo_name_ed.text.toString().isEmpty()) {
            showToast("请选择货物名称")
            return false
        }
        if (numbers_name_ed.text.toString().isEmpty()) {
            showToast("请输入件数")
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
        if (modify_reason_ed.text.toString().isBlank()) {
            showToast("请输入修改原因")
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
        if (receipt_requirements_name_ed.text.toString().isEmpty()) {
            showToast("请选择回单要求")
            return false
        }
        return true
    }

    lateinit var mAddGoodsAcceptBillingAdapter: AddGoodsAcceptBillingAdapter
    protected fun initAddGoodsRecycler() {
        fixed_cargo_info_recycler.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        mAddGoodsAcceptBillingAdapter = AddGoodsAcceptBillingAdapter(mContext).also {
            it.mOnRemoveItemInterface = object : AddGoodsAcceptBillingAdapter.OnRemoveItemInterface {
                override fun onResult(v: View, position: Int, data: String) {
                    it.removeItem(position)
                }

            }
            fixed_cargo_info_recycler.adapter = it

        }
    }

    protected fun clearCargoInfoAdd() {
        cargo_name_ed.setText("")
        numbers_name_ed.setText("")
        package_name_ed.setText("")
        weight_name_ed.setText("")
        volume_name_ed.setText("")
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
        fixed_cargo_info_add_iv.setOnClickListener(object : SingleClick() {
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
        fixed_accept_billing_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
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

    /**
     * ARouter 度娘
     * {"name":"xxxx","phone":"15999999999","address":"1111"}
     */
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


    var mEditTextAdapter: EditTextAdapter<BaseEditTextAdapterBean>? = null


}