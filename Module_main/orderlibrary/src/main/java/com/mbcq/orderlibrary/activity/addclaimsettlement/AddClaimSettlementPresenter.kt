package com.mbcq.orderlibrary.activity.addclaimsettlement

import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-02 17:56:13 添加 理赔
 */

class AddClaimSettlementPresenter : BasePresenterImpl<AddClaimSettlementContract.View>(), AddClaimSettlementContract.Presenter {
    override fun getOrderInfo(billno: String) {
        val params = HttpParams()
        params.put("Billno", billno)
        get<String>(ApiInterface.RECORD_SELECT_ORDER_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    if (!it.isNull(0)) {
                        mView?.getOrderInfoS(it.getJSONObject(0))

                    } else {
                        mView?.getOrderInfoNull()
                    }

                }
            }

        })
    }

}