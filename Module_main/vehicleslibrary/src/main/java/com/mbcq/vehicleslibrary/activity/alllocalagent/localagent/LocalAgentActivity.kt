package com.mbcq.vehicleslibrary.activity.alllocalagent.localagent


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.R
import kotlinx.android.synthetic.main.activity_local_agent.*

/**
 * @author: lzy
 * @time: 2020-09-22 11:17:45
 * 本地代理
 */
@Route(path = ARouterConstants.LocalAgentActivity)
class LocalAgentActivity : BaseMVPActivity<LocalAgentContract.View, LocalAgentPresenter>(), LocalAgentContract.View {
    override fun getLayoutId(): Int = R.layout.activity_local_agent
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        local_agent_tabLayout.addTab(local_agent_tabLayout.newTab().setText("按车(2)"))
        local_agent_tabLayout.addTab(local_agent_tabLayout.newTab().setText("按票(2)"))
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