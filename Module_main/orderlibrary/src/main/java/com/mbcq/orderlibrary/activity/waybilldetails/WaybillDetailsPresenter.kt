package com.mbcq.orderlibrary.activity.waybilldetails

import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-10-20 10:13:00
 */

class WaybillDetailsPresenter : BasePresenterImpl<WaybillDetailsContract.View>(), WaybillDetailsContract.Presenter {
    override fun getWaybillDetail(billNo: String) {
        val params = HttpParams()
        params.put("Billno", billNo)
        get<String>(ApiInterface.RECORD_SELECT_ORDER_MORE_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    if (!it.isNull(0)) {
                        mView?.getWaybillDetailS(it.getJSONObject(0))

                    } else {
                        mView?.getWaybillDetailNull()
                    }

                }
            }

        })
    }

}