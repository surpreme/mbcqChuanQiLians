package com.mbcq.orderlibrary.activity.waybillrecord

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-09-10 10:07:09
 */

class WaybillRecordContract {

    interface View : BaseView {
        fun getPageDataS(list: List<WaybillRecordBean>, totalS: String)
        fun deleteWayBillS(mIndex:Int)

    }

    interface Presenter : BasePresenter<View> {
        fun getPageData(page: Int, commonStr: String, startDate: String, endDate: String)

        //删除
        fun deleteWayBill(billno: String,mIndex:Int)


    }
}
