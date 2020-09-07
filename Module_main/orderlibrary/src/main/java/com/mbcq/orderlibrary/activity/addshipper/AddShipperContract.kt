package com.mbcq.orderlibrary.activity.addshipper

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020.09.24
 */

class AddShipperContract {

    interface View : BaseView {
        fun getShipperInfoS(result: String)

    }

    interface Presenter : BasePresenter<View> {
        fun getShipperInfo(contactMb: String)

    }
}
