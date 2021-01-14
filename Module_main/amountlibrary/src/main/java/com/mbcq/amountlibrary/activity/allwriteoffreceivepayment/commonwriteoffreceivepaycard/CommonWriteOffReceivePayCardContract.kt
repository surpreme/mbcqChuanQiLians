package com.mbcq.amountlibrary.activity.allwriteoffreceivepayment.commonwriteoffreceivepaycard

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2021-01-14 09:02:03 应收核销凭证
 */

class CommonWriteOffReceivePayCardContract {

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
        fun getWriteOffType(selectType: String)
        fun getPaymentAway()

        /**
         * 凭证编号
         */
        fun getDocumentNo()
        fun savePayCardInfo(jsonObj: JSONObject, selectType: String)

    }
}
