package com.mbcq.orderlibrary.activity.acceptbillingoperationreview

import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-12-28 10:25:17 改单申请财务和运营审核
 */

class AcceptBillingFixedReviewPresenter : BasePresenterImpl<AcceptBillingFixedReviewContract.View>(), AcceptBillingFixedReviewContract.Presenter {
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
                    if (!it.isNull(0)) {
                        val itemObj=it.getJSONObject(0)
                        mView?.getReviewDataS(itemObj.optString("updateContent"), itemObj.optString("updateRemark"),itemObj.optString("opeDate")+"   "+itemObj.optString("opeMan")+"   "+itemObj.optString("opeWebidCodeStr"))

                        mView?.getReviewMoreFixDataS(itemObj.optString("updateItem").split("－"),itemObj.optString("UpdateBeforeContent").split("－"),itemObj.optString("UpdateAfterContent").split("－"))

                    }

                }
            }

        })
    }

    override fun getOrderData(billno: String) {
        val params = HttpParams()
        params.put("billno", billno)
        get<String>(ApiInterface.RECORD_SELECT_ORDER_MORE_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    if (!it.isNull(0)) {
                        mView?.getOrderDataS(it.getJSONObject(0))

                    } else {
                        mView?.getOrderDataNull(billno)
                    }

                }
            }

        })
    }

    override fun postReviewData(billno: String, id: String, type: Int) {
        val jsonObj = JSONObject()
        jsonObj.put("billno", billno)
        jsonObj.put("id", id)
        //        //1 运营审核 2财务审核
        post<String>(if (type == 1) ApiInterface.ACCEPT_BILLING_RECORDING_INFO_OPERATION_REVIEW_POST else ApiInterface.ACCEPT_BILLING_RECORDING_INFO_FINANCE_REVIEW_POST, getRequestBody(jsonObj), object : CallBacks {
            override fun onResult(result: String) {
                mView?.postReviewDataS(result)
            }

        })
    }

}