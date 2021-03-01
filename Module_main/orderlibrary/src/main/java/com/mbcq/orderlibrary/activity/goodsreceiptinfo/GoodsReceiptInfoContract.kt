package com.mbcq.orderlibrary.activity.goodsreceiptinfo

import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-27 14:40:05
 */

class GoodsReceiptInfoContract {

    interface View : BaseView {
        fun getPaymentWayS(result: String)
        fun receiptGoodsS()
        fun postImgS(url: String)

    }

    interface Presenter : BasePresenter<View> {
        fun getPaymentWay()

        /**
         * 货物签收
         *
         */
        fun receiptGoods(job: JSONObject)
        fun postImg(params: HttpParams)

    }
}
