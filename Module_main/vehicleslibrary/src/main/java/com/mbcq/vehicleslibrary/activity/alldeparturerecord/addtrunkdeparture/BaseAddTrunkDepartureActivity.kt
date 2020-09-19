package com.mbcq.vehicleslibrary.activity.alldeparturerecord.addtrunkdeparture


import android.os.Bundle
import android.view.View
import com.google.gson.Gson
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.CommonApplication
import com.mbcq.commonlibrary.RadioGroupUtil
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterDialog
import com.mbcq.commonlibrary.greendao.DaoSession
import com.mbcq.commonlibrary.greendao.WebAreaDbInfoDao
import com.mbcq.vehicleslibrary.R
import kotlinx.android.synthetic.main.activity_add_trunk_departure.*


/**
 * @author: lzy
 * @time: 2020-09-14 11:02:45
 */
abstract class BaseAddTrunkDepartureActivity<V : BaseView, T : BasePresenterImpl<V>> : BaseMVPActivity<V,T>(), BaseView {
    var mFirstEwebidCode = ""
    var mSencondEwebidCode = ""
    var mThridEwebidCode = ""
    var mECompanyId = ""
    var mWebCodeId = ""
    var mToPayTotalPrice = ""
    var mWebCodeIdStr = ""
    var mTransneed = 1//运输类型编码
    var mTransneedStr = ""//运输类型
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        route_fee_breakdown_hide_ll.visibility = View.GONE
        cash_freight_hide_ll.visibility = View.GONE
        freight_onarrival_hide_ll.visibility = View.GONE
        oil_card_first_ed.isFocusable = false
        oil_card_first_ed.isFocusableInTouchMode = false
        oil_card_second_ed.isFocusable = false
        oil_card_second_ed.isFocusableInTouchMode = false
        oil_card_third_ed.isFocusable = false
        oil_card_third_ed.isFocusableInTouchMode = false

    }
    protected fun initModeOfTransport() {
        mTransneedStr = "普运"
        mode_transport_rg.addView(RadioGroupUtil.addSelectItem(mContext, "普运", 0))
        mode_transport_rg.addView(RadioGroupUtil.addSelectItem(mContext, "马帮快线", 1))
        mode_transport_rg.addView(RadioGroupUtil.addSelectItem(mContext, "补发数据", 2))
        mode_transport_rg.check(0)
        mode_transport_rg.setOnCheckedChangeListener { _, checkedId ->
            mTransneed = checkedId + 1
            when (checkedId) {
                0 -> mTransneedStr = "普运"
                1 -> mTransneedStr = "马帮快线"
                2 -> mTransneedStr = "补发数据"
            }
        }

    }

    fun showHideCashFreight() {
        if (cash_freight_hide_ll.visibility == View.VISIBLE) {
            cash_freight_hide_ll.visibility = View.GONE
            cash_freight_down_iv.rotation = 0f
            cash_freight_tv.text = "请选择现付运费"
        } else if (cash_freight_hide_ll.visibility == View.GONE) {
            cash_freight_hide_ll.visibility = View.VISIBLE
            cash_freight_down_iv.rotation = 180f
            cash_freight_tv.text = ""


        }
    }

    fun showHideFreightOnArrival() {
        if (freight_onarrival_hide_ll.visibility == View.VISIBLE) {
            freight_onarrival_hide_ll.visibility = View.GONE
            freight_onarrival_down_iv.rotation = 0f
            freight_onarrival_tv.text = "请选择现付运费"
        } else if (freight_onarrival_hide_ll.visibility == View.GONE) {
            freight_onarrival_hide_ll.visibility = View.VISIBLE
            freight_onarrival_down_iv.rotation = 180f
            freight_onarrival_tv.text = ""


        }
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

    fun geDeliveryPointLocal(list: MutableList<WebAreaDbInfo>, type: Int) {
        FilterDialog(getScreenWidth(), Gson().toJson(list), "webid", "选择到货网点", true, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                when (type) {
                    0 -> {
                        destination_tv.text = list[position].webid
                        mECompanyId = list[position].companyId
                        mWebCodeId = list[position].webidCode
                    }
                    1 -> {
                        if (list[position].webidCode.toString() == mSencondEwebidCode || list[position].webidCode.toString() == mThridEwebidCode) {
                            showToast("您已经选择过${list[position].webid}了哦")
                            return
                        }
                        oil_card_first_tv.text = list[position].webid
                        mFirstEwebidCode = list[position].webidCode.toString()
                        oil_card_first_ed.isFocusableInTouchMode = true
                        oil_card_first_ed.isFocusable = true
                        oil_card_first_ed.requestFocus()

                    }
                    2 -> {
                        if (list[position].webidCode.toString() == mFirstEwebidCode || list[position].webidCode.toString() == mThridEwebidCode) {
                            showToast("您已经选择过${list[position].webid}了哦")
                            return
                        }
                        oil_card_second_tv.text = list[position].webid
                        mSencondEwebidCode = list[position].webidCode.toString()
                        oil_card_second_ed.isFocusableInTouchMode = true
                        oil_card_second_ed.isFocusable = true
                        oil_card_second_ed.requestFocus()
                    }
                    3 -> {
                        if (list[position].webidCode.toString() == mFirstEwebidCode || list[position].webidCode.toString() == mSencondEwebidCode) {
                            showToast("您已经选择过${list[position].webid}了哦")
                            return
                        }
                        oil_card_third_tv.text = list[position].webid
                        mThridEwebidCode = list[position].webidCode.toString()
                        oil_card_third_ed.isFocusableInTouchMode = true
                        oil_card_third_ed.isFocusable = true
                        oil_card_third_ed.requestFocus()
                    }
                }
            }

        }).show(supportFragmentManager, "AddTrunkDepartureActivitygeDeliveryPointLocalDialogFilterDialog$type")
    }

    fun showHideRouteFeeBreakdown() {
        if (route_fee_breakdown_hide_ll.visibility == View.VISIBLE) {
            route_fee_breakdown_hide_ll.visibility = View.GONE
            route_fee_breakdown_down_iv.rotation = 0f
            route_fee_breakdown_tv.text = "请选择干线明细运费"
        } else if (route_fee_breakdown_hide_ll.visibility == View.GONE) {
            route_fee_breakdown_hide_ll.visibility = View.VISIBLE
            route_fee_breakdown_down_iv.rotation = 180f
            freight_onarrival_tv.text = ""


        }
    }

    override fun onClick() {
        super.onClick()
        add_trunk_departure_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }
        })
    }


}