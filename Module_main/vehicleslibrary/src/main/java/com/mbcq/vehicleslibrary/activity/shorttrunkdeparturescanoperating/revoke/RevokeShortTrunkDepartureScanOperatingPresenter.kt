package com.mbcq.vehicleslibrary.activity.shorttrunkdeparturescanoperating.revoke

import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-10 10:02:03 撤销干线装车扫描
 */

class RevokeShortTrunkDepartureScanOperatingPresenter : BasePresenterImpl<RevokeShortTrunkDepartureScanOperatingContract.View>(), RevokeShortTrunkDepartureScanOperatingContract.Presenter {
    override fun revokeOrder(billno: String, lableNo: String, deviceNo: String, inOneVehicleFlag: String, soundStr: String) {
        val jsonO = JSONObject()
        jsonO.put("Billno", billno)
        jsonO.put("LableNo", lableNo)
        jsonO.put("DeviceNo", deviceNo)
        jsonO.put("InOneVehicleFlag", inOneVehicleFlag)
//        jsonO.put("ScanType", 0)
//        jsonO.put("ScanTypeStr", "PDA")
        post<String>(ApiInterface.DEPARTURE_SHORT_FEEDER_DEPARTURE_SCAN_INFO_REVOKE_POST, getRequestBody(jsonO), object : CallBacks {
            override fun onResult(result: String) {
                mView?.revokeOrderS(billno)

            }

        })
    }

}