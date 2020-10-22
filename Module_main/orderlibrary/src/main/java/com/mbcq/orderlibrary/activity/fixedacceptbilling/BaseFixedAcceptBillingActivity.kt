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
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.BaseView
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

abstract class BaseFixedAcceptBillingActivity<V : BaseView, T : BasePresenterImpl<V>> : BaseMVPActivity<V,T>(), BaseView {
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

    override fun initExtra() {
        super.initExtra()
        rxPermissions = RxPermissions(this)

    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
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