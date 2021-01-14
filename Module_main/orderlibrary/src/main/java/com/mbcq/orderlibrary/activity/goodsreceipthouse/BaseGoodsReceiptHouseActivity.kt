package com.mbcq.orderlibrary.activity.goodsreceipthouse

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.mbcq.baselibrary.ui.BaseActivity
import com.mbcq.baselibrary.view.LocalEntity
import com.mbcq.orderlibrary.R
import com.mbcq.orderlibrary.fragment.goodsreceipt.GoodsReceiptFragment
import com.mbcq.orderlibrary.fragment.signrecord.SignRecordFragment
import kotlinx.android.synthetic.main.activity_goods_receipt_house.*

abstract class BaseGoodsReceiptHouseActivity : BaseActivity() {
    var mSignRecordFragment: SignRecordFragment? = null
    var mGoodsReceiptFragment: GoodsReceiptFragment? = null
    private val FRAGMENT_TAG = arrayOf("SignRecordFragment", "GoodsReceiptFragment")
    private val CODE_FRAGMENT_KEY = "GOODS_RECEIPT_HOUSE_FRAGMENT_TAG" //key
    var mFragmentTag_index = 0
    private var fragmentManager: FragmentManager? = null
    val mTabEntities: ArrayList<CustomTabEntity> = ArrayList()

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        initTab()
        setTabSelection(0)
        initRestartFragment(savedInstanceState)

    }

    private fun initTab() {
        mTabEntities.add(LocalEntity("已签收"))
        mTabEntities.add(LocalEntity("未签收"))
        goods_receipt_house_tabLayout.setTabData(mTabEntities)
        goods_receipt_house_tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onTabSelect(position: Int) {
                setTabSelection(position)
                if (position == 0) {
                    goods_receipt_house_toolbar.setRightButtonVisibility(View.VISIBLE)
                } else if (position == 1) {
                    goods_receipt_house_toolbar.setRightButtonVisibility(View.GONE)
                }

            }

            override fun onTabReselect(position: Int) {
            }

        })

    }

    override fun initExtra() {
        super.initExtra()
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
            is SignRecordFragment -> {
                mSignRecordFragment = fragment
            }
            is GoodsReceiptFragment -> {
                mGoodsReceiptFragment = fragment
            }
        }
        super.onAttachFragment(fragment)

    }

    private fun hideFragment(transaction: FragmentTransaction) {
        //隐藏碎片 避免重叠
        mSignRecordFragment?.let {
            transaction.hide(it)
        }
        mGoodsReceiptFragment?.let {
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
                    if (mSignRecordFragment == null) {
                        mSignRecordFragment = SignRecordFragment()
                        transaction.add(R.id.goods_receipt_house_fragment_fl, mSignRecordFragment!!)
                    } else {
                        transaction.show(mSignRecordFragment!!)
                    }

                }
                1 -> {

                    if (mGoodsReceiptFragment == null) {
                        mGoodsReceiptFragment = GoodsReceiptFragment()
                        transaction.add(R.id.goods_receipt_house_fragment_fl, mGoodsReceiptFragment!!)
                    } else {
                        transaction.show(mGoodsReceiptFragment!!)
                    }

                }

            }
            transaction.commit()
        }

    }

    private fun initRestartFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            setTabSelection(0)
        } else {
            if (savedInstanceState.getInt(CODE_FRAGMENT_KEY) == 0 && mSignRecordFragment == null)
                mSignRecordFragment = fragmentManager!!.findFragmentByTag(FRAGMENT_TAG[0]) as SignRecordFragment?
            else if (savedInstanceState.getInt(CODE_FRAGMENT_KEY) == 1 && mGoodsReceiptFragment == null)
                mGoodsReceiptFragment = fragmentManager!!.findFragmentByTag(FRAGMENT_TAG[1]) as GoodsReceiptFragment?
            setTabSelection(savedInstanceState.getInt(CODE_FRAGMENT_KEY))
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CODE_FRAGMENT_KEY, mFragmentTag_index)

    }

}