package com.mbcq.orderlibrary.fragment.signrecord

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-10-10 09:33:12 签收记录
 */

class SignRecordPresenter : BasePresenterImpl<SignRecordContract.View>(), SignRecordContract.Presenter {
    /**
     * {"code":0,"msg":"","count":155,"data":[
    {
    "id": 23,
    "billno": "10030000130",
    "fetComId": 2001,
    "fetchWebidCode": 1001,
    "fetchWebidCodeStr": "义乌后湖",
    "fetchDate": "2020-06-22T17:00:06",
    "fetchMan": "义乌后湖",
    "fetManIdCarType": 1,
    "fetManIdCarTypeStr": "身份证",
    "fetchIdCard": "222222222222",
    "fetchAgent": "3333333333",
    "fetAgeIdCarType": 1,
    "fetAgeIdCarTypeStr": "身份证",
    "fetAgeIdCard": "4444444444",
    "fetchType": 3,
    "fetchTypeStr": "送货签收",
    "payType": 0,
    "payTypeStr": "现金",
    "fetchCon": 0,
    "fetchConStr": "正常",
    "fetchRemark": "5555555555",
    "opeMan": "义乌后湖",
    "recordDate": "2020-06-22T17:00:06",
    "fromType": 0,
    "fromTypeStr": ""
    }
    ]}
     */
    override fun getPage(page: Int, selWebidCode: String, startDate: String, endDate: String) {
        val params = HttpParams()
        params.put("Page", page)
        params.put("Limit", 15)
        params.put("StartDate", startDate)
        params.put("EndDate", endDate)
        mView?.getContext()?.let {
            params.put("SelWebidCode", selWebidCode)

        }
        get<String>(ApiInterface.SIGN_FOR_RECORD_SELECT_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    val totalD = Gson().fromJson<SignRecordToTalBean>(obj.optString("totalRow"), SignRecordToTalBean::class.java)
                    mView?.getPageS(Gson().fromJson(obj.optString("data"), object : TypeToken<List<SignRecordBean>>() {}.type), totalD)

                }

            }

        })
    }

    override fun cancel(data: String, position: Int) {
        post<String>(ApiInterface.SIGN_FOR_RECORD_CANCEL_POST, getRequestBody(data), object : CallBacks {
            override fun onResult(result: String) {
                mView?.cancelS(position)

            }

        })
    }

}