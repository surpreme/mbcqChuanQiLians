package com.mbcq.accountlibrary.activity.login

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONArray

class LogInContract {
    /**
     * 异步回调的接口
     */
    interface View : BaseView {
        fun loInS(result: LogInSuccessBean)
        fun getWebAreaIdS(result: String)


    }

    /**
     * 调取异步的接口
     */
    interface Presenter : BasePresenter<View> {
        fun logIn(userName: String, pw: String)
        fun getWebAreaId()

    }
}