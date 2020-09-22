package com.mbcq.vehicleslibrary.activity.localagent


import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity

/**
 * @author: lzy
 * @time: 2020-09-22 11:17:45
 * 本地代理
 */

class LocalAgentActivity : BaseMVPActivity<LocalAgentContract.View, LocalAgentPresenter>(), LocalAgentContract.View {
    override fun getLayoutId(): Int = 0
}