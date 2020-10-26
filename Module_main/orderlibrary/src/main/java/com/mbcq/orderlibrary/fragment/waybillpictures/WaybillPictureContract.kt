package com.mbcq.orderlibrary.fragment.waybillpictures

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-10-26 09:32:02
 */

class WaybillPictureContract {

    interface View : BaseView {

    }

    interface Presenter : BasePresenter<View> {

        fun getPictures(billno: String)
    }
}
