package com.mbcq.orderlibrary.activity.deliverysomething

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-29 08:35:06  送货
 */

class DeliverySomeThingPresenter : BasePresenterImpl<DeliverySomeThingContract.View>(), DeliverySomeThingContract.Presenter {
    /**
     * {"code":0,"msg":"","count":3,"data":[
    {
    "id": 3,
    "companyId": 2001,
    "senWebCod": 1001,
    "senWebCodStr": "义乌后湖",
    "sendInOneFlag": "SHSM1001-20200622-001",
    "sendVehicleNo": "",
    "sendMan": "",
    "sendManTel": "",
    "sendManMb": "",
    "sendDate": "2020-06-22T08:52:15",
    "sendRemark": "",
    "sendTimes": 0,
    "opeMan": "义乌后湖"
    }
     */
    override fun getDeliveryS(page: Int) {
        val httpParams = HttpParams()
        httpParams.put("Page", page)
        httpParams.put("Limit", 15)
        get<String>(ApiInterface.DELIVERY_SOMETHING_SELECT_INFO_GET, httpParams, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data").let {
                    mView?.getDeliverySS(Gson().fromJson(obj.optString("data"), object : TypeToken<List<DeliverySomeThingBean>>() {}.type))
                }


            }

        })
    }

}