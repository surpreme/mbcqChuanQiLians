package com.mbcq.vehicleslibrary.fragment.localagentshortfeeder

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

/**
 * @author: lzy
 * @time: 2020-09-22 13:06
 */

class LocalGentByCarPresenter : BasePresenterImpl<LocalGentByCarContract.View>(), LocalGentByCarContract.Presenter {
    /**
     * {"code":0,"msg":"","count":8,"data":[
    {
    "id": 27,
    "companyId": 2001,
    "webidCode": 1003,
    "webidCodeStr": "汕头",
    "agentBillno": "BDDL1003-20200624-002",
    "agentDate": "2020-06-24T13:54:56",
    "vehcileNo": "1",
    "vehicleNoTmp": "2",
    "vehicleType": "3",
    "chauffer": "4",
    "chaufferTel": "5",
    "agentAccSend": 0.00,
    "agentAccNow": 1980.00,
    "agentAccFetch": 830.00,
    "agentAccBack": 0.00,
    "agentAccMonth": 0.00,
    "agentAccTotal": 2810.00,
    "remark": "",
    "opeMan": "汕头",
    "agentType": 1,
    "agentTypeStr": "本地代理"
    }
    ]}
     */
    override fun getPage(page: Int,selEwebidCode: String, startDate: String, endDate: String) {
        val params = HttpParams()
        params.put("Page", page)
        params.put("Limit", 15)
        params.put("AgentType", 1)
        params.put("SelWebidCode", selEwebidCode)
        params.put("startDate", startDate)
        params.put("endDate", endDate)
        get<String>(ApiInterface.LOCAL_AGENT_RECORD_SELECT_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val json = JSONTokener(obj.optString("data")).nextValue()
                if (json is JSONArray) {
                    val list = Gson().fromJson<List<LocalGentByCarBean>>(obj.optString("data"), object : TypeToken<List<LocalGentByCarBean>>() {}.type)
                    list?.let {
                        mView?.getPageS(it)
                    }
                }


            }

        })

    }

    override fun cancel(s: LocalGentByCarBean, position: Int) {
        post<String>(ApiInterface.LOCAL_AGENT_CANCEL_VEHICLE_POST, getRequestBody(Gson().toJson(s)), object : CallBacks {
            override fun onResult(result: String) {
                mView?.cancelS(position)

            }

        })
    }

}