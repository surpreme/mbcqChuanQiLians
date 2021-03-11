package com.mbcq.accountlibrary.activity.setting

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

class SettingContract {
    interface View : BaseView {
    }

    interface Presenter : BasePresenter<View> {

    }
}