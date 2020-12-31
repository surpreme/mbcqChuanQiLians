package com.mbcq.vehicleslibrary.activity.allterminalagent.terminalagent


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.flyco.tablayout.listener.CustomTabEntity
import com.google.gson.Gson
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.interfaces.RxBus
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.LocalEntity
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.WebDbUtil
import com.mbcq.commonlibrary.WebsDbInterface
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterWithTimeDialog
import com.mbcq.vehicleslibrary.R
import kotlinx.android.synthetic.main.activity_terminal_agent.*

/**
 * @author: lzy
 * @time: 2020-9-25 09:12:00
 * 终端代理
 */
@Route(path = ARouterConstants.TerminalAgentActivity)
class TerminalAgentActivity : BaseMVPActivity<TerminalAgentContract.View, TerminalAgentPresenter>(), TerminalAgentContract.View {
    private val mTabEntities: ArrayList<CustomTabEntity> = ArrayList()
    var mFragmentTag_index=0

    override fun getLayoutId(): Int = R.layout.activity_terminal_agent
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        mTabEntities.add(LocalEntity("按车"))
        mTabEntities.add(LocalEntity("按票"))
        terminal_agent_tabLayout.setTabData(mTabEntities)

    }


    override fun onClick() {
        super.onClick()
        terminal_agent_toolbar.setRightButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                WebDbUtil.getDbWebId(application,object : WebsDbInterface {
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