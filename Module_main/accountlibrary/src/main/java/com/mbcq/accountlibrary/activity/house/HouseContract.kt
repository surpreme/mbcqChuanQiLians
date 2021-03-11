package com.mbcq.accountlibrary.activity.house

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

class HouseContract {
    interface View : BaseView {
        fun getMenuAuthorityS(list:List<AuthorityMenuBean>)

    }

    interface Presenter : BasePresenter<View> {
        /**
         * 获取显示item权限
         */
        fun getMenuAuthority()
    }
}