package com.mbcq.accountlibrary.activity.housesearch

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-012-08 16:04:45 主页搜索页面
 */

class HouseSearchContract {

    interface View : BaseView {
        fun getWaybillDetailS(data: JSONObject)
        fun getWaybillDetailNull()
    }

    interface Presenter : BasePresenter<View> {
        fun getWaybillDetail(billNo: String)

    }
}
