package com.mbcq.vehicleslibrary.activity.alldeparturerecord.shortfeederhouse


import android.content.Intent
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.tabs.TabLayout
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.fragment.shortfeederhouse.inventorylist.ShortFeederHouseInventoryListFragment
import com.mbcq.vehicleslibrary.fragment.shortfeederhouse.loadinglist.ShortFeederHouseLoadingListFragment
import kotlinx.android.synthetic.main.activity_short_feeder_house.*

/**
 * @author: lzy
 * @time: 2020-09-15 10:12:09
 * 弃用 但已完成
 */
abstract class BaseShortFeederHouseActivity<V : BaseView, T : BasePresenterImpl<V>> : BaseMVPActivity<V, T>(), BaseView {
    var mShortFeederHouseInventoryListFragment: ShortFeederHouseInventoryListFragment? = null
    var mShortFeederHouseLoadingListFragment: ShortFeederHouseLoadingListFragment? = null
    private val FRAGMENT_TAG = arrayOf("ShortFeederHouseInventoryListFragment", "ShortFeederHouseLoadingListFragment")
    private val CODE_FRAGMENT_KEY = "SHORTFEEDERHOUSEACTIVITY_FRAGMENT_TAG" //key
    var mFragmentTag_index = 0
    private var fragmentManager: FragmentManager? = null


    protected fun initTab() {
        short_feeder_house_tabLayout.addTab(short_feeder_house_tabLayout.newTab().setText("库存清单(12)"))
        short_feeder_house_tabLayout.addTab(short_feeder_house_tabLayout.newTab().setText("配载清单(2)"))
        short_feeder_house_tabLayout.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                if(tab.text.toString().contains("库存清单")){
                    setTabSelection(0)

                }else if(tab.text.toString().contains("配载清单")){
                    setTabSelection(1)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

    }

    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)
        fragmentManager = supportFragmentManager

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setTabSelection(0)
    }

    /**
     * 避免重叠
     */
    override fun onAttachFragment(@NonNull fragment: Fragment) {
        when (fragment) {
            is ShortFeederHouseInventoryListFragment -> {
                mShortFeederHouseInventoryListFragment = fragment
            }
            is ShortFeederHouseLoadingListFragment -> {
                mShortFeederHouseLoadingListFragment = fragment
            }
        }
        super.onAttachFragment(fragment)

    }

    private fun hideFragment(transaction: FragmentTransaction) {
        //隐藏碎片 避免重叠
        mShortFeederHouseInventoryListFragment?.let {
            transaction.hide(it)
        }
        mShortFeederHouseLoadingListFragment?.let {
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
                    if (mShortFeederHouseInventoryListFragment == null) {
                        mShortFeederHouseInventoryListFragment = ShortFeederHouseInventoryListFragment()
                        transaction.add(R.id.short_feeder_house_fl, mShortFeederHouseInventoryListFragment!!)
                    } else {
                        transaction.show(mShortFeederHouseInventoryListFragment!!)
                    }

                }
                1 -> {

                    if (mShortFeederHouseLoadingListFragment == null) {
                        mShortFeederHouseLoadingListFragment = ShortFeederHouseLoadingListFragment()
                        transaction.add(R.id.short_feeder_house_fl, mShortFeederHouseLoadingListFragment!!)
                    } else {
                        transaction.show(mShortFeederHouseLoadingListFragment!!)
                    }

                }

            }
            transaction.commit()
        }

    }

    protected fun initRestartFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            setTabSelection(0)
        } else {
            if (savedInstanceState.getInt(CODE_FRAGMENT_KEY) == 0 && mShortFeederHouseInventoryListFragment == null)
                mShortFeederHouseInventoryListFragment = fragmentManager!!.findFragmentByTag(FRAGMENT_TAG[0]) as ShortFeederHouseInventoryListFragment?
            else if (savedInstanceState.getInt(CODE_FRAGMENT_KEY) == 1 && mShortFeederHouseLoadingListFragment == null)
                mShortFeederHouseLoadingListFragment = fragmentManager!!.findFragmentByTag(FRAGMENT_TAG[1]) as ShortFeederHouseLoadingListFragment?
            setTabSelection(savedInstanceState.getInt(CODE_FRAGMENT_KEY))
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CODE_FRAGMENT_KEY, mFragmentTag_index)

    }

}