package com.mbcq.vehicleslibrary.activity.departuretrunkdeparturescanoperating.revoke

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.system.TimeUtils
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-19 08:31:06 干线撤销扫描
 */

class RevokeDepartureTrunkDepartureScanOperatingPresenter : BasePresenterImpl<RevokeDepartureTrunkDepartureScanOperatingContract.View>(), RevokeDepartureTrunkDepartureScanOperatingContract.Presenter {
    override fun revokeOrder(billno: String, lableNo: String, deviceNo: String, inOneVehicleFlag: String, soundStr: String, scanPercentage: String) {
        val jsonO = JSONObject()
        jsonO.put("Billno", billno)
        jsonO.put("LableNo", lableNo)
        jsonO.put("DeviceNo", deviceNo)
        jsonO.put("InOneVehicleFlag", inOneVehicleFlag)
        jsonO.put("scanPercentage", scanPercentage)
        jsonO.put("RecordDate", TimeUtils.getCurrTime2())//记录日期
        jsonO.put("isScanDet", 1)
        jsonO.put("IsScan", 1)
        jsonO.put("ScanType", 0)
        jsonO.put("ScanTypeStr", "PDA")
        mView?.getContext()?.let {
            jsonO.put("opeMan", UserInformationUtil.getUserName(it))//操作人
        }
        post<String>(ApiInterface.DEPARTURE_DEPARTURE_FEEDER_DEPARTURE_SCAN_INFO_REVOKE_POST, getRequestBody(jsonO), object : CallBacks {
            override fun onResult(result: String) {
                mView?.revokeOrderS(billno, lableNo)

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

    /**
    {"code":0,"msg":"","count":1,"data":[
    {
    "id": 10708,
    "billno": "10030004857",
    "lableNo": "100300048570001",
    "webidCode": 1003,
    "webidCodeStr": "汕头",
    "scanOpeType": 0,
    "scanOpeTypeStr": "短驳装车",
    "deviceNo": "92:FD:7D:F0:1A:6D",
    "content": "PDA货物从汕头出发，下一站是，操作员是lzy",
    "inOneVehicleFlag": "DB1003-20201211-003",
    "scanType": 0,
    "scanTypeStr": "PDA",
    "opeMan": "lzy",
    "recordDate": "2020-12-11T11:12:03"
    }
    ]}
     */
    override fun getScanData(billno: String,lableNo: String, inOneVehicleFlag: String, scanOpeType: Int, mXRevokeDepartureTrunkDepartureScanOperatingBean: RevokeDepartureTrunkDepartureScanOperatingBean) {
        val params = HttpParams()
        params.put("billno", billno)
        params.put("inOneVehicleFlag", inOneVehicleFlag)
        params.put("limit", 9999)
        /**
         * @scanOpeType 操作类型
         * 0 短驳装车
         * 1 干线装车
         * 2 短驳到车
         * 3 干线到车
         * -1 查询所有扫描信息
         */
        params.put("scanOpeType", scanOpeType)
        get<String>(ApiInterface.SHORT_TRUNK_DEPARTURE_SCAN_OPERATING_MORE_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val listAry = obj.optJSONArray("data")
                listAry?.let {
                    val mShowList = arrayListOf<Long>()
                    for (itemIndex in 0 until it.length()) {
                        /**
                         * 如果不是异常数据 而且是本车的条码
                         */
                        if (listAry.getJSONObject(itemIndex).optInt("scanType") != 2) {
                            if (inOneVehicleFlag.contains(listAry.getJSONObject(itemIndex).optString("inOneVehicleFlag")))
                                mShowList.add(listAry.getJSONObject(itemIndex).optLong("lableNo"))
                        }

                    }
                    if (mShowList.isNotEmpty())
                        mView?.getScanDataS(mShowList,lableNo,mXRevokeDepartureTrunkDepartureScanOperatingBean)
                }

            }

        })
    }
}