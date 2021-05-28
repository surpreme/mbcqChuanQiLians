package com.mbcq.orderlibrary.fragment.waybillinformation

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-10-20 13:13:03 运单信息
 */

class WaybillInformationContract {

    interface View : BaseView {
        fun getCostInformationS(result: String)
        fun getOrderBigInfoS(result: String)

    }

    interface Presenter : BasePresenter<View> {
        /**
         * 获取（所有配置信息）费用信息 显示的集合
         */
        fun getCostInformation(webidCode: String)
        fun getOrderBigInfo(billno:String)
    }
}
