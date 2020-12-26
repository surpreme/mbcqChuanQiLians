package com.mbcq.vehicleslibrary.fragment.shortfeeder

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.vehicleslibrary.activity.loadingvehicles.LoadingVehiclesBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-12 13:08：55
 */

class ShortFeederPresenter : BasePresenterImpl<ShortFeederContract.View>(), ShortFeederContract.Presenter {
    /**
     * {"code":0,"msg":"","count":10,"data":[
    {
    "id": 1,
    "vehicleState": 1,
    "vehicleStateStr": "发货",
    "companyId": 2001,
    "ecompanyId": 2001,
    "inoneVehicleFlag": "GX1003-20200619-001",
    "contractNo": "GX1003-20200619-001",
    "sendOpeMan": "汕头",
    "arriOpeMan": "义乌后湖",
    "webidCode": 1003,
    "webidCodeStr": "汕头",
    "ewebidCode": 1004,
    "ewebidCodeStr": "潮州彩塘",
    "vehicleNo": "浙A90833",
    "chauffer": "2",
    "chaufferMb": "3",
    "transneed": 1,
    "transneedStr": "零担",
    "sendDate": "2020-06-19T13:06:53",
    "arrivedDate": "1900-01-01T00:00:00",
    "accNow": 0.00,
    "accBack": 0.00,
    "accYk": 0.00,
    "ykCard": "",
    "ewebidCode1": 0,
    "ewebidCodeStr1": "义乌后湖",
    "accArrived1": 0.00,
    "ewebidCode2": 0,
    "ewebidCodeStr2": "请选择",
    "accArrived2": 0.00,
    "ewebidCode3": 0,
    "ewebidCodeStr3": "请选择",
    "accArrived3": 0.00,
    "accZx": 0.00,
    "accJh": 0.00,
    "accArrSum": 0.00,
    "accTansSum": 0.00,
    "accOther": 0.00,
    "vehicleInterval": "汕头-义乌后湖",
    "remark": "",
    "fromType": 0,
    "fromTypeStr": ""

    }
    ]}
    干线 发车 Trunk Departure
     */
    override fun getShortFeeder(page: Int, selWebidCode: String, startDate: String, endDate: String) {
        val mHttpParams = HttpParams()
        mHttpParams.put("page", page)
        mHttpParams.put("limit", 15)
        mHttpParams.put("selWebidCode", selWebidCode)
        mHttpParams.put("startDate", startDate)
        mHttpParams.put("endDate", endDate)
        get<String>(ApiInterface.DEPARTURE_RECORD_SHORT_FEEDER_SELECT_INFO_GET, mHttpParams, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                mView?.getShortFeederS(Gson().fromJson<List<ShortFeederBean>>(obj.optString("data"), object : TypeToken<List<ShortFeederBean>>() {}.type), Gson().fromJson(obj.optString("totalRow"), ShortFeederTotalBean::class.java))
            }

        })
    }

    override fun invalidOrder(inoneVehicleFlag: String, id: Int) {
        val obj = JsonObject()
        obj.addProperty("inoneVehicleFlag", inoneVehicleFlag)
        obj.addProperty("id", id)
        post<String>(ApiInterface.DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_INVALID_INFO_POST, getRequestBody(obj), object : CallBacks {
            override fun onResult(result: String) {
                mView?.invalidOrderS()

            }

        })
    }

    fun searchBillnoShortFeeder(inoneVehicleFlag: String) {
        val params = HttpParams()
        params.put("InoneVehicleFlag", inoneVehicleFlag)
        get<String>(ApiInterface.DEPARTURE_RECORD_SHORT_FEEDER_SELECT_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    val list = Gson().fromJson<List<ShortFeederBean>>(obj.optString("data"), object : TypeToken<List<ShortFeederBean>>() {}.type)
                    if (list.isNotEmpty()) {
                        mView?.getShortFeederS(list, Gson().fromJson(obj.optString("totalRow"), ShortFeederTotalBean::class.java))

                    }
                }
            }


        })
    }

    override fun searchScanInfo(billno: String) {
        val params = HttpParams()
        params.put("billno", billno)
        get<String>(ApiInterface.DEPARTURE_RECORD_SHORT_FEEDER_SELECT_BILLNO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)

                obj.optJSONArray("data")?.let {
                    for (index in 0..it.length()){
                        if (!it.isNull(index)) {
                            val itemObj = it.getJSONObject(index)
                            searchBillnoShortFeeder(itemObj.optString("inoneVehicleFlag"))
                        }
                    }

                }
            }

        })
    }

}