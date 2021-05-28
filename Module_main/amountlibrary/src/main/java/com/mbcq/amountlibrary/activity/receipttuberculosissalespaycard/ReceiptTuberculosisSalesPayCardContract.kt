package com.mbcq.amountlibrary.activity.receipttuberculosissalespaycard

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2021-01-13 10:10:00 回单付核销详情
 */

class ReceiptTuberculosisSalesPayCardContract {

    interface View : BaseView {
        fun getSerialNumberS(result: String)
        fun getWriteOffTypeS(data: JSONObject)
        fun getPaymentAwayS(result: String)
        fun getDocumentNoS(result: String)
        fun savePayCardInfoS(result: String)
    }

    interface Presenter : BasePresenter<View> {
        /**
         * 获取流水号
         */
        fun getSerialNumber()
        fun getWriteOffType()
        fun getPaymentAway()

        /**
         * 凭证编号
         */
        fun getDocumentNo()
        fun savePayCardInfo(jsonObj: JSONObject)
    }
}
