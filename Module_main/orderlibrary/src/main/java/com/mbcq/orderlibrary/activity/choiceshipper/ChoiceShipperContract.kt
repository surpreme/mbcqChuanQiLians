package com.mbcq.orderlibrary.activity.choiceshipper

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-11-02 11:06:45  选择发货人
 */

class ChoiceShipperContract {

    interface View : BaseView {
        fun getInfoS(list: List<ChoiceShipperBean>)

        fun deleteShipperS(position:Int)
    }

    interface Presenter : BasePresenter<View> {
        fun getInfo()
        fun deleteShipper(id: String,position:Int)

    }
}
