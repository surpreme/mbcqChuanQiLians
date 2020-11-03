package com.mbcq.orderlibrary.activity.choicereceiver

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.orderlibrary.activity.choiceshipper.ChoiceShipperBean

/**
 * @author: lzy
 * @time: 2020-11-02 15:08:32 选择收货人
 */

class ChoiceReceiverContract {

    interface View : BaseView {
        fun  getInfoS(list: List<ChoiceReceiverBean>)

    }

    interface Presenter : BasePresenter<View> {
        fun  getInfo()

    }
}
