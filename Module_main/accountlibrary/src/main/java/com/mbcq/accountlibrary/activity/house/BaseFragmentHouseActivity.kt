package com.mbcq.accountlibrary.activity.house


import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.mbcq.accountlibrary.R
import com.mbcq.accountlibrary.fragment.FinanceFragment
import com.mbcq.accountlibrary.fragment.HouseFragment
import com.mbcq.accountlibrary.fragment.OperationFragment
import com.mbcq.accountlibrary.fragment.ReportFragment
import com.mbcq.accountlibrary.fragment.setting.SettingFragment
import com.mbcq.baselibrary.util.screen.StatusBarUtils
import kotlinx.android.synthetic.main.activity_house.*
import kotlin.system.exitProcess


/**
 * 主页面
 */
@Suppress("DEPRECATED_IDENTITY_EQUALS")
abstract class BaseFragmentHouseActivity : BaseHouseFingerActivity() {
    private var fragmentManager: FragmentManager? = null
    private val FRAGMENT_TAG = arrayOf("HouseFragment", "OperationFragment", "FinanceFragment", "ReportFragment", "SettingFragment")
    private val CODE_FRAGMENT_KEY = "FRAGMENT_TAG" //key
    private var mFragmentTag_index = 0

    /***
     * 首页1
     */
    private var mHouseFragment: HouseFragment? = null

    /**
     * 运营2
     */
    private var mOperationFragment: OperationFragment? = null

    /**
     * 财务3
     */
    private var mFinanceFragment: FinanceFragment? = null

    /**
     * 报表4
     */
    private var mReportFragment: ReportFragment? = null

    /**
     * 我的5
     */
    private var mSettingFragment: SettingFragment? = null


    override fun getLayoutId(): Int = R.layout.activity_house

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(0)
        initFragment()
        initRestartFragment(savedInstanceState)
    }

    private fun initRestartFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            setTabSelection(0)
        } else {
            if (savedInstanceState.getInt(CODE_FRAGMENT_KEY) === 0 && mHouseFragment == null)
                mHouseFragment = fragmentManager!!.findFragmentByTag(FRAGMENT_TAG[0]) as HouseFragment?
            else if (savedInstanceState.getInt(CODE_FRAGMENT_KEY) === 1 && mSettingFragment == null)
                mOperationFragment = fragmentManager!!.findFragmentByTag(FRAGMENT_TAG[1]) as OperationFragment?
            else if (savedInstanceState.getInt(CODE_FRAGMENT_KEY) === 2 && mFinanceFragment == null)
                mFinanceFragment = fragmentManager!!.findFragmentByTag(FRAGMENT_TAG[2]) as FinanceFragment?
            else if (savedInstanceState.getInt(CODE_FRAGMENT_KEY) === 3 && mReportFragment == null)
                mReportFragment = fragmentManager!!.findFragmentByTag(FRAGMENT_TAG[3]) as ReportFragment?
            else if (savedInstanceState.getInt(CODE_FRAGMENT_KEY) === 4 && mSettingFragment == null)
                mSettingFragment = fragmentManager!!.findFragmentByTag(FRAGMENT_TAG[4]) as SettingFragment?
            setTabSelection(savedInstanceState.getInt(CODE_FRAGMENT_KEY))
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CODE_FRAGMENT_KEY, mFragmentTag_index)

    }

    /**
     * 避免重叠
     */
    override fun onAttachFragment(@NonNull fragment: Fragment) {
        when (fragment) {
            is HouseFragment -> {
                mHouseFragment = fragment
            }
            is SettingFragment -> {
                mSettingFragment = fragment
            }
            is ReportFragment -> {
                mReportFragment = fragment
            }
            is OperationFragment -> {
                mOperationFragment = fragment
            }
            is FinanceFragment -> {
                mFinanceFragment = fragment
            }
        }
        super.onAttachFragment(fragment)

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setTabSelection(0)
    }

    private fun hideFragment(transaction: FragmentTransaction) {
        //隐藏碎片 避免重叠
        mHouseFragment?.let {
            transaction.hide(it)
        }
        mSettingFragment?.let {
            transaction.hide(it)
        }
        mReportFragment?.let {
            transaction.hide(it)
        }
        mOperationFragment?.let {
            transaction.hide(it)
        }
        mFinanceFragment?.let {
            transaction.hide(it)
        }

    }

    private fun setTabSelection(index: Int) {
        fragmentManager?.let {
            val transaction = it.beginTransaction()
            //隐藏碎片
            hideFragment(transaction)
            mFragmentTag_index = index
            when (index) {
                0 -> {
                    if (mHouseFragment == null) {
                        mHouseFragment = HouseFragment()
                        transaction.add(R.id.main_fragment_fl, mHouseFragment!!)
                    } else {
                        transaction.show(mHouseFragment!!)
                    }
                    setStatusBar(1)
                    StatusBarUtils.setTextDark(mContext, true)

                }
                1 -> {

                    if (mOperationFragment == null) {
                        mOperationFragment = OperationFragment()
                        transaction.add(R.id.main_fragment_fl, mOperationFragment!!)
                    } else {
                        transaction.show(mOperationFragment!!)
                    }
                    setStatusBar(1)

                }
                2 -> {
                    if (mFinanceFragment == null) {
                        mFinanceFragment = FinanceFragment()
                        transaction.add(R.id.main_fragment_fl, mFinanceFragment!!)
                    } else {
                        transaction.show(mFinanceFragment!!)
                    }
                    setStatusBar(1)

                }
                3 -> {
                    if (mReportFragment == null) {
                        mReportFragment = ReportFragment()
                        transaction.add(R.id.main_fragment_fl, mReportFragment!!)
                    } else {
                        transaction.show(mReportFragment!!)
                    }
                    setStatusBar(1)

                }
                4 -> {
                    if (mSettingFragment == null) {
                        mSettingFragment = SettingFragment()
                        transaction.add(R.id.main_fragment_fl, mSettingFragment!!)
                    } else {
                        transaction.show(mSettingFragment!!)
                    }
                    setStatusBar(1)

                }

            }
            transaction.commit()
        }

    }

    private fun initFragment() {
        fragmentManager = supportFragmentManager
        val titles= arrayListOf<String>("首页","运营","财务","报表","我的")
        val icons= arrayListOf(R.drawable.ic_house_botom,R.drawable.ic_operation_bottom,R.drawable.ic_finance_bottom,R.drawable.ic_report_bottom,R.drawable.ic_setting_bottom)
        val menu: Menu = main_bottom_navigation_view.menu
        for (i in 0 until titles.size) {
            menu.add(1, i, i, titles[i])
            val item: MenuItem = menu.findItem(i)
            item.setIcon(icons[i])
        }
        main_bottom_navigation_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                /**
                 * 首页
                 */
//                R.id.home_item
                0-> {
                    setTabSelection(0)

                }
                /**
                 * 运营
                 */
//                R.id.operation_item
                1-> {
                    setTabSelection(1)

                }
                /**
                 * 财务
                 */
//                R.id.finance_item
                2-> {
                    setTabSelection(2)

                }
                /**
                 * 报表
                 */
//                R.id.report_item
                3-> {
                    setTabSelection(3)

                }
                /**
                 * 我的
                 */
//                R.id.setting_item
                4-> {
                    setTabSelection(4)

                }
            }

            true
        }
    }

    var exitTime: Long = 0L

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.action === KeyEvent.ACTION_DOWN
        ) {
            if (System.currentTimeMillis() - exitTime > 2000) { //中间间隔的时间,可设定
                showToast("再按一次退出程序")
                exitTime = System.currentTimeMillis()
            } else {
                exitProcess(0)
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}
