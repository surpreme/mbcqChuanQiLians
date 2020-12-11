package com.mbcq.vehicleslibrary.activity.departuretrunkdeparturescanoperating.revoke

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.system.TimeUtils
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.vehicleslibrary.activity.shorttrunkdeparturescanoperating.revoke.RevokeShortTrunkDepartureScanOperatingBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-19 08:31:06 干线撤销扫描
 */

class RevokeDepartureTrunkDepartureScanOperatingPresenter : BasePresenterImpl<RevokeDepartureTrunkDepartureScanOperatingContract.View>(), RevokeDepartureTrunkDepartureScanOperatingContract.Presenter {
    override fun revokeOrder(billno: String, lableNo: String, deviceNo: String, inOneVehicleFlag: String, soundStr: String,scanPercentage:String) {
        val jsonO = JSONObject()
        jsonO.put("Billno", billno)
        jsonO.put("LableNo", lableNo)
        jsonO.put("DeviceNo", deviceNo)
        jsonO.put("InOneVehicleFlag", inOneVehicleFlag)
        jsonO.put("scanPercentage", scanPercentage)
        jsonO.put("RecordDate", TimeUtils.getCurrTime2())//记录日期
        jsonO.put("isScanDet", 1)
        jsonO.put("IsScan", 1)
        mView?.getContext()?.let {
            jsonO.put("opeMan", UserInformationUtil.getUserName(it))//操作人
        }
        post<String>(ApiInterface.DEPARTURE_DEPARTURE_FEEDER_DEPARTURE_SCAN_INFO_REVOKE_POST, getRequestBody(jsonO), object : CallBacks {
            override fun onResult(result: String) {
                mView?.revokeOrderS(billno,lableNo)

            }

        })
    }
    override fun getCarInfo(inoneVehicleFlag: String) {
        val params = HttpParams()
        params.put("InoneVehicleFlag", inoneVehicleFlag)
        get<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SELECT_LOCAL_INFO_POST, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    if (!it.isNull(1)) {
                        val dataObj = it.optJSONObject(1)
                        mView?.getCarInfoS(Gson().fromJson(dataObj.optString("data"), object : TypeToken<List<RevokeDepartureTrunkDepartureScanOperatingBean>>() {}.type))
                    }
                }
            }

        })
    }
}