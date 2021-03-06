package com.mbcq.orderlibrary.activity.receipt.receiptinfo

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-10 15:17:58 回单详情
 */

class ReceiptInformationContract {

    interface View : BaseView {
        fun getWaybillDetailS(data: JSONObject)
        fun getWaybillDetailNull()
        fun getReceiptInfoS(list:List<ReceiptInformationBean>,mStateList: List<Boolean>)
    }

    interface Presenter : BasePresenter<View> {
        fun getWayBillInfo(billno: String)
        fun getReceiptInfo(billno: String)

    }
}
