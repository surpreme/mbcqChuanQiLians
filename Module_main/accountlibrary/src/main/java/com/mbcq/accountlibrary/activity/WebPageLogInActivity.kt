package com.mbcq.accountlibrary.activity


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.accountlibrary.R
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import kotlinx.android.synthetic.main.activity_web_page_log_in.*

/**
 * @author: lzy
 * @time: 2020-11-19 16:14:00 网页登录
 */

@Route(path = ARouterConstants.WebPageLogInActivity)
class WebPageLogInActivity : BaseMVPActivity<WebPageLogInContract.View, WebPageLogInPresenter>(), WebPageLogInContract.View {
    override fun getLayoutId(): Int = R.layout.activity_web_page_log_in
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.white)
    }

    override fun onClick() {
        super.onClick()
        webpage_login_toolbar.setBackButtonOnClickListener(object :SingleClick(){
            override fun onSingleClick(v: View?) {
                onBackPressed()

            }

        })
    }
}