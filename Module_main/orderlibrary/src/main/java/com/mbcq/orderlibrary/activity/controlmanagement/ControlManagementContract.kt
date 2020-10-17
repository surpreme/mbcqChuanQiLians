package com.mbcq.orderlibrary.activity.controlmanagement

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.orderlibrary.activity.waybillrecord.WaybillRecordBean

/**
 * @author: lzy
 * @time: 2020-10-16 13:10:06 控货管理
 */

class ControlManagementContract {

    interface View : BaseView {
        fun getPageDataS(list: List<ControlManagementBean>, totalS: String)

    }

    interface Presenter : BasePresenter<View> {
        fun getPageData(page: Int, selWebidCode: String, startDate: String, endDate: String)

    }
}
