package com.mbcq.vehicleslibrary.activity.allarrivalrecord.arrivalrecord


import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.interfaces.RxBus
import com.mbcq.baselibrary.ui.BaseActivity
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.CommonApplication
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterWithTimeDialog
import com.mbcq.commonlibrary.greendao.DaoSession
import com.mbcq.commonlibrary.greendao.WebAreaDbInfoDao
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.fragment.arrivalshortfeeder.ArrivalShortFeederFragment
import com.mbcq.vehicleslibrary.fragment.arrivaltrunkdeparture.ArrivalTrunkDepartureFragment
import kotlinx.android.synthetic.main.activity_arrival_record.*

/**
 * @author: lzy
 * @time: 2020-09-09 17:22:09
 * 到车记录
 */
abstract class BaseArrivalRecordActivity<V : BaseView, T : BasePresenterImpl<V>> : BaseMVPActivity<V,T>(), BaseView {
    private var fragmentManager: FragmentManager? = null
    private var mArrivalShortFeederFragment: ArrivalShortFeederFragment? = null
    private var mArrivalTrunkDepartureFragment: ArrivalTrunkDepartureFragment? = null
    protected var mFragmentTag_index = 0

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        fragmentManager = supportFragmentManager
        setTabSelection(0)
    }

    private fun hideFragment(transaction: FragmentTransaction) {
        //隐藏碎片 避免重叠
        mArrivalShortFeederFragment?.let {
            transaction.hide(it)
        }
        mArrivalTrunkDepartureFragment?.let {
            transaction.hide(it)
        }
    }

    protected fun setTabSelection(index: Int) {
        fragmentManager?.let {
            val transaction = it.beginTransaction()
            //隐藏碎片
            hideFragment(transaction)
            mFragmentTag_index = index
            when (index) {
                0 -> {
                    if (mArrivalShortFeederFragment == null) {
                        mArrivalShortFeederFragment = ArrivalShortFeederFragment()
                        transaction.add(R.id.departure_record_fragment_fl, mArrivalShortFeederFragment!!)
                    } else {
                        transaction.show(mArrivalShortFeederFragment!!)
                    }


                }
                1 -> {

                    if (mArrivalTrunkDepartureFragment == null) {
                        mArrivalTrunkDepartureFragment = ArrivalTrunkDepartureFragment()
                        transaction.add(R.id.departure_record_fragment_fl, mArrivalTrunkDepartureFragment!!)
                    } else {
                        transaction.show(mArrivalTrunkDepartureFragment!!)
                    }

                }


            }
            transaction.commit()
        }

    }

    fun indexTop(type: Int) {
        when (type) {
            1 -> {
                short_feeder_line.background = ContextCompat.getDrawable(mContext, R.color.base_blue)
                main_line_departure_line.background = ContextCompat.getDrawable(mContext, R.color.white)
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    short_feeder_tv.setTextColor(getColor(R.color.base_blue))
                    main_line_departure_tv.setTextColor(getColor(R.color.black))
                } else {
                    short_feeder_tv.setTextColor(resources.getColor(R.color.base_blue))
                    main_line_departure_tv.setTextColor(resources.getColor(R.color.black))

                }
            }
            2 -> {
                main_line_departure_line.background = ContextCompat.getDrawable(mContext, R.color.base_blue)
                short_feeder_line.background = ContextCompat.getDrawable(mContext, R.color.white)
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    main_line_departure_tv.setTextColor(getColor(R.color.base_blue))
                    short_feeder_tv.setTextColor(getColor(R.color.black))
                } else {
                    main_line_departure_tv.setTextColor(resources.getColor(R.color.base_blue))
                    short_feeder_tv.setTextColor(resources.getColor(R.color.black))

                }
            }
        }
    }

    override fun onClick() {
        super.onClick()
        arrival_record_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }
}