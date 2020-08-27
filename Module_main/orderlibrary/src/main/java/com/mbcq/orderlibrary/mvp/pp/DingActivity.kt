package com.mbcq.orderlibrary.mvp.pp


import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity

/**
 * @author: lzy
 * @time: 2018.08.25
 */

class DingActivity : BaseMVPActivity<DingContract.View, DingPresenter>(), DingContract.View {
    override fun getLayoutId(): Int = 0
}