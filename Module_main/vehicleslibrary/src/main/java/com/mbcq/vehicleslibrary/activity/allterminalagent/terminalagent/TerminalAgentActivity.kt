package com.mbcq.vehicleslibrary.activity.allterminalagent.terminalagent


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.interfaces.RxBus
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.CommonApplication
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterWithTimeDialog
import com.mbcq.commonlibrary.greendao.DaoSession
import com.mbcq.commonlibrary.greendao.WebAreaDbInfoDao
import com.mbcq.vehicleslibrary.R
import kotlinx.android.synthetic.main.activity_terminal_agent.*

/**
 * @author: lzy
 * @time: 2020-9-25 09:12:00
 * 终端代理
 */
@Route(path = ARouterConstants.TerminalAgentActivity)
class TerminalAgentActivity : BaseMVPActivity<TerminalAgentContract.View, TerminalAgentPresenter>(), TerminalAgentContract.View {
    var mFragmentTag_index=0
    override fun getLayoutId(): Int = R.layout.activity_terminal_agent
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        terminal_agent_tabLayout.addTab(terminal_agent_tabLayout.newTab().setText("按车(X)"))
        terminal_agent_tabLayout.addTab(terminal_agent_tabLayout.newTab().setText("按票(X)"))

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

    override fun onClick() {
        super.onClick()
        terminal_agent_toolbar.setRightButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                getDbWebId(object : WebDbInterface {
                    override fun isNull() {

                    }

                    override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                        FilterWithTimeDialog(getScreenWidth(), Gson().toJson(list), "webid", "webidCode", true, "${if (mFragmentTag_index == 0) "按车" else "按票"}本地代理记录筛选", true, mClickInterface = object : OnClickInterface.OnClickInterface {
                            /**
                             * s1 网点
                             * s2  start@end
                             */
                            override fun onResult(s1: String, s2: String) {
                                val mTerminalAgentEvent = TerminalAgentEvent()
                                mTerminalAgentEvent.type = mFragmentTag_index
                                mTerminalAgentEvent.webCode = s1
                                val timeList = s2.split("@")
                                if (timeList.isNotEmpty() && timeList.size == 2) {
                                    mTerminalAgentEvent.startTime = timeList[0]
                                    mTerminalAgentEvent.endTime = timeList[1]
                                }
                                RxBus.build().post(mTerminalAgentEvent)

                            }

                        }).show(supportFragmentManager, "TerminalAgentActivityFilterWithTimeDialog")
                    }

                })
            }

        })
        terminal_agent_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }
}