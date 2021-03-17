package com.mbcq.vehicleslibrary.fragment.arrivaltrunkdeparture

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.vehicleslibrary.fragment.trunkdeparture.TrunkDepartureBean
import org.json.JSONArray
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-21 10:40:36
 */

class ArrivalTrunkDeparturePresenter : BasePresenterImpl<ArrivalTrunkDepartureContract.View>(), ArrivalTrunkDepartureContract.Presenter {
    /**
     * {"code":0,"msg":"","count":2,"data":[
    {
    "vehicleNo": "浙G12374",
    "inoneVehicleFlag": "GX1001-20200921-004",
    "sendDate": "2020-09-21T16:41:34",
    "webidCode": 1001,
    "ewebidCode": 1003,
    "chauffer": "张凯",
    "chaufferMb": "16276665366",
    "sendOpeMan": "义乌后湖",
    "arriOpeMan": "",
    "contractNo": "GX1001-20200921-004",
    "webidCode1": 1001,
    "ewebidCode1": 1003,
    "companyId": 2001,
    "vehicleInterval": "义乌后湖-汕头"
    },
    {
    "vehicleNo": "沪C38293",
    "inoneVehicleFlag": "GX1001-20200921-003",
    "sendDate": "2020-09-21T15:32:29",
    "webidCode": 1001,
    "ewebidCode": 1003,
    "chauffer": "张三",
    "chaufferMb": "16677373336",
    "sendOpeMan": "义乌后湖",
    "arriOpeMan": "",
    "contractNo": "GX1001-20200921-003",
    "webidCode1": 1001,
    "ewebidCode1": 1003,
    "companyId": 2001,
    "vehicleInterval": "义乌后湖-汕头"
    }
    ]}
     * @1在途
     * @2 已到车
     */

    override fun getArrivalCar(page: Int, selEwebidCode: String, startDate: String, endDate: String) {
        val mHttpParams = HttpParams()
        mHttpParams.put("selEwebidCode", selEwebidCode)
        mHttpParams.put("page", 1)
        mHttpParams.put("limit", 15)
//        mHttpParams.put("SelWebidCode", selEwebidCode)
        mHttpParams.put("startDate", startDate)
        mHttpParams.put("endDate", endDate)
        mHttpParams.put("vehicleStateStr", "1,2,3")
        get<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SELECT_OVERRING_LOCAL_INFO_GET, mHttpParams, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                mView?.getPageS(Gson().fromJson<List<TrunkDepartureBean>>(obj.optString("data"), object : TypeToken<List<TrunkDepartureBean>>() {}.type), obj.optJSONObject("totalRow").optInt("rowCou"))
            }

        })
    }

    override fun confirmCar(data: TrunkDepartureBean, position: Int) {
        val jsonObj = JSONObject()
        jsonObj.put("id", data.id)
        jsonObj.put("inoneVehicleFlag", data.inoneVehicleFlag)
        val jsonArray = JSONArray()
        val itemObj = JSONObject()
        itemObj.put("id", data.id)
        itemObj.put("inoneVehicleFlag", data.inoneVehicleFlag)
        jsonArray.put(itemObj)
        jsonObj.put("GxVehicleDetLst", jsonArray)
        post<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_ARRIVAL_CONFIRM_LOCAL_INFO_POST, getRequestBody(jsonObj), object : CallBacks {
            override fun onResult(result: String) {
                data.vehicleState = 2
                data.vehicleStateStr = "到货"
                mView?.confirmCarS(data, position)
//                mView?.confirmCarS(position)
            }

        })
    }

    override fun canCelCar(data: TrunkDepartureBean, position: Int) {
        post<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_ARRIVAL_CANCEL_LOCAL_INFO_POST, getRequestBody(Gson().toJson(data)), object : CallBacks {
            override fun onResult(result: String) {
                data.vehicleState = 1
                data.vehicleStateStr = "发货"
                mView?.canCelCarS(data, position)
            }

        })
    }

    override fun searchInoneVehicleFlagTrunkDepature(inoneVehicleFlag: String) {
        val params = HttpParams()
        params.put("InoneVehicleFlag", inoneVehicleFlag)
        get<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SELECT_OVERRING_LOCAL_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getPageS(Gson().fromJson<List<TrunkDepartureBean>>(obj.optString("data"), object : TypeToken<List<TrunkDepartureBean>>() {}.type), -1)
                }
            }


        })
    }

    override fun searchScanInfo(billno: String) {
        val params = HttpParams()
        params.put("billno", billno)
        get<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SELECT_BILLNO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    for (index in 0..it.length()) {
                        if (!it.isNull(index)) {
                            val itemObj = it.getJSONObject(index)
                            searchInoneVehicleFlagTrunkDepature(itemObj.optString("inoneVehicleFlag"))
                        }
                    }

                }
            }

        })
    }
}