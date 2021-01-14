package com.mbcq.amountlibrary.activity.monthlytuberculosissalespaycard

import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-01-16 11:06:31 月结核销 核销
 */

class MonthlyTuberculosisSalesPayCardPresenter : BasePresenterImpl<MonthlyTuberculosisSalesPayCardContract.View>(), MonthlyTuberculosisSalesPayCardContract.Presenter {
    /**
     *
    {"code":0,"msg":"","count":1,"data":[
    {
    "proCode": 0,
    "serialNo": "S100300000009"
    }
    ]}
     */
    override fun getSerialNumber() {
        get<String>(ApiInterface.PAYMENTING_WRITE_OFF_SERIAL_NUMBER_GET + "?=", null, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    if (!it.isNull(0)) {
                        val itemObj = it.optJSONObject(0)
                        mView?.getSerialNumberS(itemObj.optString("serialNo"))

                    }
                }

            }

        })
    }
    /**
     * {"code":0,"msg":"","count":1,"data":[
    {
    "id": 7,
    "companyId": 2001,
    "item": 1000,
    "itemStr": "现付核销",
    "oneClassItem": 10,
    "oneClassItemStr": "主营业务收入",
    "twoClassItem": 1001,
    "twoClassItemStr": "现付",
    "opeMan": "汕头",
    "recordDate": "2020-11-23T15:13:57"
    }
    ]}
     */
    override fun getWriteOffType() {
        val param = HttpParams()
        param.put("item", 1001)
        get<String>(ApiInterface.PAYMENTING_WRITE_OFF_TYPE_GET, param, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    if (!it.isNull(0)) {
                        mView?.getWriteOffTypeS(it.optJSONObject(0))
                    }
                }
            }

        })
    }

    override fun getPaymentAway() {
        val param = HttpParams()
        param.put("type", 26)
        get<String>(ApiInterface.ALLTYPE_SELECT_GET, param, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getPaymentAwayS(obj.optString("data"))
                }
            }

        })
    }

    /**
     * {"code":0,"msg":"","count":1,"data":[
    {
    "proCode": 0,
    "serialNo": "I100300000021"
    }
    ]}
     */

    override fun getDocumentNo() {
        get<String>(ApiInterface.PAYMENTING_WRITE_OFF_DOCUMENT_NO_GET + "?=", null, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    if (!it.isNull(0)) {
                        val itemObj = it.optJSONObject(0)
                        mView?.getDocumentNoS(itemObj.optString("serialNo"))

                    }
                }

            }

        })
    }

    override fun savePayCardInfo(jsonObj: JSONObject) {
        post<String>(ApiInterface.MONTHLY_TUBERCULOSIS_SALES_SAVE_POST, getRequestBody(jsonObj), object : CallBacks {
            override fun onResult(result: String) {
                mView?.savePayCardInfoS("")
            }

        })
    }
}