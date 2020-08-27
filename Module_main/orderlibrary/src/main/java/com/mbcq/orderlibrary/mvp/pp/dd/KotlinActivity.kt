package com.mbcq.orderlibrary.mvp.pp.dd


import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity

/**
 * @author: lzy
 * @time: 2018.08.25
 */

class KotlinActivity : BaseMVPActivity<KotlinContract.View, KotlinPresenter>(), KotlinContract.View {
    override fun getLayoutId(): Int = 0
}