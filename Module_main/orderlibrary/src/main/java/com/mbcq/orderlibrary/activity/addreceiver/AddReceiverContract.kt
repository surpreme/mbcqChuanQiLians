package com.mbcq.orderlibrary.activity.addreceiver

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2018.08.25
 */

class AddReceiverContract {

    interface View : BaseView {
        fun getReceiverInfoS(result: String)

    }

    interface Presenter : BasePresenter<View> {
        fun getReceiverInfo(contactMb: String)

    }
}
