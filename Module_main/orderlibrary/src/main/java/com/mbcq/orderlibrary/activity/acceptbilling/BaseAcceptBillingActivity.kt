package com.mbcq.orderlibrary.activity.acceptbilling


import android.Manifest
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.CommonApplication
import com.mbcq.commonlibrary.RadioGroupUtil
import com.mbcq.commonlibrary.adapter.BaseAdapterBean
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterDialog
import com.mbcq.commonlibrary.greendao.DaoSession
import com.mbcq.commonlibrary.greendao.WebAreaDbInfoDao
import com.mbcq.orderlibrary.R
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.activity_accept_billing.*


/**
 * @author: lzy
 * @time: 2018.08.25
 */

abstract class BaseAcceptBillingActivity<V : BaseView, T : BasePresenterImpl<V>> : BaseMVPActivity<V, T>(), BaseView {
    lateinit var rxPermissions: RxPermissions
    protected val RESULT_DATA_CODE = 5848
    protected val RECEIVER_RESULT_DATA_CODE = 4439
    override fun isShowErrorDialog() = true

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        initToolbar()
//        initTransportMethodLayout()
//        initPayWayLayout()
        waybillNumber(false)
        rxPermissions = RxPermissions(this)

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


    fun waybillNumber(isHandWriting: Boolean) {
        if (isHandWriting) {
            waybill_number_ed.isFocusableInTouchMode = true
            waybill_number_ed.isFocusable = true
            waybill_number_ed.requestFocus()

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

            }
            2 -> {
                home_delivery_tv.setBackgroundResource(R.drawable.round_blue)
                home_delivery_tv.setTextColor(Color.WHITE)

                customer_mention_tv.setBackgroundResource(R.drawable.hollow_out_gray)
                customer_mention_tv.setTextColor(Color.BLACK)
            }
        }
    }

    /**
     * 付货方式
     *
     */
    fun initDeliveryMethod(type: Int) {
        when (type) {
            1 -> {
                get_delivery_mention_tv.setBackgroundResource(R.drawable.round_blue)
                get_delivery_mention_tv.setTextColor(Color.WHITE)

                get_delivery_home_tv.setBackgroundResource(R.drawable.hollow_out_gray)
                get_delivery_home_tv.setTextColor(Color.BLACK)

                get_driver_direct_tv.setBackgroundResource(R.drawable.hollow_out_gray)
                get_driver_direct_tv.setTextColor(Color.BLACK)

            }
            2 -> {
                get_delivery_home_tv.setBackgroundResource(R.drawable.round_blue)
                get_delivery_home_tv.setTextColor(Color.WHITE)

                get_delivery_mention_tv.setBackgroundResource(R.drawable.hollow_out_gray)
                get_delivery_mention_tv.setTextColor(Color.BLACK)

                get_driver_direct_tv.setBackgroundResource(R.drawable.hollow_out_gray)
                get_driver_direct_tv.setTextColor(Color.BLACK)
            }
            3 -> {
                get_driver_direct_tv.setBackgroundResource(R.drawable.round_blue)
                get_driver_direct_tv.setTextColor(Color.WHITE)

                get_delivery_mention_tv.setBackgroundResource(R.drawable.hollow_out_gray)
                get_delivery_mention_tv.setTextColor(Color.BLACK)

                get_delivery_home_tv.setBackgroundResource(R.drawable.hollow_out_gray)
                get_delivery_home_tv.setTextColor(Color.BLACK)
            }
        }
    }

    /**
     * 静态数据
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
     * 静态数据
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