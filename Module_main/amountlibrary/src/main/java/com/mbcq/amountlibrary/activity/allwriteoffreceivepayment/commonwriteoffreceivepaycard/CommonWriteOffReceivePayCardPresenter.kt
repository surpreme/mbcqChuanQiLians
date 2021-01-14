package com.mbcq.amountlibrary.activity.allwriteoffreceivepayment.commonwriteoffreceivepaycard

import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2021-01-14 09:02:03 应收核销凭证
 */

class CommonWriteOffReceivePayCardPresenter : BasePresenterImpl<CommonWriteOffReceivePayCardContract.View>(), CommonWriteOffReceivePayCardContract.Presenter {
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
    override fun getWriteOffType(selectType: String) {
        val param = HttpParams()
        when {
            selectType.contains("返款核销") -> {
                param.put("item", 1007)

            }
            else -> {
                param.put("limit", 1000)
            }

        }

        get<String>(ApiInterface.PAYMENTING_WRITE_OFF_TYPE_GET, param, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    if (selectType.contains("返款核销")) {
                        if (!it.isNull(0)) {
                            mView?.getWriteOffTypeS(it.optJSONObject(0))
                        }
                        return@let
                    }
                    for (index in 0..it.length()) {
                        if (!it.isNull(index)) {
                            if (selectType.contains("短驳") && selectType.contains("到付")) {
                                if (it.optJSONObject(index).optString("item") == "1012") {
                                    mView?.getWriteOffTypeS(it.optJSONObject(index))
                                    break
                                }
                            }
                            if (selectType.contains("短驳") && selectType.contains("回付")) {
                                if (it.optJSONObject(index).optString("item") == "1013") {
                                    mView?.getWriteOffTypeS(it.optJSONObject(index))
                                    break
                                }
                            }
                            if (selectType.contains("短驳") && selectType.contains("预付")) {
                                if (it.optJSONObject(index).optString("item") == "1011") {
                                    mView?.getWriteOffTypeS(it.optJSONObject(index))
                                    break
                                }
                            }


                            //*******
                            if (selectType.contains("干线") && selectType.contains("到付")) {
                                if (it.optJSONObject(index).optString("item") == "1015") {
                                    mView?.getWriteOffTypeS(it.optJSONObject(index))
                                    break
                                }
                            }
                            if (selectType.contains("干线") && selectType.contains("回付")) {
                                if (it.optJSONObject(index).optString("item") == "1016") {
                                    mView?.getWriteOffTypeS(it.optJSONObject(index))
                                    break
                                }
                            }
                            if (selectType.contains("干线") && selectType.contains("预付")) {
                                if (it.optJSONObject(index).optString("item") == "1014") {
                                    mView?.getWriteOffTypeS(it.optJSONObject(index))
                                    break
                                }
                            }

                        }
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

    override fun savePayCardInfo(jsonObj: JSONObject, selectType: String) {
        post<String>(
                when {
                    selectType.contains("短驳车费核销") -> ApiInterface.SHUTTLE_FARE_VERIFICATION_SAVE_POST
                    selectType.contains("返款核销") -> ApiInterface.REBATE_VERIFICATION_SAVE_POST
                    selectType.contains("干线车费核销") -> ApiInterface.MAIN_LINE_FARE_SAVE_POST
                    selectType.contains("月结核销") -> ApiInterface.MONTHLY_TUBERCULOSIS_SALES_SAVE_POST
                    else -> ""
                }, getRequestBody(jsonObj), object : CallBacks {
            override fun onResult(result: String) {
                mView?.savePayCardInfoS("")
            }

        })
    }
}