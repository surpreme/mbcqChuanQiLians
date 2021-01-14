package com.mbcq.orderlibrary.activity.goodsreceipthouseinfo

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-01-08 16:35:12 货物签收详情
 */

class GoodsReceiptHouseInfoContract {

    interface View : BaseView {
        fun getWaybillDetailS(data: JSONObject)
        fun getWaybillDetailNull()
    }

    interface Presenter : BasePresenter<View> {
        fun getWaybillDetail(billNo: String)

    }
}
