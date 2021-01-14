package com.mbcq.vehicleslibrary.activity.alldeparturerecord.departurerecord


import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.commonlibrary.CommonApplication
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.greendao.DaoSession
import com.mbcq.commonlibrary.greendao.WebAreaDbInfoDao
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.fragment.shortfeeder.ShortFeederFragment
import com.mbcq.vehicleslibrary.fragment.trunkdeparture.TrunkDepartureFragment
import kotlinx.android.synthetic.main.activity_departure_record.*

/**
 * @author: lzy
 * @time: 2020-09-12 10:50:33
 * 发车记录
 */
abstract class BaseDepartureRecordActivity<V : BaseView, T : BasePresenterImpl<V>> : BaseMVPActivity<V, T>(), BaseView {
    var mShortFeederFragment: ShortFeederFragment? = null
    var mTrunkDepartureFragment: TrunkDepartureFragment? = null
    var mFragmentTag_index = 0
    private var fragmentManager: FragmentManager? = null

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        fragmentManager = supportFragmentManager
        indexTop(1)
        setTabSelection(0)
    }

    private fun hideFragment(transaction: FragmentTransaction) {
        //隐藏碎片 避免重叠
        mShortFeederFragment?.let {
            transaction.hide(it)
        }
        mTrunkDepartureFragment?.let {
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
                    if (mShortFeederFragment == null) {
                        mShortFeederFragment = ShortFeederFragment()
                        transaction.add(R.id.departure_record_fragment_fl, mShortFeederFragment!!)
                    } else {
                        transaction.show(mShortFeederFragment!!)
                    }

                }
                1 -> {

                    if (mTrunkDepartureFragment == null) {
                        mTrunkDepartureFragment = TrunkDepartureFragment()
                        transaction.add(R.id.departure_record_fragment_fl, mTrunkDepartureFragment!!)
                    } else {
                        transaction.show(mTrunkDepartureFragment!!)
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
}