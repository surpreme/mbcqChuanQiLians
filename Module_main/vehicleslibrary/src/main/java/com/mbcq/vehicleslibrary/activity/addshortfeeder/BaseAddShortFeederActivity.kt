package com.mbcq.vehicleslibrary.activity.addshortfeeder


import android.os.Bundle
import android.view.View
import com.google.gson.Gson
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.commonlibrary.CommonApplication
import com.mbcq.commonlibrary.RadioGroupUtil
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterDialog
import com.mbcq.commonlibrary.greendao.DaoSession
import com.mbcq.commonlibrary.greendao.WebAreaDbInfoDao
import com.mbcq.vehicleslibrary.R
import kotlinx.android.synthetic.main.activity_add_short_feeder.*

/**
 * @author: lzy
 * @time: 2020-09-14 13:42:27
 * 添加短驳发车 运输方式 update
 */
abstract class BaseAddShortFeederActivity<V : BaseView, T : BasePresenterImpl<V>> : BaseMVPActivity<V, T>(), BaseView {
    var mFirstEwebidCode = ""
    var mSencondEwebidCode = ""
    var mThridEwebidCode = ""
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        initModeOfTransport()
        cash_freight_hide_ll.visibility = View.GONE
        freight_onarrival_hide_ll.visibility = View.GONE

    }


    private fun initModeOfTransport() {
        mode_transport_rg.addView(RadioGroupUtil.addSelectItem(mContext, "普运", 0))
        mode_transport_rg.addView(RadioGroupUtil.addSelectItem(mContext, "马帮快线", 1))
        mode_transport_rg.addView(RadioGroupUtil.addSelectItem(mContext, "补发数据", 2))
        mode_transport_rg.check(0)


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

                    }
                    1 -> {
                        if (list[position].webidCode.toString() == mSencondEwebidCode || list[position].webidCode.toString() == mThridEwebidCode) {
                            showToast("您已经选择过${list[position].webid}了哦")
                            return
                        }
                        oil_card_first_tv.text = list[position].webid
                        mFirstEwebidCode = list[position].webidCode.toString()
                    }
                    2 -> {
                        if (list[position].webidCode.toString() == mFirstEwebidCode || list[position].webidCode.toString() == mThridEwebidCode) {
                            showToast("您已经选择过${list[position].webid}了哦")
                            return
                        }
                        oil_card_second_tv.text = list[position].webid
                        mSencondEwebidCode = list[position].webidCode.toString()

                    }
                    3 -> {
                        if (list[position].webidCode.toString() == mFirstEwebidCode || list[position].webidCode.toString() == mSencondEwebidCode) {
                            showToast("您已经选择过${list[position].webid}了哦")
                            return
                        }
                        oil_card_third_tv.text = list[position].webid
                        mThridEwebidCode = list[position].webidCode.toString()

                    }
                }
            }

        }).show(supportFragmentManager, "geDeliveryPointLocalDialogFilterDialog$type")
    }


}