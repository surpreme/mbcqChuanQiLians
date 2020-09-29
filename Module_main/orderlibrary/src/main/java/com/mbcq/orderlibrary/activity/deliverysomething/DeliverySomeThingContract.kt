package com.mbcq.orderlibrary.activity.deliverysomething

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-09-29 08:35:06  送货
 */

class DeliverySomeThingContract {

    interface View : BaseView {
        fun getDeliverySS(list: List<DeliverySomeThingBean>)
    }

    interface Presenter : BasePresenter<View> {

        fun getDeliveryS(page: Int)
    }
}
