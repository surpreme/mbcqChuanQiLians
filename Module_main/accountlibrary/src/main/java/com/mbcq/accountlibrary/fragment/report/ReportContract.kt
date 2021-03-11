package com.mbcq.accountlibrary.fragment.report

import com.mbcq.accountlibrary.fragment.iconadapter.IconViewBean
import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

class ReportContract {
    interface View : BaseView {
        fun getMenuAuthorityS(list:List<IconViewBean>)

    }

    interface Presenter : BasePresenter<View> {
        /**
         * 获取显示item权限
         */
        fun getMenuAuthority()
    }
}