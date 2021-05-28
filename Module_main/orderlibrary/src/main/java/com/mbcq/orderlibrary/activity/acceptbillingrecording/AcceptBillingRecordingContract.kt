package com.mbcq.orderlibrary.activity.acceptbillingrecording

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-10-17 09:58:12  改单申请 记录
 */

class AcceptBillingRecordingContract {

    interface View : BaseView {
        fun getPageS(list: List<AcceptBillingRecordingBean>)
        fun searchOrderS(list: List<AcceptBillingRecordingBean>)
        fun rejectOrderS(result: String, position: Int)

    }

    interface Presenter : BasePresenter<View> {
        fun getPage(page: Int, selWebidCode: String, startDate: String, endDate: String, type: String)

        fun rejectOrder(billno: String, id: String, position: Int)
        fun searchOrder(billno: String)
    }
}
