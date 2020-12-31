package com.mbcq.vehicleslibrary.activity.alllocalagent.localagent

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.flyco.tablayout.listener.CustomTabEntity
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.fragment.localagentshortfeeder.bycar.LocalGentByCarFragment
import com.mbcq.vehicleslibrary.fragment.localagentshortfeeder.byorder.LocalGentByOrderFragment
import kotlinx.android.synthetic.main.activity_local_agent.*

abstract class BaseLocalAgentActivity<V : BaseView, T : BasePresenterImpl<V>> : BaseMVPActivity<V, T>(), BaseView {
    var mLocalGentByCarFragment: LocalGentByCarFragment? = null
    var mLocalGentByOrderFragment: LocalGentByOrderFragment? = null
    var mFragmentTag_index = 0
    private var fragmentManager: FragmentManager? = null
    val mTabEntities: ArrayList<CustomTabEntity> = ArrayList()

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        fragmentManager = supportFragmentManager
        setTabSelection(0)
    }

    protected fun setTabSelection(index: Int) {
        fragmentManager?.let {
            val transaction = it.beginTransaction()
            //隐藏碎片
            hideFragment(transaction)
            mFragmentTag_index = index
            when (index) {
                0 -> {
                    if (mLocalGentByCarFragment == null) {
                        mLocalGentByCarFragment = LocalGentByCarFragment()
                        transaction.add(R.id.local_agent_fragment_fl, mLocalGentByCarFragment!!)
                    } else {
                        transaction.show(mLocalGentByCarFragment!!)
                    }

                }
                1 -> {

                    if (mLocalGentByOrderFragment == null) {
                        mLocalGentByOrderFragment = LocalGentByOrderFragment()
                        transaction.add(R.id.local_agent_fragment_fl, mLocalGentByOrderFragment!!)
                    } else {
                        transaction.show(mLocalGentByOrderFragment!!)
                    }

                }

            }
            transaction.commit()
        }

    }

    private fun hideFragment(transaction: FragmentTransaction) {
        //隐藏碎片 避免重叠
        mLocalGentByCarFragment?.let {
            transaction.hide(it)
        }
        mLocalGentByOrderFragment?.let {
            transaction.hide(it)
        }

    }

    override fun onClick() {
        super.onClick()
        local_agent_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }
}