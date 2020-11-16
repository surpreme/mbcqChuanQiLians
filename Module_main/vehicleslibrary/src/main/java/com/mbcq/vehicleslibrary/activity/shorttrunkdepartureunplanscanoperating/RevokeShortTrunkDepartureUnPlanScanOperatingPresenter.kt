package com.mbcq.vehicleslibrary.activity.shorttrunkdepartureunplanscanoperating

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.vehicleslibrary.activity.shorttrunkdeparturescanoperating.ShortTrunkDepartureScanOperatingBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-13 13:40:46 短驳扫描无计划装车
 */

class RevokeShortTrunkDepartureUnPlanScanOperatingPresenter : BasePresenterImpl<RevokeShortTrunkDepartureUnPlanScanOperatingContract.View>(), RevokeShortTrunkDepartureUnPlanScanOperatingContract.Presenter {
    override fun getCarInfo(inoneVehicleFlag: String) {
        val params = HttpParams()
        params.put("InoneVehicleFlag", inoneVehicleFlag)
        get<String>(ApiInterface.DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_SELECT_LOCAL_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    if (!it.isNull(1)) {
                        val dataObj = it.optJSONObject(1)
                        mView?.getCarInfoS(Gson().fromJson(dataObj.optString("data"), object : TypeToken<List<RevokeShortTrunkDepartureUnPlanScanOperatingBean>>() {}.type))
                    }
                }
            }

        })
    }

    override fun getWillByInfo(billno: String, resultBillno: String) {
        val params = HttpParams()
        params.put("Billno", billno)
        get<String>(ApiInterface.RECORD_SELECT_ORDER_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    if (!it.isNull(0)) {
                        mView?.getWillByInfoS(it.getJSONObject(0), resultBillno)

                    } else {
                        mView?.getWillByInfoNull()
                    }

                }
            }

        })
    }

    /**
     * {
      "CompanyId": 2001,
      "Billno": "10030002768",
      "LableNo": "100300027680006",
      "EwebidCode": 1004,
      "EwebidCodeStr": "潮州彩塘",
      "DeviceNo": "123",
      "InOneVehicleFlag": "DB1003-20201113-004",  
      "ScanType": 0,
      "ScanTypeStr": "PDA",
      "IsScan": 2,
      "ScanPercentage": 12 扫描率
    }
     */
    override fun scanOrder(billno: String, lableNo: String, deviceNo: String, inOneVehicleFlag: String, soundStr: String, ewebidCode: String, ewebidCodeStr: String, scanPercentage: String) {
        val jsonO = JSONObject()
        jsonO.put("CompanyId", "2001")
        jsonO.put("Billno", billno)
        jsonO.put("LableNo", lableNo)
        jsonO.put("DeviceNo", deviceNo)
        jsonO.put("ewebidCode", ewebidCode)
        jsonO.put("ewebidCodeStr", ewebidCodeStr)
        jsonO.put("InOneVehicleFlag", inOneVehicleFlag)
        jsonO.put("ScanPercentage", scanPercentage)
        jsonO.put("IsScan", 2)
        jsonO.put("ScanType", 0)
        jsonO.put("ScanTypeStr", "PDA")
        post<String>(ApiInterface.DEPARTURE_SHORT_FEEDER_DEPARTURE_SCAN_INFO_POST, getRequestBody(jsonO), object : CallBacks {
            override fun onResult(result: String) {
                mView?.scanOrderS(billno, soundStr)

            }

        })
    }

    override fun saveScanPost(id: Int, inoneVehicleFlag: String) {
        val postBody = JsonObject()
        postBody.addProperty("id", id)
        postBody.addProperty("InoneVehicleFlag", inoneVehicleFlag)
        post<String>(ApiInterface.DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_COMPLETE_LOCAL_INFO_POST, getRequestBody(postBody), object : CallBacks {
            override fun onResult(result: String) {
                mView?.saveScanPostS("")

            }

        })
    }
}