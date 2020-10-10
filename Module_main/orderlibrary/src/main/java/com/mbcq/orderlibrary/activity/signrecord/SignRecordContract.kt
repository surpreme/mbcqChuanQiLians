package com.mbcq.orderlibrary.activity.signrecord

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-10-10 09:33:12 签收记录
 */

class SignRecordContract {

    interface View : BaseView {
        fun getPageS(list: List<SignRecordBean>)
        fun cancelS( position: Int)

    }

    interface Presenter : BasePresenter<View> {

        fun getPage(page: Int,selWebidCode: String, startDate: String, endDate: String)
        fun cancel(data: String, position: Int)
    }
}
