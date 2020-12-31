package com.mbcq.vehicleslibrary.activity.alllocalagent.localagent


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
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
import kotlinx.android.synthetic.main.activity_local_agent.*


/**
 * @author: lzy
 * @time: 2020-09-22 11:17:45
 * 本地代理
 */
@Route(path = ARouterConstants.LocalAgentActivity)
class LocalAgentActivity : BaseLocalAgentActivity<LocalAgentContract.View, LocalAgentPresenter>(), LocalAgentContract.View {

    override fun getLayoutId(): Int = R.layout.activity_local_agent
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        mTabEntities.add(LocalEntity("按车"))
        mTabEntities.add(LocalEntity("按票"))
        local_agent_tabLayout.setTabData(mTabEntities)
        local_agent_tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                mFragmentTag_index = position
                setTabSelection(position)

            }

            override fun onTabReselect(position: Int) {
            }

        })
    }


    override fun onClick() {
        super.onClick()
        local_agent_toolbar.setRightButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                WebDbUtil.getDbWebId(application, object : WebsDbInterface {
                    override fun isNull() {

                    }

                    override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                        FilterWithTimeDialog(getScreenWidth(), Gson().toJson(list), "webid", "webidCode", true, "${if (mFragmentTag_index == 0) "按车" else "按票"}本地代理记录筛选", true, mClickInterface = object : OnClickInterface.OnClickInterface {
                            /**
                             * s1 网点
                             * s2  start@end
                             */

                            override fun onResult(s1: String, s2: String) {
                                val mLocalAgentEvent = LocalAgentEvent()
                                mLocalAgentEvent.type = mFragmentTag_index
                                mLocalAgentEvent.webCode = s1
                                val timeList = s2.split("@")
                                if (timeList.isNotEmpty() && timeList.size == 2) {
                                    mLocalAgentEvent.startTime = timeList[0]
                                    mLocalAgentEvent.endTime = timeList[1]
                                }
                                RxBus.build().post(mLocalAgentEvent)

                            }

                        }).show(supportFragmentManager, "LocalAgentActivityFilterWithTimeDialog")
                    }

                })
            }

        })

    }
}