package com.mbcq.orderlibrary.fragment.fixedwaybill

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-12-29 14:03:13 改单记录
 */

class FixedWaybillFragmentPresenter : BasePresenterImpl<FixedWaybillFragmentContract.View>(), FixedWaybillFragmentContract.Presenter {
    /**
     * "updateItem": "订单号－付货方式－发货人手机号－",
     *   "UpdateBeforeContent": "123－客户自提－13916742298－",
     *"UpdateAfterContent": "1234－自提－13916742299－"
     */
    override fun getReviewData(billno: String) {
        val params = HttpParams()
        params.put("billno", billno)
        get<String>(ApiInterface.ACCEPT_BILLING_RECORDING_INFO_ORDER_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {

                    val list = Gson().fromJson<List<FixedWaybillListBean>>(obj.optString("data"), object : TypeToken<List<FixedWaybillListBean>>() {}.type)
                    mView?.getReviewDataS(list)
                }
            }

        })
    }
}